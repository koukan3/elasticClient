package com.elastic.client;

import java.net.InetAddress;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Esclient {
	
	TransportClient client;
	
	@Before
	public void conn() throws Exception{

		Settings settings = Settings.builder()
		        .put("cluster.name", "elasticsearch").build(); 
		client = new PreBuiltTransportClient(settings)
		        .addTransportAddress(new TransportAddress(InetAddress.getByName("172.30.17.156"), 9300));
	}
	@After
	public void close(){
		if (client != null)
		   client.close();
	}
	@Test
	public void test() {
		indexIsExist("1244");
		
	}
	public boolean indexIsExist(String index){   
		//BasicConfigurator.configure(); 
		//indices£ºË÷Òý£¬Ö¸Êý
		IndicesExistsResponse resp = client.admin().indices().prepareExists(index).execute().actionGet();
		if(resp.isExists()){
			System.out.format("index %s is exist...", index);
			return true;
			//client.admin().indices().prepareDelete("javatest");
		}else {
			System.out.format("index %s is NOT exist...", index);
			return false;
		}
		
	}

}
