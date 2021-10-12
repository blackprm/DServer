package com.dza.dserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public  abstract class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    abstract public void start();
    abstract public void shutdown();

    public static Server getInstance(String serverClass,int port) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        final StringBuilder classPath = new StringBuilder();

        classPath.append("com.dza.dserver.server.")
                 .append(serverClass)
                 .append("."); // com.dza.dserver.server.bio.

        // TODO bio => Bio
        final byte[] bytes = serverClass.getBytes(StandardCharsets.UTF_8);
        bytes[0] = (byte) (bytes[0] - 32);

        classPath.append(new String(bytes)).append("Server");// com.dza.dserver.server.bio.BioServer



        logger.info("get class {}",classPath.toString());
        return (Server) Class.forName(classPath.toString()).getConstructor(int.class).newInstance(port);

    }
}
