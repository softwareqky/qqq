package project.edge.web.startup;

import org.flowable.engine.ProcessEngines;

// import org.flowable.engine.ProcessEngines;

// import tuan.service.system.SystemConfigService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Web Container启动后，Spring Context初始化完成后执行的系统初始化。
 * 
 */
@Component
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    // @Resource
    // private SystemConfigService systemConfigService;

    /**
     * 注解@Component和@PostConstruct确保此类的该方法在SpringContext初始化结束后，被调用。
     */
    @PostConstruct
    public void start() {
        try {

            logger.info("Starting web ...");

            // 加载系统配置
            // this.systemConfigService.loadConfig();

            // 订阅JMS(交给Spring配置处理)

            // logger.info("Init flowable ...");
            // ProcessEngines.init();

        } catch (Exception e) {
            logger.error("Exception while starting web :", e);
        }
    }


    @PreDestroy
    public void destroy() {
        // logger.info("destroy flowable ...");
        // ProcessEngines.destroy();
    }
    /**
     * 订阅JMS。
     * 
     */
    // public void subscibeJms() throws JMSException, NamingException {
    // }
}
