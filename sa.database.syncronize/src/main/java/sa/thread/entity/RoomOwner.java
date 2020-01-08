package sa.thread.entity;

public class RoomOwner extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idRoomOwner;
	private String name;
	private int gender;
	private int age;
	private String strNative;
	private String interests;
	private int civilStatus;
	private int smoker;	
	private String ocupation;
	private int status;
	private RoommateCriteria roommateCriteria;
	private Room room;	
	private Image avatar;	
	private String phone;
	private String email;
	private int change;
	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public int getIdRoomOwner() {
		return idRoomOwner;
	}
	public void setIdRoomOwner(int idRoomOwner) {
		this.idRoomOwner = idRoomOwner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getStrNative() {
		return strNative;
	}
	public void setStrNative(String strNative) {
		this.strNative = strNative;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public int getCivilStatus() {
		return civilStatus;
	}
	public void setCivilStatus(int civilStatus) {
		this.civilStatus = civilStatus;
	}
	public int getSmoker() {
		return smoker;
	}
	public void setSmoker(int smoker) {
		this.smoker = smoker;
	}	
	public String getOcupation() {
		return ocupation;
	}
	public void setOcupation(String ocupation) {
		this.ocupation = ocupation;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public RoommateCriteria getRoommateCriteria() {
		return roommateCriteria;
	}
	public void setRoommateCriteria(RoommateCriteria roommateCriteria) {
		this.roommateCriteria = roommateCriteria;
	}
	public Image getAvatar() {
		return avatar;
	}
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
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
