package hn46.sa.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import hn46.sa.dto.SearchDto;
import hn46.sa.entity.RoomOwner;
import hn46.sa.service.SearchService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.util.AppUtil;

@ViewScoped
@ManagedBean(name = "searchController")
public class SearchController {
	// service
	private SearchService searchService = ServiceContainer.getBean(SearchService.class);

	// model
	private List<RoomOwner> lstRoomOwner;
	private SearchDto searchDto;
	private String number;
	private String street;
	private String district;
	private String city;

	@PostConstruct
	public void init() {
		// lstRoomOwner = searchService.loadAllData();
		lstRoomOwner = new ArrayList<>();
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		System.out.println(paramMap);
		searchDto = new SearchDto();
		fillParamater(paramMap);
		doFilter();
	}

	public void fillParamater(Map<String, String> paramMap) {
		searchDto.setNumber(AppUtil.strNVL(paramMap.get("number"), ""));
		searchDto.setStreet(AppUtil.strNVL(paramMap.get("street"), ""));
		searchDto.setDistrict(AppUtil.strNVL(paramMap.get("district"), ""));
		searchDto.setCity(AppUtil.strNVL(paramMap.get("city"), ""));
		searchDto.setMovingDate(new Date());
		searchDto.setRent(100000000);
		searchDto.setPeriod("");
		String addressLine = "";
		if (searchDto.getNumber() != "")
			addressLine += searchDto.getNumber() + " ";
		if (searchDto.getStreet() != "")
			addressLine += searchDto.getStreet() + " ";
		if (searchDto.getDistrict() != "")
			addressLine += searchDto.getDistrict() + " ";
		if (searchDto.getCity() != "")
			addressLine += searchDto.getCity() + " ";
		searchDto.setAddressLine(addressLine);
	}

	public void doFilter() {
		try {
			RestHighLevelClient client = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9200, "http")));

			SearchRequest searchRequest = new SearchRequest("roomowner");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			BoolQueryBuilder queryBuilder = buildQuery();
			searchSourceBuilder.query(queryBuilder);
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			lstRoomOwner.clear();
			for (SearchHit hit : searchHits) {
				String json = hit.getSourceAsString();
				ObjectMapper mapper = new ObjectMapper();
				RoomOwner roomOwner = mapper.readValue(json, RoomOwner.class);
				lstRoomOwner.add(roomOwner);
				System.out.println(json);
			}

			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BoolQueryBuilder buildQuery() {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		Map<String, Object> searchParamater = new HashMap<>();
		if (searchDto.getNumber() != "")
			searchParamater.put("room.address.number", searchDto.getNumber());
		if (searchDto.getStreet() != "")
			searchParamater.put("room.address.street", searchDto.getStreet());
		if (searchDto.getDistrict() != "")
			searchParamater.put("room.address.district", searchDto.getDistrict());
		if (searchDto.getCity() != "")
			searchParamater.put("room.address.city", searchDto.getCity());
		if (searchDto.getPeriod() != "")
			searchParamater.put("room.period", searchDto.getPeriod());
		searchParamater.put("room.rent", searchDto.getRent() + "");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		searchParamater.put("room.movingDate", sdf.format(searchDto.getMovingDate()));
		searchParamater.put("status", "1");
		for (Map.Entry<String, Object> entry : searchParamater.entrySet()) {
			if (entry.getKey().equals("room.rent")) {
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()).lte(entry.getValue());
				queryBuilder.must(rangeQueryBuilder);
			} else if (entry.getKey().equals("room.movingDate")) {
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()).to(entry.getValue());
				queryBuilder.must(rangeQueryBuilder);
			} else if (entry.getKey().equals("room.address.district")) {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(entry.getKey(), entry.getValue());
				queryBuilder.should(matchQueryBuilder);
			}else if (entry.getKey().equals("room.address.street")) {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(entry.getKey(), entry.getValue());
				queryBuilder.should(matchQueryBuilder);
			} 
			else {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(entry.getKey(), entry.getValue());
				queryBuilder.must(matchQueryBuilder);
			}
		}
		return queryBuilder;
	}

	public String calLastUpdated() {
		return "test";
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public List<RoomOwner> getLstRoomOwner() {
		return lstRoomOwner;
	}

	public void setLstRoomOwner(List<RoomOwner> lstRoomOwner) {
		this.lstRoomOwner = lstRoomOwner;
	}

	public SearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(SearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
