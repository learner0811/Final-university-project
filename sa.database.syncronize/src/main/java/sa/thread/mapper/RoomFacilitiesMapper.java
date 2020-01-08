package sa.thread.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.thread.entity.Facilities;
import sa.thread.entity.Room;
import sa.thread.entity.RoomFacilities;

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
