package hn46.sa.converter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("hn46.sa.converter.LastUpdatedConverter")
public class LastUpdatedConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String value) {		
		return value;
	}

	
	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object obj) {
		Date lastUpdatedDate = (Date) obj;
		Date now = new Date();
		long diffInMillies = Math.abs(now.getTime() - lastUpdatedDate.getTime());
		long diffAsSecond = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffAsMinutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffAsHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffAsDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffAsWeeks = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 7;
		long diffAsMonths = diffAsWeeks/4;
		long diffAsYears = diffAsMonths/12;
				
		long result = diffAsSecond;
		String type = " giây trước ";
		
		if (diffAsMinutes != 0) {		
			result = diffAsMinutes;
			type = " phút trước ";
		}
		if (diffAsHours != 0) {
			result = diffAsHours;
			type = " giờ trước";
		}
		if (diffAsDays != 0) {
			result = diffAsDays;
			type = " ngày trước";
		}
		if (diffAsWeeks != 0) {
			result = diffAsWeeks;
			type = " tuần trước";
		}
		if (diffAsMonths != 0) {
			result = diffAsMonths;
			type = " tháng trước";
		}
		if (diffAsYears != 0) {
			result = diffAsYears;
			type = " năm trước";
		}
		
		return "" + result + type;
	}
}
