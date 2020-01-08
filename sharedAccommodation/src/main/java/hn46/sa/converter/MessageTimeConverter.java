package hn46.sa.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("hn46.sa.converter.MessageTimeConverter")
public class MessageTimeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String value) {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object obj) {
		Date lastUpdatedDate = (Date) obj;
		Date now = new Date();
		long diffInMillies = Math.abs(now.getTime() - lastUpdatedDate.getTime());
		long diffAsDays = TimeUnit.DAYS.convert(now.getTime(), TimeUnit.MILLISECONDS) - TimeUnit.DAYS.convert(lastUpdatedDate.getTime(), TimeUnit.MILLISECONDS);
		if (diffAsDays <= 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			return sdf.format(lastUpdatedDate);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd/MM/yyyy");
		return sdf.format(lastUpdatedDate);
	}

}
