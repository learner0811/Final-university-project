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

import sa.thread.entity.RoomFacilities;
import sa.thread.mapper.RoomFacilitiesMapper;

@Repository
public class RoomFacilitiesDao implements BaseDao<RoomFacilities> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public RoomFacilities save(final RoomFacilities entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into room_has_facilities (idroom, idfacilities) values (?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, entity.getRoom().getIdRoom());
				ps.setInt(2, entity.getFacilities().getIdfacilities());
				return ps;
			}
		}, keyHolder);
		entity.setId(keyHolder.getKey().intValue());
		return entity;
	}

	@Override
	public RoomFacilities update(RoomFacilities entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomFacilities findById(int id) {
		String sql = "SELECT * FROM room_has_facilities WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { id }, new RoomFacilitiesMapper());
	}

	@Override
	public List<RoomFacilities> findByParam(Map<String, Object> params) {
		String sql = "select * from room_has_facilities where 1=1 ";
		List<Object> args = new ArrayList<Object>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sql += " and " + entry.getKey() + " = ?";
			args.add(entry.getValue());
		}
		return jdbcTemplate.query(sql, args.toArray(), new RoomFacilitiesMapper());
	}

	@Override
	public Iterable<RoomFacilities> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(RoomFacilities entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	public void deleteByRoom(int idRoom) {
		String sql = "delete from room_has_facilities where idroom = ? ";
		List<Object> args = new ArrayList<Object>();	
		Object[] params = { idRoom };
		 		         
        int[] types = {Types.INTEGER};
		int rows = jdbcTemplate.update(sql, params, types);

		System.out.println(rows + " row(s) deleted.");
	}

}
