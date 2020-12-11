package project.edge.domain.mobile.converter;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.mobile.view.ProjectCheckVerifyBean;

/**
 * @author angel_000
 *
 */
public class ProjectCheckVerifyBeanConverter {

    /**
     * 转换检查验收提交信息。
     * 
     * @param bean
     * @param creator
     * @return
     */
    public ProjectCheck toEntity(ProjectCheckVerifyBean bean, String creator) {

        ProjectCheck entity = new ProjectCheck();

        entity.setcDatetime(new Date());
        entity.setCreator(creator);
        entity.setmDatetime(entity.getcDatetime());

        Project project = new Project();
        project.setId(bean.getProjectCheckId());
        entity.setProject(project);


        DataOption verifyResult = new DataOption();
        verifyResult.setId(bean.getVerifyResult());
        entity.setVerifyResult(verifyResult);

        if (!StringUtils.isEmpty(bean.getVerifyDate())) {
            try {
                entity.setVerifyDate(
                        DateUtils.string2Date(bean.getVerifyDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setImproveResultVerify(bean.getImproveResultVerify());
        entity.setRemark(bean.getRemark());

        return entity;
    }
}
