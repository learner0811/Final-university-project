package hn46.sa.dto;

import java.util.Date;

public class SearchTenantDto {
	private String addressLine;
	private boolean female;
	private boolean male;
	private String strNative;
	private int fromBudget;
	private int toBudget;
	private Date movingDate;
	private int fromAge;
	private int toAge;
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public boolean getFemale() {
		return female;
	}
	public void setFemale(boolean female) {
		this.female = female;
	}
	public boolean getMale() {
		return male;
	}
	public void setMale(boolean male) {
		this.male = male;
	}	
	public int getFromBudget() {
		return fromBudget;
	}
	public void setFromBudget(int fromBudget) {
		this.fromBudget = fromBudget;
	}
	public int getToBudget() {
		return toBudget;
	}
	public void setToBudget(int toBudget) {
		this.toBudget = toBudget;
	}
	public Date getMovingDate() {
		return movingDate;
	}
	public void setMovingDate(Date movingDate) {
		this.movingDate = movingDate;
	}
	public String getStrNative() {
		return strNative;
	}
	public void setStrNative(String strNative) {
		this.strNative = strNative;
	}
	public int getFromAge() {
		return fromAge;
	}
	public void setFromAge(int fromAge) {
		this.fromAge = fromAge;
	}
	public int getToAge() {
		return toAge;
	}
	public void setToAge(int toAge) {
		this.toAge = toAge;
	}		
	
}
