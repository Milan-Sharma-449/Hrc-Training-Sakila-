package com.highradius.struts.action;

import java.util.HashMap;

import com.google.gson.Gson;
import com.highradius.struts.managerImpl.SakilaManager;
import com.highradius.struts.model.FilmPojo;

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

			SakilaManager sakilaManager = new SakilaManager();
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
		
		SakilaManager sakilaManager = new SakilaManager();
		
		FilmPojo obj = new FilmPojo();
		obj.setTitle(getTitle());
		obj.setDescription(getDescription());
		obj.setDirector(getDirector());
		obj.setFilm_id(getFilm_id());
		obj.setLanguage(getLanguage());
		obj.setRating(getRating());
		obj.setRelease_year(getRelease_year());
		obj.setSpecial_features(getSpecial_features());
		
		return sakilaManager.addSakilaData(obj);
				
	}
	
	// EditData Method
	public String editData() {
		
		SakilaManager sakilaManager = new SakilaManager();
		
		FilmPojo obj = new FilmPojo();
		obj.setTitle(getTitle());
		obj.setDescription(getDescription());
		obj.setDirector(getDirector());
		obj.setFilm_id(getFilm_id());
		obj.setLanguage(getLanguage());
		obj.setRating(getRating());
		obj.setRelease_year(getRelease_year());
		obj.setSpecial_features(getSpecial_features());
		
		return sakilaManager.editSakilaData(obj);
				
	}
	
	// DeleteData Method
	public String deleteData() {
		
		SakilaManager sakilaManager = new SakilaManager();
		return sakilaManager.deleteSakilaData(del_filmIds);
				
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
