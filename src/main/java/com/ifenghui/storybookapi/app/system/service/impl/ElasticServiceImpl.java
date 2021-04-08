package com.ifenghui.storybookapi.app.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.system.service.ElasticService;
import com.ifenghui.storybookapi.app.system.service.ErrorLog;
import com.ifenghui.storybookapi.app.system.service.elastic.ElasticResp;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import org.springframework.integration.support.json.JsonObjectMapper;

@Component
public class ElasticServiceImpl implements ElasticService {
    @Value("${elastic.search.host}")
    String host;
    @Value("${elastic.search.port}")
    Integer port;

    @Value("${elastic.search.user}")
    String elasticUser;

    @Value("/${elastic.search.ex.index}/ex")
    String elasticIndex;

    @Value("${elastic.search.story.index}")
    String elasticIndex_story;


    @Autowired
    StoryDao storyDao;

    Logger logger= Logger.getLogger(ElasticServiceImpl.class);
//    RestClient restClient;

    @Autowired
    private JestClient jestClient;

    Header header;

//    String elasticKey="/"+elasticIndex+"/user";

//    public RestClient getClient() {
////        if(restClient==null){}
//
//        synchronized (ElasticServiceImpl.class) {
//            if(restClient==null){
//                restClient = RestClient.builder(
//                        new HttpHost(host, port, "http")
//
//                ).build();
//            }
//        }
//
//        return restClient;
//    }
    public Header getHeader(){
        if(header==null){
            synchronized (ElasticServiceImpl.class) {
                String token = Base64.getEncoder().encodeToString(elasticUser.getBytes());
                header = new BasicHeader("Authorization", "Basic " + token);
            }
        }

        return header;
    }

    @Autowired
    HttpServletRequest httpServletRequest;
    @Override
    public void addException(Exception ex) throws Exception {

        Map<String, String> params = Collections.emptyMap();

//        restClient=this.getClient();
        header=this.getHeader();

        ErrorLog errorLog=new ErrorLog();
        errorLog.setCreateTime(new Date());
        errorLog.setException(ex);
        ObjectMapper mapper = new ObjectMapper();

        if(httpServletRequest!=null){
            errorLog.setRequestUrl(httpServletRequest.getRequestURI());
            errorLog.setRequestData(mapper.writeValueAsString(httpServletRequest.getParameterMap()));

            Enumeration enum1 = httpServletRequest.getHeaderNames();
            String header="";
            while(enum1.hasMoreElements()){
                String key = (String)enum1.nextElement();
                String value = httpServletRequest.getHeader(key);
                header=header+":"+key+"="+value;
            }
            errorLog.setHeader(header);
        }



//        String errResult=mapper.writeValueAsString(errorLog);
//        JsonObjectMapper jsonObjectMapper=new Jackson2JsonObjectMapper();
//
//        UserPlus userPlus=new UserPlus();


//        HttpEntity entity = new NStringEntity(errResult, ContentType.APPLICATION_JSON);
        logger.info("try put elastic");




        Index index=new Index.Builder(errorLog).index(elasticIndex).id(System.currentTimeMillis()+"").type("doc").build();

        try {
            DocumentResult dr= jestClient.execute(index);
            boolean bb=dr.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Response response = restClient.performRequest("PUT", elasticIndex+"/"+System.currentTimeMillis(), params, entity,header);

//        logger.info(response.toString());
//        restClient.close();
//        restClient=null;
    }

    @Override
    public Page<Map> searchStory(String content, int pageNo, int pageSize) throws Exception {

//        String newContent = new String(content.getBytes("iso8859-1"),"utf-8");


        Pageable pageable=new PageRequest(pageNo,pageSize);

        Map<String,String> params = Collections.emptyMap();

        String queryJson="{\"bool\": {\n" +
                "        \"should\": [\n" +
                " { \"match\": { \"name\": \"" + content + "\" } },\n" +
                " { \"wildcard\": { \"name\": \""+"*"+ content + "*" + "\" } },\n" +
                " { \"wildcard\": {\"py_name\": \"" +"*"+ content  +"*"+ "\" } }\n" +
                " ]\n" +
                "}\n}" ;

        String filterJson="{\"bool\": {\n" +
                "      \"should\": [\n" +
                "        {\n" +
                "          \"term\": {\n" +
                "            \"status\": 1\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n}" ;
//        String jsonString = "{\n" +
//
//                " \"query\": {\n" +
//                "    \"bool\": {\n" +
//                "        \"should\": [\n" +
//                               " { \"match\": { \"name\": \"" + content + "\" } },\n" +
//                               " { \"wildcard\": { \"name\": \""+"*"+ content + "*" + "\" } },\n" +
//                               " { \"wildcard\": {\"py_name\": \"" +"*"+ content  +"*"+ "\" } }\n" +
//                          " ]\n" +
//                    "}\n" +
//                "},\n" +
//                "  \"post_filter\": {\n" +
//                "    \"bool\": {\n" +
//                "      \"should\": [\n" +
//                "        {\n" +
//                "          \"term\": {\n" +
//                "            \"status\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
        //如果自定义了排序,那么默认的_score排序就会失效
//                " \"sort\": [\n" +
//                    "{\"id\": \"desc\"}\n" +
//                    "]\n" +
//                " ,\"from\": \""+pageable.getOffset()+"\",\n" +
//                " \"size\": \""+pageable.getPageSize()+"\"\n" +
//                "}";



        JsonObjectMapper jsonObjectMapper=new Jackson2JsonObjectMapper();
        /////////////////
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryJson);

//        QueryBuilder filterBuilder = QueryBuilders.boolQuery();
        QueryBuilder filterBuilder=QueryBuilders.boolQuery().should(QueryBuilders.termQuery("status", 1));
//        Map filterMap=new HashMap();
//        filterMap.put("status",QueryBuilders.boolQuery().should().termQuery("status", 1));
//        filterMap.put("is_now",QueryBuilders.termQuery("is_now", 1));
        searchSourceBuilder.postFilter(filterBuilder);
//        searchSourceBuilder.postFilter(QueryBuilders.termQuery("status", 1));
//        searchSourceBuilder.postFilter(QueryBuilders.termQuery("is_now", 1));
        searchSourceBuilder.from(pageable.getOffset());
        searchSourceBuilder.size(pageable.getPageSize());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(elasticIndex_story).build();

//        List<ArticleInner> articleInners=new ArrayList();
//        ArticleInner articleInner=null;

            SearchResult result = jestClient.execute(search);
            // 自动解析
            JsonObject jsonObject = result.getJsonObject();
            JsonObject hitsobject = jsonObject.getAsJsonObject("hits");
//            long took = jsonObject.get("took").getAsLong();
            long total = hitsobject.get("total").getAsLong();

            String resp=result.getJsonString();


            ElasticResp<Story> elasticResp=(ElasticResp<Story>)jsonObjectMapper.fromJson(resp, ElasticResp.class);

//            logger.debug(response.toString());
            List<Story> statusList=elasticResp.getHits().getSouceList();


            ElasticResp<Map> elasticResp2=(ElasticResp<Map>)jsonObjectMapper.fromJson(resp,ElasticResp.class);
            List<Map> statusListMap=(List<Map>)elasticResp2.getHits().getSouceList();
            Page<Map> userPage=new PageImpl<Map>(statusListMap,pageable,elasticResp.getHits().getTotal());

        ////////////

        return userPage;

    }

    @Override
    public Page<Map> searchStoryNotNow(String content, int pageNo, int pageSize) throws Exception {

        Pageable pageable=new PageRequest(pageNo,pageSize);

        Map<String,String> params = Collections.emptyMap();
        String jsonString = "{\n" +
                " \"from\": "+pageable.getOffset()+",\n" +
                " \"size\": "+pageable.getPageSize()+",\n" +
                " \"query\": {\n" +
                "    \"bool\": {\n" +
                "        \"should\": [\n" +
                "             { \"match\": { \"name\": \"" + content + "\" } },\n" +
                "             { \"wildcard\": { \"name\": \""+"*"+ content + "*" + "\" } },\n" +
                "             { \"wildcard\": {\"py_name\": \"" +"*"+ content  +"*"+ "\" } }\n" +
                "          ],\n" +
                "         \"must_not\": [\n" +
                "            { \"match\": { \"is_now\": \"1\" } }\n" +
                "           ]\n" +


                "     }\n" +

                "  }\n" +
                //如果自定义了排序,那么默认的_score排序就会失效
//                " \"sort\": [\n" +
//                "{\"id\": \"desc\"}\n" +
//                "]\n" +
                "}";
        String queryJson="{\"bool\": {\n" +
                "        \"should\": [\n" +
                " { \"match\": { \"name\": \"" + content + "\" } },\n" +
                " { \"wildcard\": { \"name\": \""+"*"+ content + "*" + "\" } },\n" +
                " { \"wildcard\": {\"py_name\": \"" +"*"+ content  +"*"+ "\" } }\n" +
                " ]\n" +
                "}\n}" ;
        JsonObjectMapper jsonObjectMapper=new Jackson2JsonObjectMapper();
        /////////////////
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryJson);
        QueryBuilder filterBuilder=QueryBuilders.boolQuery().should(QueryBuilders.termQuery("is_now", 1)).should(QueryBuilders.termQuery("status", 1));

        searchSourceBuilder.postFilter(filterBuilder);
        searchSourceBuilder.from(pageable.getOffset());
        searchSourceBuilder.size(pageable.getPageSize());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(elasticIndex_story).build();

//        List<ArticleInner> articleInners=new ArrayList();
//        ArticleInner articleInner=null;

        SearchResult result = jestClient.execute(search);
        // 自动解析
        JsonObject jsonObject = result.getJsonObject();
        JsonObject hitsobject = jsonObject.getAsJsonObject("hits");
//            long took = jsonObject.get("took").getAsLong();
//        long total = hitsobject.get("total").getAsLong();

        String resp=result.getJsonString();


        ElasticResp<Story> elasticResp=(ElasticResp<Story>)jsonObjectMapper.fromJson(resp, ElasticResp.class);

//            logger.debug(response.toString());
        List<Story> statusList=elasticResp.getHits().getSouceList();


        ElasticResp<Map> elasticResp2=(ElasticResp<Map>)jsonObjectMapper.fromJson(resp,ElasticResp.class);
        List<Map> statusListMap=(List<Map>)elasticResp2.getHits().getSouceList();
        Page<Map> userPage=new PageImpl<Map>(statusListMap,pageable,elasticResp.getHits().getTotal());

        ////////////

        return userPage;

    }


    @Override
    public void cleanStoryByStatus0() {

        Story story=new Story();
        story.setStatus(0);
        int pageNo=0;
        int pageSize=100;
        Page<Story> stories= storyDao.findAll(Example.of(story),new PageRequest(pageNo,pageSize));
        removeById(stories.getContent());
        if(stories.hasNext()){
            pageNo++;
            stories= storyDao.findAll(Example.of(story),new PageRequest(pageNo,pageSize));
            removeById(stories.getContent());
        }

//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    }
    private void removeById(List<Story> stories){
        for(Story story:stories){
            removeById(story.getId().intValue());
        }
    }
    private void removeById(int id){
        String query="{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"range\": {\n" +
                "            \"id\": {\n" +
                "              \"gte\": "+id+",\n" +
                "              \"lte\": "+id+"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}\n";

        DeleteByQuery deleteByQuery=new DeleteByQuery.Builder(query).addIndex(elasticIndex_story).build();
        try {
            jestClient.execute(deleteByQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
