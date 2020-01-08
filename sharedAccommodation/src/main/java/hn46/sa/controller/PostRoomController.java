package hn46.sa.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import hn46.sa.dto.ParamDto;
import hn46.sa.entity.Address;
import hn46.sa.entity.Facilities;
import hn46.sa.entity.Image;
import hn46.sa.entity.LandLord;
import hn46.sa.entity.Room;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.User;
import hn46.sa.service.CommonService;
import hn46.sa.service.PostRoomService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.service.UserService;
import hn46.sa.util.AppException;
import hn46.sa.util.AppServer;
import hn46.sa.util.AppUtil;

@ViewScoped
@ManagedBean(name = "postRoomController")
public class PostRoomController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// entity
	private Room room;
	private RoommateCriteria roommateCriteria;
	private RoomOwner roomOwner;
	private LandLord landlord;
	private User userSession;
	
	// dto
	private int number1 = 0;
	private int number2 = 100;
	private UploadedFile avatar;
	private List<ParamDto> nativeList;
	private Image avatarDto;
	private ArrayList<Image> roomImagesDto;
	private Image imageSelected;
	private Facilities[] facilites;
			
	// service
	private CommonService commonService = ServiceContainer.getBean(CommonService.class);
	private PostRoomService postRoomService = ServiceContainer.getBean(PostRoomService.class);
	private UserService userService = ServiceContainer.getBean(UserService.class);
	
	// file
	private UploadedFile roomImage;
	
	private String interesting;
	private String mode;
	private boolean exist;
	private boolean type = false; //false = roomOwner -- true landlord
	private String test = "123";
	private boolean closeTopic = true;
	public String getInteresting() {
		return interesting;
	}

	public void setInteresting(String interesting) {
		this.interesting = interesting;
	}

	@PostConstruct
	public void init() {
		// get user from session
		FacesContext context = FacesContext.getCurrentInstance();
		userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		System.out.println(userSession.getType());
		// check if have data -if yes load old data else init entity
		
		List<Facilities> lstTemp = commonService.loadFacilities();
		facilites = lstTemp.toArray(new Facilities[lstTemp.size()]);
		
		if (userSession.getType() == 2) {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('dlg').show();");				
		} else if (userSession.getType() == 1) {			
			if (postRoomService.isExist(userSession) == true) {
				reload();
				mode = "update";
			}
			else {
				loadNewData();
				mode = "insert";
			}
		}else {
			loadNewData();
			mode = "insert";
		}
		
	}
	
	public void reload() {
		roomOwner = postRoomService.loadRoomOwnerByUser(userSession);
		if (roomOwner.getStatus() == 1)
			closeTopic = true;
		else
			closeTopic = false;
		if (roomOwner.getType() == 1)
			landlord = (LandLord) roomOwner;
		else
			landlord = new LandLord();
		room = roomOwner.getRoom();
		roommateCriteria = roomOwner.getRoommateCriteria();
		nativeList = commonService.loadNative();
		roomImagesDto = new ArrayList<>();
		String[] ageArr = roomOwner.getRoommateCriteria().getAge().split(",");
		number1 = Integer.parseInt(ageArr[0]);
		number2 = Integer.parseInt(ageArr[1]);
		if (roomOwner.getType() == 1) {
			type = true;
		}
		else type = false;
	}
	
	public void loadNewData() {
		room = new Room();
		room.setAddress(new Address());
		
		roommateCriteria = new RoommateCriteria();
		roommateCriteria.setGender(0);
		roommateCriteria.setCivil_status(0);
		roommateCriteria.setChildren(0);
		roommateCriteria.setCleancliness(0);
		roommateCriteria.setSmoker(0);
		roommateCriteria.setPetAllowed(0);
		roomOwner = new LandLord();
		landlord = new LandLord();
		roomOwner.setGender(0);
		avatarDto = new Image();
		roomImagesDto = new ArrayList<>();

		nativeList = commonService.loadNative();
	}

	public void postRoom() {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("----post room-----");			
		if (number1 != number2)
			roommateCriteria.setAge(number1 + "," + number2);
		else
			roommateCriteria.setAge(number1 + "");
		roomOwner.setIdUser(userSession.getIdUser());
		if (landlord.getRoomNumber() < landlord.getAvailable()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", AppServer.getProp("sa-018")));
			return;
		}
		try {			
			if (mode.equals("insert"))
				insert();
			else if (mode.equals("update"))
				update();
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Success", AppServer.getProp("sa-007")));
			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("post_room.xhtml");;			
		} catch (Exception e) {
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", e.getMessage()));
			refresh();
		}
	}
	
	public void insert() throws AppException {
		uploadAvatar();
		roomOwner.setAvatar(avatarDto);									
		room.setImages(roomImagesDto.toArray(new Image[roomImagesDto.size()]));				
		room.setMovingDate((Date) AppUtil.NVL(room.getMovingDate(), new Date()));
		roomOwner.setRoom(room);
		roomOwner.setRoommateCriteria(roommateCriteria);
		roomOwner.setIdUser(userSession.getIdUser());
		if (type) {
			roomOwner.setType(1);
			int roomNum = landlord.getRoomNumber();
			int available = landlord.getAvailable();
			landlord = (LandLord) roomOwner;		
			landlord.setRoomNumber(roomNum);
			landlord.setAvailable(available);
			postRoomService.save(landlord);
		}else {
			roomOwner.setType(0);
			postRoomService.save(roomOwner);
		}		
		userSession.setType(1);
		
		userSession = userService.update(userSession);
		refresh();
	}		
	
	public void update() throws AppException {
		if (avatar.getFileName() == null || avatar.getSize() == 0)
			roomOwner.setAvatar(null);
		else {
			uploadAvatar();
			roomOwner.getAvatar().setFileName(avatarDto.getFileName());
		}			
		roomOwner.setStatus(1);
		roomOwner.setChange(0);
		room.setImages(roomImagesDto.toArray(new Image[roomImagesDto.size()]));
		roomOwner.setRoom(room);
		roomOwner.setRoommateCriteria(roommateCriteria);
		if (type) {
			roomOwner.setType(1);
			int roomNum = landlord.getRoomNumber();
			int available = landlord.getAvailable();
			landlord = (LandLord) roomOwner;		
			landlord.setRoomNumber(roomNum);
			landlord.setAvailable(available);
		}
		else
			roomOwner.setType(0);
		if (roomOwner.getType() == 1)
			postRoomService.update(landlord);
		else
			postRoomService.update(roomOwner);
		
		refresh();
	}
	
	public void deleteImage() {
		imageSelected.setStatus(0);
		postRoomService.deleteImg(imageSelected);
		room.getImages();
		ArrayList<Image> tmp = new ArrayList<>(Arrays.asList(room.getImages()));
		tmp.remove(imageSelected);
		room.setImages(tmp.toArray(new Image[tmp.size()]));
	}

	public void refresh() {
		roomImagesDto.clear();		
	}
	
	public void uploadAvatar() throws AppException {
		String uploadedFileName = avatar.getFileName();
		StringBuilder tmpFileNameBuilder = new StringBuilder();
		tmpFileNameBuilder.append(System.currentTimeMillis());
		tmpFileNameBuilder.append(".");
		tmpFileNameBuilder.append(FilenameUtils.getExtension(uploadedFileName));
		String fileName = tmpFileNameBuilder.toString();
		String rootPath = AppServer.getProp("fileAvatarPath");
		try {
			File uploadFile = new File(rootPath + fileName);
			FileUtils.writeByteArrayToFile(uploadFile, avatar.getContents());
			avatarDto = new Image();
			avatarDto.setFileName(fileName);
			avatarDto.setStatus(1);
			avatarDto.setType(1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Error", "sa-009");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {					
		String uploadedFileName = event.getFile().getFileName();
		System.out.println(uploadedFileName);
		StringBuilder tmpFileNameBuilder = new StringBuilder();
		tmpFileNameBuilder.append(RandomStringUtils.randomAlphanumeric(8));				 
		tmpFileNameBuilder.append(".");
		tmpFileNameBuilder.append(FilenameUtils.getExtension(uploadedFileName));
		
		String fileName = tmpFileNameBuilder.toString();		
		String rootPath = AppServer.getProp("fileRoomPath");
		try {
			File uploadFile = new File(rootPath + fileName);
			FileUtils.writeByteArrayToFile(uploadFile, event.getFile().getContents());
			roomImagesDto.add(new Image(fileName, 1, 2));								
			FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		} catch (Exception e) {
			e.printStackTrace();
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", event.getFile().getFileName() + " fail uploading.");
		     FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void redirect() {
		System.out.println("no");
		try {			
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public void switchType() {
		
	}
	
	public void switchPost() {
		PrimeFaces current = PrimeFaces.current();		
		current.executeScript("PF('dlg1').show();");
	}
	
	public void confirmYes2() {
		if (closeTopic) {
			roomOwner.setStatus(1);
			roomOwner.setChange(0);
		}
		else {
			roomOwner.setStatus(0);
			roomOwner.setChange(0);
		}
		postRoomService.openOrCloseTopic(roomOwner);
	}
	
	public void confirmYes() throws IOException {								
		userSession.setType(1);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSession", userSession);		
		postRoomService.disableTenant(userSession);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSession", userSession);
		if (postRoomService.isExist(userSession) == true) {
			postRoomService.enableRoomOwner(userSession);
			FacesContext.getCurrentInstance().getExternalContext().redirect("post_room.xhtml");
		}
		else
			loadNewData();
	}	

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public RoommateCriteria getRoommateCriteria() {
		return roommateCriteria;
	}

	public void setRoommateCriteria(RoommateCriteria roommateCriteria) {
		this.roommateCriteria = roommateCriteria;
	}

	public RoomOwner getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(RoomOwner roomOwner) {
		this.roomOwner = roomOwner;
	}

	public UploadedFile getRoomImage() {
		return roomImage;
	}

	public void setRoomImage(UploadedFile roomImage) {
		this.roomImage = roomImage;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public UploadedFile getAvatar() {
		return avatar;
	}

	public void setAvatar(UploadedFile avatar) {
		this.avatar = avatar;
	}

	public List<ParamDto> getNativeList() {
		return nativeList;
	}

	public void setNativeList(List<ParamDto> nativeList) {
		this.nativeList = nativeList;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public Image getAvatarDto() {
		return avatarDto;
	}

	public void setAvatarDto(Image avatarDto) {
		this.avatarDto = avatarDto;
	}

	public ArrayList<Image> getRoomImagesDto() {
		return roomImagesDto;
	}

	public void setRoomImagesDto(ArrayList<Image> roomImagesDto) {
		this.roomImagesDto = roomImagesDto;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public Image getImageSelected() {
		return imageSelected;
	}

	public void setImageSelected(Image imageSelected) {
		this.imageSelected = imageSelected;
	}

	public Facilities[] getFacilites() {
		return facilites;
	}

	public void setFacilites(Facilities[] facilites) {
		this.facilites = facilites;
	}
	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public LandLord getLandlord() {
		return landlord;
	}

	public void setLandlord(LandLord landlord) {
		this.landlord = landlord;
	}

	public boolean isCloseTopic() {
		return closeTopic;
	}

	public void setCloseTopic(boolean closeTopic) {
		this.closeTopic = closeTopic;
	}				
}
