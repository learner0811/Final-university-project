package sa.thread.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sa.thread.entity.RoomOwner;
import sa.thread.mapper.RoomOwnerMapper;

@Repository
public class RoomOwnerDao implements BaseDao<RoomOwner> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public RoomOwner save(final RoomOwner roomOwner) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into room_owner (name, gender, age, native, interests, civil_status, smoker, ocupation, iduser, idroom, idroommate_criteria, phone, email) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, roomOwner.getName());
				ps.setInt(2, roomOwner.getGender());
				ps.setInt(3, roomOwner.getAge());
				ps.setString(4, roomOwner.getStrNative());
				ps.setString(5, roomOwner.getInterests());
				ps.setInt(6, roomOwner.getCivilStatus());
				ps.setInt(7, roomOwner.getSmoker());
				ps.setString(8, roomOwner.getOcupation());
				ps.setInt(9, roomOwner.getIdUser());
				ps.setInt(10, roomOwner.getRoom().getIdRoom());
				ps.setInt(11, roomOwner.getRoommateCriteria().getIdRoommateCriteria());
				ps.setString(12, roomOwner.getPhone());
				ps.setString(13, roomOwner.getEmail());
				return ps;
			}
		}, keyHolder);
		roomOwner.setIdRoomOwner(keyHolder.getKey().intValue());
		return roomOwner;
	}

	@Override
	public RoomOwner update(RoomOwner roomOwner) {
		/*Object[] params = { roomOwner.getName(), roomOwner.getAge(), roomOwner.getStrNative(), roomOwner.getInterests(),
				roomOwner.getCivilStatus(), roomOwner.getSmoker(), roomOwner.getOcupation(), roomOwner.getStatus(),
				roomOwner.getIdUser(), roomOwner.getRoom().getIdRoom(),
				roomOwner.getRoommateCriteria().getIdRoommateCriteria(), roomOwner.getPhone(), roomOwner.getEmail(), roomOwner.getIdRoomOwner() };
		int[] types = { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
		System.out.println(roomOwner.getIdUser());
		String updateSql = "update room_owner set name = ?, age = ?, native = ?, interests = ?, civil_status = ?, smoker = ?, ocupation = ?, status = ?, iduser = ?, idroom = ?, idroommate_criteria = ?, phone = ?, email = ? where idroomowner = ?";*/
		Object[] params = {roomOwner.getChange(), roomOwner.getIdRoomOwner()};
		int[] types = {Types.INTEGER, Types.INTEGER};
		String updateSql = "update room_owner set is_change = ? where idroomowner = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);
		System.out.println(rows + " row(s) updated.");
		return roomOwner;
	}

	@Override
	public RoomOwner findById(int id) {
		String sql = "SELECT * FROM ROOM_OWNER WHERE idroomowner = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new RoomOwnerMapper());
	}

		
	@Override
	public List<RoomOwner> findAll() {
		String sql = "select * from room_owner where is_change = 0";
		return jdbcTemplate.query(sql, new RoomOwnerMapper());
	}

	@Override
	public void delete(RoomOwner entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoomOwner> findByParam(Map<String, Object> params) {
		String sql = "select * from room_owner where 1=1 ";
		List<Object> args = new ArrayList<Object>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sql += " and " + entry.getKey() + " = ?";
			args.add(entry.getValue());
		}
		return jdbcTemplate.query(sql, args.toArray(), new RoomOwnerMapper());
	}

}
