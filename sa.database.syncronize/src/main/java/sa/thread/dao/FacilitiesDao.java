package sa.thread.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sa.thread.entity.Facilities;
import sa.thread.mapper.FacilitiesMapper;

@Repository
public class FacilitiesDao implements BaseDao<Facilities> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Facilities save(final Facilities facilities) {
		return null;
	}

	@Override
	public Facilities update(Facilities entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facilities findById(int id) {
		String sql = "SELECT * FROM  facilities WHERE idfacilities = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new FacilitiesMapper());
	}
	
	public Facilities findByName(String name) {
		String sql = "SELECT * from facilities WHERE name = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{name}, new FacilitiesMapper());
	}

	@Override
	public Iterable<Facilities> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facilities> findAll() {
		List<Facilities> listFacilites = new ArrayList<>();
		String sql = "select * from facilities";		
		listFacilites = jdbcTemplate.query(sql, new FacilitiesMapper());	
		return listFacilites;
	}

	@Override
	public void delete(Facilities entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
