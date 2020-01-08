package hn46.sa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn46.sa.dao.ImageDao;
import hn46.sa.dao.RoomCriteriaDao;
import hn46.sa.dao.RoommateCriteriaDao;
import hn46.sa.dao.TenantDao;
import hn46.sa.entity.Image;
import hn46.sa.entity.RoomCriteria;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.Tenant;

@Service
public class TenantSearchService {
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private RoomCriteriaDao roomCriteriaDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private RoommateCriteriaDao roommateCriteriaDao;
	
	public List<Tenant> loadAllData(){
		List<Tenant> lstTenant = tenantDao.findAll();
		for (int i = 0; i < lstTenant.size(); i++) {
			Tenant tenant = lstTenant.get(i);
			RoomCriteria room = roomCriteriaDao.findById(tenant.getRoomCriteria().getIdRoomCriteria());
			Map params = new HashMap<String, Object>();
			params.put("status", 1);
			params.put("type", 3);	//room type
			params.put("iddata", tenant.getIdTenant());		
			List<Image> avatar = imageDao.findByParam(params);
			RoommateCriteria roommate = roommateCriteriaDao.findById(tenant.getRoommateCriteria().getIdRoommateCriteria());
			tenant.setAvatar(avatar.get(0));
			tenant.setRoomCriteria(room);
			tenant.setRoommateCriteria(roommate);
		}
		return lstTenant;
	}
}
