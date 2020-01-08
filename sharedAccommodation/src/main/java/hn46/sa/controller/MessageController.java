package hn46.sa.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import hn46.sa.entity.Conversation;
import hn46.sa.entity.Message;
import hn46.sa.entity.User;
import hn46.sa.service.ConversationService;
import hn46.sa.service.ServiceContainer;

@ManagedBean(name = "messageController")
@ViewScoped
public class MessageController {		
	private Message message;
	private User sender;
	private User receiver;
	private String comingMessage;
	private Conversation conversation;
	private ConversationService consersationService = ServiceContainer.getBean(ConversationService.class);
	private String test = "";
	//new
	private User userSession;
	
	@PostConstruct
	public void init() {				
		FacesContext context = FacesContext.getCurrentInstance();
		userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		//PrimeFaces.current().executeScript("mountInbox();");
	}
	
	/*public void openInbox() {				
		FacesContext context = FacesContext.getCurrentInstance();
		User userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		if (userSession == null) {
			try {				
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", AppServer.getProp("sa-012")));
				context.getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			sender = userSession;
			if (sender.getIdUser() == receiver.getIdUser()) {				
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", AppServer.getProp("sa-013")));				
			}else {
				conversation.setParticipantA(sender);
				conversation.setParticipantB(receiver);
				conversation = consersationService.loadConversation(conversation);
				PrimeFaces.current().executeScript("show();");
			}
			//PrimeFaces.current().ajax().update("");			 
			try {
				context.getExternalContext().redirect("message.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	public void send() {
		try {				
			message.setCreator(sender);
			message.setText_message(comingMessage);
			conversation = consersationService.sendMesage(conversation, message);			
			comingMessage = "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}*/

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getComingMessage() {
		return comingMessage;
	}

	public void setComingMessage(String comingMessage) {
		this.comingMessage = comingMessage;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}			
}
