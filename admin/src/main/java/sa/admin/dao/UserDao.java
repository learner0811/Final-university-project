package sa.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sa.admin.entity.Account;
import sa.admin.entity.User;
import sa.admin.mapper.UserMapper;
import sa.admin.util.AppException;
import sa.admin.util.AppServer;

@Repository
public class UserDao implements BaseDao<User>{		
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User login(User user) throws Exception {
		User result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from user u join account a on u.idaccount = a.idaccount where a.username = ? and a.password = ?";			
			conn = dataSource.getConnection();			
			ps = conn.prepareStatement(sql); 
			ps.setString(1, user.getAccount().getUsername());
			ps.setString(2, user.getAccount().getPassword());
			rs = ps.executeQuery();
			if (rs.next()) {
				Account account = new Account();
				account.setIdAccount(rs.getInt("a.idaccount"));
				account.setUsername(rs.getString("a.username"));
				account.setPassword(rs.getString("a.password"));
				result = new User();
				result.setAccount(account);
				result.setIdUser(rs.getInt("u.iduser"));
				result.setStatus(rs.getInt("u.status"));
				result.setType(rs.getInt("u.type"));
				result.setFirstName(rs.getString("u.first_name"));
				result.setLastName(rs.getString("u.last_name"));
			}
			else {
				throw new AppException("sa-005", "");
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			AppServer.closeObject(rs);
			AppServer.closeObject(ps);
			AppServer.closeObject(conn);
		}		
		return result;
		
	}
	
	public User register(User user) throws Exception {
		Connection conn = null;
		CallableStatement cstm = null;	
		User result = null;
		String code = "";
		try {
			conn = dataSource.getConnection();
			String sql = "{call proc_register(?,?,?,?,?,?)}";
			cstm = conn.prepareCall(sql);
			cstm.setString(1, user.getFirstName());
			cstm.setString(2, user.getLastName());
			cstm.setString(3, user.getAccount().getUsername());
			cstm.setString(4, user.getAccount().getPassword());
			cstm.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstm.registerOutParameter(6, java.sql.Types.INTEGER);
			cstm.executeUpdate();
			code = cstm.getString(5);
			int iduser = cstm.getInt(6);			
			if (code.equals("sa-000"))
				result = findById(iduser);
			else
				throw new AppException(code, "");
		} catch (Exception ex) {
			ex.printStackTrace();			
			throw ex;
		} finally {
			AppServer.closeObject(cstm);
			AppServer.closeObject(conn);
		}		
		return result;		
	}
	
	public User findById(int iduser) {
		User result = null;		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from user "
					+ " u join account a on u.idaccount = u.idaccount "														
					+ " where iduser = ?";			
			conn = dataSource.getConnection();			
			ps = conn.prepareStatement(sql); 
			ps.setInt(1, iduser);			
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				Account account = new Account();
				account.setIdAccount(rs.getInt("a.idaccount"));
				account.setUsername(rs.getString("a.username"));
				account.setPassword(rs.getString("a.password"));				
				result = new User();
				result.setAccount(account);				
				result.setIdUser(rs.getInt("u.iduser"));
				result.setStatus(rs.getInt("u.status"));				
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			AppServer.closeObject(rs);
			AppServer.closeObject(ps);
			AppServer.closeObject(conn);
		}						
		return result;
	}
	
	public User findByid2(int id) {
		String sql = "SELECT * FROM user WHERE iduser = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new UserMapper());
	}

	@Override
	public User save(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) {
		String sql = "update user set status = ?, type = ?, first_name = ?, last_name = ? where iduser = ? ";		
		int row = jdbcTemplate.update(sql, user.getStatus(), user.getType(), user.getFirstName(), user.getLastName(), user.getIdUser());
		System.out.println("row updated " + row);
		return user;
	}

	@Override
	public Iterable<User> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		String sql = "select * from user";
		return jdbcTemplate.query(sql, new UserMapper());
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}
}
