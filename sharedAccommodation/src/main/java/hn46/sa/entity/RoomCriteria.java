package hn46.sa.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RoomCriteria {
	private int idRoomCriteria;
	private int rent;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
	private Date movingDate;
	private String searchLocation;
	private String title;
	private String period;
	
	public int getIdRoomCriteria() {
		return idRoomCriteria;
	}
	public void setIdRoomCriteria(int idRoomCriteria) {
		this.idRoomCriteria = idRoomCriteria;
	}
	public int getRent() {
		return rent;
	}
	public void setRent(int rent) {
		this.rent = rent;
	}
	public Date getMovingDate() {
		return movingDate;
	}
	public void setMovingDate(Date movingDate) {
		this.movingDate = movingDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getSearchLocation() {
		return searchLocation;
	}
	public void setSearchLocation(String searchLocation) {
		this.searchLocation = searchLocation;
	}
	
}
