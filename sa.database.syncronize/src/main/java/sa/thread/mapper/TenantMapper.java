package sa.thread.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import sa.thread.entity.RoomCriteria;
import sa.thread.entity.RoommateCriteria;
import sa.thread.entity.Tenant;

public class TenantMapper implements RowMapper<Tenant> {

	@Override
	public Tenant mapRow(ResultSet rs, int rownum) throws SQLException {
		Tenant tenant = new Tenant();
		tenant.setIdTenant(rs.getInt(1));
		tenant.setName(rs.getString("name"));
		tenant.setStrNative(rs.getString("native"));
		tenant.setInterests(rs.getString("interests"));
		tenant.setFaceBook(rs.getString("facebook"));
		tenant.setInstagram(rs.getString("instagram"));
		tenant.setSelf_expression(rs.getString("sefl_expression"));
		tenant.setCreateDate(new Date(rs.getTimestamp("create_date").getTime()));
		tenant.setPhone(rs.getString("phone"));
		tenant.setEmail(rs.getString("email"));
		tenant.setStatus(rs.getInt("status"));
		RoommateCriteria roommate = new RoommateCriteria();
		roommate.setIdRoommateCriteria(rs.getInt("idroommate_criteria"));
		RoomCriteria room = new RoomCriteria();
		room.setIdRoomCriteria(rs.getInt("idroomcriteria"));
		tenant.setGender(rs.getInt("gender"));
		tenant.setRoomCriteria(room);
		tenant.setRoommateCriteria(roommate);
		tenant.setIdUser(rs.getInt("iduser"));
		return tenant;
	}

	

}
