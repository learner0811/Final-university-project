package hn46.sa.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import hn46.sa.entity.User;
import hn46.sa.service.ServiceContainer;
import hn46.sa.service.UserService;
import hn46.sa.util.AppServer;

@ViewScoped
@ManagedBean(name = "userController")
public class UserController {
	private User user;
	private UserService userService = ServiceContainer.getBean(UserService.class);
	private boolean displayPasswordChange = true;
	private String newPassword;
	private String confirmPassword;
	private String currentPassword;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		user = (User) context.getExternalContext().getSessionMap().get("userSession");
		if (user == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", AppServer.getProp("sa-12")));
			try {
				context.getExternalContext().redirect("index.xhtml");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		user = userService.getAccount(user);
	}

	public void changeInfo() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (displayPasswordChange) {
			if (!confirmPassword.trim().equals(newPassword.trim())) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", AppServer.getProp("sa-015")));
				return;
			}
			if (!currentPassword.trim().equals(user.getAccount().getPassword())) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", AppServer.getProp("sa-016")));
				return;
			}
		}
		if (displayPasswordChange) {
			user.getAccount().setPassword(newPassword);			
		}
		user = userService.updateUserInfo(user);
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", AppServer.getProp("sa-007")));
	}

	public void passwordChangeListener() {
		System.out.println(displayPasswordChange);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDisplayPasswordChange() {
		return displayPasswordChange;
	}

	public void setDisplayPasswordChange(boolean displayPasswordChange) {
		this.displayPasswordChange = displayPasswordChange;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
}
