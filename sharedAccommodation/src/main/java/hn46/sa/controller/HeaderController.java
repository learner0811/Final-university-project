package hn46.sa.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import hn46.sa.dto.ConversationDto;
import hn46.sa.dto.ParticipantDto;
import hn46.sa.entity.Conversation;
import hn46.sa.entity.User;

@ManagedBean(name = "headerController")
@SessionScoped
public class HeaderController implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	private User sessionBean;	
	private List<Conversation> lstConversation;
	private User receiver;
	private ConversationDto conversation;
	
	public User getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(User sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	@PostConstruct
	public void init() {
		receiver = new User();
		conversation = new ConversationDto();
		ParticipantDto pa = new ParticipantDto();
		conversation.setPa(pa);
	}
	
	public void setSender() {
		ParticipantDto pa = new ParticipantDto();
		pa.setId(sessionBean.getIdUser());
		pa.setFirstName(sessionBean.getFirstName());
		pa.setLastName(sessionBean.getLastName());
		pa.setFileName(sessionBean.getAvatar().getFileName());
		conversation.setPa(pa);
		PrimeFaces.current().executeScript("createTalkSession();");
	}
	
	public void logout() {
		setSessionBean(null);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
			sessionMap.put("userSession", null);
			context
			   .getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Conversation> getLstConversation() {
		return lstConversation;
	}

	public void setLstConversation(List<Conversation> lstConversation) {
		this.lstConversation = lstConversation;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public ConversationDto getConversation() {
		return conversation;
	}

	public void setConversation(ConversationDto conversation) {
		this.conversation = conversation;
	}		
}
