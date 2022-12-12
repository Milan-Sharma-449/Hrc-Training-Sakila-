package com.highradius.struts.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.highradius.struts.dao.DAOInterface;
import com.highradius.struts.model.FilmPojo;

public class SakilaDAO implements DAOInterface {
	
	// JDBC Variables Information
	private Connection dbConnection = null;
	private PreparedStatement prStmt = null;
	private ResultSet rS = null;
		
	private String url = "jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=convertToNull";
	private String userName = "root";
	private String passWord = "root";
	private String query = "";
	private ArrayList<FilmPojo> arr = new ArrayList<>();
	private HashMap<String, Object> responseData = new HashMap<>();
	private int numberOfRows = 0;
		
    /* ####################################################################################
	#                           `getData` Execute Function                                #
	#################################################################################### */
	public HashMap<String, Object> geSakilatData(Integer start, Integer limit) {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling GetData Action...");
		System.out.println("*".repeat(50));

		// DB Connection
		try {
			
			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Checking out for Pagination Requests
			Integer nullFlag;
			if((start == null) || (limit == null))
				nullFlag = 1;
			else
				nullFlag = 0;
						
			// Open a Connection
			this.dbConnection = DriverManager.getConnection(this.url, this.userName, this.passWord);
			if(this.dbConnection != null)
				System.out.println("DB Connected!");
					
			// SQL Query String and Prepared Statement Generation
			this.query = "SELECT \r\n"
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
					
			// If START and LIMIT are NOT Defined.
			if(nullFlag == 1)
				this.prStmt = this.dbConnection.prepareStatement(this.query);

			// If START and LIMIT are Defined.
			else {
			
				this.query = this.query.replaceAll(";", "") + "\r\nLIMIT ?, ?;";
				this.prStmt = this.dbConnection.prepareStatement(this.query);
				this.prStmt.setInt(1, start);
				this.prStmt.setInt(2, limit);

			}
					
			// Execute SQL Query
			System.out.println("Executing Query...");
			this.rS = this.prStmt.executeQuery();
					
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
				this.arr.add(obj);	

			}
			this.rS.close();

			// Acquiring Number of Rows in DB
			this.query = "SELECT COUNT(*) as Number_Of_Rows FROM film;";
			this.prStmt = this.dbConnection.prepareStatement(this.query);
			this.rS = this.prStmt.executeQuery();
					
			// Extracting Data from Result Set
			while(this.rS.next()) {
				this.numberOfRows = this.rS.getInt("Number_Of_Rows");
				break;
			}

			// Converting the HashMap into Response
			this.responseData.put("success", true);
			this.responseData.put("totalCount", this.numberOfRows);
			this.responseData.put("filmData", this.arr);
					
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Execution Over!");
		}
		return responseData;
	}

	/* ####################################################################################
	#                           `addData` Execute Function                                #
	#################################################################################### */
	public String addSakilaData(FilmPojo obj) {
		

		
		System.out.println("*".repeat(50));
		System.out.println("Calling AddData Action...");
		System.out.println("*".repeat(50));

		// DB Connection
		try {

			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Checking out for Population of Request Variables
			String title = obj.getTitle();
			String description = obj.getDescription();
			Long release_year = obj.getRelease_year() != 0 ? obj.getRelease_year() : 2021;
			String language = obj.getLanguage() != null ? obj.getLanguage() : "English";
			String  director = obj.getDirector() != null ? obj.getDirector() : "";
			String rating = obj.getRating() != null ? obj.getRating() : "";
			String special_features = obj.getSpecial_features() != null ? obj.getSpecial_features() : "";
				
			// Open a Connection
			this.dbConnection = DriverManager.getConnection(this.url, this.userName, this.passWord);
			if(this.dbConnection != null)	
				System.out.println("DB Connected!");
			
			// SQL Query String and Prepared Statement Generation
			this.query = "INSERT INTO film (title, `description`, release_year, language_id, director, rating, special_features)\r\n"
			+ "VALUES (?, ?, ?, (SELECT language_id FROM `language` WHERE `name` = ?), ?, ?, ?)";
			
			this.prStmt = dbConnection.prepareStatement(this.query);
			this.prStmt.setString(1, title);
			this.prStmt.setString(2, description);
			this.prStmt.setLong(3, release_year);
			this.prStmt.setString(4, language);
			this.prStmt.setString(5, director);
			this.prStmt.setString(6, rating);
			this.prStmt.setString(7, special_features);
			
			// Execute SQL Query
			System.out.println("Query Associated: " + this.prStmt);
			System.out.println("Executing Query...");
			this.prStmt.executeUpdate();
			System.out.println("Query Sucessful! Inserted 1 Row in DB.");

			// Closing DB Connection
			this.dbConnection.close();
			this.prStmt.close();
			
		}

		catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		finally {
			System.out.println("Execution Over!");
		}

		return "success";

	}

	/* ####################################################################################
	#                          `editData` Execute Function                                #
	#################################################################################### */

	public String editSakilaData(FilmPojo obj) {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling EditData Action...");
		System.out.println("*".repeat(50));

		// DB Connection
		try {

			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
				
			// Open a Connection
			this.dbConnection = DriverManager.getConnection(this.url, this.userName, this.passWord);
			if(this.dbConnection != null)	
				System.out.println("DB Connected!");
			
			// SQL Query String and Prepared Statement Generation
			this.query = "UPDATE film \r\n"
						+ "SET title = ?, \r\n"
						+ "`description` = ?,\r\n"
						+ "release_year = ?,\r\n"
						+ "language_id = (SELECT language_id FROM `language` WHERE `name` = ?),\r\n"
						+ "director = ?,\r\n"
						+ "rating = ?,\r\n"
						+ "special_features = ?\r\n"
						+ "WHERE film_id = ?;";
			
			this.prStmt = this.dbConnection.prepareStatement(this.query);
			this.prStmt.setString(1, obj.getTitle());
			this.prStmt.setString(2, obj.getDescription());
			this.prStmt.setLong(3, obj.getRelease_year());
			this.prStmt.setString(4, obj.getLanguage());
			this.prStmt.setString(5, obj.getDirector());
			this.prStmt.setString(6, obj.getRating());
			this.prStmt.setString(7, obj.getSpecial_features());
			this.prStmt.setInt(8, obj.getFilm_id());
			
			// Execute SQL Query
			System.out.println("Query Associated: " + this.prStmt);
			System.out.println("Executing Query...");
			this.prStmt.executeUpdate();
			System.out.println("Query Sucessful! Updated 1 Row in DB.");

			// Closing DB Connection
			this.dbConnection.close();
			this.prStmt.close();
			
		}

		catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		finally {
			System.out.println("Execution Over!");
		}

		return "success";

	}

	/* ####################################################################################
	#                         `deleteData` Execute Function                               #
	#################################################################################### */
	public String deleteSakilaData(String del_filmIds) {
		
		System.out.println("*".repeat(50));
		System.out.println("Calling DeleteData Action...");
		System.out.println("*".repeat(50));

		// DB Connection
		try {

			// Registering JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
				
			// Open a Connection
			this.dbConnection = DriverManager.getConnection(this.url, this.userName, this.passWord);
			if(this.dbConnection != null)	
				System.out.println("DB Connected!");

			// Making the Request into Suitable Format
			String[] filmIdListString = del_filmIds.split(",");
			ArrayList<Integer> filmIdList = new ArrayList<>();
			for(String id : filmIdListString) {
				filmIdList.add(Integer.parseInt(id));
			}
			
			// Using For Loop to Perform the Task
						
			for(int id : filmIdList) {
							
				// SQL Query String and Prepared Statement Generation
				this.query = "DELETE FROM film WHERE film_id = ?;";
				this.prStmt = this.dbConnection.prepareStatement(this.query);
				this.prStmt.setInt(1, id);
				
				// Execute SQL Query
				System.out.println("Query Associated: " + this.prStmt);
				System.out.println("Executing Query...");
				this.prStmt.executeUpdate();

			}

			System.out.println(String.format("Query Sucessful! Deleted %d Row(s) from DB.", filmIdList.size()));
							
			// Closing DB Connection
			this.dbConnection.close();
			this.prStmt.close();
			
		}

		catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		finally {
			System.out.println("Execution Over!");
		}

		return "success";

	}

}
