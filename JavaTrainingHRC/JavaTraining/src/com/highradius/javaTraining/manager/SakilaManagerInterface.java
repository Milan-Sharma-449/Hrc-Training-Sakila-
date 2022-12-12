package com.highradius.javaTraining.manager;

import java.util.HashMap;

import com.highradius.javaTraining.model.FilmPojo;

public interface SakilaManagerInterface {
	
	public HashMap<String, Object> getSakilaData(Integer start, Integer limit);
	public HashMap<String, Object> getSakilaLangData();
	public HashMap<String, Object> addSakilaData(FilmPojo obj);
	public HashMap<String, Object> editSakilaData(FilmPojo obj);
	public HashMap<String, Object> deleteSakilaData(String del_filmIds);

}
