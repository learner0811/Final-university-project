package hn46.sa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import hn46.sa.entity.Conversation;
import hn46.sa.entity.Message;
import hn46.sa.mapper.ImageMapper;
import hn46.sa.mapper.MessageMapper;

@Repository
public class MessageDao implements BaseDao<Message>{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Message save(final Message message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into message (text_message, is_read, status, idcreator, idconversation) values (?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, message.getText_message());
				ps.setInt(2, 0);
				ps.setInt(3, 1);
				ps.setInt(4, message.getCreator().getIdUser());
				ps.setInt(5, message.getConversation().getIdConversation());				
				return ps;
			}
		}, keyHolder);		
		message.setIdmessage(keyHolder.getKey().intValue());		
		return message;		
	}

	@Override
	public Message update(Message entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findById(int id) {
		String sql = "SELECT * FROM message WHERE idmessage = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new MessageMapper());
	}

	@Override
	public List<Message> findByParam(Map<String, Object> params) {
		String sql = "select * from message where 1=1 ";		
		List<Object> args = new ArrayList<Object>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sql +=  " and " + entry.getKey() + " = ?"; 
			args.add(entry.getValue());
		}		
		return jdbcTemplate.query(sql, args.toArray(), new MessageMapper());
	}
	
	public List<Message> latestMessage(Conversation conversation) {
		String sql = "select * from message where create_date = (select max(create_date) from message where idconversation = ?)";
		 return jdbcTemplate.query(
	                sql, new Object[]{conversation.getIdConversation()}, new MessageMapper());
	}

	@Override
	public Iterable<Message> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Message entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}
}
