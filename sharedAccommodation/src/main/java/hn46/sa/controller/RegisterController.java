package hn46.sa.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import hn46.sa.entity.Account;
import hn46.sa.entity.User;
import hn46.sa.service.UserService;
import hn46.sa.util.AppServer;

@ManagedBean(name = "registerController")
@Component
public class RegisterController {
	private User user;	
	private boolean termCheck;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@PostConstruct
	public void init() {
		user = new User();
		user.setAccount(new Account());
	}

	public String register() {
		try {			
//			String hashPassword = bcrypt.encode(user.getAccount().getPassword());
//			user.getAccount().setPassword(hashPassword);
			
			User result = userService.register(user);
			if (result == null) {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				sessionMap.put("userSession", result);
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Successful", AppServer.getProp("sa-000")));			
		} catch (Exception ex) {
			System.out.println("msg " + AppServer.getProp(ex.getMessage()));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", AppServer.getProp(ex.getMessage())));
		}
		return "/shared.accommodation/search.xhtml";

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

	public boolean isTermCheck() {
		return termCheck;
	}

	public void setTermCheck(boolean termCheck) {
		this.termCheck = termCheck;
	}

}
