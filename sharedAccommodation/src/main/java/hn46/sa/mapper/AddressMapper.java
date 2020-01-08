package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.Address;

public class AddressMapper implements RowMapper<Address> {

	@Override
	public Address mapRow(ResultSet rs, int rownum) throws SQLException {
		Address address = new Address();
		address.setIdAddress(rs.getInt(1));
		address.setNumber(rs.getInt("number"));
		address.setStreet(rs.getString("street"));
		address.setDistrict(rs.getString("district"));
		address.setCity(rs.getString("city"));
		address.setLatitude(rs.getDouble("latitude"));
		address.setLongitude(rs.getDouble("longitude"));
		return address;
	}

}
