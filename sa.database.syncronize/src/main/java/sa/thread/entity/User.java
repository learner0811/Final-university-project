package sa.thread.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idUser;
	private Account account;
	private List<MailBox> mailBox;
	
	private String firstName;
	private String lastName;
	private int type;
	private transient int status;
	
	private transient Image avatar;
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<MailBox> getMailBox() {
		return mailBox;
	}
	public void setMailBox(List<MailBox> mailBox) {
		this.mailBox = mailBox;
	}
	/*public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}*/
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Image getAvatar() {
		return avatar;
	}
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}		
}
