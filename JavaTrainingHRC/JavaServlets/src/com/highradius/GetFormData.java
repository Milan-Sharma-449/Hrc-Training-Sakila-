package com.highradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetFormData")
public class GetFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFormData() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling GetFormData Servlet...");
		System.out.println("*".repeat(50));
		
		// Reading in Data from Request
		String name = request.getParameter("movieName_");
		String director = request.getParameter("directorName_");
		String tempReleaseYear = request.getParameter("releaseYear_") != null ? request.getParameter("releaseYear_"): "2006";
		String language = request.getParameter("language_");
		
		// Modifying Release Year
		long releaseYear = Long.parseLong(tempReleaseYear.substring(0, 4));
		
//		// Checking Condition for language [Converted to Language ID]
//		int langauge_id = 1;
//		if(language.equals("Italian")) langauge_id = 2;
//		else if(language.equals("French")) langauge_id = 5;
//		else if(language.equals("German")) langauge_id = 6;
//		else if(language.equals("Japanese")) langauge_id = 3;
//		else if(language.equals("Mandarin")) langauge_id = 4;
//		else if(language.equals("Mongolian")) langauge_id = 7;
//		else if(language.equals("Hindi")) langauge_id = 10;
		
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
			query = "SELECT * FROM film \r\n"
					+ "WHERE\r\n"
					+ "title LIKE \"%?%\"\r\n"
					+ "# and director IS NULL\r\n"
					+ "AND release_year = ?\r\n"
					+ "AND language_id = (SELECT langauge_id FROM `language` WHERE `name` = ?);";
					
			prStmt = dbConnection.prepareStatement(query);
			prStmt.setString(1, name);
			// prStmt.setString(2, director);
			prStmt.setLong(2, releaseYear);
			prStmt.setString(3, language);
			// prStmt.setInt(3, langauge_id);
					
			// Execute SQL Query
			System.out.println("Query Associated: " + prStmt);
			System.out.println("Executing Query...");
			prStmt.executeQuery();
			System.out.println("Query Sucessful!");
					
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
