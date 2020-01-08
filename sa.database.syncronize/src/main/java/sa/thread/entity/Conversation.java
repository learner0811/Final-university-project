package sa.thread.entity;

import java.util.Date;
import java.util.List;

public class Conversation {
	private int idConversation;
	private List<Message> messages;
	private User participantA;
	private User participantB;
	private Date createDate;
	
	public int getIdConversation() {
		return idConversation;
	}
	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public User getParticipantA() {
		return participantA;
	}
	public void setParticipantA(User participantA) {
		this.participantA = participantA;
	}
	public User getParticipantB() {
		return participantB;
	}
	public void setParticipantB(User participantB) {
		this.participantB = participantB;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
