package hn46.sa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import hn46.sa.entity.Conversation;
import hn46.sa.entity.User;
import hn46.sa.mapper.ConversationMapper;

@Repository
public class ConversationDao implements BaseDao<Conversation> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Conversation save(final Conversation conversation) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into conversation (iduser_a, iduser_b) values (?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, conversation.getParticipantA().getIdUser());
				ps.setInt(2, conversation.getParticipantB().getIdUser());				
				return ps;
			}
		}, keyHolder);		
		conversation.setIdConversation(keyHolder.getKey().intValue());		
		return conversation;
	}

	@Override
	public Conversation update(Conversation entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Conversation findById(int primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Conversation findByParticipants(int idUserA, int idUserB) {		
		String sql = "SELECT * FROM conversation WHERE (iduser_a = ? and iduser_b = ?) or (iduser_a = ? and iduser_b = ?)";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{idUserA, idUserB, idUserB, idUserA}, new ConversationMapper());
	}

	@Override
	public Iterable<Conversation> findByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Conversation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Conversation> findByUser(User user) {
		String sql = "select * from conversation where (iduser_a = ?) or (iduser_b = ?)";		
		Object[] args = new Object[] { user.getIdUser(), user.getIdUser()};					
		return jdbcTemplate.query(sql, args, new ConversationMapper());			
	}

	@Override
	public void delete(Conversation entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean existsConversation(int userA, int userB) {
		String sql = "select count(*) from conversation where (iduser_a = ? and iduser_b = ?) or (iduser_a = ? and iduser_b = ?)";		
		Object[] args = new Object[] { userA, userB, userB, userA };			
		int count = jdbcTemplate.queryForObject(sql, args, Integer.class);
		return count != 0;
	}

}
