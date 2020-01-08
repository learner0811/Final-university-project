package sa.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import sa.admin.entity.User;
import sa.admin.service.ServiceContainer;
import sa.admin.service.UserService;

@ViewScoped
@ManagedBean(name = "userController")
public class UserController {
	private List<User> lstUser;
	private List<User> lstFilteredUser;
	private UserService userService = ServiceContainer.getBean(UserService.class);
	private String test = "";
	private User selectedUser;
	
	@PostConstruct
	public void init() {
		lstUser = userService.findAll();		
	}
	
	public void lockAccount() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedUser == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-019")));
			return;
		}
		if (selectedUser.getStatus() == 0) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-017")));
			return;
		}
		userService.lockUser(selectedUser);
	}
	
	public void unLockAccount() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedUser == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-019")));
			return;
		}
		if (selectedUser.getStatus() == 1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-018")));
			return;
		}
		userService.unLockUser(selectedUser);
	}

	public List<User> getLstUser() {
		return lstUser;
	}

	public void setLstUser(List<User> lstUser) {
		this.lstUser = lstUser;
	}

	public List<User> getLstFilteredUser() {
		return lstFilteredUser;
	}

	public void setLstFilteredUser(List<User> lstFilteredUser) {
		this.lstFilteredUser = lstFilteredUser;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}		
}
