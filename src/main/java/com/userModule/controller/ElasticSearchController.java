package com.userModule.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*import org.apache.log4j.Level;
import org.apache.log4j.Logger;*/
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userModule.model.User;
/**
 * This class is to demo how ElasticsearchTemplate can be used to Save/Retrieve
 */

@RestController
@RequestMapping("/rest/users")
public class ElasticSearchController {
	
	//private static final Logger LOG = Logger.getLogger(ElasticSearchController.class.getName());
	private static Log log = LogFactory.getLog(ElasticSearchController.class);

    @Autowired
    Client client;
    
    @RequestMapping(value = "/elk")
	public String helloWorld() {
		String response = "Welcome to JavaInUse" + new Date();
		//LOG.log(Level.INFO, response);
		log.info("Inside test ELK!!"+response);	
		return response;
	}
    
    @PostMapping("/create")
    public String create(@RequestBody User user) throws IOException
	{    
		IndexResponse response = client.prepareIndex("userelastic", "user", String.valueOf(user.getId()))
                .setSource(jsonBuilder()
                        .startObject()
                        .field("name", user.getFirstName())
                        .field("accountId", user.getAccount())
                        .endObject()
                )
                .get();
		//LOG.log(Level.INFO, response.getId()+" saved successfully");
		log.info("Inside create");
        System.out.println("response id:"+response.getId());
        return response.getResult().toString();
	}  


    @GetMapping("/view/{id}")
    public Map<String, Object> view(@PathVariable String id) {
        GetResponse getResponse = client.prepareGet("userelastic", "user", id).get();
        //LOG.log(Level.INFO, id+" fetched successfully");
        log.info("Inside view");
        System.out.println(getResponse.getSource());
        return getResponse.getSource();
    }
    /*@GetMapping("/view/name/{field}")
    public Map<String, Object> searchByName(@PathVariable final String field) {
        Map<String,Object> map = null;
        SearchResponse response = client.prepareSearch("users")
                                .setTypes("employee")
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchQuery("name", field))
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        map =   searchHits.get(0).getSource();
        return map;

    }*/

    @GetMapping("/update/{id}")
    public String update(@PathVariable final String id) throws IOException {

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("userelastic")
                .type("user")
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("name", "Rajesh")
                        .endObject());
        try {
            UpdateResponse updateResponse = client.update(updateRequest).get();
            System.out.println(updateResponse.status());
            return updateResponse.status().toString();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return "Exception";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable final String id) {

        DeleteResponse deleteResponse = client.prepareDelete("userelastic", "user", id).get();

        System.out.println(deleteResponse.getResult().toString());
        return deleteResponse.getResult().toString();
    }
}