package hn46.sa.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import hn46.sa.dao.FacilitiesDao;
import hn46.sa.entity.Facilities;
import hn46.sa.service.ServiceContainer;

@FacesConverter("hn46.sa.converter.FacilitiesConverter")
public class FacilitiesConverter implements Converter {
	private FacilitiesDao facilitiesDao = ServiceContainer.getBean(FacilitiesDao.class);
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String value) {
		Facilities facilities = facilitiesDao.findByName(value);
		return facilities;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object facilities) {						
		return facilities.toString();
	}

}
