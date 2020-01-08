package sa.admin.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.admin.entity.RoomCriteria;

public class RoomCriteriaMapper implements RowMapper<RoomCriteria> {

	@Override
	public RoomCriteria mapRow(ResultSet rs, int rownum) throws SQLException {
		RoomCriteria room = new RoomCriteria();
		room.setIdRoomCriteria(rs.getInt(1));
		room.setRent(rs.getInt("rent"));
		room.setPeriod(rs.getString("period"));
		room.setMovingDate(rs.getTimestamp("movingDate"));
		room.setTitle(rs.getString("title"));
		room.setSearchLocation(rs.getString("search_location"));
		return room;
	}

}
