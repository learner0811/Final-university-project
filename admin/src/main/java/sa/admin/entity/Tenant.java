package sa.admin.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Tenant extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idTenant;
	private String name;
	private String strNative;
	private int status;	
	private String interests;
	private String faceBook;
	private String instagram;
	private String self_expression;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
	private Date createDate;
	private RoommateCriteria roommateCriteria;
	private RoomCriteria roomCriteria;
	private Image avatar;
	private int gender;
	private String phone;
	private String email;
	private int change;
	
	public int getIdTenant() {
		return idTenant;
	}
	public void setIdTenant(int idTenant) {
		this.idTenant = idTenant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStrNative() {
		return strNative;
	}
	public void setStrNative(String strNative) {
		this.strNative = strNative;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getFaceBook() {
		return faceBook;
	}
	public void setFaceBook(String faceBook) {
		this.faceBook = faceBook;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public String getSelf_expression() {
		return self_expression;
	}
	public void setSelf_expression(String self_expression) {
		this.self_expression = self_expression;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public RoommateCriteria getRoommateCriteria() {
		return roommateCriteria;
	}
	public void setRoommateCriteria(RoommateCriteria roommateCriteria) {
		this.roommateCriteria = roommateCriteria;
	}
	public RoomCriteria getRoomCriteria() {
		return roomCriteria;
	}
	public void setRoomCriteria(RoomCriteria roomCriteria) {
		this.roomCriteria = roomCriteria;
	}
	public Image getAvatar() {
		return avatar;
	}
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}	
}
