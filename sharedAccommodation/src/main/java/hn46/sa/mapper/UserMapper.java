package hn46.sa.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hn46.sa.entity.Account;
import hn46.sa.entity.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rownum) throws SQLException {
		User user = new User();
		user.setIdUser(rs.getInt(1));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		Account account = new Account();
		account.setIdAccount(rs.getInt("idaccount"));
		user.setAccount(account);
		user.setType(rs.getInt("type"));
		user.setStatus(rs.getInt("status"));
		return user;
	}

}
