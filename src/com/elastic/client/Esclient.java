package com.elastic.client;

import java.net.InetAddress;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Esclient {
	
	TransportClient client;
	private String clusterName = "elasticsearch";
	private String host = "172.30.17.156";
	private int port  = 9300;
	
	@Before
	public void conn() throws Exception{

		Settings settings = Settings.builder()
		        .put("cluster.name", clusterName).build(); 
		client = new PreBuiltTransportClient(settings)
		        .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
	}
	@After
	public void close(){
		if (client != null)
		   client.close();
	}
	@Test
	public void test() {
		//indexIsExist("serverresources");
		getSearchRecord("admin");
	}
	
	public void getSearchRecord(String index) {
		if(indexIsExist(index)) {
			SearchResponse resp = client.prepareSearch(index)
					//.setQuery(QueryBuilders.termsQuery("_id", "wIK0P2cBatfvJP9s0viS"))
					.setPostFilter(QueryBuilders.rangeQuery("doc.offset").from(7000).to(10000))
					.get();
			System.out.println("\nsearch response: "+resp.toString());
		}
		
	}
	public boolean indexIsExist(String index){   
		//BasicConfigurator.configure(); 
		//indices£ºË÷Òý£¬Ö¸Êý
		IndicesExistsResponse resp = client.admin().indices().prepareExists(index).execute().actionGet();
		if(resp.isExists()){
			System.out.format("index [%s] is exist...", index);
			return true;
			//client.admin().indices().prepareDelete("javatest");
		}else {
			System.out.format("index [%s] is NOT exist...", index);
			return false;
		}
		
	}

}
