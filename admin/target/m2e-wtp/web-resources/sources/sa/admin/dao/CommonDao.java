package sa.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import sa.admin.dto.KeyValueDto;

@Repository
public class CommonDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<KeyValueDto> staticByCity(){
		String sql = "select count(1) v, city k from address group by city";
		List<KeyValueDto> result = new ArrayList<>();
		result = jdbcTemplate.query(sql, new RowMapper<KeyValueDto>() {

			@Override
			public KeyValueDto mapRow(ResultSet rs, int rownum) throws SQLException {
				KeyValueDto kv = new KeyValueDto();
				kv.setKey(rs.getString("k"));
				kv.setValue(rs.getString("v"));
				return kv;
			}
			
		});
		
		return result;
	}
	
	public List<KeyValueDto> staticByRent(){
		String sql = "select rent from room";
		List<KeyValueDto> result = new ArrayList<>();
		result = jdbcTemplate.query(sql, new RowMapper<KeyValueDto>() {

			@Override
			public KeyValueDto mapRow(ResultSet rs, int rownum) throws SQLException {
				KeyValueDto kv = new KeyValueDto();
				kv.setKey("");
				kv.setValue(rs.getString("rent"));
				return kv;
			}			
		});
		return result;
	}
}
