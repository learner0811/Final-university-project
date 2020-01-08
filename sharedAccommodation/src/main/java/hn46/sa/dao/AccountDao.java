package hn46.sa.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hn46.sa.entity.Account;
import hn46.sa.mapper.AccountMapper;

@Repository
public class AccountDao implements BaseDao<Account> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Account save(Account entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account update(Account account) {
		String sql = "update account set username = ?, password = ? where idaccount = ? ";		
		int row = jdbcTemplate.update(sql, account.getUsername(), account.getPassword(), account.getIdAccount());
		System.out.println("row updated " + row);
		return account;
	}

	@Override
	public Account findById(int id) {
		String sql = "SELECT * FROM account WHERE idaccount = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new AccountMapper());
	}

	@Override
	public Iterable<Account> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Account entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
