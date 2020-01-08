package sa.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sa.admin.dao.AccountDao;
import sa.admin.dao.ImageDao;
import sa.admin.dao.TenantDao;
import sa.admin.dao.UserDao;
import sa.admin.entity.Account;
import sa.admin.entity.Image;
import sa.admin.entity.Tenant;
import sa.admin.entity.User;

@Service
public class TenantService {
	@Autowired
	private TenantDao tenantDao;
		
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private ImageDao imageDao;
	
	public List<Tenant> findAll() {
		List<Tenant> lstTenant = tenantDao.findAll();
		for (Tenant tenant : lstTenant) {
			User user = userDao.findByid2(tenant.getIdUser());
			Account account = accountDao.findById(user.getAccount().getIdAccount());
			tenant.setAccount(account);		
			tenant.setFirstName(user.getFirstName());
			tenant.setLastName(user.getLastName());
			Map params = new HashMap<String, Object>();
			params.put("status", 1);
			params.put("type", 3);
			params.put("iddata", tenant.getIdTenant());		
			List<Image> lstImg = imageDao.findByParam(params);
			Image avatar = lstImg.get(0);
			tenant.setAvatar(avatar);
		}
		return lstTenant;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockOrUnlock(Tenant tenant) {
		tenantDao.update(tenant);
	}
	
}
