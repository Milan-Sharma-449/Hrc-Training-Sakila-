package com.highradius.javaTraining.action;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.highradius.javaTraining.managerImpl.SakilaManager;
import com.highradius.javaTraining.model.FilmPojo;

public class SakilaAction extends FilmPojo {
	
	// Request Parameters
	private Integer start, limit;
	private String del_filmIds;
	
	// Response Parameter
	private HashMap<String, Object> dataResponse = new HashMap<String, Object>();
	
	// <--------------- Execute Methods ---------------> //
	
	// GetData Method [DB Table]
	public String getData() {
		
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			
			dataResponse = sakilaManager.getSakilaData(getStart(), getLimit());
		
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}
		
		return "success";
		
	}
	
	//GetLangData Method
	public String getLangData() {
		
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			
			dataResponse = sakilaManager.getSakilaLangData();
		
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}
		
		return "success";
		
	}
	
	// AddData Method
	public String addData() {
		
		try {
			
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			FilmPojo film = (FilmPojo) context.getBean("Movies");
			
			film.setTitle(getTitle());
			film.setDescription(getDescription());
			film.setDirector(getDirector());
			film.setFilm_id(getFilm_id());
			film.setLanguage_name(getLanguage_name());
			film.setRating(getRating());
			film.setRelease_year(getRelease_year());
			film.setSpecial_features(getSpecial_features());
			
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			dataResponse = sakilaManager.addSakilaData(film);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}
		
		return "success";
				
	}
	
	// EditData Method
	public String editData() {

		try {
			
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			FilmPojo film = (FilmPojo) context.getBean("Movies");
			
			film.setTitle(getTitle());
			film.setDescription(getDescription());
			film.setDirector(getDirector());
			film.setFilm_id(getFilm_id());
			film.setLanguage_name(getLanguage_name());
			film.setRating(getRating());
			film.setRelease_year(getRelease_year());
			film.setSpecial_features(getSpecial_features());
			
			
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			dataResponse = sakilaManager.editSakilaData(film);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}

		return "success";
		
	}
	
	// DeleteData Method
	public String deleteData() {
		
		try {
			
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			dataResponse = sakilaManager.deleteSakilaData(getDel_filmIds());
		
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}
		
		return "success";
				
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getDel_filmIds() {
		return del_filmIds;
	}

	public void setDel_filmIds(String del_filmIds) {
		this.del_filmIds = del_filmIds;
	}

	public HashMap<String, Object> getDataResponse() {
		return dataResponse;
	}

	public void setDataResponse(HashMap<String, Object> dataResponse) {
		this.dataResponse = dataResponse;
	}	
	
}
