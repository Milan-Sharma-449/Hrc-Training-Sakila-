package com.highradius.spring.action;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.highradius.spring.managerImpl.SakilaManager;
import com.highradius.spring.model.FilmPojo;

public class SakilaAction extends FilmPojo {
	
	// Request Parameters
	private Integer start, limit;
	private String del_filmIds;
	
	// Response Parameter
	private String dataResponse;
	
	// <--------------- Execute Methods ---------------> //
	
	// GetData Method
	public String getData() {
		
		try {
			
			// Context Initialisation [Dependency Injection] using Spring
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			
			HashMap<String, Object> data = sakilaManager.geSakilaData(start, limit);
			Gson gson = new Gson();
			setDataResponse(gson.toJson(data));
		
		}
		catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
		
	}
	
	// AddData Method
	public String addData() {
		
		try {
			
			// Context Initialisation [Dependency Injection] using Spring
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			
			FilmPojo obj = new FilmPojo();
			obj.setTitle(getTitle());
			obj.setDescription(getDescription());
			obj.setDirector(getDirector());
			obj.setFilm_id(getFilm_id());
			obj.setLanguage(getLanguage());
			obj.setRating(getRating());
			obj.setRelease_year(getRelease_year());
			obj.setSpecial_features(getSpecial_features());
			
			sakilaManager.addSakilaData(obj);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}
		
		return "success";
				
	}
	
	// EditData Method
	public String editData() {
		
		try {
			
			// Context Initialisation [Dependency Injection] using Spring
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			
			FilmPojo obj = new FilmPojo();
			obj.setTitle(getTitle());
			obj.setDescription(getDescription());
			obj.setDirector(getDirector());
			obj.setFilm_id(getFilm_id());
			obj.setLanguage(getLanguage());
			obj.setRating(getRating());
			obj.setRelease_year(getRelease_year());
			obj.setSpecial_features(getSpecial_features());
			
			sakilaManager.editSakilaData(obj);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return "error";
			
		}

		return "success";
		
	}
	
	// DeleteData Method
	public String deleteData() {

		try {
			
			// Context Initialisation [Dependency Injection] using Spring
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			SakilaManager sakilaManager = (SakilaManager) context.getBean("Manager");
			sakilaManager.deleteSakilaData(del_filmIds);
		
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

	public String getDataResponse() {
		return dataResponse;
	}

	public void setDataResponse(String dataResponse) {
		this.dataResponse = dataResponse;
	}
	
	
}
