package hn46.sa.entity;

public class LandLord extends RoomOwner{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int roomNumber;
	private int available;
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}		
}
