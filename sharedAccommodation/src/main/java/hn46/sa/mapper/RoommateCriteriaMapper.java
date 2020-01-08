package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.RoommateCriteria;

public class RoommateCriteriaMapper implements RowMapper<RoommateCriteria> {

	@Override
	public RoommateCriteria mapRow(ResultSet rs, int rownum) throws SQLException {		
		RoommateCriteria roommate = new RoommateCriteria();
		roommate.setIdRoommateCriteria(rs.getInt(1));
		roommate.setAge(rs.getString("age"));
		roommate.setChildren(rs.getInt("children"));
		roommate.setCivil_status(rs.getInt("civil_status"));
		roommate.setCleancliness(rs.getInt("cleanclienss"));
		roommate.setGender(rs.getInt("gender"));
		roommate.setPetAllowed(rs.getInt("pet_allowed"));
		roommate.setSmoker(rs.getInt("smoker"));
		roommate.setStatus(rs.getInt("status"));
		return roommate;
	}

}
