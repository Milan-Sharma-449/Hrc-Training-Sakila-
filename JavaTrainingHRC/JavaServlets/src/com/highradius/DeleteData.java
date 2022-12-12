package com.highradius;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteData")
public class DeleteData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteData() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling DeleteData Servlet...");
		System.out.println("*".repeat(50));
		
		// Reading in Data from Request and Converting it's Datatypes
		String requestString = request.getParameter("film_id");
		String[] filmIdListString = requestString.split(",");
		ArrayList<Integer> filmIdList = new ArrayList<>();
		for(String id : filmIdListString) {
			filmIdList.add(Integer.parseInt(id));
		}
				 
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
			
//			// Changing ArrayList -> Array to Support Prepared Statement
//			Array filmIDs = dbConnection.createArrayOf("INTEGER", filmIdList.toArray());
//			
//			// SQL Query String and Prepared Statement Generation
//			query = "DELETE FROM film WHERE film_id IN (?);";
//			prStmt = dbConnection.prepareStatement(query);
//			prStmt.setArray(1, filmIDs);
//			
//			// Execute SQL Query
//			System.out.println("Query Associated: " + prStmt);
//			System.out.println("Executing Query...");
//			prStmt.executeUpdate();
			
			// Using For Loop to Perform the Task
			
			for(int id : filmIdList) {
				
				// SQL Query String and Prepared Statement Generation
				query = "DELETE FROM film WHERE film_id = ?;";
				prStmt = dbConnection.prepareStatement(query);
				prStmt.setInt(1, id);
				
				// Execute SQL Query
				System.out.println("Query Associated: " + prStmt);
				System.out.println("Executing Query...");
				prStmt.executeUpdate();
			
			}
			
			System.out.println(String.format("Query Sucessful! Deleted %d Row(s) from DB.", filmIdList.size()));
			                 
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