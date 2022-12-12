package com.highradius.struts.dao;

import com.highradius.struts.model.FilmPojo;
import java.util.HashMap;

public interface DAOInterface {
	
	public HashMap<String, Object> geSakilatData(Integer start, Integer limit);
	public String addSakilaData(FilmPojo obj);
	public String editSakilaData(FilmPojo obj);
	public String deleteSakilaData(String del_filmIds);

}
