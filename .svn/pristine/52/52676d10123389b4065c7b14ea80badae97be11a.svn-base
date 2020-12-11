package project.edge.web.controller.process;

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
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.CompletedInfoBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.CompletedInfo;
import project.edge.domain.entity.CompletedInfoAttachment;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Project;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.CompletedInfoBean;
import project.edge.service.process.CompletedInfoService;
import project.edge.service.project.ProjectService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 项目竣工画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/process/project-completed")
public class ProjectCompleteController
        extends TreeGridUploadController<CompletedInfo, CompletedInfoBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectCompleteController.class);

    private static final String PID = "P7007";

    private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL

    private static final String ID_MAP_KEY_IMPROVE_DIALOG = "edit-improve-form-dialog";
    private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";
    
    private static final String VIEWPRINT_DIALOG = "viewprint-dialog";
    private static final String OPEN_VIEWPRINT = "open-viewprint";
    private static final String VIEWPRINT_DIALOG_ID = "%1$s-%2$s-ViewPrintDialog";
    private static final String MODEL_KEY_VIEWPRINT_FIELDS = "viewPrintFields";

    private static final String JS_MAP_KEY_GENERATE_CERT = "generate-cert";
    
    @Resource
    private CompletedInfoService completedInfoService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private ProjectService projectService;
        
    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.COMPLETED_INFO.value();
    }

    @Override
    protected Service<CompletedInfo, String> getDataService() {
        return this.completedInfoService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<CompletedInfo, CompletedInfoBean> getViewConverter() {
        return new CompletedInfoBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/progress/completedInfoJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/progress/completedInfoHidden.jsp";
    }

    /**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

//        if (field.isCommonVisible()) {
//            return;
//        }
        
        if ((field.getFieldName().equals("archiveCert_"))
        		|| (field.getFieldName().equals("project_"))
        		|| (field.getFieldName().equals("projectNum_"))
        		|| (field.getFieldName().equals("projectGovernmentNum_"))
        		|| (field.getFieldName().equals("completedContent"))
        		|| (field.getFieldName().equals("archive_"))
        		|| (field.getFieldName().equals("archive2_"))
        		|| (field.getFieldName().equals("archive3_"))
        		|| (field.getFieldName().equals("archive4_"))
        		|| (field.getFieldName().equals("projectNum_"))
        		|| (field.getFieldName().equals("projectGovernmentNum_"))
        		|| (field.getFieldName().equals("projectStatus_"))
        		|| (field.getFieldName().equals("projectStage_"))
        		|| (field.getFieldName().equals("projectSet_"))
        		|| (field.getFieldName().equals("projectKind_"))
        		|| (field.getFieldName().equals("projectSource"))
        		|| (field.getFieldName().equals("specialCategory"))
        		|| (field.getFieldName().equals("performanceCheck"))
        		) {
            this.putFiledList(model, MODEL_KEY_VIEWPRINT_FIELDS, field);
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

        return optionMap;

    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_EDIT_FILE, contextPath + "/process/project-completed/edit-file.json");
        urlMap.put(ControllerUrlMapKeys.EDIT, contextPath + "/process/project-completed/edit-file.json");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_IMPROVE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Improve", this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_VERIFICATION_DIALOG, String.format( "%1$s-%2$s-EditFormDialog-Verification", this.getPageId(), this.getDataType()));
        idMap.put(VIEWPRINT_DIALOG, String.format(VIEWPRINT_DIALOG_ID,  this.getPageId(), this.getDataType()));

        return idMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面，以及连续新建保存时，增加回调，刷新项目集下拉框的内容

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', COMPLETED_INFO);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开修改
        //jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
        //        String.format("COMPLETED_INFO.openEditFormDialog('#%1$s', COMPLETED_INFO);",
        //                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 打开查看
        jsMap.put(OPEN_VIEWPRINT,
                String.format("COMPLETED_INFO.openViewPrint('#%1$s', '%2$s', '#%3$s', %4$s)", 
                		idMap.get(VIEWPRINT_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.getDataType()));
        
        // 生成证书
        jsMap.put(JS_MAP_KEY_GENERATE_CERT,
                String.format("COMPLETED_INFO.generateCert('#%1$s', '%2$s');", 
                		idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                		urlMap.get(ControllerUrlMapKeys.FIND)));

        return jsMap;
    }
    
    @Override
    protected void createFunctionBean(Page p, List<FunctionBean> functions,
            List<FunctionBean> searchFunctions, Map<String, String> funcGroupMap,
            Map<String, String> jsMap, Locale locale) {

        String pageId = p.getId();
        if (pageId.endsWith(ControllerFunctionIds.GENERATE_CERT)) { // 生成证书

            this.parseFunction(p, functions, funcGroupMap, "", jsMap.get(JS_MAP_KEY_GENERATE_CERT),
                    locale);

        } else if (pageId.endsWith(ControllerFunctionIds.VIEW_PRINT)) {
        	
        	this.parseFunction(p, functions, funcGroupMap, "", jsMap.get(OPEN_VIEWPRINT),
                    locale);
        	
        }
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
        
//        Map<String, Object> modelMap = model.asMap();
//        @SuppressWarnings("unchecked")
//        ArrayList<FieldBean> addEditFields =
//                (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.ADD_FIELDS);
//
//        List<FieldBean> mergeFields = new ArrayList<>();
//        for (FieldBean bean : addEditFields) {
//        	if (!"archiveCert_".equals(bean.getFieldName())) {
//        		mergeFields.add(bean);
//        	}
//        }
//        
//        model.addAttribute(ControllerModelKeys.ADD_FIELDS, mergeFields);
        
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
    @SysLogAnnotation(description = "项目竣工", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute CompletedInfoBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archive_", bean.getArchiveList());
        fieldNameArchiveListMap.put("archive2_", bean.getArchive2List());
        fieldNameArchiveListMap.put("archive3_", bean.getArchive3List());
        fieldNameArchiveListMap.put("archive4_", bean.getArchive4List());

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
    @SysLogAnnotation(description = "项目竣工", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute CompletedInfoBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        bean.setUploadFileType("edit");
        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archive_", bean.getArchiveList());
        fieldNameArchiveListMap.put("archive2_", bean.getArchive2List());
        fieldNameArchiveListMap.put("archive3_", bean.getArchive3List());
        fieldNameArchiveListMap.put("archive4_", bean.getArchive4List());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(CompletedInfo entity, CompletedInfo oldEntity,
            CompletedInfoBean bean, java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        // this.deleteArchiveFiles(entity.getArchivesToDelete());

        List<Archive> list = new ArrayList<>();
        for (CompletedInfoAttachment attachment : entity.getAttachmentsToDelete()) {
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
    @SysLogAnnotation(description = "项目竣工", action = "更新")
    public JsonResultBean update(@ModelAttribute CompletedInfoBean bean,
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
    @SysLogAnnotation(description = "项目竣工", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // ★ Project的文件夹对应的Archive的id，就是Project的id
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);
        paramMap.put(KEY_NEED_DELETE_SELF_RECORD, true);

        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    /**
     * 生成电子证书
     * @param id
     * @param locale
     * @return
     */
    @RequestMapping("/generate-cert")
    @ResponseBody
    public JsonResultBean generateCert(
    		@ModelAttribute CompletedInfoBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        
        return completedInfoService.generateCert(bean, userBean, locale);
    }
}
