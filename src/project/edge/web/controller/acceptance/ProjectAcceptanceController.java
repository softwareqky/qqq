package project.edge.web.controller.acceptance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.AcceptanceCheckBeanConverter;
import project.edge.domain.entity.AcceptanceCheck;
import project.edge.domain.entity.AcceptanceCheckAttachment;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.view.AcceptanceCheckBean;
import project.edge.domain.view.ArchiveBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.acceptance.AcceptanceCheckAttachmentService;
import project.edge.service.acceptance.AcceptanceCheckService;
import project.edge.service.project.ProjectService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 项目验收画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/acceptance/project-acceptance")
public class ProjectAcceptanceController
        extends TreeGridUploadController<AcceptanceCheck, AcceptanceCheckBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectAcceptanceController.class);

    private static final String PID = "P7001";

    private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL

    private static final String MODEL_KEY_IMPROVE_FIELDS = "improveFields";
    private static final String MODEL_KEY_VERIFICATION_FIELDS = "verificationFields";

    private static final String ID_MAP_KEY_IMPROVE_DIALOG = "edit-improve-form-dialog";
    private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";

//    private static final String JS_MAP_KEY_EDIT_IMPROVE_SUBMIT = "edit-improve-submit";
//    private static final String JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT = "edit-verification-submit";

    @Resource
    private AcceptanceCheckService acceptanceCheckService;
    
    @Resource
    private AcceptanceCheckAttachmentService acceptanceCheckAttachmentService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private ProjectService projectService;
    
    @Autowired
	HttpServletRequest request;
    
	@Resource
	CreateWorkFlowManager createWorkFlowManager;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.ACCEPTANCE_CHECK.value();
    }

    @Override
    protected Service<AcceptanceCheck, String> getDataService() {
        return this.acceptanceCheckService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<AcceptanceCheck, AcceptanceCheckBean> getViewConverter() {
        return new AcceptanceCheckBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/acceptance/acceptanceCheckJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/acceptance/acceptanceCheckHidden.jsp";
    }

    /**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        if (field.isCommonVisible()) {
            return;
        }

        // 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见
        if ((field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("checkedContent"))
                || (field.getFieldName().equals("acceptanceCheckType_"))
                || (field.getFieldName().equals("acceptanceCheckResult_"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("improvePlanDate"))
                || (field.getFieldName().equals("improveActualDate"))
                || (field.getFieldName().equals("improveReq"))
                || (field.getFieldName().equals("improvePlan"))) { // 建议
            this.putFiledList(model, MODEL_KEY_IMPROVE_FIELDS, field);

        }

        if ((field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("checkedContent"))
                || (field.getFieldName().equals("acceptanceCheckType_"))
                || (field.getFieldName().equals("acceptanceCheckResult_"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("verifyDate"))
                || (field.getFieldName().equals("improveResultVerify"))
                || (field.getFieldName().equals("acceptanceVerifyResult_"))
                || (field.getFieldName().equals("improvearchive_"))) { // 整改
            this.putFiledList(model, MODEL_KEY_VERIFICATION_FIELDS, field);
        }
    }

    @SuppressWarnings("unchecked")
    private void putFiledList(Model model, String key, FieldBean f) {
        List<FieldBean> list = null;
        if (model.containsAttribute(key)) {
            list = (List<FieldBean>) model.asMap().get(key);
        } else {
            list = new ArrayList<>();
            model.addAttribute(key, list);
        }
        list.add(f);
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
        // TODO Auto-generated method stub

        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();

        ArrayList<ComboboxOptionBean> projectOptions = new ArrayList<ComboboxOptionBean>();
        CommonFilter f = null;
        List<Project> projectList = this.projectService.list(f, null);
        if (projectList != null) {
            for (Project p : projectList) {
                projectOptions.add(new ComboboxOptionBean(p.getId(), p.getProjectName()));
            }
        }
        optionMap.put("ProjectOptions", projectOptions);

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.ACCEPTANCE_CHECK_TYPE.value());
        dataTypeList.add(DataTypeEnum.ACCEPTANCE_CHECK_KIND.value());
        dataTypeList.add(DataTypeEnum.CHECK_RESULT.value());
        dataTypeList.add(DataTypeEnum.VERIFY_RESULT.value());
        dataTypeList.add(DataTypeEnum.CHECK_STATUS.value());

        f = new CommonFilter().addWithin("dataType", dataTypeList);

        List<ComboboxOptionBean> acceptanceCheckTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> acceptanceCheckKindOptions = new ArrayList<>();
        List<ComboboxOptionBean> acceptanceCheckResultTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> acceptanceVerifyResultTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> acceptanceCheckStatusOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);
        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.ACCEPTANCE_CHECK_TYPE.value())) {
                acceptanceCheckTypeOptions
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.ACCEPTANCE_CHECK_KIND.value())) {
                acceptanceCheckKindOptions
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.CHECK_RESULT.value())) {
                acceptanceCheckResultTypeOptions
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.VERIFY_RESULT.value())) {
                acceptanceVerifyResultTypeOptions
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.CHECK_STATUS.value())) {
                acceptanceCheckStatusOptions
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

        optionMap.put("acceptanceCheckTypeOptions", acceptanceCheckTypeOptions);
        optionMap.put("acceptanceCheckKindOptions", acceptanceCheckKindOptions);
        optionMap.put("acceptanceCheckResultTypeOptions", acceptanceCheckResultTypeOptions);
        optionMap.put("acceptanceVerifyResultTypeOptions", acceptanceVerifyResultTypeOptions);
        optionMap.put("AcceptanceCheckStatusOptions", acceptanceCheckStatusOptions);

        ArrayList<ComboboxOptionBean> flowStatusTypeOptions = new ArrayList<ComboboxOptionBean>();

        for (FlowStatusEnum s : FlowStatusEnum.values()) {
            flowStatusTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    messageSource.getMessage(s.resourceName(), null, locale)));
        }

        optionMap.put("flowStatusTypeOptions", flowStatusTypeOptions);

        return optionMap;

    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_EDIT_FILE,
                contextPath + "/acceptance/project-acceptance/edit-file.json");
        urlMap.put(ControllerUrlMapKeys.EDIT, 
        		contextPath + "/acceptance/project-acceptance/edit-file.json");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_IMPROVE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Improve",
                this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_VERIFICATION_DIALOG, String.format(
                "%1$s-%2$s-EditFormDialog-Verification", this.getPageId(), this.getDataType()));

        return idMap;
    }
    
    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面，以及连续新建保存时，增加回调，刷新项目集下拉框的内容

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', ACCEPTANCECHECK);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 连续新建，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, ACCEPTANCECHECK);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

//        // 打开修改
//        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
//                String.format("ACCEPTANCECHECK.openEditFormDialog('#%1$s', '%2$s');",
//                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
//                        urlMap.get(ControllerUrlMapKeys.FIND)));

//        // 修改保存
//        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
//                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false)", 
//                		idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
//                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));
        
//        // 修改保存-抽查
//        jsMap.put(JS_MAP_KEY_EDIT_IMPROVE_SUBMIT,
//                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
//                        idMap.get(ID_MAP_KEY_IMPROVE_DIALOG),
//                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
//
//        // 修改保存-验证
//        jsMap.put(JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT,
//                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
//                        idMap.get(ID_MAP_KEY_VERIFICATION_DIALOG),
//                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        return jsMap;
    }

    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return super.main(paramMap, model, userBean, locale);
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param page 页数
     * @param rows 每页的记录数
     * @param sort 排序的字段，CSV
     * @param order 顺序，CSV
     * @param locale
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

        return jsonResult;
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    @Override
    public JsonResultBean find(@RequestParam String id, Locale locale) {
        return super.find(id, locale);
    }

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @SysLogAnnotation(description = "项目验收", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute AcceptanceCheckBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archive_", bean.getArchiveList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean,
                locale);
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit-file")
    @SysLogAnnotation(description = "项目验收", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute AcceptanceCheckBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

//        bean.setUploadFileType("edit");
        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archive_", bean.getArchiveList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(AcceptanceCheck entity, AcceptanceCheck oldEntity,
            AcceptanceCheckBean bean, java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        // this.deleteArchiveFiles(entity.getArchivesToDelete());

        List<Archive> list = new ArrayList<>();
        for (AcceptanceCheckAttachment attachment : entity.getAttachmentsToDelete()) {
            list.add(attachment.getArchive());
        }
        this.deleteArchiveFiles(list);
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "项目验收", action = "更新")
    public JsonResultBean update(@ModelAttribute AcceptanceCheckBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        bean.setUploadFileType(uploadType);
        return super.update(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "项目验收", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // ★ Project的文件夹对应的Archive的id，就是Project的id
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);
        paramMap.put(KEY_NEED_DELETE_SELF_RECORD, true);

        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    /**
     * 管理平台提交审核
     * @param bean
     * @param uploadType
     * @param userBean
     * @param locale
     * @return
     * @throws IOException 
     */
    @RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "项目验收", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute AcceptanceCheckBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		AcceptanceCheck entity = this.getDataService().find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}

		// 向OA提交审核请求
		
		// TODO:模拟测试使用，暂时跳过OA审批流程 ---START
//			jsonResult.setStatus(JsonStatus.OK);
//        	jsonResult.setMessage("模拟测试请求成功");
//          jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");
//            
//          entity.setFlowStatus(FlowStatusEnum.REVIEW_PASSED.value());
//          this.acceptanceCheckService.setData(entity);
        // TODO:模拟测试使用，暂时跳过OA审批流程 ---END
		
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PROJECT_ACCEPTANCE.value();
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("projectName", entity.getProject().getProjectName());
		reqMap.put("checkedContent", entity.getCheckedContent());
		if (entity.getAcceptanceCheckType() != null) {
			reqMap.put("acceptanceCheckType", entity.getAcceptanceCheckType().getOptionName());
		} else {
			reqMap.put("acceptanceCheckType", "");
		}
		if (entity.getAcceptanceCheckKind() != null) {
			reqMap.put("acceptanceCheckKind", entity.getAcceptanceCheckKind().getOptionName());
		} else {
			reqMap.put("acceptanceCheckKind", "");
		}
		reqMap.put("acceptanceCheckResult", entity.getAcceptanceCheckResult());
		reqMap.put("checkingUnit", entity.getCheckingUnit());
		reqMap.put("chechedUnit", entity.getChechedUnit());
		reqMap.put("checkBasis", entity.getCheckBasis());
		reqMap.put("checker", entity.getChecker());
		if (entity.getIsImprove() != null) {
			reqMap.put("isImprove", entity.getIsImprove() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		} else {
			reqMap.put("isImprove", "");
		}
		reqMap.put("checkDate", DateUtils.date2String(entity.getCheckDate(), Constants.DATE_FORMAT));
		reqMap.put("checkResultContent", entity.getCheckResultContent());
		reqMap.put("improveReqDate", DateUtils.date2String(entity.getImproveReqDate(), Constants.DATE_FORMAT));
		reqMap.put("acceptanceCheckStatus", entity.getAcceptanceCheckStatus());
		reqMap.put("remark", entity.getRemark());
		reqMap.put("flowStartDate", DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		reqMap.put("flowEndDate", DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		reqMap.put("flowStatus", messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
		
		CommonFilter filter = new CommonFilter();
        filter.addExact("acceptanceCheck.id", entity.getId());
        List<AcceptanceCheckAttachment> attchments = acceptanceCheckAttachmentService.list(filter, null);
        String fileName = "";
        String filePath = "";
        String fileType = "";
        List<HashMap<String, String>> archives = new ArrayList<>();
        if (attchments.size() > 0) {
            for (AcceptanceCheckAttachment attchment : attchments) {
                fileName = attchment.getArchive().getArchiveName();
                String rootFolderPath = this.systemConfigService
                        .getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

                filePath = rootFolderPath + attchment.getArchive().getRelativePath();
                fileType = String.valueOf(attchment.getIsImprove());
                File refereeFile = new File(filePath);
                byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(refereeFile);
                byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
                String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
                HashMap<String, String> attMap = new HashMap<String, String>();
                attMap.put("attachmentFileName", fileName);
                attMap.put("attachmentFile", UpFile);
                // 0-非整改附件，1-验收时附件
                attMap.put("attchmentType", fileType);
                archives.add(attMap);
            }
        }
        reqMap.put("archives", archives);

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
		// 调用oa接口
		logger.info("[OA Audit] No.15 ProjectAcceptance");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		//如果请求审批成功更新表t_plan 审批中
		if(jsonResult.getStatus() == JsonStatus.OK){
			logger.info("[OA Audit] No.15 ProjectAcceptance Audit request success, data id was {}", dataId);
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			entity.setFlowStartDate(new Date());
			entity.setmDatetime(new Date());
			entity.setModifier(userName);
			this.acceptanceCheckService.setData(entity);
		}

		return jsonResult;
	}
}
