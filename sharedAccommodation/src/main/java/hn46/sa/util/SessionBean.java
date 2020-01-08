package hn46.sa.util;

import hn46.sa.entity.User;

public class SessionBean {
	private User user;
	private int VALID_TIME = 60*60;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getVALID_TIME() {
		return VALID_TIME;
	}
	public void setVALID_TIME(int vALID_TIME) {
		VALID_TIME = vALID_TIME;
	}
	
	
	
}
