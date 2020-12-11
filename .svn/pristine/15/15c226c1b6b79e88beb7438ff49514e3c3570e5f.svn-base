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
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.mobile.converter.ProjectInspectBeanConverter;
import project.edge.domain.mobile.converter.ProjectInspectEditBeanConverter;
import project.edge.domain.mobile.converter.ProjectInspectFieldBeanConverter;
import project.edge.domain.mobile.converter.ProjectInspectImproveBeanConverter;
import project.edge.domain.mobile.converter.ProjectInspectInfoBeanConverter;
import project.edge.domain.mobile.converter.ProjectInspectListItemBeanConverter;
import project.edge.domain.mobile.converter.ProjectInspectVerifyBeanConverter;
import project.edge.domain.mobile.view.FieldListWrapperBean;
import project.edge.domain.mobile.view.OptionBean;
import project.edge.domain.mobile.view.PagedResultBean;
import project.edge.domain.mobile.view.ProjectInspectEditBean;
import project.edge.domain.mobile.view.ProjectInspectFieldBean;
import project.edge.domain.mobile.view.ProjectInspectImproveBean;
import project.edge.domain.mobile.view.ProjectInspectListItemBean;
import project.edge.domain.mobile.view.ProjectInspectVerifyBean;
import project.edge.mobile.controller.common.GenericController;
import project.edge.service.process.ProjectInspectService;
import project.edge.service.system.DataOptionService;

/**
 * @author angel_000
 *         项目。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class ProjectInspectInfoController extends GenericController<ProjectInspect> {

    private static final Logger logger =
            LoggerFactory.getLogger(ProjectInspectInfoController.class);

    @Resource
    private MessageSource messageSource;

    @Resource
    private ProjectInspectService projectInspectService;

    @Resource
    private DataOptionService dataOptionService;

    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return ProjectInspectInfoController.logger;
    }

    @Override
    public String getDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.PROJECT_INSPECT.value();
    }

    @Override
    protected Service<ProjectInspect, String> getDataService() {
        // TODO Auto-generated method stub
        return this.projectInspectService;
    }

    @Override
    protected Object converter(ProjectInspect entity) {
        // TODO Auto-generated method stub
        return entity;
    }

    @Override
    protected Object converter(ProjectInspect entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ProjectInspectBeanConverter p = new ProjectInspectBeanConverter();
        return p.fromEntity(entity, messageSource, locale);
    }

    @Override
    protected Map<String, List<OptionBean>> getOptionMap(Map<String, Object> paramMap,
            Locale locale) {

        if (paramMap == null || !paramMap.containsKey("userId")) {
            return null;
        }

        Map<String, List<OptionBean>> map = new HashMap<>();

        // 巡查类型
        ArrayList<OptionBean> projectInspectMode = new ArrayList<OptionBean>();
        CommonFilter commonFilter =
                new CommonFilter().addExact("dataType", DataTypeEnum.PROJECT_INSPECT_TYPE.value());
        List<DataOption> expertDomainlist = dataOptionService.list(commonFilter, null);

        for (DataOption dataOption : expertDomainlist) {

            OptionBean option = new OptionBean();
            option.setOptionKey(dataOption.getId());
            option.setOptionValue(dataOption.getOptionName());
            projectInspectMode.add(option);
        }
        map.put("PROJECT_CHECK_TYPE", projectInspectMode);

        // 巡查结果
        ArrayList<OptionBean> inspectResultMode = new ArrayList<OptionBean>();
        CommonFilter commonFilter1 =
                new CommonFilter().addExact("dataType", DataTypeEnum.INSPECT_RESULT.value());
        List<DataOption> list = dataOptionService.list(commonFilter1, null);

        for (DataOption dataOption : list) {

            OptionBean option = new OptionBean();
            option.setOptionKey(dataOption.getId());
            option.setOptionValue(dataOption.getOptionName());
            inspectResultMode.add(option);
        }
        map.put("CHECK_RESULT", inspectResultMode);

        // 验证结果
        ArrayList<OptionBean> verifyResultMode = new ArrayList<OptionBean>();
        CommonFilter commonFilter2 =
                new CommonFilter().addExact("dataType", DataTypeEnum.VERIFY_RESULT.value());
        List<DataOption> list1 = dataOptionService.list(commonFilter2, null);

        for (DataOption dataOption : list1) {
            OptionBean option = new OptionBean();
            option.setOptionKey(dataOption.getId());
            option.setOptionValue(dataOption.getOptionName());
            verifyResultMode.add(option);
        }
        map.put("VERIFY_RESULT", verifyResultMode);
        
        // 巡查状态
        ArrayList<OptionBean> inspectStatusOptions = new ArrayList<OptionBean>();
        commonFilter = new CommonFilter().addExact("dataType", DataTypeEnum.INSPECT_STATUS.value());
        list = dataOptionService.list(commonFilter, null);

        for (DataOption dataOption : list) {
            OptionBean option = new OptionBean();
            option.setOptionKey(dataOption.getId());
            option.setOptionValue(dataOption.getOptionName());
            inspectStatusOptions.add(option);
        }
        map.put("InspectStatusOptions", inspectStatusOptions);
        
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
     * 项目巡查列表。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/project-list", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckList(
            @RequestParam(required = false, defaultValue = "") String projectId,
            @RequestParam(required = false, defaultValue = "0") String isImprove,
            @RequestParam(required = false, defaultValue = "0") String isVerify,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 过滤
            CommonFilter filter = new CommonFilter();

            if (!StringUtils.isEmpty(projectId)) {
                filter.addExact("project.id", projectId);
            }

            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            if (page > 0 && rows > 0) {
                pageCtrl.setCurrentPage(page);
                pageCtrl.setPageSize(rows);
            }
            //整改
            if ("1".equals(isImprove)) {
            	  filter.addExact("flowStatus", FlowStatusEnum.REVIEW_PASSED.value());
            	  filter.addExact("isImprove", new Integer(1).shortValue());
            	  //优良 合格
            	  filter.addWithin("inspectResult", new String[] {"INSPECT_RESULT_0","INSPECT_RESULT_1"});
            }
            //验证
            if ("1".equals(isVerify)) {
	          	  filter.addExact("flowStatus", FlowStatusEnum.REVIEW_PASSED.value());
	          	  filter.addExact("isImprove", new Integer(1).shortValue());
	        }
            // 设置排序信息
            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("cDatetime"));

            List<ProjectInspect> projectInspectList =
                    this.projectInspectService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = projectInspectList.size();

            PagedResultBean<ProjectInspectListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                ProjectInspectListItemBeanConverter converter =
                        new ProjectInspectListItemBeanConverter();
                for (ProjectInspect p : projectInspectList) {
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
     * 巡查一览。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/info", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectCheckInfo(
            @RequestParam(required = true, defaultValue = "") String projectInspectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 参数检查
            if (StringUtils.isEmpty(projectInspectId)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectInspect p = this.projectInspectService.find(projectInspectId);

            if (p != null) {

                ProjectInspectInfoBeanConverter converter = new ProjectInspectInfoBeanConverter();
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
     * 新增巡查提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "project-inspect/add", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectInspectAdd(
            @ModelAttribute ProjectInspectFieldBean projectInspect, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectInspect.getProjectId())
                    || StringUtils.isEmpty(projectInspect.getSiteId())
                    || StringUtils.isEmpty(projectInspect.getInspectType())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectInspectFieldBeanConverter converter = new ProjectInspectFieldBeanConverter();
            this.projectInspectService.create(converter.toEntity(projectInspect, userid));


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
     * 修改巡查提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/add-edit", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectInspectEdit(
            @ModelAttribute ProjectInspectEditBean projectInspect, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectInspect.getProjectInspectId())
                    || StringUtils.isEmpty(projectInspect.getSiteId())
                    || StringUtils.isEmpty(projectInspect.getInspectType())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectInspect p =
                    this.projectInspectService.find(projectInspect.getProjectInspectId());
            if (p == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectInspectEditBeanConverter converter = new ProjectInspectEditBeanConverter();
            this.projectInspectService.update(converter.toEntity(projectInspect, userid));

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
     * 巡查整改提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/improve", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectInspectImprove(
            @ModelAttribute ProjectInspectImproveBean projectInspect, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectInspect.getProjectInspectId())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectInspect p =
                    this.projectInspectService.find(projectInspect.getProjectInspectId());
            if (p == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectInspectImproveBeanConverter converter = new ProjectInspectImproveBeanConverter();
            this.projectInspectService.update(converter.toEntity(projectInspect, userid));

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
     * 巡查验收提交。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/verify", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectInspectVerify(
            @ModelAttribute ProjectInspectVerifyBean projectInspect, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectInspect.getProjectInspectId())) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            ProjectInspect p =
                    this.projectInspectService.find(projectInspect.getProjectInspectId());
            if (p == null) {
                return jsonResult.setErrorMessage("message.error.record.not.exist",
                        this.messageSource, locale);
            }

            String userid = userBean.getSessionUserId();

            ProjectInspectVerifyBeanConverter converter = new ProjectInspectVerifyBeanConverter();
            this.projectInspectService.update(converter.toEntity(projectInspect, userid));

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
     * 增加巡查字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/add-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsAdd(
            @RequestParam(required = true, defaultValue = "") String projectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

//            // 参数检查
//            if (StringUtils.isEmpty(projectId)) {
//                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
//                        locale);
//            }

            String userId = userBean.getSessionUserId();

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);

            FieldListWrapperBean listBean = new FieldListWrapperBean();
            this.prepareAddEditFieldList(paramMap, listBean, null, "巡查信息", locale);

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
     * 修改巡查字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/edit-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsEdit(
            @RequestParam(required = true, defaultValue = "") String projectInspectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        // 参数检查
        if (StringUtils.isEmpty(projectInspectId)) {

            MobileJsonResultBean jsonResult = new MobileJsonResultBean();
            return jsonResult.setErrorMessage("message.error.param", this.messageSource, locale);
        }

        String userId = userBean.getSessionUserId();
        return super.getFieldsEdit(userId, projectInspectId, "巡查信息", locale);

    }

    /**
     * 巡查整改字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/improve-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsImprove(
            @RequestParam(required = true, defaultValue = "") String projectInspectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        // 参数检查
        if (StringUtils.isEmpty(projectInspectId)) {

            MobileJsonResultBean jsonResult = new MobileJsonResultBean();
            return jsonResult.setErrorMessage("message.error.param", this.messageSource, locale);
        }

        String userId = userBean.getSessionUserId();
        return super.getFieldsEdit(userId, projectInspectId, "整改信息", locale);

    }

    /**
     * 巡查验收字段。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/verify-field", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getFieldsVerify(
            @RequestParam(required = true, defaultValue = "") String projectInspectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        // 参数检查
        if (StringUtils.isEmpty(projectInspectId)) {

            MobileJsonResultBean jsonResult = new MobileJsonResultBean();
            return jsonResult.setErrorMessage("message.error.param", this.messageSource, locale);
        }

        String userId = userBean.getSessionUserId();
        return super.getFieldsEdit(userId, projectInspectId, "验证信息", locale);

    }

    /**
     * 项目巡查列表检索
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-inspect/search", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectInspectSearch(
            @RequestParam(required = false, defaultValue = "") String inspectType,
            @RequestParam(required = false, defaultValue = "") String siteId,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 过滤
            CommonFilter filter = new CommonFilter();

            if (!StringUtils.isEmpty(inspectType)) {
                filter.addExact("inspectType", inspectType);
            }
            
            if (!StringUtils.isEmpty(siteId)) {
                filter.addExact("site.id", siteId);
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

            List<ProjectInspect> projectInspectList =
                    this.projectInspectService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = projectInspectList.size();

            PagedResultBean<ProjectInspectListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                ProjectInspectListItemBeanConverter converter =
                        new ProjectInspectListItemBeanConverter();
                for (ProjectInspect p : projectInspectList) {
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
}
