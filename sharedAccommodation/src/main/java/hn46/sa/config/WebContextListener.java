package hn46.sa.config;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;

import hn46.sa.service.ServiceContainer;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener{

	@Override
	public void contextInitialized(ServletContextEvent event) {		
		super.contextInitialized(event);
		WebApplicationContext context = (WebApplicationContext) event.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ServiceContainer.setApplicationContext(context);		
	}
	
}
