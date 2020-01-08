package sa.thread.util;


public class AppUtil {
	public static Object NVL(Object value, Object defaul) {
		if (value == null)
			return defaul;
		return value;
	}
	
	public static String strNVL(String value, String defaul) {
		if (value == null || value.equals(""))
			return defaul;
		return value;
	}
}
