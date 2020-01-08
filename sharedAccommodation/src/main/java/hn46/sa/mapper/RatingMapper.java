package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.Rating;
import hn46.sa.entity.User;

public class RatingMapper implements RowMapper<Rating> {

	@Override
	public Rating mapRow(ResultSet rs, int rownum) throws SQLException {
		Rating rating = new Rating();
		rating.setIdRating(rs.getInt(1));
		rating.setIddata(rs.getInt("iddata"));
		User user = new User();
		user.setIdUser(rs.getInt("iduser"));
		rating.setUser(user);
		rating.setStar(rs.getInt("star"));
		rating.setType(rs.getInt("type"));
		return rating;
	}

}
