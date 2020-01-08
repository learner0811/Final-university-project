package hn46.sa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hn46.sa.dao.AccountDao;
import hn46.sa.dao.ImageDao;
import hn46.sa.dao.RoomOwnerDao;
import hn46.sa.dao.TenantDao;
import hn46.sa.dao.UserDao;
import hn46.sa.entity.Account;
import hn46.sa.entity.Image;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private AccountDao accountDao;
	
	public User login(User user) throws Exception {
		return userDao.login(user);
	}
	
	public User register(User user) throws Exception {
		return userDao.register(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User update(User user) {
		return userDao.update(user);
	}
	
	public User getUesrInfo(User user) {
		user = userDao.findByid2(user.getIdUser());		
		if (user.getType() == 1) {
			Map params = new HashMap<String, Object>();
			params.put("iduser", user.getIdUser());
			List<RoomOwner> lstRoomOwner =  roomOwnerDao.findByParam(params);
			RoomOwner roomOwner = lstRoomOwner.get(0);
			params.clear();
			params.put("status", 1);
			params.put("type", 1);	//avatar type
			params.put("iddata", roomOwner.getIdRoomOwner());
			List<Image> lstAvatar = imageDao.findByParam(params);
			user.setAvatar(lstAvatar.get(0));
		}else if (user.getType() == 2) {
			Tenant tenant = new Tenant();
			Map params = new HashMap<String, Object>();
			params.put("iduser", user.getIdUser());
			List<Tenant> lstTenant = tenantDao.findByParam(params);
			tenant = lstTenant.get(0);
			params.clear();
			params.put("status", 1);
			params.put("type", 1);	//avatar type
			params.put("iddata", tenant.getIdTenant());		
			List<Image> lstAvatar = imageDao.findByParam(params);
			user.setAvatar(lstAvatar.get(0));
		}
		else {
			Image avatar = new Image();
			avatar.setFileName("default.jpg");
			user.setAvatar(avatar);
		}
		return user;
	}
	
	public User getAccount(User user) {
		Account account = accountDao.findById(user.getAccount().getIdAccount());
		user.setAccount(account);
		return user;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User updateUserInfo(User user) {
		Account account = accountDao.update(user.getAccount());
		user.setAccount(account);
		user = userDao.update(user);		
		return user;
	}
}
