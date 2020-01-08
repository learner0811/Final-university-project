package sa.thread.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sa.thread.entity.Conversation;
import sa.thread.entity.User;

public class ConversationMapper implements RowMapper<Conversation> {

	@Override
	public Conversation mapRow(ResultSet rs, int rownum) throws SQLException {
		Conversation conversation = new Conversation();
		conversation.setIdConversation(rs.getInt(1));
		User pa = new User();
		User pb = new User();		
		pa.setIdUser(rs.getInt("iduser_a"));
		pb.setIdUser(rs.getInt("iduser_b"));
		conversation.setParticipantA(pa);
		conversation.setParticipantB(pb);		
		return conversation;
	}

}
