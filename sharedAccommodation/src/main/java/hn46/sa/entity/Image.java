package hn46.sa.entity;

import java.io.Serializable;

public class Image implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idImage;
	private String fileName;
	private int status;
	private int type;
	private int idData;
	
	public Image() {
		
	}
	
	public Image(String fileName, int status, int type) {
		this.fileName = fileName;
		this.status = status;
		this.type = type;
	}
	
	public int getIdImage() {
		return idImage;
	}
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIdData() {
		return idData;
	}

	public void setIdData(int idData) {
		this.idData = idData;
	}	
}
