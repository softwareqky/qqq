package project.edge.web.controller.quality;

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
import project.edge.domain.converter.IssueBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Issue;
import project.edge.domain.entity.IssueAttachment;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.IssueBean;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.quality.IssueService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 问题管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/quality/issue")
public class IssueController extends TreeGridUploadController<Issue, IssueBean> {

    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    private static final String PID = "P8050";

    private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL

    private static final String MODEL_KEY_SPOT_CHECK_FIELDS = "spotcheckFields";
    private static final String MODEL_KEY_SOLVE_FIELDS = "solveFields";

    private static final String ID_MAP_KEY_SPOT_CHECK_DIALOG = "edit-spotcheck-form-dialog";
    private static final String ID_MAP_KEY_SOLVE_DIALOG = "edit-solve-form-dialog";

    private static final String JS_MAP_KEY_EDIT_SPOT_CHECK_SUBMIT = "edit-spotcheck-submit";
    private static final String JS_MAP_KEY_EDIT_SOLVE_SUBMIT = "edit-solve-submit";


    @Resource
    private IssueService issueService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private VirtualOrgService virtualOrgService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.ISSUE.value();
    }

    @Override
    protected Service<Issue, String> getDataService() {
        return this.issueService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<Issue, IssueBean> getViewConverter() {
        return new IssueBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/quality/issueJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/quality/issueHidden.jsp";
    }

    /**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        if (field.isCommonVisible() && (field.getFieldName().equals("solveDatetime"))
                && (field.getFieldName().equals("solveStatus_"))) {

            return;
        }

        // 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见
        if ((field.getFieldName().equals("issueTitle"))
                || (field.getFieldName().equals("issueType_"))
                || (field.getFieldName().equals("issuePriority_"))
                || (field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("solveContent"))
                || (field.getFieldName().equals("solveDatetime"))
                || (field.getFieldName().equals("solveStatus_"))
                || (field.getFieldName().equals("solvearchive_"))) { // 建议
            this.putFiledList(model, MODEL_KEY_SOLVE_FIELDS, field);

        }

        if ((field.getFieldName().equals("issueTitle"))
                || (field.getFieldName().equals("issueType_"))
                || (field.getFieldName().equals("issuePriority_"))
                || (field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("internalVerify"))
                || (field.getFieldName().equals("internalVerifyFeedback"))
                || (field.getFieldName().equals("externalVerify"))
                || (field.getFieldName().equals("externalVerifyFeedback"))
                || (field.getFieldName().equals("reassignTo_"))
                || (field.getFieldName().equals("reassignReason"))) { // 整改
            this.putFiledList(model, MODEL_KEY_SPOT_CHECK_FIELDS, field);
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

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.ISSUE_TYPE.value());
        dataTypeList.add(DataTypeEnum.ISSUE_STATUS.value());
        dataTypeList.add(DataTypeEnum.ISSUE_PRIORITY.value());

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        List<ComboboxOptionBean> issueTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> issuePriorityOptions = new ArrayList<>();
        List<ComboboxOptionBean> solveStatusTypeOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);
        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.ISSUE_TYPE.value())) {
                issueTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.ISSUE_STATUS.value())) {
                solveStatusTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.ISSUE_PRIORITY.value())) {
                issuePriorityOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

        optionMap.put("issueTypeOptions", issueTypeOptions);
        optionMap.put("solveStatusTypeOptions", solveStatusTypeOptions);
        optionMap.put("issuePriorityOptions", issuePriorityOptions);

        CommonFilter virtualFilter = null;
        List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
        List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(virtualFilter, null);
        if (virtualOrgList != null) {
            for (VirtualOrg v : virtualOrgList) {
                projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
            }
        }
        optionMap.put("projectGroupOptions", projectGroupOptions);

        return optionMap;
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_EDIT_FILE, contextPath + "/quality/issue/edit-file.json");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_SPOT_CHECK_DIALOG, String.format("%1$s-%2$s-EditFormDialog-SpotCheck",
                this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_SOLVE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Solve",
                this.getPageId(), this.getDataType()));

        return idMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面，以及连续新建保存时，增加回调，刷新项目集下拉框的内容

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', ISSUE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, ISSUE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 连续新建，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, ISSUE);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
                String.format("ISSUE.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 修改保存
        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', false, false, false, ISSUE);",
                        idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
        // 修改保存-抽查
        jsMap.put(JS_MAP_KEY_EDIT_SPOT_CHECK_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_SPOT_CHECK_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));

        // 修改保存-验证
        jsMap.put(JS_MAP_KEY_EDIT_SOLVE_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_SOLVE_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

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
    @SysLogAnnotation(description = "问题管理", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute IssueBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archive_", bean.getArchiveList());
        // fieldNameArchiveListMap.put("solvearchive_", bean.getSlovearchiveList());

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
    @SysLogAnnotation(description = "问题管理", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute IssueBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {


        bean.setUploadFileType(uploadType);
        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        // fieldNameArchiveListMap.put("archive_", bean.getArchiveList());
        fieldNameArchiveListMap.put("solvearchive_", bean.getSlovearchiveList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(Issue entity, Issue oldEntity, IssueBean bean,
            java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);

        List<Archive> list = new ArrayList<>();
        for (IssueAttachment attachment : entity.getAttachmentsToDelete()) {
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
    @SysLogAnnotation(description = "问题管理", action = "更新")
    public JsonResultBean update(@ModelAttribute IssueBean bean,
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
    @SysLogAnnotation(description = "问题管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // ★ Project的文件夹对应的Archive的id，就是Project的id
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);
        paramMap.put(KEY_NEED_DELETE_SELF_RECORD, true);

        return super.delete(idsToDelete, paramMap, userBean, locale);
    }
}
