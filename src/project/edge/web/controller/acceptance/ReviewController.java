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
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.ReviewBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Review;
import project.edge.domain.entity.ReviewAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ReviewBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.acceptance.ReviewAttachmentService;
import project.edge.service.acceptance.ReviewService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 评审管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/acceptance/review")
public class ReviewController extends TreeGridUploadController<Review, ReviewBean> {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL
    private static final String URL_MAP_KEY_SEL_EXPERT = "sel-expert";
    
    private static final String ID_MAP_KEY_IMPROVE_DIALOG = "edit-improve-form-dialog";
    private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";
    private static final String ID_MAP_KEY_EXPERTREVIEW_DIALOG = "edit-expertreview-form-dialog";
    private static final String ID_MAP_KEY_SEL_EXPERT_DIALOG = "sel-expert-dialog";
    
    private static final String JS_MAP_KEY_EDIT_IMPROVE_SUBMIT = "edit-improve-submit";
    private static final String JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT = "edit-verification-submit";
    private static final String JS_MAP_KEY_EDIT_EXPERTREVIEW_SUBMIT = "edit-expertreview-submit";
    private static final String JS_MAP_KEY_SEL_EXPERT = "sel-expert";

    private static final String SEL_EXPERT_DIALOG_ID = "%1$s-%2$s-SelExpertDialog";
    
    private static final String MODEL_KEY_IMPROVE_FIELDS = "improveFields";
    private static final String MODEL_KEY_VERIFICATION_FIELDS = "verificationFields";
    private static final String MODEL_KEY_EXPERTREVIEW_FIELDS = "expertReviewFields";

    private static final String PID = "P7005";

    @Resource
    private ReviewService reviewService;
    
    @Resource
    private ReviewAttachmentService reviewAttachmentService;

    @Resource
    private DataOptionService dataOptionService;
    
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
        return DataTypeEnum.REVIEW.value();
    }

    @Override
    protected Service<Review, String> getDataService() {
        return this.reviewService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<Review, ReviewBean> getViewConverter() {
        return new ReviewBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/acceptance/reviewJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/acceptance/reviewHidden.jsp";
    }

    /**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        //if (field.isCommonVisible()) {
        //    return;
        //}
		
		// 项目评审结果更新编辑项
        if ((field.getFieldName().equals("reviewBasis"))
                || (field.getFieldName().equals("reviewer"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("archive_"))
                || (field.getFieldName().equals("reviewDate"))
                || (field.getFieldName().equals("reviewResultContent"))
                || (field.getFieldName().equals("improveReqDate"))
                || (field.getFieldName().equals("reviewResult_"))
                || (field.getFieldName().equals("improveReq"))
                || (field.getFieldName().equals("reviewStatus_"))
                || (field.getFieldName().equals("remark"))) { // 专家审核结果
        	this.putFiledList(model, MODEL_KEY_EXPERTREVIEW_FIELDS, field);
        }

        // 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见
        if ((field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("reviewContent"))
                || (field.getFieldName().equals("reviewType_"))
                || (field.getFieldName().equals("reviewResult_"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("improvePlanDate"))
                || (field.getFieldName().equals("improveActualDate"))
                || (field.getFieldName().equals("improveReq"))
                || (field.getFieldName().equals("reviewStatus_"))
                || (field.getFieldName().equals("improvePlan"))) { // 整改
            this.putFiledList(model, MODEL_KEY_IMPROVE_FIELDS, field);

        }

        if ((field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("reviewContent"))
                || (field.getFieldName().equals("reviewType_"))
                || (field.getFieldName().equals("reviewResult_")) 
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("verifyDate"))
                || (field.getFieldName().equals("improveResultVerify"))
                || (field.getFieldName().equals("verifyResult_"))
                || (field.getFieldName().equals("reviewStatus_"))
                || (field.getFieldName().equals("improvearchive_"))) { // 验证
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
    
    @Override
    protected Map<String, String> prepareIdMap() {
        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_IMPROVE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Improve",
                this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_VERIFICATION_DIALOG, String.format(
                "%1$s-%2$s-EditFormDialog-Verification", this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_EXPERTREVIEW_DIALOG, String.format(
                "%1$s-%2$s-EditFormDialog-ExpertReview", this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_SEL_EXPERT_DIALOG,
                String.format(SEL_EXPERT_DIALOG_ID, this.getPageId(), this.getDataType()));
        return idMap;
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_EDIT_FILE, contextPath + "/acceptance/review/edit-file.json");
        urlMap.put(URL_MAP_KEY_SEL_EXPERT, contextPath + "/acceptance/review-expert/main.htm");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
                String.format("REVIEW.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 专家评审结果更新
        jsMap.put(ControllerJsMapKeys.OPEN_EXPERTREVIEW,
                String.format("REVIEW.openEditExpertReviewFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 整改
        jsMap.put(ControllerJsMapKeys.OPEN_IMPROVE,
                String.format("REVIEW.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 验证
        jsMap.put(ControllerJsMapKeys.OPEN_VERIFY,
                String.format("REVIEW.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 修改保存-抽查
        jsMap.put(JS_MAP_KEY_EDIT_IMPROVE_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_IMPROVE_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));

        // 修改保存-验证
        jsMap.put(JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_VERIFICATION_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));
        
        // 修改保存-专家评审结果更新
        jsMap.put(JS_MAP_KEY_EDIT_EXPERTREVIEW_SUBMIT, 
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_EXPERTREVIEW_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));

        jsMap.put(JS_MAP_KEY_SEL_EXPERT, "REVIEW.openSelExpertDialog()");

        return jsMap;
    }

    @Override
    protected void createFunctionBean(Page p, List<FunctionBean> functions,
            List<FunctionBean> searchFunctions, Map<String, String> funcGroupMap,
            Map<String, String> jsMap, Locale locale) {

        String pageId = p.getId();
        if (pageId.endsWith(ControllerFunctionIds.SELECT_EXPERT)) { // 专家选择

            this.parseFunction(p, functions, funcGroupMap, "", jsMap.get(JS_MAP_KEY_SEL_EXPERT),
                    locale);

        }
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

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.REVIEW_TYPE.value());
        dataTypeList.add(DataTypeEnum.CHECK_RESULT.value());
        dataTypeList.add(DataTypeEnum.VERIFY_RESULT.value());
        dataTypeList.add(DataTypeEnum.CHECK_STATUS.value());

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        List<ComboboxOptionBean> reviewTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> reviewResultTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> verifyResultTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> reviewStatusOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);
        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.REVIEW_TYPE.value())) {
                reviewTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.CHECK_RESULT.value())) {
                reviewResultTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.VERIFY_RESULT.value())) {
                verifyResultTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.CHECK_STATUS.value())) {
            	reviewStatusOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

        optionMap.put("reviewTypeOptions", reviewTypeOptions);
        optionMap.put("reviewResultTypeOptions", reviewResultTypeOptions);
        optionMap.put("verifyResultTypeOptions", verifyResultTypeOptions);
        optionMap.put("reviewStatusOptions", reviewStatusOptions);

        ArrayList<ComboboxOptionBean> flowStatusTypeOptions = new ArrayList<ComboboxOptionBean>();

        for (FlowStatusEnum s : FlowStatusEnum.values()) {
            flowStatusTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    messageSource.getMessage(s.resourceName(), null, locale)));
        }

        optionMap.put("flowStatusTypeOptions", flowStatusTypeOptions);

        return optionMap;
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

        String view = super.main(paramMap, model, userBean, locale);
        
        // Datagrid行选择的事件，除了默认的逻辑，还需要特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮
        model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "REVIEW.handleSelect");
        model.addAttribute(ControllerModelKeys.CLICK_CELL_HANDLER, "REVIEW.clickCellHandler");
        
        return view;
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
    @ResponseBody
    @SysLogAnnotation(description = "评审管理", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
    		@ModelAttribute ReviewBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        //return super.create(bean, null, userBean, locale);
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
    @SysLogAnnotation(description = "评审管理", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ReviewBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        bean.setUploadFileType(uploadType);
        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("improvearchive_", bean.getImprovearchiveList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(Review entity, Review oldEntity, ReviewBean bean,
            java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        // this.deleteArchiveFiles(entity.getArchivesToDelete());

        List<Archive> list = new ArrayList<>();
        for (ReviewAttachment attachment : entity.getAttachmentsToDelete()) {
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
    @SysLogAnnotation(description = "评审管理", action = "更新")
    public JsonResultBean update(@ModelAttribute ReviewBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
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
    @SysLogAnnotation(description = "评审管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
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
    @SysLogAnnotation(description = "评审管理", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute ReviewBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		Review entity = this.getDataService().find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}

		// 向OA提交审核请求
		
//			// TODO:模拟测试使用，暂时跳过OA审批流程 ---START
//			jsonResult.setStatus(JsonStatus.OK);
//        	jsonResult.setMessage("模拟测试请求成功");
//            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");
//            
//            entity.setFlowStatus(FlowStatusEnum.REVIEW_PASSED.value());
//            this.getDataService().update(entity);
//            // TODO:模拟测试使用，暂时跳过OA审批流程 ---END
		
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PROJECT_REVIEW.value();
		
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("projectName", entity.getProject().getProjectName());
		reqMap.put("reviewContent", entity.getReviewContent());
		reqMap.put("reviewType", entity.getReviewType().getOptionName());
		reqMap.put("reviewResult", entity.getReviewResult());
		reqMap.put("reviewingUnit", entity.getReviewingUnit());
		reqMap.put("reviewedUnit", entity.getReviewedUnit());
		reqMap.put("reviewBasis", entity.getReviewBasis());
		reqMap.put("reviewer", entity.getReviewer());
		if (entity.getIsImprove() != null) {
			reqMap.put("isImprove", entity.getIsImprove() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		} else {
			reqMap.put("isImprove", "");
		}
		
		CommonFilter filter = new CommonFilter();
        filter.addExact("review.id", entity.getId());
        List<ReviewAttachment> attchments = reviewAttachmentService.list(filter, null);
        String fileName = "";
        String filePath = "";
        String fileType = "";
        List<HashMap<String, String>> archives = new ArrayList<>();
        if (attchments.size() > 0) {
            for (ReviewAttachment attchment : attchments) {
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
		logger.info("[OA Audit] No.16 Review");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		//如果请求审批成功更新表t_plan 审批中
		if(jsonResult.getStatus() == JsonStatus.OK){
			logger.info("[OA Audit] No.16 Review Audit request success, data id was {}", dataId);
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			entity.setFlowStartDate(new Date());
			entity.setmDatetime(new Date());
			entity.setModifier(userName);
			this.reviewService.setData(entity);
		}

		return jsonResult;
	}
}
