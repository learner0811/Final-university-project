package sa.thread.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.thread.entity.Facilities;

public class FacilitiesMapper implements RowMapper<Facilities> {

	@Override
	public Facilities mapRow(ResultSet rs, int arg1) throws SQLException {
		Facilities facility = new Facilities();
		facility.setIdfacilities(rs.getInt(1));
		facility.setIcon(rs.getString("icon"));
		facility.setName(rs.getString("name"));
		facility.setStatus(rs.getInt("status"));
		return facility;
	}

}
