package project.edge.domain.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;

import project.edge.domain.entity.Plan;
import project.edge.domain.view.MainInfoPlanFinishListBean;

public class MainInfoPlanFinishListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoPlanFinishListBean fromEntity(Plan p, MessageSource messageSource,
            Locale locale) {

        if (p == null) {
            return null;
        }

        MainInfoPlanFinishListBean bean = new MainInfoPlanFinishListBean();
        bean.setId(p.getId());
        if(p.getVirtualOrg() != null) {
            bean.setVirtualOrgName(p.getVirtualOrg().getVirtualOrgName());
        }

        // 总任务
        if (p.getTotalTaskCount() != null) {
            bean.setTaskCount(p.getTotalTaskCount());
        } else {
            bean.setTaskCount(0);
        }
        
        // 总滞后数
        if (p.getTotalDelayTaskCount() != null) {
        	bean.setUnfinishTaskCount(p.getTotalDelayTaskCount());
        } else {
        	bean.setUnfinishTaskCount(0);
        }
        
        // 总完成数
        if (p.getTotalFinishTaskCount() != null) {
        	bean.setFinishTaskCount(p.getTotalFinishTaskCount());
        } else {
        	bean.setFinishTaskCount(0);
        }
        

        return bean;

    }
}
