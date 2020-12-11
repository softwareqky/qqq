package project.edge.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import project.edge.integration.oa.HrmManager;

/**
 * 同步OA的人力资源数据的Job。
 *
 */
public class HrmSyncJob {

    private static final Logger logger = LoggerFactory.getLogger(HrmSyncJob.class);

    @Resource
    private HrmManager hrmManager;

    /**
     * 执行任务的主体入口。
     */
    public void execute() {

        try {
            boolean r = this.hrmManager.doImport();
            if (!r) {
                logger.error("Sync hrm data from OA failed!");
            }
        } catch (Throwable t) {
            logger.error("Exception executing OA hrm data synchronization job.", t);
        }
    }
}
