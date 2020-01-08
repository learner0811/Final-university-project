package hn46.sa.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;

import hn46.sa.dto.ParamDto;
import hn46.sa.entity.Image;
import hn46.sa.entity.RoomCriteria;
import hn46.sa.entity.RoommateCriteria;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;
import hn46.sa.service.CommonService;
import hn46.sa.service.PostProfileService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.service.UserService;
import hn46.sa.util.AppException;
import hn46.sa.util.AppServer;

@ViewScoped
@ManagedBean(name = "postProfileController")
public class PostProfileController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tenant tenant;
	private RoommateCriteria roommate;
	private RoomCriteria room;
	private User userSession;
	private UploadedFile avatar;
	private PostProfileService postProfileService = ServiceContainer.getBean(PostProfileService.class);
	private UserService userService = ServiceContainer.getBean(UserService.class);
	private CommonService commonService = ServiceContainer.getBean(CommonService.class);

	private String mode;
	private Image avatarDto;
	private List<ParamDto> nativeList;

	private int num1 = 0;
	private int num2 = 100;
	private boolean closeTopic = false;
	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		userSession = (User) context.getExternalContext().getSessionMap().get("userSession");
		if (userSession.getType() == 1) {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('dlg').show();");
		} else if (userSession.getType() == 2) {
			if (postProfileService.isExist(userSession) == true) {
				reload();
				mode = "update";
			} else {
				initData();
				mode = "insert";
			}
		} else {
			initData();
			mode = "insert";
		}
	}

	private void reload() {
		tenant = postProfileService.loadByUser(userSession);
		room = tenant.getRoomCriteria();
		roommate = tenant.getRoommateCriteria();
		nativeList = commonService.loadNative();
		String[] temp = roommate.getAge().split(",");
		num1 = Integer.parseInt(temp[0]);
		num2 = Integer.parseInt(temp[1]);
		if (tenant.getStatus() == 1)
			closeTopic = true;
		else
			closeTopic = false;
	}

	public void initData() {
		tenant = new Tenant();
		room = new RoomCriteria();
		roommate = new RoommateCriteria();
		nativeList = commonService.loadNative();
	}

	public void confirmYes() throws IOException {
		userSession.setType(2);
		postProfileService.disableRoomOwner(userSession);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSession", userSession);
		if (postProfileService.isExist(userSession) == true) {
			postProfileService.enableTenant(userSession);
			FacesContext.getCurrentInstance().getExternalContext().redirect("post_profile.xhtml");
		}
		else
			initData();
	}
	
	public void confirmYes2() {
		if (closeTopic) {
			tenant.setStatus(1);
			tenant.setChange(0);
		}
		else {
			tenant.setStatus(0);
			tenant.setChange(0);
		}
		postProfileService.openOrClose(tenant);
	}
	
	public void switchPost() {
		PrimeFaces current = PrimeFaces.current();		
		current.executeScript("PF('dlg1').show();");
	}

	public void redirect() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void post_profile() {
		System.out.println("-----post profile-----");
		if (num1 != num2)
			roommate.setAge(num1 + "," + num2);
		else
			roommate.setAge(num1 + "");
		try {
			if (mode.equals("insert"))
				insert();
			else if (mode.equals("update"))
				update();
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", AppServer.getProp("sa-011")));
			context.getExternalContext().redirect("post_profile.xhtml");
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			if (ex instanceof AppException)
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", AppServer.getProp(((AppException) ex).getReason())));
			else
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", ex.getMessage()));
		}
	}

	private void update() {
		try {
			if (avatar.getFileName() == null || avatar.getSize() == 0)
				tenant.setAvatar(null);
			else {
				uploadAvatar();
				avatarDto.setIdImage(tenant.getAvatar().getIdImage());
				tenant.setAvatar(avatarDto);
			}
			tenant.setRoomCriteria(room);
			tenant.setRoommateCriteria(roommate);
			tenant.setStatus(1);
			tenant.setIdUser(userSession.getIdUser());
			postProfileService.update(tenant);
		} catch (Exception ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", AppServer.getProp(ex.getMessage())));
		}
	}

	public void insert() throws AppException {
		uploadAvatar();
		tenant.setAvatar(avatarDto);
		tenant.setStatus(1);
		tenant.setRoomCriteria(room);
		tenant.setRoommateCriteria(roommate);
		tenant.setIdUser(userSession.getIdUser());
		postProfileService.save(tenant);
		userSession.setType(2);
		userSession = userService.update(userSession);
	}

	public void uploadAvatar() throws AppException {
		try {
			if (avatar == null || avatar.getFileName() == null || avatar.getSize() == 0)
				throw new Exception("sa-010");
			String uploadedFileName = avatar.getFileName();
			StringBuilder tmpFileNameBuilder = new StringBuilder();
			tmpFileNameBuilder.append(System.currentTimeMillis());
			tmpFileNameBuilder.append(".");
			tmpFileNameBuilder.append(FilenameUtils.getExtension(uploadedFileName));
			String fileName = tmpFileNameBuilder.toString();
			String rootPath = AppServer.getProp("fileAvatarPath");

			File uploadFile = new File(rootPath + fileName);
			FileUtils.writeByteArrayToFile(uploadFile, avatar.getContents());
			avatarDto = new Image();
			avatarDto.setFileName(fileName);
			avatarDto.setStatus(1);
			avatarDto.setType(3);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("sa-010"))
				throw new AppException("Error", "sa-010");
			else
				throw new AppException("Error", "sa-009");
		}
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public RoommateCriteria getRoommate() {
		return roommate;
	}

	public void setRoommate(RoommateCriteria roommate) {
		this.roommate = roommate;
	}

	public RoomCriteria getRoom() {
		return room;
	}

	public void setRoom(RoomCriteria room) {
		this.room = room;
	}

	public UploadedFile getAvatar() {
		return avatar;
	}

	public void setAvatar(UploadedFile avatar) {
		this.avatar = avatar;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public Image getAvatarDto() {
		return avatarDto;
	}

	public void setAvatarDto(Image avatarDto) {
		this.avatarDto = avatarDto;
	}

	public List<ParamDto> getNativeList() {
		return nativeList;
	}

	public void setNativeList(List<ParamDto> nativeList) {
		this.nativeList = nativeList;
	}

	public boolean isCloseTopic() {
		return closeTopic;
	}

	public void setCloseTopic(boolean closeTopic) {
		this.closeTopic = closeTopic;
	}	
}
