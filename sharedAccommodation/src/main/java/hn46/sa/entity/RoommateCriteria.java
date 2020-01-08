package hn46.sa.entity;

public class RoommateCriteria {
	private int idRoommateCriteria;
	private int gender;
	private String age;
	private String fromAge;
	private String toAge;
	private int children;
	private int status;
	private int civil_status;
	private int cleancliness;
	private int smoker;
	private int petAllowed;
	public int getIdRoommateCriteria() {
		return idRoommateCriteria;
	}
	public void setIdRoommateCriteria(int idRoommateCriteria) {
		this.idRoommateCriteria = idRoommateCriteria;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCivil_status() {
		return civil_status;
	}
	public void setCivil_status(int civil_status) {
		this.civil_status = civil_status;
	}
	public int getCleancliness() {
		return cleancliness;
	}
	public void setCleancliness(int cleancliness) {
		this.cleancliness = cleancliness;
	}
	public int getSmoker() {
		return smoker;
	}
	public void setSmoker(int smoker) {
		this.smoker = smoker;
	}
	public int getPetAllowed() {
		return petAllowed;
	}
	public void setPetAllowed(int petAllowed) {
		this.petAllowed = petAllowed;
	}
	public String getFromAge() {
		return fromAge;
	}
	public void setFromAge(String fromAge) {
		this.fromAge = fromAge;
	}
	public String getToAge() {
		return toAge;
	}
	public void setToAge(String toAge) {
		this.toAge = toAge;
	}		
}
