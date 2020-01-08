package hn46.sa.entity;

public class Facilities {
	private int idfacilities;
	private String name;
	private String icon;
	private int status;	
	private boolean display = false;	//only for indicate displaying status
	
	public int getIdfacilities() {
		return idfacilities;
	}
	public void setIdfacilities(int idfacilities) {
		this.idfacilities = idfacilities;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idfacilities;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Facilities other = (Facilities) obj;
		if (idfacilities != other.idfacilities)
			return false;
		return true;
	}	
	
	
}
