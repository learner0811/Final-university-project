package sa.thread.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.thread.entity.Rating;

public class RatingMapper implements RowMapper<Rating> {

	@Override
	public Rating mapRow(ResultSet rs, int rownum) throws SQLException {
		Rating rating = new Rating();
		rating.setIdRating(rs.getInt(1));
		rating.setIddata(rs.getInt("iddata"));
		rating.setIdUser(rs.getInt("iduser"));
		rating.setStar(rs.getInt("star"));
		rating.setType(rs.getInt("type"));
		return rating;
	}

}
