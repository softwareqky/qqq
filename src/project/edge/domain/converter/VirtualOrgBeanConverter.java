
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.VirtualOrgBean;


/**
 * @author angel_000
 *         转换虚拟组织表对应的view和entity的转换器。
 *
 */
public class VirtualOrgBeanConverter implements ViewConverter<VirtualOrg, VirtualOrgBean> {

    @Override
    public VirtualOrgBean fromEntity(VirtualOrg entity, MessageSource messageSource,
            Locale locale) {

        VirtualOrgBean bean = new VirtualOrgBean();

        bean.setId(entity.getId());
        
        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setVirtualOrgName(entity.getVirtualOrgName());

        bean.setShortName(entity.getShortName());
        bean.setOrgCode(entity.getOrgCode());

        VirtualOrg virtualOrg = entity.getPid();
        if(virtualOrg != null) {
            bean.setPid_(virtualOrg.getId());
            bean.setPidText(virtualOrg.getVirtualOrgName());
        }
        
        Person person = entity.getLeader();

        if (person != null) {
            bean.setLeader_(person.getId());
            bean.setLeaderText(person.getPersonName());
        } else {
            bean.setLeader_("");
            bean.setLeaderText("");
        }
        
        bean.setLevel(entity.getLevel());
        bean.setOrgOrder(entity.getOrgOrder());
        bean.setSyncStatus(entity.getSyncStatus());
        
        if (entity.getIsEchartsShow() == 0) {
        	bean.setIsEchartsShow(0);
        	bean.setIsEchartsShowText("否");
        } else {
        	bean.setIsEchartsShow(entity.getIsEchartsShow());
        	bean.setIsEchartsShowText("是");
        }
        
        if (entity.getIsBudgetMerge() == 0) {
        	bean.setIsBudgetMerge(0);
        	bean.setIsBudgetMergeText("否");
        } else {
        	bean.setIsBudgetMerge(entity.getIsBudgetMerge());
        	bean.setIsBudgetMergeText("是");
        }
        
        if (entity.getIsGetAllBudget() == 0) {
        	bean.setIsGetAllBudget(0);
        	bean.setIsGetAllBudgetText("否");
        } else {
        	bean.setIsGetAllBudget(entity.getIsGetAllBudget());
        	bean.setIsGetAllBudgetText("是");
        }

        return bean;
    }

    @Override
    public VirtualOrg toEntity(VirtualOrgBean bean, VirtualOrg oldEntity,
            AbstractSessionUserBean user, Date now) {
        VirtualOrg entity = oldEntity;

        if (entity == null) {// 表示新建
            entity = new VirtualOrg();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else {// 表示修改
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

        entity.setVirtualOrgName(bean.getVirtualOrgName());
        entity.setShortName(bean.getShortName());
        entity.setOrgCode(bean.getOrgCode());
        //entity.setPid(bean.getPid());
        if (!StringUtils.isEmpty(bean.getPid_())) {
            VirtualOrg virtualOrg = new VirtualOrg();
            virtualOrg.setId(bean.getPid_());
            entity.setPid(virtualOrg);
        }       
        
        if (!StringUtils.isEmpty(bean.getLeader_())) {
            Person person = new Person();
            person.setId(bean.getLeader_());
            entity.setLeader(person);
        }
        
        entity.setLevel(bean.getLevel());
        entity.setOrgOrder(bean.getOrgOrder());
        entity.setSyncStatus(bean.getSyncStatus());
        entity.setIsEchartsShow(bean.getIsEchartsShow());
        entity.setIsBudgetMerge(bean.getIsBudgetMerge());
        entity.setIsGetAllBudget(bean.getIsGetAllBudget());

        return entity;
    }

}
