package hn46.sa.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hn46.sa.dto.ParamDto;
import hn46.sa.entity.Facilities;
import hn46.sa.mapper.FacilitiesMapper;
import hn46.sa.mapper.NativeRowMapper;

@Repository
public class CommonDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<ParamDto> loadNative(){
		List<ParamDto> listNative = new ArrayList<>();
		String sql = "select * from native";		
		listNative = jdbcTemplate.query(sql, new NativeRowMapper());	
		return listNative;
	}	
}
