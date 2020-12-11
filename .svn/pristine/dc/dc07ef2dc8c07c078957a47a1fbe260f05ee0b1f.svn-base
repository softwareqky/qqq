/**
 * 
 */
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.CapitalPlanVersion;
import project.edge.domain.entity.Project;
import project.edge.domain.view.CapitalPlanVersionBean;


/**
 * @author wwy
 *         转换资金计划版本信息对应的view和entity的转换器。
 */
public class CapitalPlanVersionBeanConverter
        implements ViewConverter<CapitalPlanVersion, CapitalPlanVersionBean> {

    @Override
    public CapitalPlanVersionBean fromEntity(CapitalPlanVersion entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	CapitalPlanVersionBean bean = new CapitalPlanVersionBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
        
        bean.setVersion(entity.getVersion());
        if (entity.getcDatetime() != null) {
            bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
        }
		
		return bean;
    }

    @Override
    public CapitalPlanVersion toEntity(CapitalPlanVersionBean bean, CapitalPlanVersion oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	CapitalPlanVersion entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new CapitalPlanVersion();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);
		
        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }
		
		entity.setVersion(bean.getVersion());
		
		return entity;
    }

}
