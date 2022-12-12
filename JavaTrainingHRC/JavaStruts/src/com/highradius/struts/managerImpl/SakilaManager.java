package com.highradius.struts.managerImpl;

import java.util.HashMap;

import com.highradius.struts.daoImpl.SakilaDAO;
import com.highradius.struts.manager.SakilaManagerInterface;
import com.highradius.struts.model.FilmPojo;

public class SakilaManager implements SakilaManagerInterface {
	
	public HashMap<String, Object> geSakilaData(Integer start, Integer limit) {
		
		SakilaDAO sakilaDao = new SakilaDAO();
		return sakilaDao.geSakilatData(start, limit);
		
	}
	
	public String addSakilaData(FilmPojo obj) {
		
		SakilaDAO sakilaDao = new SakilaDAO();
		return sakilaDao.addSakilaData(obj);
		
	}
	
	public String editSakilaData(FilmPojo obj) {
		
		SakilaDAO sakilaDao = new SakilaDAO();
		return sakilaDao.editSakilaData(obj);
		
	}
	
	public String deleteSakilaData(String del_filmIds) {
		
		SakilaDAO sakilaDao = new SakilaDAO();
		return sakilaDao.deleteSakilaData(del_filmIds);
		
	}

}
