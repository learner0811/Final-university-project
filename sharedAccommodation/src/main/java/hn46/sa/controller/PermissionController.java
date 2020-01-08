package hn46.sa.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import hn46.sa.entity.User;
import hn46.sa.util.AppServer;

@ViewScoped
@ManagedBean(name = "permissionController")
public class PermissionController {
	private String temp = "";
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		User userSession = (User) sessionMap.get("userSession");
		String redirectTo = (String) sessionMap.get("redirectTo");
		if (userSession == null) {
			try {				
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", AppServer.getProp("sa-012")));
				context.getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else {
			try {
				//reset session
				sessionMap.put("redirectTo", "");
				if (redirectTo.equals(""))
					context.getExternalContext().redirect("index.xhtml");
				else
					context.getExternalContext().redirect(redirectTo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
}
