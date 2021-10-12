package com.dza.dserver.server.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dza.dserver.server.Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
                        final BufferedInputStream bin = new BufferedInputStream(client.getInputStream());
                        final byte[] datas = new byte[bin.available()];
                        bin.read(datas);
                        System.out.println(new String(datas));
                        client.close();
                        logger.info("接收到一个http连接！");
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
