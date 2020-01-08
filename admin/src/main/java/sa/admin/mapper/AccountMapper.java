package sa.admin.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.admin.entity.Account;

public class AccountMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int arg1) throws SQLException {
		Account account = new Account();
		account.setIdAccount(rs.getInt(1));
		account.setUsername(rs.getString("username"));
		account.setPassword(rs.getString("password"));
		return account;
	}

}
