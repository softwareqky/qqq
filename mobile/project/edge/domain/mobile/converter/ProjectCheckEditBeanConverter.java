package project.edge.domain.mobile.converter;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.mobile.view.ProjectCheckEditBean;

/**
 * @author angel_000
 *
 */
public class ProjectCheckEditBeanConverter {

    /**
     * 转换修改检查提交信息。
     * 
     * @param bean
     * @param creator
     * @return
     */
    public ProjectCheck toEntity(ProjectCheckEditBean bean, String creator) {

        ProjectCheck entity = new ProjectCheck();

        entity.setcDatetime(new Date());
        entity.setCreator(creator);
        entity.setmDatetime(entity.getcDatetime());


        Project project = new Project();
        project.setId(bean.getProjectCheckId());
        entity.setProject(project);

        DataOption checkType = new DataOption();
        checkType.setId(bean.getCheckType());
        entity.setCheckType(checkType);

        entity.setCheckingUnit(bean.getCheckingUnit());
        entity.setChechedUnit(bean.getChechedUnit());
        entity.setCheckedContent(bean.getCheckedContent());
        entity.setCheckBasis(bean.getCheckBasis());
        entity.setChecker(bean.getChecker());
        entity.setIsImprove(bean.getIsImprove());

        DataOption checkResult = new DataOption();
        checkResult.setId(bean.getCheckResult());
        entity.setCheckResult(checkResult);

        if (!StringUtils.isEmpty(bean.getCheckDate())) {
            try {
                entity.setCheckDate(
                        DateUtils.string2Date(bean.getCheckDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getImproveReqDate())) {
            try {
                entity.setImproveReqDate(
                        DateUtils.string2Date(bean.getImproveReqDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setCheckResultContent(bean.getCheckResultContent());
        entity.setRemark(bean.getRemark());

        return entity;
    }
}
