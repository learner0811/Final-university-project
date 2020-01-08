package hn46.sa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hn46.sa.dao.ImageDao;
import hn46.sa.dao.RoomCriteriaDao;
import hn46.sa.dao.RoomOwnerDao;
import hn46.sa.dao.RoommateCriteriaDao;
import hn46.sa.dao.TenantDao;
import hn46.sa.dao.UserDao;
import hn46.sa.entity.Image;
import hn46.sa.entity.RoomCriteria;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;
import hn46.sa.util.AppException;

@Service
public class PostProfileService {
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoommateCriteriaDao roommateCriteriaDao;
	
	@Autowired
	private RoomCriteriaDao roomCriteriaDao;
	
	@Autowired
	private ImageDao imageDao;
	
	public boolean isExist(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<Tenant> lstTenant =  tenantDao.findByParam(params);
		if (lstTenant == null || lstTenant.isEmpty())
			return false;
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void disableRoomOwner(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<RoomOwner> lstRoomOwner =  roomOwnerDao.findByParam(params);
		if (!lstRoomOwner.isEmpty()) {
			RoomOwner roomOwner = lstRoomOwner.get(0);
			roomOwner.setChange(0);
			roomOwner.setStatus(0);
			roomOwner.setIdUser(user.getIdUser());
			roomOwnerDao.update(roomOwner);
		}
		userDao.update(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void enableTenant(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<Tenant> lstTenant =  tenantDao.findByParam(params);
		if (!lstTenant.isEmpty()) {
			Tenant tenant = lstTenant.get(0);
			tenant.setStatus(1);
			tenant.setIdUser(user.getIdUser());
			tenantDao.update(tenant);
		}
		userDao.update(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Tenant tenant) {
		RoommateCriteria roommate = tenant.getRoommateCriteria();
		roommate = roommateCriteriaDao.save(roommate);
		RoomCriteria room = tenant.getRoomCriteria();
		room = roomCriteriaDao.save(room);
		tenant.setRoomCriteria(room);
		tenant.setRoommateCriteria(roommate);
		tenant = tenantDao.save(tenant);	
		Image avatar = tenant.getAvatar();
		avatar.setIdData(tenant.getIdTenant());
		avatar.setType(3);
		imageDao.save(tenant.getAvatar());	
	}
	
	public Tenant loadByUser(User user) {
		Tenant tenant = new Tenant();
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<Tenant> lstTenant = tenantDao.findByParam(params);
		tenant = lstTenant.get(0);
		params.clear();
		params.put("status", 1);
		params.put("type", 3);	//avatar type
		params.put("iddata", tenant.getIdTenant());		
		List<Image> lstAvatar = imageDao.findByParam(params);
		tenant.setAvatar(lstAvatar.get(0));
		RoommateCriteria roommate = roommateCriteriaDao.findById(tenant.getRoommateCriteria().getIdRoommateCriteria());
		RoomCriteria room = roomCriteriaDao.findById(tenant.getRoomCriteria().getIdRoomCriteria());
		tenant.setRoomCriteria(room);
		tenant.setRoommateCriteria(roommate);
		return tenant;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(Tenant tenant) throws AppException {
		try {
			if (tenant.getAvatar() != null) {
				tenant.getAvatar().setIdData(tenant.getIdTenant());
				imageDao.update(tenant.getAvatar());			
			}
			roommateCriteriaDao.update(tenant.getRoommateCriteria());
			roomCriteriaDao.update(tenant.getRoomCriteria());
			tenantDao.update(tenant);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("Error", "sa-002");
		}
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void openOrClose(Tenant tenant) {
		tenantDao.update(tenant);
	}

}
