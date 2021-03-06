package com.userModule.config;
import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.UnknownHostException;
import org.elasticsearch.common.settings.Settings;
@Configuration
public class ElasticSearchConfiguration{

    @Value("${elasticsearch.host}") 
    public String host;
    @Value("${elasticsearch.port}") 
    public int port;

    public String getHost() {
		return host;
	}


	public int getPort() {
		return port;
    }
    
    @Bean
    public Client client(){
        TransportClient client = null;
        try{
            System.out.println("host:"+ host+"port:"+port);
            client = new PreBuiltTransportClient(Settings.EMPTY)
            .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }
}
