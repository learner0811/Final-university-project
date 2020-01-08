package hn46.sa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn46.sa.dao.AddressDao;
import hn46.sa.dao.FacilitiesDao;
import hn46.sa.dao.ImageDao;
import hn46.sa.dao.RoomDao;
import hn46.sa.dao.RoomFacilitiesDao;
import hn46.sa.dao.RoomOwnerDao;
import hn46.sa.dao.RoommateCriteriaDao;
import hn46.sa.dao.UserDao;
import hn46.sa.entity.Address;
import hn46.sa.entity.Facilities;
import hn46.sa.entity.Image;
import hn46.sa.entity.Room;
import hn46.sa.entity.RoomFacilities;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.User;

@Service
public class SearchService {

	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoommateCriteriaDao roommateDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private RoomFacilitiesDao roomFacilitiesDao;
	
	@Autowired
	private FacilitiesDao facilitiesDao;
	
	@Autowired
	private RoommateCriteriaDao roommateCriteriaDao;
	
	@Autowired
	private UserDao userDao;
	
	public List<RoomOwner> loadAllData(){
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
	
	public RoomOwner findById(int idRoomOwner) {
		RoomOwner roomOwner = new RoomOwner();
		roomOwner.setIdRoomOwner(idRoomOwner);
		roomOwner = roomOwnerDao.findById(idRoomOwner);
		User user = userDao.findByid2(roomOwner.getIdUser());
		roomOwner.setFirstName(user.getFirstName());
		roomOwner.setLastName(user.getLastName());
		Room room = roomDao.findById(roomOwner.getRoom().getIdRoom());
		Address address = addressDao.findById(room.getAddress().getIdAddress());
		room.setAddress(address);
		Map params = new HashMap<String, Object>();
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
	
}
