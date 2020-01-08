package com.github.adminfaces.showcase.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import sa.admin.entity.Account;
import sa.admin.entity.User;

/**
 * Created by rmpestano on 04/02/17.
 */
@Named
@ViewScoped
public class LogonMB implements Serializable {

    private String email;

    private String password;

    private boolean remember;

    public String doLogon() {
    	FacesContext context = FacesContext.getCurrentInstance();
	    if (email.equals("huynd46@gmail.com") && password.equals("admin")) {
	    	Account account = new Account();
	    	User user = new User();
	    	account.setUsername(email);
	    	account.setPassword(password);
	    	user.setFirstName("admin");
	    	user.setAccount(account);
	        context.getExternalContext().getSessionMap().put("userSession", user);
	    	Faces.getFlash().setKeepMessages(true);
	        Messages.addGlobalInfo("Logged in successfully!");
	        return "/index.xhtml?faces-redirect=true";
    	}
	    return "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
