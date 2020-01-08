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

import hn46.sa.entity.Address;
import hn46.sa.mapper.AddressMapper;

@Repository
public class AddressDao implements BaseDao<Address>{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Address save(final Address address) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into address (number, street, district, province, city, latitude, longitude) values (?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, address.getNumber());
				ps.setString(2, address.getStreet());
				ps.setString(3, address.getDistrict());
				ps.setString(4, address.getProvince());
				ps.setString(5, address.getCity());
				ps.setDouble(6, address.getLatitude());
				ps.setDouble(7, address.getLongitude());
				return ps;
			}
		}, keyHolder);
		address.setIdAddress(keyHolder.getKey().intValue());
		return address;
	}

	@Override
	public Address update(Address adress) {
		Object[] params = {adress.getNumber(), adress.getStreet(), adress.getDistrict(), adress.getProvince(), adress.getCity(), adress.getLatitude(), adress.getLongitude() ,adress.getIdAddress()};
		int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE ,Types.INTEGER};
		String updateSql = "update address set number = ?, street = ?, district = ?, province = ?, city = ?, latitude = ?, longitude = ? where idaddress = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);		 		   		
		System.out.println(rows + " row(s) updated.");
		return adress;
	}

	@Override
	public Address findById(int id) {
		String sql = "SELECT * FROM address WHERE idaddress = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new AddressMapper());
	}

	@Override
	public Iterable<Address> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Address entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Address> findByParam(Map<String, Object> params) {
		
		return null;
	}
	
	

}
