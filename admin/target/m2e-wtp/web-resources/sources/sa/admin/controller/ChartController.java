package sa.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

import sa.admin.dto.KeyValueDto;
import sa.admin.service.CommonService;
import sa.admin.service.ServiceContainer;

@ViewScoped
@ManagedBean(name = "chartController")
public class ChartController {
	private BarChartModel barModel;
	private HorizontalBarChartModel horizontalBarModel;
	private CommonService commonService = ServiceContainer.getBean(CommonService.class);
	private PieChartModel pieModel1;

	@PostConstruct
	public void init() {
		createBarModels();
	}

	private void createBarModels() {
		createBarModel();
		createPieModels();
		// createHorizontalBarModel();

	}

	private void createPieModels() {
		createPieModel1();
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();
		List<KeyValueDto> lstData = commonService.staticByRent();
		for (KeyValueDto kv : lstData) {
			String strValue = kv.getValue();
			int value = Integer.parseInt(strValue);
			if (value < 1000000)
				pieModel1.set("Ít hơn 1 triệu", value);
			else if (value < 5000000)
				pieModel1.set("1 triệu đến 5 triệu", value);
			else
				pieModel1.set("Hơn 5 triệu", value);
		}

        pieModel1.setTitle("Biểu đồ giá cho thuê");
        pieModel1.setLegendPosition("w");
        pieModel1.setShowDataLabels(true);
        pieModel1.setDiameter(150);
        pieModel1.setShadow(false);
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		List<KeyValueDto> lstData = commonService.staticByCity();
		ChartSeries city = new ChartSeries();

		for (KeyValueDto kv : lstData)
			city.set(kv.getKey(), Integer.parseInt(kv.getValue()));
		model.addSeries(city);
		return model;
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Biểu đồ số lượng bài theo thành phố");
		// barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Thành phố/Tỉnh");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Số lượng");
		yAxis.setMin(0);
		yAxis.setMax(20);
	}

	private void createHorizontalBarModel() {
		horizontalBarModel = new HorizontalBarChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Boys");
		boys.set("2004", 50);
		boys.set("2005", 96);
		boys.set("2006", 44);
		boys.set("2007", 55);
		boys.set("2008", 25);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 82);
		girls.set("2007", 35);
		girls.set("2008", 120);

		horizontalBarModel.addSeries(boys);
		horizontalBarModel.addSeries(girls);

		horizontalBarModel.setTitle("Horizontal and Stacked");
		horizontalBarModel.setLegendPosition("e");
		horizontalBarModel.setStacked(true);

		Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
		xAxis.setLabel("Births");
		xAxis.setMin(0);
		xAxis.setMax(200);

		Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
		yAxis.setLabel("Gender");
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
		this.horizontalBarModel = horizontalBarModel;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}	
}
