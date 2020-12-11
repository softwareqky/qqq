package project.edge.web.controller.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
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
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.ProjectChangeBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectChange;
import project.edge.domain.entity.ProjectChangeAttachment;
import project.edge.domain.entity.ProjectSet;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectChangeBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.project.ProjectChangeService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.ProjectSetService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 项目变更画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/project-change")
public class ProjectChangeController
        extends TreeGridUploadController<ProjectChange, ProjectChangeBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectChangeController.class);

    private static final String PID = "P6510";
    @Autowired
   	HttpServletRequest request;
       @Resource
   	CreateWorkFlowManager createWorkFlowManager;
    @Resource
    private ProjectChangeService projectChangeService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private ProjectSetService projectSetService;
    
    @Resource
    private ProjectService projectService;

	@Resource
	private VirtualOrgService virtualOrgService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PROJECT_CHANGE.value();
    }

    @Override
    protected Service<ProjectChange, String> getDataService() {
        return this.projectChangeService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<ProjectChange, ProjectChangeBean> getViewConverter() {
        return new ProjectChangeBeanConverter();
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.PROJECT_KIND.value()); // 项目类别

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        // 项目类别
        List<ComboboxOptionBean> projectKindOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);

        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.PROJECT_KIND.value())) {
                // 屏蔽复合类项目
                if (!o.getOptionName().equals("复合类项目")) {
                    projectKindOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
                }

            }
        }

        optionMap.put("ProjectKindOptions", projectKindOptions);

        return optionMap;

    }

    @Override
    protected String getJsCallbackObjName() {
        return this.getDataType();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/project/projectChangeJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/project/projectChangeHidden.jsp";
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建，实际是find在TreeGrid中选中项目的信息，之后将项目信息加载到弹出的项目变更的新建画面
        jsMap.put(ControllerJsMapKeys.OPEN_ADD, "PROJECT_CHANGE.openAddFormDialog()");
        
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

        // Datagrid行双击事件，增加回调，用来刷新项目集下拉框的内容
        model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER,
                "PROJECT_CHANGE.handleDblClickRow");

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

        return super.list(commonFilterJson, null, page, rows, sort, order, locale);
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

        JsonResultBean jsonResult = super.find(id, locale);
        try {

            // 把项目集准备好，以便前端更新项目集下拉框的内容

            List<ProjectSet> list = this.projectSetService.list(null, null);
            List<ComboboxOptionBean> optionList = new ArrayList<>();
            String format = "(%1$s) %2$s";

            for (ProjectSet ps : list) {
                optionList.add(new ComboboxOptionBean(ps.getId(),
                        String.format(format, ps.getProjectNum(), ps.getProjectName())));
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, optionList);

        } catch (Exception e) {
            this.getLogger().error("Exception finding the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
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
    @SysLogAnnotation(description = "项目变更", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ProjectChangeBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put(ARCHIVE_FIELD_NAME, bean.getArchiveList());
        
        // 找到并更新project_num
        Project project = projectService.find(bean.getProject_());
        bean.setProjectNum(project.getProjectNum());

        // 项目变更的文件，位于/project-change/id文件夹内，id是项目变更的主键值
        super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean,
                locale);
    }

    /**
     * 处理保留的来自Project的ProjectAttachment，将其转成ProjectChangeAttachment。
     * 
     * @deprecated
     */
    protected void beforeCreate(ProjectChange entity, ProjectChangeBean bean,
            Map<String, Object> paramMap) throws Exception {

        return;
    }

    private static final String ARCHIVE_FIELD_NAME = "archive_";

    /**
     * 修改，返回Json格式。
     * 
     * @param request
     * @param response
     * @param bean 表现层对象
     * @param uploadType
     *            用来区分是普通修改画面、项目建议文件上传画面、项目可研文件上传画面还是项目初设文件画面提交的。普通修改画面的提交URL中不带此参数，其他都在URL后加上了此参数。此处仅用普通修改。
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @SysLogAnnotation(description = "项目变更", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ProjectChangeBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
        // 参数在projectInitHidden.jsp中用GET的方式设定
        bean.setUploadFileType(uploadType);

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put(ARCHIVE_FIELD_NAME, bean.getArchiveList());
        
        
        ProjectChange entity = projectChangeService.find(bean.getId());
		bean.setProjectNum(entity.getProjectNum());

        // 项目变更的文件，位于/project-change/id文件夹内，id是项目变更的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(ProjectChange entity, ProjectChange oldEntity, ProjectChangeBean bean,
            java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);

        List<Archive> list = new ArrayList<>();
        for (ProjectChangeAttachment attachment : entity.getAttachmentsToDelete()) {
            list.add(attachment.getArchive());
        }
        this.deleteArchiveFiles(list);
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
    @SysLogAnnotation(description = "项目变更", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // ★ ProjectChange的文件夹对应的Archive的id，就是ProjectChange的id
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);
        paramMap.put(KEY_NEED_DELETE_SELF_RECORD, true);

        return super.delete(idsToDelete, paramMap, userBean, locale);
    }
    
    @RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "项目变更", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		ProjectChange entity = this.getDataService().find(id);
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		
		if(entity.getFlowStatus() == 1) {
		    jsonResult.setStatus(JsonStatus.ERROR);
		    jsonResult.setMessage(this.messageSource.getMessage("message.error.record.audit", null, locale));
		    return jsonResult;
		}
		
		String dataId = entity.getId();
		String remark = entity.getRemark();
		String userName=userBean.getSessionLoginName();
    	String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PROJECTCHANGE.value();

		// 向OA提交审核请求
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("changeTitle", entity.getChangeRemark());
		reqMap.put("changeReason", entity.getChangeReason());
		reqMap.put("changedContent", entity.getChangedContent());
		reqMap.put("projectName", entity.getProject().getProjectName());
		reqMap.put("projectKind", entity.getProjectKind().getOptionName());
		reqMap.put("governmentProjectNum", entity.getGovernmentProjectNum());
		reqMap.put("applicant", entity.getApplicant().getUser().getId());
		reqMap.put("applicantMobile", entity.getApplicantMobile());
		if (entity.getProject().getProjectSet() != null) {
			reqMap.put("projectSetName", entity.getProject().getProjectSet().getProjectName());
		} else {
			reqMap.put("projectSetName", "");
		}
		Set<ProjectChangeAttachment> attchments = entity.getProjectChangeAttachments();
		String fileName = "";
		String filePath = "";
		String fileType = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {
			for (ProjectChangeAttachment attchment : attchments) {
				fileName = attchment.getArchive().getArchiveName();
				String rootFolderPath = this.systemConfigService
						.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

				filePath = rootFolderPath + attchment.getArchive().getRelativePath();
				fileType = attchment.getAttachmentType() + "";
				File refereeFile = new File(filePath);
				byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(refereeFile);
				byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
				String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
				HashMap<String, String> attMap = new HashMap<String, String>();
				attMap.put("attachmentFileName", fileName);
				attMap.put("attachmentFile", UpFile);
				// 0-项目附件，1-项目建议书，2-可研文档，3-环评材料，4-能评材料，5-土地材料，6-初设文件
				attMap.put("attchmentType", fileType);
				archives.add(attMap);
			}
		}
		reqMap.put("archives", archives);

		String projectId = "";
		if (entity.getProject() != null) {
			projectId = entity.getProject().getId();
			reqMap.put("projectCost", String.valueOf(entity.getProject().getProjectCost()));
			reqMap.put("projectStartDate", DateUtils.date2String(entity.getProject().getProjectStartDate(), Constants.DATE_FORMAT));
			reqMap.put("projectEndDate", DateUtils.date2String(entity.getProject().getProjectEndDate(), Constants.DATE_FORMAT));
			reqMap.put("pjmReq", entity.getProject().getPjmReq());
			if (entity.getProject().getMainLeader() != null)
				reqMap.put("mainLeader", entity.getProject().getMainLeader().getId());
			else
				reqMap.put("mainLeader", "");
			
			// 查询对应专业组
			String virtualOrgName = "";
			CommonFilter f1 = new CommonFilter();
			if (!StringUtils.isEmpty(projectId)) {
				f1 = new CommonFilter().addExact("project.id", projectId);
			}
			List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);
			if (virtualOrgList != null && virtualOrgList.size() > 0) {
				virtualOrgName = virtualOrgList.get(0).getVirtualOrgName();
			} else {
				logger.error("[OA Audit] Virtual Org Not Found. Project Id:{}", projectId);
			}
			reqMap.put("virtualOrg", virtualOrgName);
		} else {
			reqMap.put("projectCost", "");
			reqMap.put("projectStartDate", "");
			reqMap.put("projectEndDate", "");
			reqMap.put("pjmReq", "");
			reqMap.put("mainLeader", "");
			reqMap.put("virtualOrg", "");
		}
		
		logger.info("[OA Audit] No.11 ProjectChange");
		jsonResult=createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		//如果请求审批成功更新表t_project 审批中
		if(jsonResult.getStatus() == JsonStatus.OK){
			logger.info("[OA Audit] No.11 ProjectChange Audit request success, history data id was {}", dataId);
			ProjectChange projectChange = projectChangeService.find(dataId);
			projectChange.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			projectChange.setFlowStartDate(new Date());
			projectChange.setmDatetime(new Date());
			projectChange.setModifier(userName);
			projectChangeService.setData(projectChange);
			
		}

		// 提交成功需要将flowhistory action 修改为REVIEWING 审核中



		return jsonResult;
	}
}
