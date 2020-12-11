/**
 * 
 */
package project.edge.web.controller.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.common.util.ControllerMapUtils;
import project.edge.dao.notice.NoticeAnnouncementDao;
import project.edge.domain.converter.NoticeAnnouncementBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.NoticeAnnouncement;
import project.edge.domain.entity.NoticeAttachment;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.NoticeAnnouncementBean;
import project.edge.service.notice.NoticeAnnouncementService;
import project.edge.service.org.DeptService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.apiService.notify.NotifyApiService;
import project.edge.web.controller.common.SingleGridUploadController;

/**
 * @author angel_000
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/notice/announcement")
public class NoticeAnnouncementController
		extends SingleGridUploadController<NoticeAnnouncement, NoticeAnnouncementBean> {

	private static final Logger logger = LoggerFactory.getLogger(NoticeAnnouncementController.class);

	@Resource
	private NoticeAnnouncementDao noticeAnnouncementDao;

	private static final String PID = "P13001";

	@Resource
	private NoticeAnnouncementService noticeAnnouncementService;

	@Resource
	private DeptService deptService;

	@Resource
	private ProjectService projectService;
	
	@Resource
	private ProjectPersonService projectPersonService;
	
	@Resource
	private VirtualOrgService virtualOrgService;
	
	@Resource
	private NotifyApiService notifyApiService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.NOTICE_ANNOUNCEMENT.value();
	}

	@Override
	protected Service<NoticeAnnouncement, String> getDataService() {
		return this.noticeAnnouncementService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}
	
	@Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/notice/noticeDetailJs.jsp";
    }

//    @Override
//    protected String getHiddenContentJspPath() {
//        return "/WEB-INF/jsp/facility/siteHidden.jsp";
//    }

	@Override
	protected ViewConverter<NoticeAnnouncement, NoticeAnnouncementBean> getViewConverter() {
		return new NoticeAnnouncementBeanConverter();
	}

	@Override
	protected List<CommonFilter> getUniqueFilter(NoticeAnnouncementBean bean) {
		// TODO Auto-generated method stub
		List<CommonFilter> list = new ArrayList<>();
		CommonFilter filter = new CommonFilter().addExact("id", bean.getId());
		list.add(filter);
		return list;
	}

	@Override
	protected List<CommonFilter> getUniqueFilter(NoticeAnnouncementBean bean, NoticeAnnouncement oldEntity) {
		// TODO Auto-generated method stub
		List<CommonFilter> list = new ArrayList<>();
		if (!bean.getId().equals(oldEntity.getId())) {
			CommonFilter filter = new CommonFilter().addExact("id", bean.getId());
			list.add(filter);
		}
		return list;
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

		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<String, List<ComboboxOptionBean>>();

		List<ComboboxOptionBean> deptOptions = new ArrayList<>();
		List<Dept> deptOptionsList = this.deptService.list(null, null);
		if (deptOptionsList != null) {
			for (Dept dept : deptOptionsList) {
				deptOptions.add(new ComboboxOptionBean(dept.getId(), dept.getDeptName()));
			}
		}

		optionMap.put("DeptOptions", deptOptions);

		List<ComboboxOptionBean> projectOptions = new ArrayList<>();
		CommonFilter filter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		List<Project> projectList = this.projectService.list(filter, null);
		if (projectList != null) {
			for (Project p : projectList) {
				projectOptions.add(new ComboboxOptionBean(p.getId(), p.getProjectName()));
			}
		}
		optionMap.put("ProjectOptions", projectOptions);
		return optionMap;
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = ControllerMapUtils.buildJsMap(idMap, urlMap, this.useFile(),
				this.getJsCallbackObjName());

		// 导出文件
		jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
				String.format("NoticeUtils.publish('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
		
		// 打开修改
		jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
				String.format("CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, NOTICE_DETAIL);",
						idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG), urlMap.get(ControllerUrlMapKeys.FIND),
						idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 新建保存
		jsMap.put(ControllerJsMapKeys.ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, NOTICE_DETAIL);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));
		
		// 编辑保存
		jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, NOTICE_DETAIL);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));
		
		return jsMap;
	}

	/**
	 * 画面Open的入口方法，用于生成JSP。
	 * 
	 * @param paramMap 画面请求中的任何参数，都会成为检索的字段
	 * @param model    model
	 * @param userBean session中的当前登录的用户信息
	 * @param locale   locale
	 * @return
	 */
	@RequestMapping("/main")
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "NOTICE_DETAIL.handleSelect");
		return super.main(paramMap, model, userBean, locale);
	}

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param page             页数
	 * @param rows             每页的记录数
	 * @param sort             排序的字段，CSV
	 * @param order            顺序，CSV
	 * @param locale
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		JsonResultBean jsonResult = this.list(commonFilterJson, null, page, rows, sort, order, locale);

		return jsonResult;
	}

	@Override
	protected JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			CommonFilter addedFilter, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 设置过滤信息
			CommonFilter filter = this.generateCommonFilter(addedFilter, commonFilterJson);

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			if (page > 0 && rows > 0) {
				pageCtrl.setCurrentPage(page);
				pageCtrl.setPageSize(rows);
			}

			// 设置排序信息
			List<OrderByDto> orders = new ArrayList<OrderByDto>();
			if (!StringUtils.isEmpty(sort)) {
				String[] orderArray = StringUtils.commaDelimitedListToStringArray(order);
				String[] sortArray = StringUtils.commaDelimitedListToStringArray(sort);
				for (int i = 0; i < orderArray.length; i++) {

					// 将bean的Text后缀的名字改为对应的entity的名字，即去掉Text后缀，规则见[t_data_fields].field_name_view
					String beanSort = sortArray[i];
					if (beanSort.endsWith("Text")) {
						beanSort = beanSort.substring(0, beanSort.length() - 4);
					}
					orders.add(new OrderByDto(beanSort, orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
				}
			}

			// 获取分页后的数据
			List<NoticeAnnouncement> list = this.getDataService().list(filter, orders, pageCtrl);
			List<ViewBean> resultList = new ArrayList<>();
			for (NoticeAnnouncement entity : list) {
				NoticeAnnouncementBean bean = this.getViewConverter().fromEntity(entity, this.messageSource, locale);
				if (!StringUtils.isEmpty(bean.getRecivers_())) {
					bean.setReciversText(virtualOrgService.find(bean.getRecivers_()).getVirtualOrgName());
				}
				bean.setPublish_(entity.getIsPublish());
				bean.setPublishText(entity.getIsPublish() == null || entity.getIsPublish() == 0 ? "未发布" : "已发布");
				resultList.add(bean);
			}
			long total = pageCtrl.getRecordAmount();

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用

		} catch (Exception e) {
			this.getLogger().error("Exception listing the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id     ID
	 * @param locale
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	public JsonResultBean find(@RequestParam String id, Locale locale) {
		return this.findCustomer(id, locale);
	}

	protected JsonResultBean findCustomer(@RequestParam String id, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {
			// 检查是否存在记录
			NoticeAnnouncement entity = this.getDataService().find(id);
			if (entity == null) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
				return jsonResult;
			}

			NoticeAnnouncementBean bean = this.getViewConverter().fromEntity(entity, this.messageSource, locale);
			if (!StringUtils.isEmpty(bean.getRecivers_())) {
				VirtualOrg virtualOrg = virtualOrgService.find(bean.getRecivers_());
				if (virtualOrg != null) bean.setReciversText(virtualOrg.getVirtualOrgName());
			}
			if (!StringUtils.isEmpty(bean.getProject_())) {
				Project project = projectService.find(bean.getProject_());
				if (project != null) bean.setProjectText(project.getProjectName());
			}
			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, bean);

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
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */

	@RequestMapping("/add")
	@ResponseBody
	@SysLogAnnotation(description = "通知公告", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute NoticeAnnouncementBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
		String userId = userBean.getSessionUserId();
		bean.setPublish_((short) 0);
		
		// 设定发起人
		CommonFilter f = new CommonFilter();
		f.addExact("person.user.id", userId);
		List<ProjectPerson> pjPersonList = projectPersonService.list(f, new ArrayList<OrderByDto>());
		if (pjPersonList != null && pjPersonList.size()>0) {
			bean.setOriginator_(pjPersonList.get(0).getId());
		}
		super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean, locale);
	}

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "通知公告", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute NoticeAnnouncementBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		bean.setUploadFileType("edit");
		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
	}

	/**
	 * 发布，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/publish")
	@ResponseBody
	@SysLogAnnotation(description = "通知公告", action = "发布")
	public void publish(HttpServletRequest request, HttpServletResponse response,
			@RequestBody NoticeAnnouncementBean noticeAnnouncementBean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		NoticeAnnouncement noticeAnnouncement = this.noticeAnnouncementService.find(noticeAnnouncementBean.getId());
		noticeAnnouncement.setIsPublish(noticeAnnouncementBean.getPublish_());
		NoticeAnnouncementBeanConverter converter = new NoticeAnnouncementBeanConverter();
		NoticeAnnouncementBean bean = converter.fromEntity(noticeAnnouncement, this.messageSource, locale);
		bean.setArchiveList(null); //防止附件重复更新
		super.update(bean, null, userBean, locale);
		
		// 发送通知消息
		String subject = noticeAnnouncement.getTittle();
		String message = noticeAnnouncement.getContent();
		
		// 判断是不是全局通知
		if (bean.getIsForAll() != null && bean.getIsForAll() == 1) {
			this.sendNotifyAll(subject, message);
		} else {
			
			// 发送给接收人
			if (!StringUtils.isEmpty(noticeAnnouncement.getRecvPerson())) {
				this.sendNotify(noticeAnnouncement.getRecvPerson().getPerson(), subject, message);
			}
			
			// 发送给所在项目组成员
			if (!StringUtils.isEmpty(noticeAnnouncement.getRecivers())) {
				String recivers = noticeAnnouncement.getRecivers();
				String virtualOrgId = recivers.split(":")[2];
				CommonFilter filter = new CommonFilter().addExact("virtualOrg.id", virtualOrgId).addExact("isOnDuty", 0);
				List<ProjectPerson> personList = projectPersonService.list(filter, new ArrayList<OrderByDto>());
				for (ProjectPerson person : personList) {
					this.sendNotify(person.getPerson(), subject, message);
				}
			}
			
		}
	}
	
	/**
	 * 发送全员系统提醒
	 * @param subject
	 * @param message
	 */
	private void sendNotifyAll(String subject, String message) {
    	notifyApiService.sendNotifyAll(subject, message, NotifyTargetTypeEnum.NOTICE_ANNOUNCEMENT.value());
	}
	
	 /**
     * 发送系统提醒
     * @param person
     * @param subject
     * @param message
     */
    private void sendNotify(Person person, String subject, String message) {
    	notifyApiService.sendNotify(person.getUser(), subject, message, NotifyTargetTypeEnum.NOTICE_ANNOUNCEMENT.value());
    }

	@Override
	protected void postUpdate(NoticeAnnouncement entity, NoticeAnnouncement oldEntity, NoticeAnnouncementBean bean,
			java.util.Map<String, Object> paramMap) throws IOException {
		super.postUpdate(entity, oldEntity, bean, paramMap);

		List<Archive> list = new ArrayList<>();
		for (NoticeAttachment attachment : entity.getAttachmentsToDelete()) {
			list.add(attachment.getArchive());
		}
		this.deleteArchiveFiles(list);
	}

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "通知公告", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}
}
