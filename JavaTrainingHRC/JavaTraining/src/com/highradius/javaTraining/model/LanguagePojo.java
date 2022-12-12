package com.highradius.javaTraining.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;

@Table(name="language")
public class LanguagePojo {
	
	// Setting in the DataTypes of the Columns
	private Integer language_id;
	private String name;
	private Set<FilmPojo> movie = new HashSet<FilmPojo>();
	
	// Getters and Setters
	public Integer getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<FilmPojo> getMovie() {
		return movie;
	}
	public void setMovie(Set<FilmPojo> movie) {
		this.movie = movie;
	}	
	
}
