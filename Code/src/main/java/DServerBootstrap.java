import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PropertyUtil;

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


    }
}
