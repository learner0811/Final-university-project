package sa.admin.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import sa.admin.service.IndexService;
import sa.admin.service.ServiceContainer;

@ViewScoped
@ManagedBean(name = "indexController")
public class IndexController {
	private String test = "";
	private IndexService indexService = ServiceContainer.getBean(IndexService.class);
	
	@PostConstruct
	public void init() {
		System.out.println(indexService);
		System.out.println("index controller");
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}	
}
