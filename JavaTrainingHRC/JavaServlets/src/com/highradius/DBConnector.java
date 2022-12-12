package com.highradius;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

public class DBConnector extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public DBConnector() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		System.out.println("Calling DB Connector Servlet...");
		Connection dbConnection = null;
		PreparedStatement prStmt = null;
		ResultSet rS = null;
		
		String url = "jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=convertToNull";
		String userName = "root";
		String passWord = "root";
		String query = "SELECT COUNT(*) AS Number_Of_Columns FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = 'sakila' AND TABLE_NAME = 'film';";
		
		try {
			
			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Open a Connection
			dbConnection = DriverManager.getConnection(url, userName, passWord);
			if(dbConnection != null)	
				System.out.println("DB Connected!");
			
			// Execute SQL Query
			prStmt = dbConnection.prepareStatement(query);
			System.out.println("Executing Query...");
			rS = prStmt.executeQuery();
			int numberOfColumns = 0;
			
			// Extracting Data from Result Set
			while(rS.next()) {
				
				numberOfColumns = rS.getInt("Number_Of_Columns");
				
			}
			
			// Generate Response JSON
			Map<String, Integer> jsonResponse = new HashMap<String, Integer>();
			Gson gson = new Gson();
			PrintWriter documentOut = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			jsonResponse.put("resultStatus", 200);
			jsonResponse.put("noOfCols", numberOfColumns);
			String jsonString = gson.toJson(jsonResponse);
			documentOut.write(jsonString);
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
