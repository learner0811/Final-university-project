package sa.thread.dao;

import java.sql.Connection;
import java.sql.Date;
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

import sa.thread.entity.RoomCriteria;
import sa.thread.mapper.RoomCriteriaMapper;

@Repository
public class RoomCriteriaDao implements BaseDao<RoomCriteria> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public RoomCriteria save(final RoomCriteria room) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into room_criteria (rent, period, movingDate, title, search_location) values (?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, room.getRent());
				ps.setString(2, room.getPeriod());
				ps.setDate(3, new Date(room.getMovingDate().getTime()));
				ps.setString(4, room.getTitle());
				ps.setString(5, room.getSearchLocation());				
				return ps;
			}
		}, keyHolder);		
		room.setIdRoomCriteria(keyHolder.getKey().intValue());
		return room;
	}

	@Override
	public RoomCriteria update(RoomCriteria room) {
		Object[] params = {room.getRent(), room.getPeriod(), room.getMovingDate(), room.getTitle(), room.getSearchLocation(), room.getIdRoomCriteria()};
		int[] types = {Types.INTEGER, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
		String updateSql = "update room_criteria set rent = ?, period = ?, movingDate = ?, title = ?, search_location = ? where idroomcriteria = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);		 		   		
		System.out.println(rows + " row(s) updated.");
		return room;
	}

	@Override
	public RoomCriteria findById(int id) {
		String sql = "SELECT * FROM room_criteria WHERE idroomcriteria = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new RoomCriteriaMapper());		
	}

	@Override
	public Iterable<RoomCriteria> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<RoomCriteria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(RoomCriteria entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
