/**
 * 
 */
package project.edge.web.controller.budget;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
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
import project.edge.domain.converter.BudgetTemplateBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.BudgetTemplate;
import project.edge.domain.entity.BudgetTemplateAttachment;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.BudgetTemplateBean;
import project.edge.service.budget.BudgetTemplateService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * @author angel_000
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetTemplate")
public class BudgetTemplateController extends TreeGridUploadController<BudgetTemplate, BudgetTemplateBean> {

	private static final Logger logger = LoggerFactory.getLogger(BudgetTemplateController.class);

	private static final String PID = "P10009";
	
	private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL
	
    private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";
    
    private static final String JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT = "edit-verification-submit";
    
    private static final String MODEL_KEY_SOLVE_FIELDS = "verificationFields";



	@Resource
	private BudgetTemplateService budgetTemplateService;
	
	@Resource
	private VirtualOrgService virtualOrgService;

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.BUDGET_TEMPLATE.value();
	}

	@Override
	protected Service<BudgetTemplate, String> getDataService() {
		// TODO Auto-generated method stub
		return this.budgetTemplateService;
	}

	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return PID;
	}

    @Override
    protected boolean useFile() {
        return true;
    }
	
	@Override
	protected ViewConverter<BudgetTemplate, BudgetTemplateBean> getViewConverter() {
		// TODO Auto-generated method stub
		return new BudgetTemplateBeanConverter();
	}
	
    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/budgetTemplateJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/budget/budgetTemplateHidden.jsp";
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
        // if (field.getFieldName().equals("proposalArchive_")) { // 建议
            this.putFiledList(model, MODEL_KEY_SOLVE_FIELDS, field);

        // }
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
		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<String, List<ComboboxOptionBean>>();
		
		CommonFilter f = null;
		List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f, null);
		if (virtualOrgList != null) {
			for (VirtualOrg v : virtualOrgList) {
				projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
			}
		}

		optionMap.put("groupOptions", projectGroupOptions);
		return optionMap;
	}

    
    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_EDIT_FILE, contextPath + "/budget/budgetTemplate/edit-file.json");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

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
                String.format("CrudUtils.openAddFormDialog('#%1$s', BUDGETTEMPLATE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, BUDGETTEMPLATE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
                String.format("BUDGETTEMPLATE.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));

        // 修改保存
        jsMap.put(JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, BUDGETTEMPLATE);",
                        idMap.get(ID_MAP_KEY_VERIFICATION_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        return jsMap;
    }
	
	/**
	 * 画面Open的入口方法，用于生成JSP。
	 * 
	 * @param paramMap
	 *            画面请求中的任何参数，都会成为检索的字段
	 * @param model
	 *            model
	 * @param userBean
	 *            session中的当前登录的用户信息
	 * @param locale
	 *            locale
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
	 * @param commonFilterJson
	 *            JSON字符串形式的过滤条件
	 * @param page
	 *            页数
	 * @param rows
	 *            每页的记录数
	 * @param sort
	 *            排序的字段，CSV
	 * @param order
	 *            顺序，CSV
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

		JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);

		return jsonResult;
	}

	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id
	 *            ID
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
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/add")
	@SysLogAnnotation(description = "预算模板", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute BudgetTemplateBean bean, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archive_", bean.getArchiveList());

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean, locale);
	}
	

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
    
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "预算模板", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute BudgetTemplateBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archive_", bean.getArchiveList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }
    
	@Override
	protected void postUpdate(BudgetTemplate entity, BudgetTemplate oldEntity, BudgetTemplateBean bean,
			java.util.Map<String, Object> paramMap) throws IOException {
		super.postUpdate(entity, oldEntity, bean, paramMap);
        List<Archive> list = new ArrayList<>();
        for (BudgetTemplateAttachment attachment : entity.getArchivesToDelete()) {
            list.add(attachment.getArchive());
        }
        this.deleteArchiveFiles(list);
        }

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete
	 *            待删除的ID，CSV
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "预算模板", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	 

}
