/**
 * 
 */
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.ContractBudget;
import project.edge.domain.entity.Project;
import project.edge.domain.view.ContractBudgetBean;


/**
 * @author wwy
 *         转换合同预算信息对应的view和entity的转换器。
 */
public class ContractBudgetBeanConverter
        implements ViewConverter<ContractBudget, ContractBudgetBean> {

    @Override
    public ContractBudgetBean fromEntity(ContractBudget entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	ContractBudgetBean bean = new ContractBudgetBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();

        if (project != null) {
        	bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
		
		bean.setName(entity.getName());
		bean.setContent(entity.getContent());
		bean.setContract(entity.getContract());
		
		return bean;
    }

    @Override
    public ContractBudget toEntity(ContractBudgetBean bean, ContractBudget oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	ContractBudget entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new ContractBudget();

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
            entity.setProject(project);
        }
		
		entity.setName(bean.getName());
		entity.setContent(bean.getContent());
		entity.setContract(bean.getContract());
		
		return entity;
    }

}
