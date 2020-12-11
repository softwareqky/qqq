/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectFavorite;
import project.edge.domain.view.MainInfoProjectInfoListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoProjectInfoListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoProjectInfoListBean fromEntity(Project p, MessageSource messageSource,
            Locale locale) {


        if (p == null) {
            return null;
        }

        MainInfoProjectInfoListBean bean = new MainInfoProjectInfoListBean();
        bean.setId(p.getId());
        bean.setName(p.getProjectName());
        if(p.getMainLeader() != null) {
        	bean.setLeader(p.getMainLeader().getPersonName());
        }
        bean.setStatus(p.getProjectStatus().getOptionName());
        // bean.setStatus(messageSource
        // .getMessage(FlowStatusEnum.getResouceName(p.getFlowStatusProject()), null, locale));
        if (p.getProjectStartDate() != null) {
            bean.setTime(DateUtils.date2String(p.getProjectStartDate(), Constants.DATE_FORMAT));
        }

        return bean;
    }

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoProjectInfoListBean fromEntityFavorite(ProjectFavorite p,
            MessageSource messageSource, Locale locale) {


        if (p == null) {
            return null;
        }

        MainInfoProjectInfoListBean bean = new MainInfoProjectInfoListBean();
        bean.setId(p.getId());
        bean.setName(p.getProject().getProjectName());

        Project project = p.getProject();
        if (project != null) {
            DataOption dataOption = p.getProject().getProjectStatus();
            if (dataOption != null) {
                bean.setStatus(dataOption.getOptionName());
            }
            
            Person leader = p.getProject().getMainLeader();
            if(leader != null) {
            	bean.setLeader(leader.getPersonName() );
            }
        }

        if (p.getProject().getProjectStartDate() != null) {
            bean.setTime(DateUtils.date2String(p.getProject().getProjectStartDate(),
                    Constants.DATE_FORMAT));
        }

        return bean;
    }

}
