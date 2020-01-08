package sa.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sa.admin.dao.AccountDao;
import sa.admin.dao.RoomOwnerDao;
import sa.admin.dao.TenantDao;
import sa.admin.dao.UserDao;
import sa.admin.entity.Account;
import sa.admin.entity.RoomOwner;
import sa.admin.entity.Tenant;
import sa.admin.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	public List<User> findAll(){
		List<User> lstUser = userDao.findAll();
		for (User user : lstUser) {
			Account account = accountDao.findById(user.getAccount().getIdAccount());
			user.setAccount(account);
			HashMap<String, Object> params = new HashMap<>();
			params.put("idUser", user.getIdUser());
			if (user.getType() == 1) { 	//roomOwner
				List<RoomOwner> lstRoomOwner = roomOwnerDao.findByParam(params);
				user.setDataId(lstRoomOwner.get(0).getIdRoomOwner());
			}
			else if (user.getType() == 2) {
				List<Tenant> lstTenant = tenantDao.findByParam(params);
				user.setDataId(lstTenant.get(0).getIdTenant());
			}
		}
		return lstUser;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockUser(User user) {
		user.setStatus(0);
		if (user.getType() == 1 && user.getDataId() != 0) {//room owner
			RoomOwner roomOwner = roomOwnerDao.findById(user.getDataId());
			roomOwner.setStatus(0);
			roomOwner.setChange(0);
			roomOwnerDao.update(roomOwner);
		}else if (user.getType() == 2 && user.getDataId() != 0) {
			Tenant tenant = tenantDao.findById(user.getDataId());
			tenant.setStatus(0);
			tenant.setChange(0);
			tenantDao.update(tenant);
		}		
		userDao.update(user);		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unLockUser(User user) {
		user.setStatus(1);
		if (user.getType() == 1 && user.getDataId() != 0) {//room owner
			RoomOwner roomOwner = roomOwnerDao.findById(user.getDataId());
			roomOwner.setStatus(1);
			roomOwner.setChange(0);
			roomOwnerDao.update(roomOwner);
		}else if (user.getType() == 2 && user.getDataId() != 0) {
			Tenant tenant = tenantDao.findById(user.getDataId());
			tenant.setStatus(1);
			tenant.setChange(0);
			tenantDao.update(tenant);
		}
		userDao.update(user);
	}
}
