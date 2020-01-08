package sa.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sa.admin.entity.Tenant;
import sa.admin.mapper.TenantMapper;

@Repository
public class TenantDao implements BaseDao<Tenant> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Tenant save(final Tenant tenant) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into tenant (name, native, interests, facebook, instagram, sefl_expression, iduser, idroommate_criteria, idroomcriteria, gender, phone, email) values (?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, tenant.getName());
				ps.setString(2, tenant.getStrNative());
				ps.setString(3, tenant.getInterests());
				ps.setString(4, tenant.getFaceBook());
				ps.setString(5, tenant.getInstagram());
				ps.setString(6, tenant.getSelf_expression());
				ps.setInt(7, tenant.getIdUser());
				ps.setInt(8, tenant.getRoommateCriteria().getIdRoommateCriteria());
				ps.setInt(9, tenant.getRoomCriteria().getIdRoomCriteria());
				ps.setInt(10, tenant.getGender());
				ps.setString(11, tenant.getPhone());
				ps.setString(12, tenant.getEmail());
				return ps;
			}
		}, keyHolder);
		tenant.setIdTenant(keyHolder.getKey().intValue());
		return tenant;
	}

	@Override
	public Tenant update(Tenant tenant) {
		Object[] params = { tenant.getName(), tenant.getStrNative(), tenant.getStatus(), tenant.getInterests(),
				tenant.getFaceBook(), tenant.getInstagram(), tenant.getSelf_expression(), 
				tenant.getIdUser(), tenant.getRoommateCriteria().getIdRoommateCriteria(),
				tenant.getRoomCriteria().getIdRoomCriteria(), tenant.getGender(), tenant.getPhone(), tenant.getEmail(), tenant.getChange() ,tenant.getIdTenant() };
		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR , Types.INTEGER ,Types.INTEGER };
		String updateSql = "update tenant set name = ?, native = ?, status = ?, interests = ?, facebook = ?, instagram = ?, sefl_expression = ?, iduser = ?, idroommate_criteria = ?, idroomcriteria = ?, gender = ?, phone = ?, email = ?, is_change = ?  where idtenant = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);
		System.out.println(rows + " row(s) updated.");
		return tenant;
	}

	@Override
	public Tenant findById(int id) {
		String sql = "SELECT * FROM tenant WHERE idtenant = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new TenantMapper());
	}

	@Override
	public List<Tenant> findByParam(Map<String, Object> params) {
		String sql = "select * from tenant where 1=1 ";
		List<Object> args = new ArrayList<Object>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sql += " and " + entry.getKey() + " = ?";
			args.add(entry.getValue());
		}
		return jdbcTemplate.query(sql, args.toArray(), new TenantMapper());
	}

	@Override
	public List<Tenant> findAll() {
		String sql = "select * from tenant";// where status = 1";
		return jdbcTemplate.query(sql, new TenantMapper());
	}

	@Override
	public void delete(Tenant entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
