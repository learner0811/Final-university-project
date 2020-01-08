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
import sa.admin.dao.RoomOwnerDao;
import sa.admin.dao.UserDao;
import sa.admin.entity.Account;
import sa.admin.entity.Image;
import sa.admin.entity.RoomOwner;
import sa.admin.entity.User;

@Service
public class RoomOwnerService {
	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private ImageDao imageDao;
	
	public List<RoomOwner> findAll() {
		List<RoomOwner> lstRoomOwner = roomOwnerDao.findAll();
		for (RoomOwner roomOwner : lstRoomOwner) {
			User user = userDao.findByid2(roomOwner.getIdUser());
			Account account = accountDao.findById(user.getAccount().getIdAccount());
			roomOwner.setFirstName(user.getFirstName());
			roomOwner.setLastName(user.getLastName());
			roomOwner.setAccount(account);	
			Map params = new HashMap<String, Object>();
			params.put("status", 1);
			params.put("type", 1);
			params.put("iddata", roomOwner.getIdRoomOwner());		
			List<Image> lstImg = imageDao.findByParam(params);
			Image avatar = lstImg.get(0);
			roomOwner.setAvatar(avatar);
		}
		return lstRoomOwner;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockOrUnlock(RoomOwner roomOwner) {
		roomOwnerDao.update(roomOwner);
	}
	
	
}
