package com.highradius.javaTraining.managerImpl;

import java.util.HashMap;

import com.highradius.javaTraining.daoImpl.SakilaDAO;
import com.highradius.javaTraining.manager.SakilaManagerInterface;
import com.highradius.javaTraining.model.FilmPojo;

public class SakilaManager implements SakilaManagerInterface {
	
	// Sakila DAO Object Instance
	private SakilaDAO sakilaDao;
	
	public SakilaDAO getSakilaDao() {
		return sakilaDao;
	}

	public void setSakilaDao(SakilaDAO sakilaDao) {
		this.sakilaDao = sakilaDao;
	}

	// Data Sanity Check for NULL [Handling Corner Cases]
	public FilmPojo dataSanityCheck(FilmPojo obj) {
		
		if(obj.getDescription() == "")
			obj.setDescription(null);
		if(obj.getRelease_year() == null ||  obj.getRelease_year() == 0)
			obj.setRelease_year(null);
		if(obj.getLanguage_name() == "")
			obj.setLanguage_name("English");
		// if(obj.getDirector() == "")
		// 	obj.setDirector(" ");
		if(obj.getRating() == "")
			obj.setRating(null);
		if(obj.getSpecial_features() == "")
			obj.setSpecial_features(null);

		return obj;
		
	}

	public HashMap<String, Object> getSakilaData(Integer start, Integer limit) {
		
		return sakilaDao.getSakilaData(start, limit);
		
	}
	
	public HashMap<String, Object> getSakilaLangData() {
		
		return sakilaDao.getSakilaLangData();
		
	}
	
	
	public HashMap<String, Object> addSakilaData(FilmPojo obj) {
		
		return sakilaDao.addSakilaData(dataSanityCheck(obj));
		
	}
	
	public HashMap<String, Object> editSakilaData(FilmPojo obj) {
		
		return sakilaDao.editSakilaData(dataSanityCheck(obj));
		
	}
	
	public HashMap<String, Object> deleteSakilaData(String del_filmIds) {
		
		return sakilaDao.deleteSakilaData(del_filmIds);
		
	}

}
