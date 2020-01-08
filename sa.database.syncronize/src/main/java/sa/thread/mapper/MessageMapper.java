package sa.thread.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.thread.entity.Conversation;
import sa.thread.entity.Message;
import sa.thread.entity.User;

public class MessageMapper implements RowMapper<Message> {

	@Override
	public Message mapRow(ResultSet rs, int rownum) throws SQLException {
		Message message = new Message();
		message.setIdmessage(rs.getInt(1));
		message.setCreateDate(rs.getTimestamp("create_date"));
		message.setRead(rs.getInt("is_read") == 1 ? true : false);
		message.setStatus(rs.getInt("status"));
		message.setText_message(rs.getString("text_message"));
		User creator = new User();
		creator.setIdUser(rs.getInt("idcreator"));
		message.setCreator(creator);
		Conversation conversation = new Conversation();
		conversation.setIdConversation(rs.getInt("idconversation"));
		message.setConversation(conversation);
		return message;
	}

}
