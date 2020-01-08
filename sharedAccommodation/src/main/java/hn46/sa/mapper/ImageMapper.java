package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.Image;

public class ImageMapper implements RowMapper<Image> {

	@Override
	public Image mapRow(ResultSet rs, int rownum) throws SQLException {
		Image image = new Image();
		image.setIdImage(rs.getInt(1));
		image.setFileName(rs.getString("file_name"));
		image.setStatus(rs.getInt("status"));
		image.setType(rs.getInt("type"));
		image.setIdData(rs.getInt("iddata"));
		return image;
	}

}
