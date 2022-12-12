package com.highradius;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.google.gson.Gson;

@WebServlet("/GetData")
public class GetData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public GetData() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		System.out.println("*".repeat(50));
		System.out.println("Calling GetData Servlet...");
		System.out.println("*".repeat(50));
		
		// Checking out for Pagination Requests
		int startParameter = 0, limitParameter = 1001, nullFlag = 0;
		try {
			startParameter = Integer.parseInt(request.getParameter("start"));
			limitParameter = Integer.parseInt(request.getParameter("limit"));
			System.out.println("Parameterised API Call");
			System.out.println("Parameters - Start: " + startParameter + " End: " + limitParameter);
		}
		catch(Exception e) {
			System.out.println("Non-Parameterised API Call");
			nullFlag = 1;
		}
 
		// JDBC Variables Information
		Connection dbConnection = null;
		PreparedStatement prStmt = null;
		ResultSet rS = null;
		
		String url = "jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=convertToNull";
		String userName = "root";
		String passWord = "root";
		String query = "";
		
		try {
			
			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Open a Connection
			dbConnection = DriverManager.getConnection(url, userName, passWord);
			if(dbConnection != null)	
				System.out.println("DB Connected!");
			
			// SQL Query String and Prepared Statement Generation
			if(nullFlag == 1){
				// query = "SELECT * FROM film;";
				query = "SELECT \r\n"
						+ "film_data.film_id,\r\n"
						+ "film_data.title,\r\n"
						+ "film_data.description,\r\n"
						+ "film_data.release_year,\r\n"
						+ "lang.name AS `language`,\r\n"
						+ "film_data.original_language_id,\r\n"
						+ "film_data.rental_duration,\r\n"
						+ "film_data.rental_rate,\r\n"
						+ "film_data.length,\r\n"
						+ "film_data.replacement_cost,\r\n"
						+ "film_data.rating,\r\n"
						+ "film_data.special_features,\r\n"
						+ "film_data.last_update,\r\n"
						+ "film_data.director\r\n"
						+ "FROM film AS film_data\r\n"
						+ "LEFT JOIN `language` AS lang ON film_data.language_id = lang.language_id;"; 
				prStmt = dbConnection.prepareStatement(query);
			}
			else {
				// query = "SELECT * FROM film LIMIT ?, ?;";
				query = "SELECT \r\n"
						+ "film_data.film_id,\r\n"
						+ "film_data.title,\r\n"
						+ "film_data.description,\r\n"
						+ "film_data.release_year,\r\n"
						+ "lang.name AS `language`,\r\n"
						+ "film_data.original_language_id,\r\n"
						+ "film_data.rental_duration,\r\n"
						+ "film_data.rental_rate,\r\n"
						+ "film_data.length,\r\n"
						+ "film_data.replacement_cost,\r\n"
						+ "film_data.rating,\r\n"
						+ "film_data.special_features,\r\n"
						+ "film_data.last_update,\r\n"
						+ "film_data.director\r\n"
						+ "FROM film AS film_data\r\n"
						+ "LEFT JOIN `language` AS lang ON film_data.language_id = lang.language_id\r\n"
						+ "LIMIT ?, ?;";
				prStmt = dbConnection.prepareStatement(query);
				prStmt.setInt(1, startParameter);
				prStmt.setInt(2, limitParameter);
			}
			
			// Execute SQL Query
			//	// System.out.println("Query Associated: " + prStmt);
			System.out.println("Executing Query...");
			rS = prStmt.executeQuery();
			ArrayList<FilmPojo> arr = new ArrayList<>();
			
			// Extract Data from Result Set
			while(rS.next()) {
				
				FilmPojo obj = new FilmPojo();
				obj.setFilm_id(rS.getInt("film_id"));
				obj.setTitle(rS.getString("title"));
				obj.setDescription(rS.getString("description"));
				obj.setRelease_year(rS.getLong("release_year"));
				obj.setLanguage(rS.getString("language"));
				obj.setOriginal_language_id(rS.getInt("original_language_id"));
				obj.setRental_duration(rS.getInt("rental_duration"));
				obj.setRental_rate(rS.getDouble("rental_rate"));
				obj.setLength(rS.getLong("length"));
				obj.setReplacement_cost(rS.getDouble("replacement_cost"));
				obj.setRating(rS.getString("rating"));
				obj.setSpecial_features(rS.getString("special_features"));
				obj.setLast_update(rS.getDate("last_update"));
				obj.setDirector(rS.getString("director"));
				
				// Adding (Appending) Line by Line Parse of SQl Query
				arr.add(obj);	
			}
			
			// Converting the Same to JSON Object
			Gson gson = new Gson();
			JSONObject responseData = new JSONObject();
			String jsonData = gson.toJson(arr);
			
			
			PrintWriter documentOut = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			responseData.put("success", true);
			// responseData.put("filmData", arr);
			responseData.put("filmData", jsonData.toString());
			responseData.put("totalCount", NumberOfEntries.noOfRows());
			documentOut.write(responseData.toString());
//			String jsonString = gson.toJson(responseData);
//			documentOut.write(jsonString);
			
			// documentOut.write(responseData.toString());
			documentOut.flush();
			System.out.println("Query Sucessful!");
			
			// Closing DB Connection
			rS.close();
			dbConnection.close();
			prStmt.close();
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		finally {
			
			System.out.println("DB Connection Closed!");
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}
}