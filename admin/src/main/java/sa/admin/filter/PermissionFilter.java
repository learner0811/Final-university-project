package sa.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sa.admin.entity.User;

@WebFilter("/test")
public class PermissionFilter implements Filter{	
		
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletRequest httpRequest = (HttpServletRequest) req;
         HttpServletResponse httpResponse = (HttpServletResponse) res;
         HttpSession httpSession = httpRequest.getSession(false);	
         
         String strRequestURI = httpRequest.getRequestURI(); 
         if (!strRequestURI.contains("login.xhtml")) {
	         if (httpSession == null) {
	        	 httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
	        	 return;
	         }
	         else{
	        	 User userSession = (User) httpSession.getAttribute("userSession");
	        	 if (userSession == null) {
		        	 httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
		        	 return;
		         }
	         }
         }         
         chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
