package com.highradius;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddData")
public class AddData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddData() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling AddData Servlet...");
		System.out.println("*".repeat(50));
		
		// Reading in Data from Request
		String title = request.getParameter("movieTitle");
		String description = request.getParameter("movieDescription");
		long releaseYear = request.getParameter("movieReleaseYear") != null ? Long.parseLong(request.getParameter("movieReleaseYear")) : 2021;
		String language = request.getParameter("movieLanguage") != null ? request.getParameter("movieLanguage") : "English";
		String director = request.getParameter("movieDirector") != null ? request.getParameter("movieDirector") : "";
		String rating = request.getParameter("movieRating") != null ? request.getParameter("movieRating") : "";
		String specialFeature = request.getParameter("movieSpecialFeatures") != null ? request.getParameter("movieSpecialFeatures") : "";
		
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
			query = "INSERT INTO film (title, `description`, release_year, language_id, director, rating, special_features)\r\n"
					+ "VALUES (?, ?, ?, (SELECT language_id FROM `language` WHERE `name` = ?), ?, ?, ?)";
			
			prStmt = dbConnection.prepareStatement(query);
			prStmt.setString(1, title);
			prStmt.setString(2, description);
			prStmt.setLong(3, releaseYear);
			// prStmt.setInt(4, langauge_id);
			prStmt.setString(4, language);
			prStmt.setString(5, director);
			prStmt.setString(6, rating);
			prStmt.setString(7, specialFeature);
			
			// Execute SQL Query
			System.out.println("Query Associated: " + prStmt);
			System.out.println("Executing Query...");
			prStmt.executeUpdate();
			System.out.println("Query Sucessful! Inserted 1 Row in DB.");
			
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
