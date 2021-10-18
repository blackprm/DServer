package com.dza.dserver;

import com.dza.dserver.server.Server;
import com.dza.dserver.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Scanner;

/**
 * web server的入口类
 * TODO 负责读取配置文件中的端口
 * TODO 开启BIO端口监听
 */
public class DServerBootstrap {
    private static Logger logger = LoggerFactory.getLogger(DServerBootstrap.class);
    public void run(){
        final int port = PropertyUtil.getPort();

        if(port == -1){
            logger.error("Failed to get port number!we will kill application!");
            return;
        }

        logger.info("成功获取到配置文件中的端口号！");

        Server server = null;
        try {
            server = Server.getInstance("bio", port);
            server.start();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            logger.error("服务器初始化错误，请检查端口是否被占用，以及服务器模式是够为bio 或 nio");
            e.printStackTrace();
            return;
        }

        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            final String command = scanner.next().toUpperCase(Locale.ROOT);
            if(command.equals("EXIT")){
                logger.info("收到退出指令!即将退出！");
                server.shutdown();


            }
        }


    }

    public static void main(String[] args) {
        new DServerBootstrap().run();
    }
}
