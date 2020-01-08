package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.LandLord;
import hn46.sa.entity.Room;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.RoommateCriteria;

public class RoomOwnerMapper implements RowMapper<RoomOwner> {

	@Override
	public RoomOwner mapRow(ResultSet rs, int arg1) throws SQLException {
		LandLord roomOwner = new LandLord();
		roomOwner.setRoomNumber(rs.getInt("room_number"));
		roomOwner.setAvailable(rs.getInt("room_available"));
		roomOwner.setAge(rs.getInt("age"));
		roomOwner.setCivilStatus(rs.getInt("civil_status"));
		roomOwner.setGender(rs.getInt("gender"));
		roomOwner.setIdRoomOwner(rs.getInt("idroomowner"));
		roomOwner.setInterests(rs.getString("interests"));
		roomOwner.setName(rs.getString("name"));
		roomOwner.setOcupation(rs.getString("ocupation"));
		roomOwner.setSmoker(rs.getInt("smoker"));
		roomOwner.setStrNative(rs.getString("native"));
		roomOwner.setPhone(rs.getString("phone"));
		roomOwner.setEmail(rs.getString("email"));
		roomOwner.setChange(rs.getInt("is_change"));
		roomOwner.setStatus(rs.getInt("status"));
		roomOwner.setType(rs.getInt("type"));
		Room room = new Room();
		room.setIdRoom(rs.getInt("idroom"));
		roomOwner.setRoom(room);		
		RoommateCriteria roommate = new RoommateCriteria();
		roommate.setIdRoommateCriteria(rs.getInt("idroommate_criteria"));
		roomOwner.setRoommateCriteria(roommate);
		roomOwner.setIdUser(rs.getInt("iduser"));		
		return roomOwner;
	}

}
