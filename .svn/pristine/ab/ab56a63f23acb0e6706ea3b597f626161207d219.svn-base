package project.edge.web.controller.process;

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
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.ProjectPerformanceAwardBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.ProjectPerformanceAward;
import project.edge.domain.entity.ProjectPerformanceAwardAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectPerformanceAwardBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.process.ProjectPerformanceAwardService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 奖罚信息画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/process/project-performance-award")
public class ProjectPerformanceAwardController
        extends TreeGridUploadController<ProjectPerformanceAward, ProjectPerformanceAwardBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(ProjectPerformanceAwardController.class);

    private static final String PID = "P60013";
    @Autowired
	HttpServletRequest request;
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
    @Resource
    private ProjectPerformanceAwardService projectPerformanceAwardService;

    @Resource
    private DataOptionService dataOptionService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PROJECT_PERFORMANCE_AWARD.value();
    }

    @Override
    protected Service<ProjectPerformanceAward, String> getDataService() {
        return this.projectPerformanceAwardService;
    }

    @Override
    protected String getPageId() {
        // TODO Auto-generated method stub
        return this.PID;
    }

    @Override
    protected ViewConverter<ProjectPerformanceAward, ProjectPerformanceAwardBean> getViewConverter() {
        return new ProjectPerformanceAwardBeanConverter();
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();
        // 赏罚类型
        List<ComboboxOptionBean> expertDomainOption = new ArrayList<ComboboxOptionBean>();
        CommonFilter f = null;
        // f = new CommonFilter().addExact("dataType", this.getDataType());

        List<DataOption> list = this.dataOptionService.list(f, null);
        if (list != null) {
            for (DataOption o : list) {
                if (o.getDataType().equals(DataTypeEnum.DATA_OPTION_REWARDS_TYPE.value())) {
                    expertDomainOption.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
                }
            }
        }

        optionMap.put("ProjectPerformanceKindOptions", expertDomainOption);
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
    @SysLogAnnotation(description = "奖罚信息", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ProjectPerformanceAwardBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

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
    @RequestMapping("/edit")
    @SysLogAnnotation(description = "奖罚信息", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ProjectPerformanceAwardBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(ProjectPerformanceAward entity, ProjectPerformanceAward oldEntity,
            ProjectPerformanceAwardBean bean, java.util.Map<String, Object> paramMap)
            throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        //this.deleteArchiveFiles(entity.getArchivesToDelete());
        
        List<Archive> list = new ArrayList<>();
        for (ProjectPerformanceAwardAttachment attachment : entity.getAttachmentsToDelete()) {
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
    @SysLogAnnotation(description = "奖罚信息", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    @RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "奖罚信息", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute ProjectPerformanceAwardBean bean, 
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		ProjectPerformanceAward entity = this.getDataService().find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PROJECT_PERFORMANCE_AWARD.value();

		// 向OA提交审核请求
		HashMap<String, Object> reqMap = new HashMap<>();
		if(entity.getProject()!=null){
			reqMap.put("projectName", entity.getProject().getProjectName());
		} else {
			reqMap.put("projectName", "");
		}
		
		if (entity.getPerson() != null) {
			reqMap.put("personName", entity.getPerson().getId());
		} else {
			reqMap.put("personName", "");
		}
		
		reqMap.put("awardAndPunishmentType", entity.getAwardAndPunishmentType().getOptionName());
		reqMap.put("amount", String.valueOf(entity.getAmount()));
		reqMap.put("executeTime", DateUtils.date2String(entity.getExecuteTime(), Constants.DATE_FORMAT));
		reqMap.put("executeBasis", entity.getExecuteBasis());
		reqMap.put("executeReason", entity.getExecuteReason());

		reqMap.put("remark", entity.getRemark());
		Set<ProjectPerformanceAwardAttachment> attchments = entity.getProjectPerformanceAwardAttachments();
		
		String fileName = "";
		String filePath = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {//待测试
			for (ProjectPerformanceAwardAttachment attchment : attchments) {
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

		logger.info("[OA Audit] No.14 ProjectPerformanceAward");
		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		// 如果请求审批成功更新表t_project 审批中
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.14 ProjectPerformanceAward Audit request success, history data id was {}", dataId);
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			entity.setFlowStartDate(new Date());
			projectPerformanceAwardService.setData(entity);
		}

		// 提交成功需要将flowhistory action 修改为REVIEWING 审核中


		return jsonResult;
	}
}
