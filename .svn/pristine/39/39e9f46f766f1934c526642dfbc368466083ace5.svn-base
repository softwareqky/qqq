package project.edge.web.controller.archive;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.PaperLibraryStatusEnum;
import project.edge.domain.converter.PaperLibraryLendHistoryBeanConverter;
import project.edge.domain.entity.PaperLibrary;
import project.edge.domain.entity.PaperLibraryLendHistory;
import project.edge.domain.view.PaperLibraryLendHistoryBean;
import project.edge.domain.view.QualityReportBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.archive.PaperLibraryLendHistoryService;
import project.edge.service.archive.PaperLibraryService;
import project.edge.web.controller.common.SingleGridController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/paper-library-req")
public class PaperLibraryLendHistoryController
		extends SingleGridController<PaperLibraryLendHistory, PaperLibraryLendHistoryBean> {

	private static final Logger logger = LoggerFactory.getLogger(PaperLibraryLendHistoryController.class);
	private static final String PID = "P901002";
	
	private static final String URL_MAP_KEY_EDIT_LEND = "edit-lend";
	
	private static final String ID_MAP_KEY_TAKE_DIALOG = "edit-take-form-dialog";
    private static final String ID_MAP_KEY_RETURN_DIALOG = "edit-return-form-dialog";

    private static final String JS_MAP_KEY_EDIT_TAKE_SUBMIT = "edit-take-submit";
    private static final String JS_MAP_KEY_EDIT_RETURN_SUBMIT = "edit-return-submit";
	
	@Resource
	private PaperLibraryLendHistoryService paperLibraryLendHistoryService;
	
	@Resource
	private PaperLibraryService paperLibraryService;

    @Autowired
	HttpServletRequest request;
    
	@Resource
	CreateWorkFlowManager createWorkFlowManager;

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.PAPER_LIBRARY_LEND_HISTORY.value();
	}

	@Override
	protected Service<PaperLibraryLendHistory, String> getDataService() {
		// TODO Auto-generated method stub
		return this.paperLibraryLendHistoryService;
	}

	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return this.PID;
	}

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/archive/paperLibraryLendHistoryJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/archive/paperLibraryLendHistoryHidden.jsp";
    }
    
    /**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
    	
    	// 借阅领取
        if (field.getFieldName().equals("paperLibrary_")
        		|| field.getFieldName().equals("lendPerson_")
        		|| field.getFieldName().equals("contactMobile")
        		|| field.getFieldName().equals("lendDays")
        		|| field.getFieldName().equals("flowStatus")
        		|| field.getFieldName().equals("getPerson_")
                || field.getFieldName().equals("getTime")) {
        	this.putFiledList(model, "takeFields", field);
        }

        // 借阅归还
        if (field.getFieldName().equals("paperLibrary_")
        		|| field.getFieldName().equals("lendPerson_")
        		|| field.getFieldName().equals("contactMobile")
        		|| field.getFieldName().equals("lendDays")
        		|| field.getFieldName().equals("flowStatus")
        		|| (field.getFieldName().equals("getPerson_"))
                || (field.getFieldName().equals("getTime"))
                || (field.getFieldName().equals("returnPerson_"))
                || (field.getFieldName().equals("returnTime"))) {
            this.putFiledList(model, "returnFields", field);
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
	protected ViewConverter<PaperLibraryLendHistory, PaperLibraryLendHistoryBean> getViewConverter() {
		// TODO Auto-generated method stub
		return new PaperLibraryLendHistoryBeanConverter();
	}
	
	@Override
	protected Map<String, String> prepareUrlMap() {

		Map<String, String> urlMap = super.prepareUrlMap();

		String contextPath = this.context.getContextPath();
		urlMap.put(URL_MAP_KEY_EDIT_LEND, contextPath + "/archive/paper-library-req/edit.json");

		return urlMap;
	}

	@Override
	protected Map<String, String> prepareIdMap() {

		Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_TAKE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Take",
                this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_RETURN_DIALOG, String.format(
                "%1$s-%2$s-EditFormDialog-Return", this.getPageId(), this.getDataType()));
        
		return idMap;
	}

	@Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 借阅领取
        jsMap.put(ControllerJsMapKeys.LEND_TAKE, String.format(
                "PaperLibraryLendHistory.openEditFormDialog('#%1$s', '%2$s');", 
		                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), 
				        urlMap.get(ControllerUrlMapKeys.FIND)));

        // 借阅归还
        jsMap.put(ControllerJsMapKeys.LEND_RETURN, String.format(
        		"PaperLibraryLendHistory.openEditFormDialog('#%1$s', '%2$s');", 
		                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), 
				        urlMap.get(ControllerUrlMapKeys.FIND)));

        // 提交审核
//        jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT,
//        		String.format("PaperLibraryLendHistory.handleAuditClick('#%1$s', '%2$s');", 
//        		         idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), 
//        		         urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, null, null, PaperLibraryLendHistory);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
        
        // 修改保存-借阅领取
        jsMap.put(JS_MAP_KEY_EDIT_TAKE_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, null, null, PaperLibraryLendHistory);",
                        idMap.get(ID_MAP_KEY_TAKE_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
        
        // 修改保存-借阅归还
        jsMap.put(JS_MAP_KEY_EDIT_RETURN_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, null, null, PaperLibraryLendHistory);",
                        idMap.get(ID_MAP_KEY_RETURN_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
        

        return jsMap;
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

        List<ComboboxOptionBean> flowStatus = new ArrayList<>();

        for (FlowStatusEnum s : FlowStatusEnum.values()) {
            flowStatus.add(new ComboboxOptionBean(s.value().toString(), messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("FlowStatus", flowStatus);
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

        String r = super.main(paramMap, model, userBean, locale);
        
        // Datagrid行选择的事件，除了默认的逻辑，还需要特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮
        model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "PaperLibraryLendHistory.handleSelect");
        model.addAttribute(ControllerModelKeys.CLICK_CELL_HANDLER, "PaperLibraryLendHistory.clickCellHandler");
        
        return r;
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
    @SysLogAnnotation(description = "借阅管理", action = "新增")
    public JsonResultBean create(@ModelAttribute PaperLibraryLendHistoryBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	// 状态检查
    	String id = bean.getPaperLibrary_();
    	if (!StringUtils.isEmpty(id)) {
    		PaperLibrary entity = paperLibraryService.find(id);
    		if (entity != null) {
    			if (entity.getStatus() != PaperLibraryStatusEnum.IN_LIBRARY.value()) {
    				JsonResultBean jsonResult = new JsonResultBean();
    				jsonResult.setStatus(JsonStatus.ERROR);
    	            jsonResult.setMessage(
    	                    this.messageSource.getMessage("message.error.data.status", null, locale));
    	            return jsonResult;
    			}
    		}
    	}
    	
        return super.create(bean, null, userBean, locale);
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
    @SysLogAnnotation(description = "借阅管理", action = "更新")
    public JsonResultBean update(@ModelAttribute PaperLibraryLendHistoryBean bean,
    		@RequestParam(required = false, defaultValue = "") String actionType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	if (!StringUtils.isEmpty(actionType)) {
    		bean.setActionType(actionType);
    	}
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
    @SysLogAnnotation(description = "借阅管理", action = "删除")
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
     */
    @RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "借阅管理", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute QualityReportBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();

		PaperLibraryLendHistory entity = this.getDataService().find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}

		// 向OA提交审核请求
		
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_ARCHIVE_LEND.value();
		
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("paperLibrary", entity.getPaperLibrary().getArchiveName());
		reqMap.put("lendPerson", entity.getLendPerson().getUser().getId());
		reqMap.put("contactMobile", entity.getContactMobile());
		reqMap.put("lendDays", String.valueOf(entity.getLendDays()));
		
		// 查询所属项目
		String projectId = "";
		if (entity.getPaperLibrary() != null && entity.getPaperLibrary().getProject() != null) projectId = entity.getPaperLibrary().getProject().getId();
		
		// 调用oa接口
		logger.info("[OA Audit] No.19 PaperLibraryLendHistory");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		//如果请求审批成功更新表t_plan 审批中
		if(jsonResult.getStatus() == JsonStatus.OK){
			logger.info("[OA Audit] No.19 PaperLibraryLendHistory Audit request success, data id was {}", dataId);
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			entity.setmDatetime(new Date());
			entity.setModifier(userName);
			this.paperLibraryLendHistoryService.setData(entity);
		}

		return jsonResult;
	}
}
