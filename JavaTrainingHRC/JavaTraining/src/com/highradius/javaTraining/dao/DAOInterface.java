package com.highradius.javaTraining.dao;

import java.util.HashMap;

import org.hibernate.SessionFactory;

import com.highradius.javaTraining.model.FilmPojo;

public interface DAOInterface {
	
	// Helper Methods
	public SessionFactory getSession();
	public Integer getLanguageId(String lang_name);
	public Long getTotalRows();
	
	// Execute Methods
	public HashMap<String, Object> getSakilaData(Integer start, Integer limit);
	public HashMap<String, Object> getSakilaLangData();
	public HashMap<String, Object> addSakilaData(FilmPojo obj);
	public HashMap<String, Object> editSakilaData(FilmPojo obj);
	public HashMap<String, Object> deleteSakilaData(String del_filmIds);

}
