package sa.thread.util;

public class AppException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String reason;
	
	public AppException(String code, String reason) {
		super(code);
		this.code = code;
		this.reason = reason;		
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
