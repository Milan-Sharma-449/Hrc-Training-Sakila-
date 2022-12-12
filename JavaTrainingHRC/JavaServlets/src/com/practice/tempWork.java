package com.practice;

import com.highradius.FilmPojo;
import java.util.*;
import java.sql.*;
import com.google.gson.Gson;

public class tempWork {
	public static void main(String[] args) {
		
		System.out.println("Inside main()");
		
		// JDBC Variables Information
		Connection dbConnection = null;
		PreparedStatement prStmt = null;
		ResultSet rS = null;
		
		String url = "jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=convertToNull";
		String userName = "root";
		String passWord = "root";
		String query = "";
		ArrayList<FilmPojo> arr = new ArrayList<>();
		
		try {
			
			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Open a Connection
			dbConnection = DriverManager.getConnection(url, userName, passWord);
			if(dbConnection != null)	
				System.out.println("DB Connected!");
			
			// SQL Query String and Prepared Statement Generation
			query = "SELECT * FROM film ? 5";
			prStmt = dbConnection.prepareStatement(query);
			prStmt.setString(1, "LIMIT");
			
			// Execute SQL Query
			System.out.println("Query: " + prStmt);
			System.out.println("Executing Query...");
			rS = prStmt.executeQuery();
			
			// Extract Data from Result Set
			while(rS.next()) {
				
				FilmPojo obj = new FilmPojo();
				obj.setFilm_id(rS.getInt("film_id"));
				obj.setTitle(rS.getString("title"));
				obj.setDescription(rS.getString("description"));
				obj.setRelease_year(rS.getLong("release_year"));
				// obj.setLanguage(rS.getString("language"));
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
			String jsonData = gson.toJson(arr);
			System.out.println(jsonData);
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
}
