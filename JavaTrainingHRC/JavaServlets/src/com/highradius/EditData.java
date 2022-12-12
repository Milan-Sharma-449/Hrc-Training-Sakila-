package com.highradius;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditData")
public class EditData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EditData() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling EditData Servlet...");
		System.out.println("*".repeat(50));
		
		// Reading in Data from Request
		int film_id = Integer.parseInt(request.getParameter("film_id"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		long releaseYear = request.getParameter("release_year") != null ? Long.parseLong(request.getParameter("release_year")) : 2021;
		String language = request.getParameter("language");
		String director = request.getParameter("director");
		String rating = request.getParameter("rating");
		String specialFeature = request.getParameter("special_features");
		
		// // Modifying Release Year
		// long releaseYear = Long.parseLong(tempReleaseYear.substring(0, 4));
		
		// // Checking Condition for language [Converted to Language ID]
		// int langauge_id = 1;
		// if(language.equals("Italian")) langauge_id = 2;
		// else if(language.equals("French")) langauge_id = 5;
		// else if(language.equals("German")) langauge_id = 6;
		// else if(language.equals("Japanese")) langauge_id = 3;
		// else if(language.equals("Mandarin")) langauge_id = 4;
		// else if(language.equals("Mongolian")) langauge_id = 7;
		// else if(language.equals("Hindi")) langauge_id = 10;
 
		// JDBC Variables Information
		Connection dbConnection = null;
		PreparedStatement prStmt = null;
		
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
			query = "UPDATE film \r\n"
					+ "SET title = ?, \r\n"
					+ "`description` = ?,\r\n"
					+ "release_year = ?,\r\n"
					+ "language_id = (SELECT language_id FROM `language` WHERE `name` = ?),\r\n"
					+ "director = ?,\r\n"
					+ "rating = ?,\r\n"
					+ "special_features = ?\r\n"
					+ "WHERE film_id = ?;";
			
			prStmt = dbConnection.prepareStatement(query);
			prStmt.setString(1, title);
			prStmt.setString(2, description);
			prStmt.setLong(3, releaseYear);
			// prStmt.setInt(4, langauge_id);
			prStmt.setString(4, language);
			prStmt.setString(5, director);
			prStmt.setString(6, rating);
			prStmt.setString(7, specialFeature);
			prStmt.setInt(8, film_id);
			
			// Execute SQL Query
			System.out.println("Query Associated: " + prStmt);
			System.out.println("Executing Query...");
			prStmt.executeUpdate();
			System.out.println("Query Sucessful! Updated 1 Row in DB.");
			
			// Closing DB Connection
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}