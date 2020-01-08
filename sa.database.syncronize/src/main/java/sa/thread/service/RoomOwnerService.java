package sa.thread.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sa.thread.dao.AddressDao;
import sa.thread.dao.FacilitiesDao;
import sa.thread.dao.ImageDao;
import sa.thread.dao.RoomDao;
import sa.thread.dao.RoomFacilitiesDao;
import sa.thread.dao.RoomOwnerDao;
import sa.thread.dao.UserDao;
import sa.thread.entity.Address;
import sa.thread.entity.Facilities;
import sa.thread.entity.Image;
import sa.thread.entity.Room;
import sa.thread.entity.RoomFacilities;
import sa.thread.entity.RoomOwner;
import sa.thread.entity.User;

@Service
public class RoomOwnerService {

	@Autowired
	private RoomOwnerDao roomOwnerDao;

	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private RoomFacilitiesDao roomFacilitiesDao;
	
	@Autowired
	private FacilitiesDao facilitiesDao;
		
	@Autowired
	private UserDao userDao;

	public List<RoomOwner> loadAllData() {
		List<RoomOwner> lstRoomOwner = roomOwnerDao.findAll();
		for (int i = 0; i < lstRoomOwner.size(); i++) {
			RoomOwner roomOwner = lstRoomOwner.get(i);						
			Room room = roomDao.findById(roomOwner.getRoom().getIdRoom());					
			Map params = new HashMap<String, Object>();
			params.put("status", 1);
			params.put("type", 2);	//room type
			params.put("iddata", room.getIdRoom());		
			List<Image> lstRoomImg = imageDao.findByParam(params);
			room.setImages(lstRoomImg.toArray(new Image[lstRoomImg.size()]));
			Address address = addressDao.findById(room.getAddress().getIdAddress());
			room.setAddress(address);
			params.clear();
			params.put("idroom", room.getIdRoom());
			List<Facilities> lstTmp = new ArrayList<>();
			List<RoomFacilities> lstRoomFacilities = roomFacilitiesDao.findByParam(params);
			for (int j = 0; j < lstRoomFacilities.size(); j++) {
				Facilities facilites = facilitiesDao.findById(lstRoomFacilities.get(j).getFacilities().getIdfacilities());
				lstTmp.add(facilites);
			}
			room.setFacilities(lstTmp.toArray(new Facilities[lstTmp.size()]));
			roomOwner.setRoom(room);
		}
		return lstRoomOwner;
	}
	
	public List<RoomOwner> delete() {
		List<RoomOwner> lstRoomOwner = roomOwnerDao.findAll();
		for (int i = 0; i < lstRoomOwner.size(); i++) {
			RoomOwner roomOwner = lstRoomOwner.get(i);
			User user = userDao.findByid2(roomOwner.getIdUser());
			roomOwner.setType(user.getType());
			roomOwner.setFirstName(user.getFirstName());
			roomOwner.setLastName(user.getLastName());
		}
		return lstRoomOwner;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void update(RoomOwner roomOwner) {
		roomOwnerDao.update(roomOwner);
	}
}
