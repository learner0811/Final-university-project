package hn46.sa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hn46.sa.dao.AddressDao;
import hn46.sa.dao.FacilitiesDao;
import hn46.sa.dao.ImageDao;
import hn46.sa.dao.RoomDao;
import hn46.sa.dao.RoomFacilitiesDao;
import hn46.sa.dao.RoomOwnerDao;
import hn46.sa.dao.RoommateCriteriaDao;
import hn46.sa.dao.TenantDao;
import hn46.sa.dao.UserDao;
import hn46.sa.entity.Address;
import hn46.sa.entity.Facilities;
import hn46.sa.entity.Image;
import hn46.sa.entity.Room;
import hn46.sa.entity.RoomFacilities;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;
import hn46.sa.util.AppException;

@Service
public class PostRoomService {
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private RoommateCriteriaDao roommateCriteriaDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoomFacilitiesDao roomFacilitiesDao;
	
	@Autowired
	private FacilitiesDao facilitiesDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(RoomOwner roomOwner) throws AppException {
		try {
			Address address = addressDao.save(roomOwner.getRoom().getAddress());
			roomOwner.getRoom().setAddress(address);
			Room room = roomDao.save(roomOwner.getRoom());
			roomOwner.setRoom(room);
			RoommateCriteria  roommate = roommateCriteriaDao.save(roomOwner.getRoommateCriteria());
			roomOwner.setRoommateCriteria(roommate);
			roomOwner = roomOwnerDao.save(roomOwner);
			roomOwner.getAvatar().setIdData(roomOwner.getIdRoomOwner());
			imageDao.save(roomOwner.getAvatar());			
			int idRoom = roomOwner.getRoom().getIdRoom();			
			for (int i = 0; i < roomOwner.getRoom().getImages().length; i++) {
				roomOwner.getRoom().getImages()[i].setIdData(idRoom);
				imageDao.save(roomOwner.getRoom().getImages()[i]);
			}	
			for (int i = 0; i < roomOwner.getRoom().getFacilities().length; i++) {	
				RoomFacilities entity = new RoomFacilities();
				entity.setFacilities(roomOwner.getRoom().getFacilities()[i]);
				entity.setRoom(roomOwner.getRoom());
				roomFacilitiesDao.save(entity);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("SQL Exception", ex.getMessage());
		}		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(RoomOwner roomOwner) throws AppException {
		try {
			Address address = addressDao.update(roomOwner.getRoom().getAddress());
			roomOwner.getRoom().setAddress(address);
			Room room = roomDao.update(roomOwner.getRoom());
			roomOwner.setRoom(room);
			RoommateCriteria  roommate = roommateCriteriaDao.update(roomOwner.getRoommateCriteria());
			roomOwner.setRoommateCriteria(roommate);
			
			roomOwner = roomOwnerDao.update(roomOwner);
			if (roomOwner.getAvatar() != null) {
				roomOwner.getAvatar().setIdData(roomOwner.getIdRoomOwner());
				imageDao.update(roomOwner.getAvatar());			
			}
			int idRoom = roomOwner.getRoom().getIdRoom();
			System.out.println(roomOwner.getRoom().getImages().length);
			for (int i = 0; i < roomOwner.getRoom().getImages().length; i++) {
				roomOwner.getRoom().getImages()[i].setIdData(idRoom);
				imageDao.save(roomOwner.getRoom().getImages()[i]);
			}
						
			roomFacilitiesDao.deleteByRoom(room.getIdRoom());			
			for (int i = 0; i < roomOwner.getRoom().getFacilities().length; i++) {	
				RoomFacilities entity = new RoomFacilities();
				entity.setFacilities(roomOwner.getRoom().getFacilities()[i]);
				entity.setRoom(roomOwner.getRoom());
				roomFacilitiesDao.save(entity);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("SQL Exception", ex.getMessage());
		}		
	}
	
	public boolean roomOwnerExist(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<RoomOwner> roomOwner = roomOwnerDao.findByParam(params);
		if (roomOwner == null)
			return false;
		return true;
	}
	
	public RoomOwner loadRoomOwnerByUser(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<RoomOwner> lstRoomOwner =  roomOwnerDao.findByParam(params);
		RoomOwner roomOwner = lstRoomOwner.get(0);
		Room room = roomDao.findById(roomOwner.getRoom().getIdRoom());
		Address address = addressDao.findById(room.getAddress().getIdAddress());
		room.setAddress(address);
		params.clear();
		params.put("status", 1);
		params.put("type", 2);	//avatar type
		params.put("iddata", room.getIdRoom());		
		List<Image> lstRoomImg = imageDao.findByParam(params);
		room.setImages(lstRoomImg.toArray(new Image[lstRoomImg.size()]));
		
		params.clear();
		params.put("idroom", room.getIdRoom());
		List<Facilities> lstTmp = new ArrayList<>();
		List<RoomFacilities> lstRoomFacilities = roomFacilitiesDao.findByParam(params);
		for (int i = 0; i < lstRoomFacilities.size(); i++) {
			Facilities facilites = facilitiesDao.findById(lstRoomFacilities.get(i).getFacilities().getIdfacilities());
			lstTmp.add(facilites);
		}
		room.setFacilities(lstTmp.toArray(new Facilities[lstTmp.size()]));
		
		RoommateCriteria roommate = roommateCriteriaDao.findById(roomOwner.getRoommateCriteria().getIdRoommateCriteria());
		params.clear();
		params.put("status", 1);
		params.put("type", 1);	//avatar type
		params.put("iddata", roomOwner.getIdRoomOwner());
		List<Image> lstAvatar = imageDao.findByParam(params);
		roomOwner.setAvatar(lstAvatar.get(0));
		roomOwner.setRoom(room);
		roomOwner.setRoommateCriteria(roommate);
		return roomOwner; 
	}
	
	public boolean isExist(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<RoomOwner> lstRoomOwner =  roomOwnerDao.findByParam(params);
		if (lstRoomOwner == null || lstRoomOwner.isEmpty())
			return false;
		return true;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteImg(Image image) {
		imageDao.update(image);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void disableTenant(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<Tenant> lstTenant =  tenantDao.findByParam(params);
		if (!lstTenant.isEmpty()) {
			Tenant tenant = lstTenant.get(0);
			tenant.setChange(0);
			tenant.setStatus(0);
			tenant.setIdUser(user.getIdUser());
			tenantDao.update(tenant);
		}
		userDao.update(user);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void enableRoomOwner(User user) {
		Map params = new HashMap<String, Object>();
		params.put("iduser", user.getIdUser());
		List<RoomOwner> lstRoomOwner =  roomOwnerDao.findByParam(params);
		if (!lstRoomOwner.isEmpty()) {
			RoomOwner roomOwner = lstRoomOwner.get(0);		
			roomOwner.setStatus(1);
			roomOwner.setChange(0);
			roomOwner.setIdUser(user.getIdUser());
			roomOwnerDao.update(roomOwner);
		}
		userDao.update(user);		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void openOrCloseTopic(RoomOwner roomOwner) {
		roomOwnerDao.update(roomOwner);
	}
}
