package hn46.sa.service;

import org.springframework.context.ApplicationContext;

public class ServiceContainer {
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ServiceContainer.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> beanClass) {
		return applicationContext.getBean(beanClass);
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
