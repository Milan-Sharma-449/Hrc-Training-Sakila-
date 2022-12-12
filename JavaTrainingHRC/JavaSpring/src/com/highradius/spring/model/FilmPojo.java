package com.highradius.spring.model;

public class FilmPojo {
	
	// Setting in the DataTypes of the Columns
	private int film_id;
	private String title;
	private String description;
	private long release_year;
	private String language;
	private int original_language_id;
	private int rental_duration;
	private double rental_rate;
	private long length;
	private double replacement_cost;
	private String rating; 
	private String special_features; 
	private java.sql.Date last_update;
	private String director;
	
	
	// Default Constructor
	public FilmPojo() {
		// TODO Auto-generated constructor stub
	}
	
	// Defining Parameterized Constructor
	public FilmPojo(int film_id, String title, String description, long release_year, String language, int original_language_id, int rental_duration, double rental_rate, long length, double replacement_cost, String rating, String special_features, java.sql.Date last_update, String director) {
		
		super();
		this.film_id = film_id;
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language = language;
		this.original_language_id = original_language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.special_features = special_features;
		this.last_update = last_update;
		this.director = director;
	}
	
	// Getters and Setters
	public int getFilm_id() {
		return film_id;
	}

	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getRelease_year() {
		return release_year;
	}

	public void setRelease_year(long release_year) {
		this.release_year = release_year;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getOriginal_language_id() {
		return original_language_id;
	}

	public void setOriginal_language_id(int original_language_id) {
		this.original_language_id = original_language_id;
	}

	public int getRental_duration() {
		return rental_duration;
	}

	public void setRental_duration(int rental_duration) {
		this.rental_duration = rental_duration;
	}

	public double getRental_rate() {
		return rental_rate;
	}

	public void setRental_rate(double rental_rate) {
		this.rental_rate = rental_rate;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public double getReplacement_cost() {
		return replacement_cost;
	}

	public void setReplacement_cost(double replacement_cost) {
		this.replacement_cost = replacement_cost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecial_features() {
		return special_features;
	}

	public void setSpecial_features(String special_features) {
		this.special_features = special_features;
	}

	public java.sql.Date getLast_update() {
		return last_update;
	}

	public void setLast_update(java.sql.Date last_update) {
		this.last_update = last_update;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
}
