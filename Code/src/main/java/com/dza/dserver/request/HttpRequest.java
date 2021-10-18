package com.dza.dserver.request;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.dza.dserver.cookie.Cookie;
import com.dza.dserver.enumeration.ContentType;
import com.dza.dserver.enumeration.HttpMethod;

public class HttpRequest {
    public static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpMethod method;
    private String requestUrl;
    private String httpProtocol;
    private Cookie[] cookies;
    private HashMap<String,String> getParams = null; // get的参数
    private HashMap<String,String> headers = new HashMap<>();
    private BufferedReader reader = null;
    private ContentType contentType = null;
    private int contentLength;
    private Map<String,Object> jsonMap = null;

    public HttpRequest(InputStream in) throws IOException {
        reader = new BufferedReader(new InputStreamReader(in));
        parseHttpRequestLine(); // 解析请求行！
        parseHttpRequestHeader(); // 解析HttpHeader
        parseCookies();
        if(contentLength != 0){  // 如果有数据，就解析数据
            parseBody();
        }

//        final char[] bytes = new char[1024 * 10];
//        int len = reader.read(bytes);
//        System.out.println(len + "  " + new String(bytes,0,len));
    }

    private void parseCookies() {
        final String cookie = headers.get("Cookie");
        if(cookie == null){
            return;
        }

        final String[] k_vs = cookie.split(";");
        cookies= new Cookie[k_vs.length];
        int index = 0;
        for(String kv:k_vs){
            final String[] k_v = kv.split("=");
            cookies[index++] = new Cookie(k_v[0].trim(), k_v[1].trim());
        }
        logger.info("cookies is {}",Arrays.toString(cookies));
    }

    private void parseBody() throws IOException {
        // 读取body数据
        char [] data = new char[contentLength];
        final int read = reader.read(data);
        final String dataString = new String(data, 0, read);
//        logger.info("read data over : data is {}", dataString);

        if("JSON".equals(contentType.toString())) {
            jsonMap = new Gson().fromJson(dataString, new TypeToken<HashMap<String, Object>>() {
            }.getType());
            logger.info("json data is {}",jsonMap);
        }
        else
            logger.info("data is {}",dataString);
    }

    public void parseHttpRequestHeader() throws IOException {
        String str = null;
        // 记录到请求头
        while(!"".equals((str = reader.readLine()))){
            final String[] split = str.split(": ");
            headers.put(split[0],split[1]);
        }

        // 发送数据的类型
        final String MIME = headers.getOrDefault("Content-Type", null);
        if(MIME != null){
            String type = MIME.split("/")[1];
            if("json".equals(type)){
                contentType = ContentType.valueOf("JSON");
                logger.info("Content-Type is {}", contentType);
            }
        }

        // 发送数据的长度
        contentLength = Integer.parseInt(headers.getOrDefault("Content-Length","0"));
    }

    public  void parseHttpRequestLine() throws IOException,IllegalArgumentException {
        final String[] headerLine = reader.readLine().split("\\s+");
        logger.info("http header line parse over : {} {} {}",headerLine[0],headerLine[1],headerLine[2]);
        method = HttpMethod.valueOf(headerLine[0]);
        if(!headerLine[1].contains("?"))
            requestUrl = headerLine[1];
        else{
            // 解析get参数
            final String[] urlAndParams = headerLine[1].split("\\?");  ///parse/absec?name=dza&age=18
            requestUrl = urlAndParams[0];
            getParams = new HashMap<>();
            final String[] kvs = urlAndParams[1].split("&");
            for(String kv:kvs){
                final String[] kAndV = kv.split("=");
                getParams.put(kAndV[0],kAndV[1]);
            }

            logger.info("get params is {}",getParams);
        }
        httpProtocol = headerLine[2];

    }





}
