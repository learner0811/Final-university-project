package hn46.sa.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import hn46.sa.entity.Account;
import hn46.sa.entity.Conversation;
import hn46.sa.entity.User;
import hn46.sa.service.ConversationService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.service.UserService;
import hn46.sa.util.AppServer;


@ManagedBean(name = "loginController")
//@Component
public class LoginController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private UserService userService = (UserService) ServiceContainer.getBean("userService");	
	private ConversationService consersationService = (ConversationService) ServiceContainer.getBean(ConversationService.class);
	@ManagedProperty(value="#{headerController}")	 
	private HeaderController header;
	
	private User user;
	private Account account;
	private boolean loginFlag;
	private List<Conversation> lstConversation;
	private String previousPage;
	private int idParam;
	
	@PostConstruct
	public void init() {
		user = new User();
		account = new Account();			
	}
			
	public void doLogin() {								
		FacesContext context = FacesContext.getCurrentInstance();
		user.setAccount(account);					
		try {
			User result = userService.login(user);
			if (result.getStatus() == 0) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", AppServer.getProp("sa-017")));
				return;
			}
			result = userService.getUesrInfo(result);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.put("userSession", result);			
			header.setSessionBean(result);
			header.setSender();
					
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", AppServer.getProp("sa-006")));
		} catch (Exception ex) {			
			ex.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", AppServer.getProp("sa-005")));
		}		
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}	

	public boolean isLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(boolean loginFlag) {
		this.loginFlag = loginFlag;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HeaderController getHeader() {
		return header;
	}

	public void setHeader(HeaderController header) {
		this.header = header;
	}	

	public List<Conversation> getLstConversation() {
		return lstConversation;
	}

	public void setLstConversation(List<Conversation> lstConversation) {
		this.lstConversation = lstConversation;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public int getIdParam() {
		return idParam;
	}

	public void setIdParam(int idParam) {
		this.idParam = idParam;
	}
	
}
