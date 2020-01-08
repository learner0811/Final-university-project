package sa.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa.admin.dao.CommonDao;
import sa.admin.dto.KeyValueDto;

@Service
public class CommonService {
	@Autowired
	private CommonDao commonDao;
	
	public List<KeyValueDto> staticByCity(){
		return commonDao.staticByCity();
	}
	
	public List<KeyValueDto> staticByRent(){
		return commonDao.staticByRent();
	}
}
