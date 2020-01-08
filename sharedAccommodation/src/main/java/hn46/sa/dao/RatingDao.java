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

import hn46.sa.entity.Rating;
import hn46.sa.mapper.RatingMapper;
import hn46.sa.mapper.RoomMapper;

@Repository
public class RatingDao implements BaseDao<Rating> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Rating save(Rating rating) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into rating (star, iduser, iddata, type) values (?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, rating.getStar());
				ps.setInt(2, rating.getUser().getIdUser());
				ps.setInt(3, rating.getIddata());
				ps.setInt(4, rating.getType());
				return ps;
			}
		}, keyHolder);		
		rating.setIdRating(keyHolder.getKey().intValue());		
		return rating;
	}

	@Override
	public Rating update(Rating rating) {
		Object[] params = { rating.getStar(), rating.getUser().getIdUser(),  rating.getIddata(), rating.getType() ,rating.getIdRating()};
		int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
		String updateSql = "update rating set star = ?, iduser = ?, iddata = ?, type = ? where idrating = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);		 		   		
		System.out.println(rows + " row(s) updated.");
		return rating;
	}

	@Override
	public Rating findById(int id) {
		String sql = "SELECT * FROM rating WHERE idrating = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new RatingMapper());
	}

	@Override
	public Iterable<Rating> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Rating> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Rating entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public int existsByIdData(Rating rating) {
		String sql = "select idrating from rating where iduser = ? and iddata = ? and type = ?";		
		Object[] args = new Object[] { rating.getUser().getIdUser(), rating.getIddata(), rating.getType()};
		int idrating = 0;
		try {
			idrating = jdbcTemplate.queryForObject(sql, args, Integer.class);
		} catch (Exception ex) {
			
		}		
		return idrating;		
	}	
	
	public double getRatingStar(Rating rating) {
		String sql = "SELECT avg(star) FROM mydb.rating where type = ? and iddata = ?";
		Object[] args = new Object[] { rating.getType(), rating.getIddata()};
		double star = 5;		
		Double result = jdbcTemplate.queryForObject(sql, args, Double.class);
		if (result != null)
			star = result;
		return star;		
	}
}
