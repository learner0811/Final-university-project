package hn46.sa.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RateEvent;

import hn46.sa.entity.Rating;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;
import hn46.sa.service.RatingService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.service.TenantService;
import hn46.sa.util.AppServer;

@ViewScoped
@javax.faces.bean.ManagedBean(name = "tenantController")
public class TenantController {
	private Tenant tenant;
	private String[] interestDto;
	private int ratingDto;
	private User userSession;
	private double ratingTotal;
	private TenantService tenantService = ServiceContainer.getBean(TenantService.class);
	private String test;
	private RatingService ratingService = ServiceContainer.getBean(RatingService.class);
	private String strRatingTotal;
	
	@ManagedProperty(value = "#{headerController}")
	private HeaderController header;
	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String roomOwnerId = paramMap.get("id");
		int id = 0;
		id = Integer.parseInt(roomOwnerId);

		tenant = tenantService.findById(id);
		interestDto = tenant.getInterests().split(",");
		
		userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		Rating rating = new Rating();
		rating.setIddata(tenant.getIdTenant());
		if (userSession != null) {						
			rating.setUser(userSession);
			ratingDto = ratingService.getRating(rating);			
		}
		rating.setType(2);
		ratingTotal = ratingService.getRatingStar(rating);
		NumberFormat formatter = new DecimalFormat("#0.0");
		strRatingTotal = formatter.format(ratingTotal);		
	}
	
	public void onrate(RateEvent rateEvent) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
			if (userSession == null) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", AppServer.getProp("sa-012"));
				context.addMessage(null, message);
				ratingDto = 0;
				return;
			}
			Rating rating = new Rating();
			rating.setStar(((Integer) rateEvent.getRating()).intValue());
			rating.setIddata(tenant.getIdTenant());
			rating.setUser(userSession);
			rating.setType(2);
			ratingService.doRating(rating);

			//re calculate star			
			ratingTotal = ratingService.getRatingStar(rating);
			NumberFormat formatter = new DecimalFormat("#0.0");
			strRatingTotal = formatter.format(ratingTotal);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Đánh giá",
					"Bạn đánh giá: " + ((Integer) rateEvent.getRating()).intValue());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void openInbox() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (header.getSessionBean() == null ) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning",
					AppServer.getProp("sa-012"));
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}		
		userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		if (userSession.getIdUser() == tenant.getIdUser()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning",
					"Không thể tự nhắn tin cho mình");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}		
		PrimeFaces.current().executeScript("openInbox();");
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String[] getInterestDto() {
		return interestDto;
	}

	public void setInterestDto(String[] interestDto) {
		this.interestDto = interestDto;
	}

	public int getRatingDto() {
		return ratingDto;
	}

	public void setRatingDto(int ratingDto) {
		this.ratingDto = ratingDto;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public double getRatingTotal() {
		return ratingTotal;
	}

	public void setRatingTotal(double ratingTotal) {
		this.ratingTotal = ratingTotal;
	}

	public String getStrRatingTotal() {
		return strRatingTotal;
	}

	public void setStrRatingTotal(String strRatingTotal) {
		this.strRatingTotal = strRatingTotal;
	}

	public HeaderController getHeader() {
		return header;
	}

	public void setHeader(HeaderController header) {
		this.header = header;
	}			
}
