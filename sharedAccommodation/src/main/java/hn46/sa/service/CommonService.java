package hn46.sa.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn46.sa.dao.CommonDao;
import hn46.sa.dao.FacilitiesDao;
import hn46.sa.dto.ParamDto;
import hn46.sa.entity.Facilities;

@Service
public class CommonService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private FacilitiesDao facilitiesDao;
	
	public List<ParamDto> loadNative(){
		return commonDao.loadNative();	
	}
	
	public List<Facilities> loadFacilities(){
		return facilitiesDao.findAll();
	}
	
}
