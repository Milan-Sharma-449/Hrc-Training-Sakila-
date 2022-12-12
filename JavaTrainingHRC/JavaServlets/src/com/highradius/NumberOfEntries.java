package com.highradius;

import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/getEntriesNumber")
public class NumberOfEntries extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public NumberOfEntries() {
		super();
	}
	
	// protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	public static int noOfRows() {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling NumberOfEntries Servlet...");
		System.out.println("*".repeat(50));
 
		// JDBC Variables Information
		Connection dbConnection = null;
		PreparedStatement prStmt = null;
		ResultSet rS = null;
		
		String url = "jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=convertToNull";
		String userName = "root";
		String passWord = "root";
		String query = "";
		int numberOfRows = 0;
		
		try {
			
			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Open a Connection
			dbConnection = DriverManager.getConnection(url, userName, passWord);
			if(dbConnection != null)	
				System.out.println("DB Connected!");
			
			// SQL Query String and Prepared Statement Generation
			query = "SELECT COUNT(*) as Number_Of_Rows FROM film;";
			prStmt = dbConnection.prepareStatement(query);
			
			// Execute SQL Query
			System.out.println("Executing Query...");
			rS = prStmt.executeQuery();

			// Extracting Data from Result Set
			while(rS.next()) {
				numberOfRows = rS.getInt("Number_Of_Rows");
				break;
			}
			System.out.println("Result: Number of Rows = " + numberOfRows);
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

		return numberOfRows;

	}
}
