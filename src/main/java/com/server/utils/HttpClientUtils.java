package com.server.utils;

import com.server.entity.HttpResponseEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtils {


    /**
     *  多参数上传并且含多个文件
     * @param params_map
     * @param list
     * @return
     */
    public static HttpResponseEntity post_init(String host, Integer port, String uri, Map<String, String> params_map, List<File> list) {
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;
        String data = null;
        Integer code = 500;
        try{
            httpClient = HttpClients.createDefault();
            String url = settUrl(host, port, uri);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(setHttpClientConfig());
            HttpEntity httpEntity = getHttpEntity(params_map, list);
            httpPost.setEntity(httpEntity);
            response = httpClient.execute(httpPost);
            code = response.getStatusLine().getStatusCode();
            data = EntityUtils.toString(response.getEntity());
            EntityUtils.consume(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return new HttpResponseEntity(code, data);
    }

    /**
     * 多参数上传
     * @param params_map
     * @return
     */
    public static HttpResponseEntity post_init(String host, Integer port, String uri, Map<String, String> params_map) {
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;
        String data = null;
        Integer code = 500;
        try{
            httpClient = HttpClients.createDefault();
            String url = settUrl(host, port, uri);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(setHttpClientConfig());
            HttpEntity httpEntity = getHttpEntity(params_map);
            httpPost.setEntity(httpEntity);
            response = httpClient.execute(httpPost);
            code = response.getStatusLine().getStatusCode();
            data = EntityUtils.toString(response.getEntity());
            EntityUtils.consume(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return new HttpResponseEntity(code, data);
    }

    /**
     * 单个参数上传
     * @param key
     * @param value
     * @return
     */
    public static HttpResponseEntity post_init(String host, Integer port, String uri, String key, String value) {
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;
        String data = null;
        Integer code = 500;
        try{
            httpClient = HttpClients.createDefault();
            String url = settUrl(host, port, uri);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(setHttpClientConfig());
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(key, value));
            HttpEntity httpEntity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
            httpPost.setEntity(httpEntity);
            response = httpClient.execute(httpPost);
            code = response.getStatusLine().getStatusCode();
            data = EntityUtils.toString(response.getEntity());
            EntityUtils.consume(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return new HttpResponseEntity(code, data);
    }

    private static HttpEntity getHttpEntity(Map<String, String> params_map, List<File> list) throws UnsupportedEncodingException {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if(!CollectionUtils.isEmpty(params_map)){
            Set<Map.Entry<String, String>> entries = params_map.entrySet();
            for(Map.Entry<String, String> entry: entries){
                multipartEntityBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
            }
        }
        if(!CollectionUtils.isEmpty(list)){
            for(File file:list){
                multipartEntityBuilder.addPart("file", new FileBody(file));
            }
        }
        return multipartEntityBuilder.build();
    }

    private static HttpEntity getHttpEntity(Map<String, String> params_map){
        List<NameValuePair> params = new ArrayList<>();
        if(!CollectionUtils.isEmpty(params_map)){
            Set<Map.Entry<String, String>> entries = params_map.entrySet();
            for(Map.Entry<String, String> entry: entries){
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
    }

    private static RequestConfig setHttpClientConfig(){
        return RequestConfig
                .custom()
                .setConnectionRequestTimeout(2000)
                .setConnectTimeout(3000)
                .setSocketTimeout(3000)
                .build();
    }

    /**
     * get方式
     * 带参数
     * @param params_map  可以为null
     * @return
     */
    public static HttpResponseEntity get_init(String host, Integer port, String uri, Map<String, String> params_map){
        HttpClient httpClient = null;
        HttpResponse response = null;
        String data = null;
        Integer code = 500;
        try{
            httpClient = new DefaultHttpClient();
            String url = setGetUrl(host, port, uri, params_map);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(setHttpClientConfig());
            response = httpClient.execute(httpGet);
            code = response.getStatusLine().getStatusCode();
            data = EntityUtils.toString(response.getEntity());
            EntityUtils.consume(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return new HttpResponseEntity(code, data);
    }

    /**
     * 不带参数方式
     * @param host
     * @param port
     * @param uri
     * @return
     */
    public static HttpResponseEntity get_init(String host, Integer port, String uri){
        HttpClient httpClient = null;
        HttpResponse response = null;
        String data = null;
        Integer code = 500;
        try{
            httpClient = new DefaultHttpClient();
            String url = setGetUrl(host, port, uri, null);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(setHttpClientConfig());
            response = httpClient.execute(httpGet);
            code = response.getStatusLine().getStatusCode();
            data = EntityUtils.toString(response.getEntity());
            EntityUtils.consume(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return new HttpResponseEntity(code, data);
    }

    private static String settUrl(String host, Integer port, String uri){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://");
        stringBuffer.append(host);
        stringBuffer.append(":");
        stringBuffer.append(port);
        stringBuffer.append(uri);
        return stringBuffer.toString();
    }

    private static String setGetUrl(String host, Integer port, String uri, Map<String ,String> params_map) throws URISyntaxException {
        String url = settUrl(host, port, uri);
        URIBuilder uriBuilder = new URIBuilder(url);
        if(!CollectionUtils.isEmpty(params_map)){
            Set<Map.Entry<String, String>> entries = params_map.entrySet();
            for(Map.Entry<String, String> entry: entries){
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return uriBuilder.build().toString();
    }


}
