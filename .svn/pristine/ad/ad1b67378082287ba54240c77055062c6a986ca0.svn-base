package project.edge.mobile.controller.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.service.Service;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.mobile.converter.ProjectCheckBeanConverter;
import project.edge.domain.mobile.converter.ProjectCheckEditBeanConverter;
import project.edge.domain.mobile.converter.ProjectCheckFieldBeanConverter;
import project.edge.domain.mobile.converter.ProjectCheckImproveBeanConverter;
import project.edge.domain.mobile.converter.ProjectCheckInfoBeanConverter;
import project.edge.domain.mobile.converter.ProjectCheckListItemBeanConverter;
import project.edge.domain.mobile.converter.ProjectCheckVerifyBeanConverter;
import project.edge.domain.mobile.view.FieldListWrapperBean;
import project.edge.domain.mobile.view.OptionBean;
import project.edge.domain.mobile.view.PagedResultBean;
import project.edge.domain.mobile.view.ProjectCheckEditBean;
import project.edge.domain.mobile.view.ProjectCheckFieldBean;
import project.edge.domain.mobile.view.ProjectCheckImproveBean;
import project.edge.domain.mobile.view.ProjectCheckListItemBean;
import project.edge.domain.mobile.view.ProjectCheckVerifyBean;
import project.edge.mobile.controller.common.GenericController;
import project.edge.service.process.ProjectCheckService;
import project.edge.service.system.DataOptionService;

/**
 * @author angel_000
 *         项目检查列表。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class ProjectCheckInfoController extends GenericController<ProjectCheck> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectCheckInfoController.class);

    @Resource
    private MessageSource messageSource;

    @Resource
    private ProjectCheckService projectCheckService;

    @Resource
    private DataOptionService dataOptionService;

    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return ProjectCheckInfoController.logger;
    }

    @Override
    public String getDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.PROJECT_CHECK.value();
    }

    @Override
    protected Service<ProjectCheck, String> getDataService() {
        // TODO Auto-generated method stub
        return this.projectCheckService;
    }

    @Override
    protected Object converter(ProjectCheck entity) {
        // TODO Auto-generated method stub
        return entity;
    }

    @Override
    protected Object converter(ProjectCheck entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ProjectCheckBeanConverter p = new ProjectCheckBeanConverter();
        return p.fromEntity(entity, messageSource, locale);
    }

    @Override
    protected Map<String, List<OptionBean>> getOptionMap(Map<String, Object> paramMap,
            Locale locale) {

        if (paramMap == null || !paramMap.containsKey("userId")) {
            return null;
        }

        Map<String, List<OptionBean>> map = new HashMap<>();


        ArrayList<OptionBean> ProjectCheckTypeOptions = new ArrayList<OptionBean>();
        CommonFilter f = null;
        f = new CommonFilter();
        f.addExact("dataType", DataTypeEnum.PROJECT_CHECK_TYPE.value());
        List<DataOption> ProjectCheckTypeList = this.dataOptionService.list(f, null);
        if (ProjectCheckTypeList != null) {
            for (DataOption d : ProjectCheckTypeList) {
                OptionBean option = new OptionBean();
                option.setOptionKey(d.getId());
                option.setOptionValue(d.getOptionName());

                ProjectCheckTypeOptions.add(option);
            }
        }
        map.put("ProjectCheckTypeOptions", ProjectCheckTypeOptions);

        ArrayList<OptionBean> CheckResultOptions = new ArrayList<OptionBean>();

        f = new CommonFilter();
        f.addExact("dataType", DataTypeEnum.CHECK_RESULT.value());
        List<DataOption> CheckResultTypeList = this.dataOptionService.list(f, null);
        if (CheckResultTypeList != null) {
            for (DataOption d : CheckResultTypeList) {
                OptionBean option = new OptionBean();
                option.setOptionKey(d.getId());
                option.setOptionValue(d.getOptionName());
                CheckResultOptions.add(option);
            }
        }

        map.put("CheckResultOptions", CheckResultOptions);

        ArrayList<OptionBean> VerifyResultOptions = new ArrayList<OptionBean>();

        f = new CommonFilter();
        f.addExact("dataType", DataTypeEnum.VERIFY_RESULT.value());
        List<DataOption> VerifyResultTypeList = this.dataOptionService.list(f, null);
        if (VerifyResultTypeList != null) {
            for (DataOption d : VerifyResultTypeList) {
                OptionBean option = new OptionBean();
                option.setOptionKey(d.getId());
                option.setOptionValue(d.getOptionName());
                VerifyResultOptions.add(option);
            }
        }

        map.put("VerifyResultOptions", VerifyResultOptions);

        ArrayList<OptionBean> FlowStatus = new ArrayList<OptionBean>();

        for (FlowStatusEnum s : FlowStatusEnum.values()) {
            OptionBean option = new OptionBean();
            option.setOptionKey(s.value().toString());
            option.setOptionValue(messageSource.getMessage(s.resourceName(), null, locale));
            FlowStatus.add(option);
        }

        map.put("FlowStatus", FlowStatus);

        return map;

    }


    /**
     * 项目检查列表。
     * 
     * @param projectId
     * @param page
     * @param rows
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/project-list", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckList(
            @RequestParam(required = false, defaultValue = "") String projectId,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // String userid = userBean.getSessionUserId();

            // 过滤
            CommonFilter filter = new CommonFilter();
            /*
             * filter = new CommonFilter().addExact("ps.user.id", userid)
             * .addExact("pj.flowStatusProject", FlowStatusEnum.REVIEW_PASSED.value());
             */

            if (!StringUtils.isEmpty(projectId)) {
                filter.addExact("project.id", projectId);
            }

            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            if (page > 0 && rows > 0) {
                pageCtrl.setCurrentPage(page);
                pageCtrl.setPageSize(rows);
            }

            // 设置排序信息
            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("cDatetime"));

            List<ProjectCheck> projectCheckList =
                    this.projectCheckService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = projectCheckList.size();

            PagedResultBean<ProjectCheckListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                ProjectCheckListItemBeanConverter converter =
                        new ProjectCheckListItemBeanConverter();
                for (ProjectCheck p : projectCheckList) {
                    paged.getRows().add(converter.fromEntity(p, this.messageSource, locale));
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 检查一览。
     * 
     * @param projectId
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/info", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckInfo(
            @RequestParam(required = true, defaultValue = "") String projectCheckId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 参数检查
            if (StringUtils.isEmpty(projectCheckId)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectCheck p = this.projectCheckService.find(projectCheckId);

            if (p != null) {

                ProjectCheckInfoBeanConverter converter = new ProjectCheckInfoBeanConverter();
                FieldListWrapperBean result = converter.fromEntity(p, this.messageSource, locale);

                jsonResult.setResult(result);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 增加检查字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/add-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsAdd(
            @RequestParam(required = true, defaultValue = "") String projectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();


        try {

            // 参数检查
            if (StringUtils.isEmpty(projectId)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            String userId = userBean.getSessionUserId();

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);

            FieldListWrapperBean listBean = new FieldListWrapperBean();
            this.prepareAddEditFieldList(paramMap, listBean, null, "检查信息", locale);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setResult(listBean);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the fields for add.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;
    }

    /**
     * 修改检查字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/edit-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsEdit(
            @RequestParam(required = true, defaultValue = "") String projectCheckId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        // 参数检查
        if (StringUtils.isEmpty(projectCheckId)) {

            MobileJsonResultBean jsonResult = new MobileJsonResultBean();
            return jsonResult.setErrorMessage("message.error.param", this.messageSource, locale);
        }

        String userId = userBean.getSessionUserId();
        return super.getFieldsEdit(userId, projectCheckId, "检查信息", locale);

    }

    /**
     * 检查整改字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/improve-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsImprove(
            @RequestParam(required = true, defaultValue = "") String projectCheckId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        // 参数检查
        if (StringUtils.isEmpty(projectCheckId)) {

            MobileJsonResultBean jsonResult = new MobileJsonResultBean();
            return jsonResult.setErrorMessage("message.error.param", this.messageSource, locale);
        }

        String userId = userBean.getSessionUserId();
        return super.getFieldsEdit(userId, projectCheckId, "整改信息", locale);

    }

    /**
     * 检查验收字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/verify-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsVerify(
            @RequestParam(required = true, defaultValue = "") String projectCheckId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        // 参数检查
        if (StringUtils.isEmpty(projectCheckId)) {

            MobileJsonResultBean jsonResult = new MobileJsonResultBean();
            return jsonResult.setErrorMessage("message.error.param", this.messageSource, locale);
        }

        String userId = userBean.getSessionUserId();
        return super.getFieldsEdit(userId, projectCheckId, "验证信息", locale);

    }

    /**
     * 新增检查提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/add", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckAdd(
            @ModelAttribute ProjectCheckFieldBean projectCheck, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectCheck.getProjectId())
                    || StringUtils.isEmpty(projectCheck.getCheckType())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectCheckFieldBeanConverter converter = new ProjectCheckFieldBeanConverter();
            this.projectCheckService.create(converter.toEntity(projectCheck, userid));

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 修改检查提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/add-edit", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckEdit(
            @ModelAttribute ProjectCheckEditBean projectCheck, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectCheck.getProjectCheckId())
                    || StringUtils.isEmpty(projectCheck.getCheckType())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectCheck p = this.projectCheckService.find(projectCheck.getProjectCheckId());
            if (p == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectCheckEditBeanConverter converter = new ProjectCheckEditBeanConverter();
            this.projectCheckService.update(converter.toEntity(projectCheck, userid));

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 检查整改提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/improve", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckImprove(
            @ModelAttribute ProjectCheckImproveBean projectCheck, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectCheck.getProjectCheckId())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectCheck p = this.projectCheckService.find(projectCheck.getProjectCheckId());
            if (p == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectCheckImproveBeanConverter converter = new ProjectCheckImproveBeanConverter();
            this.projectCheckService.update(converter.toEntity(projectCheck, userid));

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 检查验收提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-check/verify", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckVerify(
            @ModelAttribute ProjectCheckVerifyBean projectCheck, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectCheck.getProjectCheckId())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectCheck p = this.projectCheckService.find(projectCheck.getProjectCheckId());
            if (p == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectCheckVerifyBeanConverter converter = new ProjectCheckVerifyBeanConverter();
            this.projectCheckService.update(converter.toEntity(projectCheck, userid));

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

}
