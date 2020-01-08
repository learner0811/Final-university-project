package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.Facilities;
import hn46.sa.entity.Room;
import hn46.sa.entity.RoomFacilities;

public class RoomFacilitiesMapper implements RowMapper<RoomFacilities> {

	@Override
	public RoomFacilities mapRow(ResultSet rs, int rownum) throws SQLException {
		RoomFacilities roomFacilities = new RoomFacilities();
		roomFacilities.setId(rs.getInt(1));
		Room room = new Room();
		room.setIdRoom(rs.getInt("idroom"));
		roomFacilities.setRoom(room);
		Facilities facilities = new Facilities();
		facilities.setIdfacilities(rs.getInt("idfacilities"));
		roomFacilities.setFacilities(facilities);
		return roomFacilities;
	}

}
