package hn46.sa.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppServer {

	private static Properties appConfig = new Properties();
	private static String fileName = "/msg.properties";
	
	static {
		try {
			appConfig.load(AppServer.class.getResourceAsStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static String getProp(String key) {
		return appConfig.getProperty(key);
	}

	public static void closeObject(CallableStatement obj) {
		try {
			if (obj != null) {
				obj.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void closeObject(Statement obj) {
		try {
			if (obj != null) {
				obj.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void closeObject(ResultSet obj) {
		try {
			if (obj != null) {
				obj.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void closeObject(Connection obj) {
		try {
			if (obj != null) {
				if (!obj.isClosed()) {
					if (!obj.getAutoCommit())
						obj.rollback();
					obj.close();
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
