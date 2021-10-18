package com.dza.dserver.server.bio;

import com.dza.dserver.enumeration.ContentType;
import com.dza.dserver.reponse.HttpResponse;
import com.dza.dserver.request.HttpRequest;
import com.dza.dserver.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
                        final HttpResponse httpResponse = new HttpResponse(client.getOutputStream());
                        httpResponse.setAllowCrossDomain(true);
                        final FileInputStream in = new FileInputStream(new File("D:\\CODE\\java\\DServer\\Code\\abc.png"));


                        final byte[] bytes = new byte[in.available()];
                        final int read = in.read(bytes);
                        httpResponse.setDataLength(read);
                        httpResponse.setContentType(ContentType.PNG);

                        final byte[] bytes1 = httpResponse.buildResponse();


                        client.getOutputStream().write(bytes1);
                        client.getOutputStream().write(bytes);

                        client.getOutputStream().flush();
                        client.close();


                        logger.info("客户端关闭!");

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
