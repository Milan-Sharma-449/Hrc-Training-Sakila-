package com.highradius.struts.manager;

import java.util.HashMap;
import com.highradius.struts.model.FilmPojo;

public interface SakilaManagerInterface {
	
	public HashMap<String, Object> geSakilaData(Integer start, Integer limit);
	public String addSakilaData(FilmPojo obj);
	public String editSakilaData(FilmPojo obj);
	public String deleteSakilaData(String del_filmIds);

}
