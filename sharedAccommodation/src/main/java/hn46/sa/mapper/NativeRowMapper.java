package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.dto.ParamDto;

public class NativeRowMapper implements RowMapper<ParamDto>{

	@Override
	public ParamDto mapRow(ResultSet rs, int arg1) throws SQLException {
		ParamDto paramDto = new ParamDto();
		paramDto.setId(rs.getInt(1));
		paramDto.setName(rs.getString("name"));
		return paramDto;
	}

}
