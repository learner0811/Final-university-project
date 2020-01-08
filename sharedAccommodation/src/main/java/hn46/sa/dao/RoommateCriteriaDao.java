package hn46.sa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import hn46.sa.entity.RoommateCriteria;
import hn46.sa.mapper.RoommateCriteriaMapper;

@Repository
public class RoommateCriteriaDao implements BaseDao<RoommateCriteria>{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public RoommateCriteria save(final RoommateCriteria roommate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into roommate_criteria (age, gender, children, civil_status, cleanclienss, smoker, pet_allowed) values (?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, roommate.getAge());
				ps.setInt(2, roommate.getGender());
				ps.setInt(3, roommate.getChildren());
				ps.setInt(4, roommate.getCivil_status());
				ps.setInt(5, roommate.getCleancliness());
				ps.setInt(6, roommate.getSmoker());
				ps.setInt(7, roommate.getPetAllowed());
				return ps;
			}
		}, keyHolder);		
		roommate.setIdRoommateCriteria(keyHolder.getKey().intValue());
		return roommate;
	}

	@Override
	public RoommateCriteria update(RoommateCriteria roommate) {
		Object[] params = {roommate.getAge(), roommate.getGender(), roommate.getChildren(), roommate.getCivil_status(), roommate.getCleancliness(), roommate.getSmoker(), roommate.getPetAllowed(), roommate.getIdRoommateCriteria()};
		int[] types = {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
		String updateSql = "update roommate_criteria set age = ?, gender = ?, children = ?, civil_status = ?, cleanclienss = ?, smoker = ?, pet_allowed = ? where idroommate_criteria = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);		 		   		
		System.out.println(rows + " row(s) updated.");
		return roommate;
	}

	@Override
	public RoommateCriteria findById(int id) {
		String sql = "SELECT * FROM roommate_criteria WHERE idroommate_criteria = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new RoommateCriteriaMapper());		
	}

	@Override
	public Iterable<RoommateCriteria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(RoommateCriteria entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<RoommateCriteria> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
