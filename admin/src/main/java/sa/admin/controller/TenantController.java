package sa.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import sa.admin.entity.Image;
import sa.admin.entity.Tenant;
import sa.admin.service.ServiceContainer;
import sa.admin.service.TenantService;

@ViewScoped
@ManagedBean(name = "tenantController")
public class TenantController {
	private List<Tenant> lstTenant;
	private TenantService tenantService = ServiceContainer.getBean(TenantService.class);
	private Tenant selectedItem;
	private Tenant receiver;
	
	@PostConstruct
	public void init() {
		lstTenant = tenantService.findAll();
		receiver = new Tenant();
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
		tenantService.lockOrUnlock(selectedItem);
	}
	
	public void unLock() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedItem == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Warning", sa.admin.util.AppServer.getProp("sa-019")));
			return;
		}
		selectedItem.setStatus(1);
		tenantService.lockOrUnlock(selectedItem);
	}

	public List<Tenant> getLstTenant() {
		return lstTenant;
	}

	public void setLstTenant(List<Tenant> lstTenant) {
		this.lstTenant = lstTenant;
	}

	public Tenant getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Tenant selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Tenant getReceiver() {
		return receiver;
	}

	public void setReceiver(Tenant receiver) {
		this.receiver = receiver;
	}			
}
