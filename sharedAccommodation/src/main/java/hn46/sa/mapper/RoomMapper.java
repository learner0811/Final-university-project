package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.Address;
import hn46.sa.entity.Room;

public class RoomMapper implements RowMapper<Room> {

	@Override
	public Room mapRow(ResultSet rs, int arg1) throws SQLException {
		Room room = new Room();
		room.setIdRoom(rs.getInt(1));
		room.setCreateDate(new Date(rs.getTimestamp("create_date").getTime()));
		room.setDescription(rs.getString("description"));
		room.setPeriod(rs.getString("period"));
		room.setMovingDate(new Date(rs.getDate("moving_date").getTime()));
		room.setRent(rs.getInt("rent"));
		room.setSquare(rs.getInt("square"));
		room.setTitle(rs.getString("title"));
		room.setStatus(rs.getInt("status"));
		Address address = new Address();
		address.setIdAddress(rs.getInt("idaddress"));
		room.setAddress(address);
		return room;
	}

}
