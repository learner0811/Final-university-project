package hn46.sa.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RateEvent;

import hn46.sa.dto.ConversationDto;
import hn46.sa.dto.ParticipantDto;
import hn46.sa.entity.Facilities;
import hn46.sa.entity.LandLord;
import hn46.sa.entity.Rating;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.User;
import hn46.sa.service.CommonService;
import hn46.sa.service.RatingService;
import hn46.sa.service.SearchService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.util.AppServer;

@ViewScoped
@javax.faces.bean.ManagedBean(name = "roomOwnerController")
public class RoomOwnerController {
	private User userSession;
	private boolean login;
	private RoomOwner roomOwner;
	private SearchService searchService = ServiceContainer.getBean(SearchService.class);
	private CommonService commonService = ServiceContainer.getBean(CommonService.class);
	private RatingService ratingService = ServiceContainer.getBean(RatingService.class);
	private Facilities[] facilites;
	private int ratingDto;
	private double ratingTotal;
	private String strRatingTotal;
	private String test = "123";
	private boolean type = false;
	private LandLord landlord;
	
	@ManagedProperty(value = "#{headerController}")
	private HeaderController header;

	@PostConstruct
	public void init() {
		// get user from session
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String roomOwnerId = paramMap.get("id");
		int id = 0;
		id = Integer.parseInt(roomOwnerId);
						
		roomOwner = searchService.findById(id);
		landlord = new LandLord();
		if (roomOwner.getType() == 1) {
			type= true;
			landlord = (LandLord) roomOwner;
		}
		List<Facilities> lstTemp = commonService.loadFacilities();
		facilites = lstTemp.toArray(new Facilities[lstTemp.size()]);
		for (int i = 0; i < roomOwner.getRoom().getFacilities().length; i++) {
			Facilities item = roomOwner.getRoom().getFacilities()[i];
			if (lstTemp.contains(item))
				facilites[i].setDisplay(true);
		}

		userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		// userSession = header.getSessionBean();
		Rating rating = new Rating();
		rating.setIddata(roomOwner.getIdRoomOwner());
		if (userSession != null) {
			rating.setUser(userSession);
			ratingDto = ratingService.getRating(rating);
		}
		rating.setType(1);
		ratingTotal = ratingService.getRatingStar(rating);
		NumberFormat formatter = new DecimalFormat("#0.0");
		strRatingTotal = formatter.format(ratingTotal);
	}		

	public void onrate(RateEvent rateEvent) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
			if (userSession == null) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning",
						AppServer.getProp("sa-012"));
				context.addMessage(null, message);
				ratingDto = 0;
				return;
			}
			Rating rating = new Rating();
			rating.setStar(((Integer) rateEvent.getRating()).intValue());
			rating.setIddata(roomOwner.getIdRoomOwner());
			rating.setUser(userSession);
			rating.setType(1);
			ratingService.doRating(rating);

			// re calculate star
			ratingTotal = ratingService.getRatingStar(rating);
			NumberFormat formatter = new DecimalFormat("#0.0");
			strRatingTotal = formatter.format(ratingTotal);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Đánh giá",
					"Bạn đánh giá: " + ((Integer) rateEvent.getRating()).intValue());
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception ex) {
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
		if (userSession.getIdUser() == roomOwner.getIdUser()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning",
					"Không thể tự nhắn tin cho mình");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}		
		PrimeFaces.current().executeScript("openInbox();");
	}

	public RoomOwner getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(RoomOwner roomOwner) {
		this.roomOwner = roomOwner;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public Facilities[] getFacilites() {
		return facilites;
	}

	public void setFacilites(Facilities[] facilites) {
		this.facilites = facilites;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getRatingDto() {
		return ratingDto;
	}

	public void setRatingDto(int ratingDto) {
		this.ratingDto = ratingDto;
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

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public LandLord getLandlord() {
		return landlord;
	}

	public void setLandlord(LandLord landlord) {
		this.landlord = landlord;
	}			
}
