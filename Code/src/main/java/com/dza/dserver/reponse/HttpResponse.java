package com.dza.dserver.reponse;

import com.dza.dserver.constant.FinalString;
import com.dza.dserver.cookie.Cookie;
import com.dza.dserver.enumeration.ContentType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;

public class HttpResponse {
    // 响应的第一行
    private static final String HTTP_VERSION = "HTTP/1.1";
    private HttpStates states = HttpStates.OK;

    // 响应头
    private HashMap<String,String> headers = new HashMap<>();
    private LinkedList<Cookie> cookies = new LinkedList<>();
    private ContentType contentType = ContentType.HTML;
    private StringBuilder responseBody = new StringBuilder();
    // 数据
    private BufferedWriter out = null;
    private boolean allowCrossDomain = false;
    private OutputStream outputStream = null;

    private byte[] body = new byte[0];
    private int dataLength = 0;

    public HttpResponse(OutputStream out){
        outputStream = out;
        this.out = new BufferedWriter(new OutputStreamWriter(out));
        headers.put("Connection","close");

    }


    public void buildRespFirstLine() throws IOException {
        responseBody.append(HTTP_VERSION)
                    .append(" ")
                    .append(states.getCode())
                    .append(" ")
                    .append(states.getName())
                    .append(FinalString.CRLF.getString());

    }

    public void buildHeaders() throws IOException {
        // 将header中的k-v写到客户端中
        for(String k:headers.keySet()){
            responseBody.append(k)
                        .append(": ")
                        .append(headers.get(k))
                    .append(FinalString.CRLF.getString());
        }
        if(cookies.size() > 0){
            responseBody.append("Set-Cookie: ");
            // 写cookies
            for(Cookie cookie:cookies){
                responseBody.append(cookie.getKey())
                        .append("=")
                        .append(cookie.getValue())
                        .append(";");
            }
            responseBody.append(FinalString.CRLF.getString());
        }

        if(allowCrossDomain){
            responseBody.append("access-control-allow-origin: *");
            responseBody.append(FinalString.CRLF.getString());
        }

        // 写Content-Type
        responseBody.append("Content-Type: ");
        responseBody.append(contentType.toString());
        responseBody.append(FinalString.CRLF.getString());








    }

    public void setAllowCrossDomain(boolean allow){
        this.allowCrossDomain = allow;
    }


    public byte[] buildResponse() throws IOException {
        buildRespFirstLine();
        buildHeaders();

        if(dataLength > 0){
            responseBody.append("Content-Length: ");
            responseBody.append(dataLength);
            responseBody.append(FinalString.CRLF.getString());
        }
        responseBody.append(FinalString.CRLF.getString());

        return responseBody.toString().getBytes();
    }


    /*==============================================*/

    public void setDataLength(int length){
        this.dataLength = length;

    }

    public void setContentType(ContentType c){
        contentType = c;
    }


}
