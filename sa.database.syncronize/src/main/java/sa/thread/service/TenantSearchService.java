package sa.thread.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sa.thread.dao.ImageDao;
import sa.thread.dao.RoomCriteriaDao;
import sa.thread.dao.RoommateCriteriaDao;
import sa.thread.dao.TenantDao;
import sa.thread.entity.Image;
import sa.thread.entity.RoomCriteria;
import sa.thread.entity.RoomOwner;
import sa.thread.entity.RoommateCriteria;
import sa.thread.entity.Tenant;

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
			HashMap<String, Object> params = new HashMap<String, Object>();
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
		
	@Transactional(rollbackFor = Exception.class)
	public void update(Tenant tenant) {
		tenantDao.update(tenant);		
	}
}
