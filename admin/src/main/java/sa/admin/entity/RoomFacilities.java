package sa.admin.entity;

public class RoomFacilities {
	private int id;
	private Room room;
	private Facilities facilities;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Facilities getFacilities() {
		return facilities;
	}
	public void setFacilities(Facilities facilities) {
		this.facilities = facilities;
	}
	
	
}
