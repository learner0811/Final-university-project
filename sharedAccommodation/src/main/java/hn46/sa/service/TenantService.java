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
import hn46.sa.dao.UserDao;
import hn46.sa.entity.Address;
import hn46.sa.entity.Image;
import hn46.sa.entity.Room;
import hn46.sa.entity.RoomCriteria;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;

@Service
public class TenantService {
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private RoomCriteriaDao roomCriteriaDao;
	
	@Autowired
	private RoommateCriteriaDao roommateCriteriaDao;
	
	@Autowired
	private UserDao userDao;
	
	public Tenant findById(int id) {
		Tenant tenant = tenantDao.findById(id);	
		User user = userDao.findByid2(tenant.getIdUser());
		tenant.setFirstName(user.getFirstName());
		tenant.setLastName(user.getLastName());
		Map params = new HashMap<String, Object>();
		params.put("status", 1);
		params.put("type", 3);
		params.put("iddata", tenant.getIdTenant());		
		List<Image> lstImg = imageDao.findByParam(params);
		Image avatar = lstImg.get(0);
		tenant.setAvatar(avatar);
		RoomCriteria room = roomCriteriaDao.findById(tenant.getRoomCriteria().getIdRoomCriteria());
		RoommateCriteria roommate = roommateCriteriaDao.findById(tenant.getRoommateCriteria().getIdRoommateCriteria());
		tenant.setRoomCriteria(room);
		tenant.setRoommateCriteria(roommate);
		return tenant;
	}
}
