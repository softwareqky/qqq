package project.edge.web.controller.budget;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.CapitalApplyBeanConverter;
import project.edge.domain.entity.CapitalApply;
import project.edge.domain.entity.CapitalApplyAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.CapitalApplyBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.budget.CapitalApplyService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;
import project.edge.web.controller.general.DataPermissionService;

/**
 * 项目成员。从属于项目成员变更画面，作为项目成员变更画面的SubGrid。不需要main()来渲染画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/capitalReceive")
public class CapitalApplyController
        extends TreeGridUploadController<CapitalApply, CapitalApplyBean> {

    private static final Logger logger = LoggerFactory.getLogger(CapitalApplyController.class);

    private static final String PID = "P10023";
    
    @Resource
    private CapitalApplyService capitalApplyService;

    @Resource
    private VirtualOrgService virtualOrgService;
	
	@Resource
	private DataPermissionService dataPermissionService;
	
	@Resource
	private DataOptionService dataOptionService;
	
	@Resource
	CreateWorkFlowManager createWorkFlowManager;

    @Autowired
	HttpServletRequest request;
    
    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.CAPITAL_APPLY.value();
    }

    @Override
    protected String getPageId() {
    	return PID;
    }

    @Override
    protected Service<CapitalApply, String> getDataService() {
        return this.capitalApplyService;
    }

    @Override
    protected ViewConverter<CapitalApply, CapitalApplyBean> getViewConverter() {
        return new CapitalApplyBeanConverter();
    }
    
	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/budget/capitalApplyJs.jsp";
	}
    

	/*  * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
	  * 
	  * @param locale
	  * @return key:[v_data_option].option_source，value:[v_data_option]*/
	  
/*	 @Override
	 protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
	
	     Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();
	     
			List<String> dataTypeList = new ArrayList<>();
			dataTypeList.add(DataTypeEnum.CAPITAL_SOURCE.value());

			CommonFilter f = null;
			f = new CommonFilter().addWithin("dataType", dataTypeList);
			
			List<ComboboxOptionBean> sourceOptions = new ArrayList<>();

			List<DataOption> list = this.dataOptionService.list(f, null);
			for (DataOption o : list) {
				if (o.getDataType().equals(DataTypeEnum.CAPITAL_SOURCE.value())) {
					sourceOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
				} 
			}

			optionMap.put("sourceOptions", sourceOptions);
	
	     return optionMap;
	 }*/

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
    @RequestMapping("/sub-grid-list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);
		return jsonResult;
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-find")
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
    @RequestMapping("/sub-grid-add")
    @ResponseBody
	public void create(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CapitalApplyBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
		// 实际进度的文件，位于/plan-progress/id文件夹内，id是项目组的主键值
		super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean, locale);
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-edit")
    @ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CapitalApplyBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
		// 参数在projectInitHidden.jsp中用GET的方式设定

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

		// 项目的文件，位于/project/id文件夹内，id是项目的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-delete")
    @ResponseBody
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
	@SysLogAnnotation(description = "资金申请", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute CapitalApplyBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		CapitalApply entity = this.capitalApplyService.find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_CAPITALPLAN.value();

		// 向OA提交审核请求
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("projectName", entity.getProject().getProjectName());
		reqMap.put("projectNum", entity.getProject().getProjectNum());
		reqMap.put("applyAmount", entity.getApplyAmount());
		reqMap.put("applyReason", entity.getApplyReason());
		reqMap.put("source", entity.getSource().getOptionName());
		reqMap.put("dataId", dataId);
		
		Set<CapitalApplyAttachment> attchments = entity.getCapitalApplyAttachments();
		String fileName = "";
		String filePath = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {
			for (CapitalApplyAttachment attchment : attchments) {
				fileName = attchment.getArchive().getArchiveName();
				String rootFolderPath = this.systemConfigService
						.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

				filePath = rootFolderPath + attchment.getArchive().getRelativePath();
				File refereeFile = new File(filePath);
				byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(refereeFile);
				byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
				String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
				HashMap<String, String> attMap = new HashMap<String, String>();
				attMap.put("attachmentFileName", fileName);
				attMap.put("attachmentFile", UpFile);

				archives.add(attMap);
			}
		}
		reqMap.put("archives", archives);

		logger.info("[OA Audit] No.12 ProjectCheck");
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, entity.getProject().getId(), reqMap, request);
		// 如果请求审批成功更新表t_project 审批中
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.12 ProjectCheck Audit request success, data id was {}", dataId);
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			capitalApplyService.setData(entity);
		}

		return jsonResult;
	}

}
