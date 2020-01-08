package sa.thread.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sa.thread.entity.RoomOwner;
import sa.thread.entity.RoommateCriteria;
import sa.thread.entity.Tenant;

@Component
@EnableScheduling
public class DataBaseSyncronize {
	@Autowired
	private RoomOwnerService roomOwnerService;

	@Autowired
	private TenantSearchService tenantSearchService;
	/*
	 * @Autowired private RestHighLevelClient restHighLevelClient;
	 */

	@Scheduled(fixedRate = 5000)
	public void syncronizeRoomOwner() {
		List<RoomOwner> lstRoomOwner = roomOwnerService.loadAllData();
		for (RoomOwner roomOwner : lstRoomOwner) {
			updateIndexRoomOwner(roomOwner);			
			//deleteIndexRoomOwner();
			System.out.println("----syncronize----");
			roomOwner.setChange(1);
			roomOwnerService.update(roomOwner);
		}
		// delete record which is not roomOwner

		System.out.println("-----end task-----");
	}

	@Scheduled(fixedRate = 5000)
	public void syncronizeTenant() {
		List<Tenant> lstTenant = tenantSearchService.loadAllData();
		for (Tenant tenant : lstTenant) {
			String[] age = tenant.getRoommateCriteria().getAge().split(",");
			if (age.length != 2) {
				RoommateCriteria roommateCriteria = tenant.getRoommateCriteria();
				roommateCriteria.setFromAge(age[0]);
				roommateCriteria.setToAge(age[0]);
				tenant.setRoommateCriteria(roommateCriteria);
			} else {
				RoommateCriteria roommateCriteria = tenant.getRoommateCriteria();
				roommateCriteria.setFromAge(age[0]);
				roommateCriteria.setToAge(age[1]);
				tenant.setRoommateCriteria(roommateCriteria);
			}
			updateIndexTenant(tenant);
			//deleteIndexTenant();
			System.out.println("----syncronize----");
			tenant.setChange(1);
			tenantSearchService.update(tenant);
		}
		System.out.println("-----end task-----");
	}

	private void deleteIndexTenant() {
		SearchRequest searchRequest = new SearchRequest("tenant");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("status", 0);
		queryBuilder.must(matchQueryBuilder);
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);
		DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest("tenant");
		deleteRequest.setQuery(queryBuilder);
		//DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest(searchRequest);
		try {
			RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9200, "http")));			
			restHighLevelClient.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
			restHighLevelClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void updateIndexRoomOwner(RoomOwner roomOwner) {
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		String strJson = gson.toJson(roomOwner);
		System.out.println(strJson);

		IndexRequest indexRequest = new IndexRequest("roomowner", "_doc", roomOwner.getIdRoomOwner() + "")
				.source(strJson, XContentType.JSON);
		try {
			RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9200, "http")));
			restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
			restHighLevelClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteIndexRoomOwner() {
		SearchRequest searchRequest = new SearchRequest("roomowner");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("status", 0);
		queryBuilder.must(matchQueryBuilder);
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);
		DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest("roomowner");
		deleteRequest.setQuery(queryBuilder);
		//DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest(searchRequest);
		try {
			RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9200, "http")));			
			restHighLevelClient.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
			restHighLevelClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateIndexTenant(Tenant tenant) {
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		String strJson = gson.toJson(tenant);
		System.out.println(strJson);

		IndexRequest indexRequest = new IndexRequest("tenant", "_doc", tenant.getIdTenant() + "").source(strJson,
				XContentType.JSON);
		try {
			RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9200, "http")));
			restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
			restHighLevelClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
