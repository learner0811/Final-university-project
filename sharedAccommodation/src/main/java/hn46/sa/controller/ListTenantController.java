package hn46.sa.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import org.primefaces.PrimeFaces;

import com.fasterxml.jackson.databind.ObjectMapper;

import hn46.sa.dto.ParamDto;
import hn46.sa.dto.SearchTenantDto;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.Tenant;
import hn46.sa.service.CommonService;
import hn46.sa.service.ServiceContainer;
import hn46.sa.service.TenantSearchService;
import hn46.sa.util.AppServer;

@ViewScoped
@ManagedBean(name = "listTenantController")
public class ListTenantController {
	private TenantSearchService tenantSearchService = ServiceContainer.getBean(TenantSearchService.class);
	private CommonService commonService = ServiceContainer.getBean(CommonService.class);
	private List<Tenant> listTenant;
	private List<ParamDto> nativeList;
	private SearchTenantDto searchDto;

	@PostConstruct
	public void init() {
		listTenant = tenantSearchService.loadAllData();
		nativeList = commonService.loadNative();
		searchDto = new SearchTenantDto();
		searchDto.setFromBudget(0);
		searchDto.setToBudget(100000000);
		searchDto.setFromAge(0);
		searchDto.setToAge(100);			
	}

	public void doFilter() {
		if (searchDto.getFromBudget() > searchDto.getToBudget()) {
			FacesContext context = FacesContext.getCurrentInstance();			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validate", AppServer.getProp("sa-014")));
			return;
		}
		try {
			RestHighLevelClient client = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9200, "http")));

			SearchRequest searchRequest = new SearchRequest("tenant");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			BoolQueryBuilder queryBuilder = buildQuery();
			searchSourceBuilder.query(queryBuilder);
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			listTenant.clear();
			for (SearchHit hit : searchHits) {
				String json = hit.getSourceAsString();
				ObjectMapper mapper = new ObjectMapper();
				Tenant tenant = mapper.readValue(json, Tenant.class);
				listTenant.add(tenant);
				System.out.println(json);
			}

			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("setDate();");
	}

	private BoolQueryBuilder buildQuery() {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		Map<String, Object> searchParamater = new HashMap<>();
		if (searchDto.getFemale() && !searchDto.getMale())
			searchParamater.put("roommateCriteria.gender", "1");
		else if (!searchDto.getFemale() && searchDto.getMale())
			searchParamater.put("roommateCriteria.gender", "0");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		searchParamater.put("status", "1");
		searchParamater.put("roommateCriteria.fromAge", searchDto.getFromAge());
		searchParamater.put("roommateCriteria.toAge", searchDto.getToAge());
		if (!searchDto.getStrNative().equals(""))
			searchParamater.put("strNative", searchDto.getStrNative());
		searchParamater.put("roomCriteria.movingDate", sdf.format(searchDto.getMovingDate()));
		searchParamater.put("roomCriteria.rent", "");
		if (!searchDto.getAddressLine().equals(""))
			searchParamater.put("roomCriteria.searchLocation", searchDto.getAddressLine());
		for (Map.Entry<String, Object> entry : searchParamater.entrySet()) {
			if (entry.getKey().equals("roommateCriteria.fromAge")) {
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()).gte(entry.getValue());
				queryBuilder.must(rangeQueryBuilder);
			} else if (entry.getKey().equals("roommateCriteria.toAge")) {
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()).lte(entry.getValue());
				queryBuilder.must(rangeQueryBuilder);
			} else if (entry.getKey().equals("roomCriteria.movingDate")) {
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey()).to(entry.getValue());
				queryBuilder.must(rangeQueryBuilder);
			} else if (entry.getKey().equals("roomCriteria.rent")) {
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(entry.getKey())
						.from(searchDto.getFromBudget()).to(searchDto.getToBudget());
				queryBuilder.must(rangeQueryBuilder);
			} else {
				MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(entry.getKey(), entry.getValue());
				queryBuilder.must(matchQueryBuilder);
			}
		}
		return queryBuilder;
	}

	public List<Tenant> getListTenant() {
		return listTenant;
	}

	public void setListTenant(List<Tenant> listTenant) {
		this.listTenant = listTenant;
	}

	public List<ParamDto> getNativeList() {
		return nativeList;
	}

	public void setNativeList(List<ParamDto> nativeList) {
		this.nativeList = nativeList;
	}

	public SearchTenantDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(SearchTenantDto searchDto) {
		this.searchDto = searchDto;
	}
}
