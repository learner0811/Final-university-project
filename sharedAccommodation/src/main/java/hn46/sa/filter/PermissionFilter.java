package hn46.sa.filter;

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

import hn46.sa.entity.User;

@WebFilter("/*")
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
         if (strRequestURI.indexOf("post_room") >= 0 || strRequestURI.indexOf("post_profile") >= 0 || strRequestURI.indexOf("message") >= 0) {        	 
        	 User userSession = (User) httpSession.getAttribute("userSession");
        	 if (strRequestURI.indexOf("post_room") >= 0)
        		 httpSession.setAttribute("redirectTo", "post_room.xhtml");
        	 else if (strRequestURI.indexOf("post_profile") >= 0)
        		 httpSession.setAttribute("redirectTo", "post_profile.xhtml");
        	 else if (strRequestURI.indexOf("message") >= 0)
        		 httpSession.setAttribute("redirectTo", "message.xhtml");
        	 if (userSession == null) {
        		 httpResponse.sendRedirect(httpRequest.getContextPath() + "/permission.xhtml");
        		 return ;
        	 }
         }
         chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
