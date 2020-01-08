package hn46.sa.dao;

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

import hn46.sa.entity.Room;
import hn46.sa.mapper.RoomMapper;

@Repository
public class RoomDao implements BaseDao<Room>{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Room save(final Room room) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into room (square, title, rent, moving_date, period, description, idaddress) values (?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, room.getSquare());
				ps.setString(2, room.getTitle());
				ps.setInt(3, room.getRent());
				ps.setDate(4, new Date(room.getMovingDate().getTime()));
				ps.setString(5, room.getPeriod());
				ps.setString(6, room.getDescription());
				ps.setInt(7, room.getAddress().getIdAddress());
				return ps;
			}
		}, keyHolder);		
		room.setIdRoom(keyHolder.getKey().intValue());
		return room;
	}

	@Override
	public Room update(Room room) {
		Object[] params = {room.getSquare(), room.getTitle(), room.getStatus(), room.getRent(), new Date(room.getMovingDate().getTime()), room.getPeriod(), room.getDescription() , room.getAddress().getIdAddress(), room.getIdRoom()};
		int[] types = {Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
		String updateSql = "update room set square = ?, title = ?, status = ?, rent = ?, moving_date = ?, period = ?, description = ?, idaddress = ? where idroom = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);		 		   		
		System.out.println(rows + " row(s) updated.");
		return room;
	}

	@Override
	public Room findById(int id) {
		String sql = "SELECT * FROM ROOM WHERE IDROOM = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new RoomMapper());		
	}

	@Override
	public Iterable<Room> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Room entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Room> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
