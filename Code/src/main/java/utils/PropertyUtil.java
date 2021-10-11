package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties prop = null;
    private static String portFile = "port.properties";
    static{
        try {
            loadPropertyFile();
        } catch (IOException e) {
            logger.error("port.properties file not find!");
            e.printStackTrace();
        }
    }
    private static void loadPropertyFile() throws IOException {
        logger.info("开始读取port.properties配置文件!");
        final InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(portFile);
        prop = new Properties();
        prop.load(in);
        logger.info("读取port.properties完成!");
    }

    public static int getPort(){
        if(prop == null){
            try {
                loadPropertyFile();
            } catch (IOException e) {
                logger.error("{} file not find!",portFile);
                e.printStackTrace();
            }
        }

        final String stringPort = prop.getProperty("port");
        try {
            return Integer.parseInt(stringPort);

        }catch (NumberFormatException e){
            logger.error("The port number you specified is not an integer");
        }
        return -1;
    }

}
