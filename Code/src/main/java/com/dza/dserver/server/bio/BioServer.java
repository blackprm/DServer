package com.dza.dserver.server.bio;

import com.dza.dserver.constant.FinalString;
import com.dza.dserver.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dza.dserver.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioServer extends Server {
    private static Logger logger = LoggerFactory.getLogger(BioServer.class);
    private ServerSocket server;
    private volatile boolean isRunning;

    public BioServer(int port) throws IOException {
        server = new ServerSocket(port);
        isRunning = false;
    }

    @Override
    public void start() {
        isRunning = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    try {
                        final Socket client = server.accept();
                        final HttpRequest httpRequest = new HttpRequest(client.getInputStream());
                        logger.info("接收到一个http连接，解析完毕");
                        client.getOutputStream().write("HTTP/1.1 200 ok".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write("Connection: close".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write("Content-Length: 0".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write("access-Control-Allow-Credentials: true".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write("access-control-allow-origin: *".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write("access-Control-Allow-Headers: *".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write("access-Control-Allow-Methods: *".getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));

                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));
                        client.getOutputStream().flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("服务器退出成功!");

            }
        }).start();

    }

    @Override
    public void shutdown() {
        isRunning = false;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
