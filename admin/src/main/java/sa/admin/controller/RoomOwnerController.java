package sa.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import sa.admin.entity.Image;
import sa.admin.entity.RoomOwner;
import sa.admin.entity.User;
import sa.admin.service.RoomOwnerService;
import sa.admin.service.ServiceContainer;

@ViewScoped
@ManagedBean(name = "roomOwnerController")
public class RoomOwnerController {
	private List<RoomOwner> lstRoomOwner;
	private RoomOwnerService roomOwnerService = ServiceContainer.getBean(RoomOwnerService.class);
	private RoomOwner selectedItem;
	private User receiver;
	
	@PostConstruct
	public void init() {
		lstRoomOwner = roomOwnerService.findAll();
		receiver = new User();
		receiver.setAvatar(new Image());
	}
	
	public void openInbox() {	
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedItem == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-019")));
			return;
		}
		receiver.setIdUser(selectedItem.getIdUser());
		receiver.setFirstName(selectedItem.getFirstName());
		receiver.setLastName(selectedItem.getLastName());
		receiver.setAvatar(selectedItem.getAvatar());
		PrimeFaces.current().executeScript("openInbox();");
	}
	
	public void lock() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedItem == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-019")));
			return;
		}
		selectedItem.setStatus(0);
		roomOwnerService.lockOrUnlock(selectedItem);
	}
	
	public void unLock() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedItem == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-019")));
			return;
		}
		selectedItem.setStatus(1);
		roomOwnerService.lockOrUnlock(selectedItem);
	}

	public List<RoomOwner> getLstRoomOwner() {
		return lstRoomOwner;
	}

	public void setLstRoomOwner(List<RoomOwner> lstRoomOwner) {
		this.lstRoomOwner = lstRoomOwner;
	}

	public RoomOwner getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(RoomOwner selectedItem) {
		this.selectedItem = selectedItem;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}		
}
