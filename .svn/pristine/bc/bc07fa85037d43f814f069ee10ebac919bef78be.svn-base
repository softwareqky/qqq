/**
 * 
 */
package project.edge.web.controller.archive;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.CheckUtils;
import garage.origin.common.util.ZipUtils;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
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
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.KnowledgeBaseBeanConverter;
import project.edge.domain.entity.KnowledgeBase;
import project.edge.domain.filter.ArchiveFilter;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.KnowledgeBaseBean;
import project.edge.service.archive.KnowledgeBaseService;
import project.edge.web.controller.common.SingleGridUploadController;

/**
 * 知识库画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/knowledge-base")
public class KnowledgeBaseController
        extends SingleGridUploadController<KnowledgeBase, KnowledgeBaseBean> {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeBaseController.class);

    private static final String KNOWLEDGE_BASE_VIEW_NAME = "archive/knowledgeBaseMain"; // 定制的JSP

    private static final String MODEL_KEY_ADD_FILE_FIELDS = "addFileFields"; // 上传文件画面的字段列表
    private static final String MODEL_KEY_ADD_FOLDER_FIELDS = "addFolderFields"; // 新建文件夹画面的字段列表
    private static final String MODEL_KEY_EDIT_INFO_FIELDS = "editInfoFields"; // 修改信息画面的字段列表

    private static final String URL_MAP_KEY_ADD_FILE = "add-file"; // 上传文件的URL
    private static final String URL_MAP_KEY_ADD_FOLDER = "add-folder"; // 新建文件夹的URL
    private static final String URL_MAP_KEY_DOWNLOAD = "download"; // 下载的URL

    private static final String ID_MAP_KEY_ADD_FILE_DIALOG = "add-file-dialog"; // 上传文件Dialog的id
    private static final String ID_MAP_KEY_ADD_FOLDER_DIALOG = "add-folder-dialog"; // 新建文件夹Dialog的id

    private static final String JS_MAP_KEY_OPEN_ADD_FILE = "open-add-file"; // 打开上传文件的JS
    private static final String JS_MAP_KEY_CON_ADD_FILE_SUBMIT = "con-add-file-submit"; // 连续新增文件提交的JS
    private static final String JS_MAP_KEY_ADD_FILE_SUBMIT = "add-file-submit"; // 新增文件提交的JS
    private static final String JS_MAP_KEY_OPEN_ADD_FOLDER = "open-add-folder"; // 打开新建文件夹的JS

    private static final String PID = "P9005";

    @Resource
    private KnowledgeBaseService knowledgeBaseService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.KNOWLEDGE_BASE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<KnowledgeBase, String> getDataService() {
        return this.knowledgeBaseService;
    }

    @Override
    protected ViewConverter<KnowledgeBase, KnowledgeBaseBean> getViewConverter() {
        return new KnowledgeBaseBeanConverter();
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(KnowledgeBaseBean bean) {

        List<CommonFilter> list = new ArrayList<>();

        // 文件夹不允许重名
        if (bean.getIsDir() == OnOffEnum.ON.value()) {
            CommonFilter filter = new CommonFilter().addExact("isDir", OnOffEnum.ON.value())
                    .addExact("isDeleted", OnOffEnum.OFF.value())
                    .addExact("pid", CheckUtils.checkString(bean.getPid(), null))
                    .addExact("archiveName", bean.getArchiveName());
            list.add(filter);
        }
        return list;
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(KnowledgeBaseBean bean, KnowledgeBase oldEntity) {

        // 文件夹不允许重名，一旦修改了文件夹的名称，就要做唯一检查
        // PS:重名文件会产生新版本
        if (oldEntity.getIsDir() == OnOffEnum.ON.value()
                && !bean.getArchiveName().equals(oldEntity.getArchiveName())) {
            return this.getUniqueFilter(bean);
        }
        return new ArrayList<>();
    }

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return KNOWLEDGE_BASE_VIEW_NAME;
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/archive/knowledgeBaseJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/archive/knowledgeBaseHidden.jsp";
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_ADD_FOLDER, contextPath + "/archive/knowledge-base/add-folder.json");
        urlMap.put(URL_MAP_KEY_ADD_FILE, contextPath + "/archive/knowledge-base/add-file.json");
        urlMap.put(URL_MAP_KEY_DOWNLOAD, contextPath + "/archive/knowledge-base/download.json");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_ADD_FILE_DIALOG,
                String.format("%1$s-%2$s-AddFileDialog", this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_ADD_FOLDER_DIALOG,
                String.format("%1$s-%2$s-AddFolderDialog", this.getPageId(), this.getDataType()));
        return idMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 检索
        jsMap.put(ControllerJsMapKeys.SEARCH,
                String.format("KNOWLEDGE_BASE.searchSimple('#%1$s', '#%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        idMap.get(ControllerIdMapKeys.FILTER_FORM)));

        // 打开上传文件，callback设为null，且dialog id另设
        jsMap.put(JS_MAP_KEY_OPEN_ADD_FILE, String.format(ControllerMapUtils.OPEN_ADD_JS,
                idMap.get(ID_MAP_KEY_ADD_FILE_DIALOG), null));

        // 连续新建文件，dialog id另设
        jsMap.put(JS_MAP_KEY_CON_ADD_FILE_SUBMIT, String.format(
                ControllerMapUtils.CONTINUOUS_ADD_SUBMIT_JS, idMap.get(ID_MAP_KEY_ADD_FILE_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile(), this.getDataType()));

        // 新建保存文件，dialog id另设
        jsMap.put(JS_MAP_KEY_ADD_FILE_SUBMIT, String.format(ControllerMapUtils.ADD_SUBMIT_JS,
                idMap.get(ID_MAP_KEY_ADD_FILE_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                this.useFile(), this.getDataType()));

        // 打开新建文件夹，callback设为null，且dialog id另设
        jsMap.put(JS_MAP_KEY_OPEN_ADD_FOLDER, String.format(ControllerMapUtils.OPEN_ADD_JS,
                idMap.get(ID_MAP_KEY_ADD_FOLDER_DIALOG), null));

        // 连续新建文件夹，不使用文件，且dialog id另设
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT,
                String.format(ControllerMapUtils.CONTINUOUS_ADD_SUBMIT_JS,
                        idMap.get(ID_MAP_KEY_ADD_FOLDER_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false, this.getDataType()));

        // 新建保存文件夹，不使用文件，且dialog id另设
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format(ControllerMapUtils.ADD_SUBMIT_JS,
                        idMap.get(ID_MAP_KEY_ADD_FOLDER_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false, this.getDataType()));

        // 删除，两次确认
        jsMap.put(ControllerJsMapKeys.OPEN_DELETE,
                String.format("KNOWLEDGE_BASE.batchDeleteSelected('%1$s', '#%2$s');",
                        urlMap.get(ControllerUrlMapKeys.DELETE),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 打开修改信息，使用默认

        // 修改保存，不使用文件，且callback设为null
        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
                String.format(ControllerMapUtils.EDIT_SUBMIT_JS,
                        idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false, null));

        // 下载
        jsMap.put(ControllerJsMapKeys.OPEN_DOWNLOAD,
                String.format("KNOWLEDGE_BASE.download('%1$s', '#%2$s');",
                        urlMap.get(URL_MAP_KEY_DOWNLOAD),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 分配权限
        jsMap.put(ControllerJsMapKeys.OPEN_AUTHORITY, "KNOWLEDGE_BASE.openAuth()");

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

        // 5. 获取画面所需的各个数据字段
        ArrayList<FieldBean> addFileFields = new ArrayList<>(); // 上传文件弹出画面的字段
        ArrayList<FieldBean> addFolderFields = new ArrayList<>(); // 新建文件夹弹出画面的字段
        ArrayList<FieldBean> editInfoFields = new ArrayList<>(); // 修改信息弹出画面的字段

        // 文件名
        FieldBean archiveNameField = new FieldBean();
        archiveNameField.setId("KNOWLEDGE_BASE.archiveName");
        archiveNameField.setFieldName("archiveName");
        archiveNameField.setFieldNameView("archiveName");
        archiveNameField.setCaptionName(
                this.messageSource.getMessage("ui.fields.knowledge.base.name", null, locale));
        archiveNameField.setMandatory(true);
        archiveNameField.setWidget("textbox");
        archiveNameField.setValueType(FieldValueType.STRING);
        archiveNameField.setMaxLength("50");
        archiveNameField.setCustomValidType("filename");
        archiveNameField.setValidationPrompt(
                this.messageSource.getMessage("ui.common.maxlength", null, locale) + " 50");

        // 文件内容
        FieldBean archiveContentField = new FieldBean();
        archiveContentField.setId("KNOWLEDGE_BASE.archiveContent");
        archiveContentField.setFieldName("archiveContent");
        archiveContentField.setFieldNameView("archiveContent");
        archiveContentField.setCaptionName(
                this.messageSource.getMessage("ui.fields.knowledge.base.content", null, locale));
        archiveContentField.setMandatory(true);
        archiveContentField.setWidget("filebox");
        archiveContentField.setMultiLine(true);
        archiveContentField.setValueType(FieldValueType.STRING);

        // 文件概述
        FieldBean remarkField = new FieldBean();
        remarkField.setId("KNOWLEDGE_BASE.remark");
        remarkField.setFieldName("remark");
        remarkField.setFieldNameView("remark");
        remarkField.setCaptionName(
                this.messageSource.getMessage("ui.fields.knowledge.base.remark", null, locale));
        remarkField.setWidget("textbox");
        remarkField.setMultiLine(true);
        remarkField.setCopyReserve(true);
        remarkField.setValueType(FieldValueType.STRING);
        remarkField.setMaxLength("500");
        remarkField.setValidationPrompt(
                this.messageSource.getMessage("ui.common.maxlength", null, locale) + " 500");

        addFileFields.add(remarkField);
        addFileFields.add(archiveContentField);

        addFolderFields.add(archiveNameField);
        addFolderFields.add(remarkField);

        editInfoFields.add(archiveNameField);
        editInfoFields.add(remarkField);

        model.addAttribute(MODEL_KEY_ADD_FILE_FIELDS, addFileFields);
        model.addAttribute(MODEL_KEY_ADD_FOLDER_FIELDS, addFolderFields);
        model.addAttribute(MODEL_KEY_EDIT_INFO_FIELDS, editInfoFields);

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

        /**
         * 0. 一览检索时，一共只对两个字段进行过滤，其一是文件名/备注(共用一个字段)，其二是所在目录(pid)。
         * 对文件名和文件概述(备注)进行模糊检索时要特殊处理，如果检索文件名或备注，则一定对所检索的目标文件夹做限制(递归pidPath)；
         * 如果没有对文件和备注检索，则只需要对一览的所在目录做限制(pid)
         */
        ArchiveFilter af = new ArchiveFilter();
        FilterFieldInfoDto archiveNameField = null;
        FilterFieldInfoDto pidField = null;
        try {
            CommonFilter filter = this.generateCommonFilter(null, commonFilterJson); // 可能是null
            if (filter != null) {
                for (FilterFieldInfoDto field : filter.getFilterFieldList()) {
                    if (field.getFieldName().equals("archiveName")) {
                        archiveNameField = field;
                    } else {
                        pidField = field;
                    }
                }
            }

            if (archiveNameField == null) {
                if (pidField == null) {
                    af.addExact("pid", Constants.SLASH);
                } else {
                    if (StringUtils.isEmpty(pidField.getValue())) {
                        pidField.setValue(Constants.SLASH);
                    }
                    af.getFilterFieldList().add(pidField);
                }
            } else {
                af.setFileInfo(archiveNameField.getValue().toString());
                if (pidField != null && pidField.getValue() != null) {
                    af.setPidPath(pidField.getValue().toString());
                }
            }

        } catch (Exception e) {
            this.getLogger().error("Exception parsing filter.", e);

            // 在JSON对象内设定服务器处理结果状态
            JsonResultBean jsonResult = new JsonResultBean();
            jsonResult.setStatus(JsonStatus.ERROR);
            return jsonResult;
        }

        // 1. 调用父类的list
        sort = "isDir," + sort;
        order = OrderByDto.DESC + Constants.COMMA + order;
        JsonResultBean jsonResult = super.list(null, af, page, rows, sort, order, locale);

        // 2. 准备面包屑路径
        List<ComboboxOptionBean> breadcrumbList = new ArrayList<>();

        // 2.1 根节点"全部文件"
        breadcrumbList.add(new ComboboxOptionBean(Constants.SLASH,
                this.messageSource.getMessage("ui.common.all.files", null, locale)));

        // 2.2 pid不是根节点
        if (pidField != null && !Constants.SLASH.equals(pidField.getValue())) {
            KnowledgeBase k = this.knowledgeBaseService.find(pidField.getValue().toString());

            // 父节点的上层节点
            if (!StringUtils.isEmpty(k.getPath())) {

                // 去掉第0层的根节点
                String[] idArray = StringUtils.commaDelimitedListToStringArray(k.getPath());
                CommonFilter pnFilter = new CommonFilter().addWithin("id", idArray)
                        .addRange("level", FieldValueType.INT, 0, null, true, false);

                List<KnowledgeBase> pathNodeList = this.knowledgeBaseService.list(pnFilter,
                        Collections.singletonList(new OrderByDto("level")));
                for (KnowledgeBase node : pathNodeList) {
                    String nodeName = node.getArchiveName();
                    if (nodeName.length() > 10) {
                        nodeName = nodeName.substring(0, 9) + "...";
                    }
                    breadcrumbList.add(new ComboboxOptionBean(node.getId(), nodeName));
                }
            }

            String pName = k.getArchiveName();
            if (pName.length() > 10) {
                pName = pName.substring(0, 9) + "...";
            }

            breadcrumbList.add(new ComboboxOptionBean(k.getId(), pName));
        }

        // 2.3 检索了文件名或备注
        if (archiveNameField != null) {
            // 将 检索: "xxx" 作为最后一个路径
            breadcrumbList.add(new ComboboxOptionBean("",
                    this.messageSource.getMessage("ui.common.search", null, locale)
                            + Constants.COLON + " \"" + archiveNameField.getValue().toString()
                            + "\""));
        }

        jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, breadcrumbList);

        // 3. 如果对文件名或文件概述(备注)进行了检索，则填充文件的pname
        try {
            @SuppressWarnings("unchecked")
            List<KnowledgeBaseBean> beanList = (List<KnowledgeBaseBean>) jsonResult.getDataMap()
                    .get(JsonResultBean.KEY_EASYUI_ROWS);

            List<String> pidList = new ArrayList<>();
            for (KnowledgeBaseBean bean : beanList) {
                String pid = bean.getPid();
                if (!StringUtils.isEmpty(pid)) {
                    pidList.add(pid);
                }
            }

            List<KnowledgeBase> plist = this.knowledgeBaseService
                    .list(new CommonFilter().addWithin("id", pidList), null);
            Map<String, KnowledgeBase> pmap = new HashMap<>();
            for (KnowledgeBase p : plist) {
                pmap.put(p.getId(), p);
            }

            for (KnowledgeBaseBean bean : beanList) {
                String pid = bean.getPid();
                if (!Constants.SLASH.equals(pid)) {
                    bean.setPname(pmap.get(pid).getArchiveName());
                } else {
                    bean.setPname(
                            this.messageSource.getMessage("ui.common.all.files", null, locale));
                }
            }

        } catch (Exception e) {
            this.getLogger().error("Exception parsing filter.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
            return jsonResult;
        }

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
     * 新建文件夹，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add-folder")
    @ResponseBody
    public JsonResultBean createFolder(@ModelAttribute KnowledgeBaseBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        bean.setIsDir(OnOffEnum.ON.value());
        return super.create(bean, null, userBean, locale);
    }

    /**
     * 根据pid获取此文件(夹)所在目录，然后设置Archive的relativePath，level和path。
     */
    @Override
    protected void beforeCreate(KnowledgeBase entity, KnowledgeBaseBean bean,
            Map<String, Object> paramMap) throws Exception {

        String pid = entity.getPid();
        KnowledgeBase k = this.knowledgeBaseService.find(pid);

        String relativePath = k.getRelativePath() + File.separator + entity.getId();
        int level = k.getLevel() + 1;

        String path = null;
        if (StringUtils.isEmpty(k.getPath())) {
            path = k.getId();
        } else {
            path = k.getPath() + Constants.COMMA + k.getId();
        }

        entity.setLevel(level);
        entity.setPath(path);
        entity.setRelativePath(relativePath);

        // 同目录下的同名文件进行update
        if (OnOffEnum.OFF.value() == entity.getIsDir()) {

            CommonFilter f = new CommonFilter().addExact("pid", entity.getPid())
                    .addExact("archiveName", entity.getArchiveName())
                    .addExact("isDir", OnOffEnum.OFF.value())
                    .addExact("isDeleted", OnOffEnum.OFF.value());

            List<KnowledgeBase> list = this.knowledgeBaseService.list(f, null);
            if (!list.isEmpty()) {

                // id、path和relativePath都用原来的。在ArchiveService.create中，会判断是否存在相同id的记录，并覆盖和版本控制。
                KnowledgeBase dbArchive = list.get(0);

                entity.setId(dbArchive.getId());
                entity.setRelativePath(dbArchive.getRelativePath());
                entity.setPath(dbArchive.getPath());
            }
        }

        // 在保存文件前，重新设置id和relativePath，以便支持同名文件覆盖原文件
        bean.setId(entity.getId());
        bean.setRelativePath(entity.getRelativePath());
    }

    @Override
    protected void postCreate(KnowledgeBase entity, KnowledgeBaseBean bean,
            Map<String, Object> paramMap, Locale locale) throws IOException {

        super.postCreate(entity, bean, paramMap, locale);
    }

    @Override
    protected String getRootFolderPathKey() {
        return SystemConfigKeys.KNOWLEDGE_BASE_ROOT_FOLDER_PATH;
    }

    /**
     * 新建文件，返回Json格式。
     * 
     * @param request
     * @param response
     * @param b 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add-file")
    public void createFile(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute KnowledgeBaseBean b,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        List<ArchiveBean> beanList = new ArrayList<>();
        Map<String, List<ArchiveBean>> fieldNameKnowledgeBaseListMap = new HashMap<>();
        fieldNameKnowledgeBaseListMap.put("archiveContent", beanList);

        Map<String, Object> paramMap = new HashMap<>();

        JsonResultBean jsonResult =
                this.parseMultipart(request, response, paramMap, fieldNameKnowledgeBaseListMap);
        if (jsonResult.getStatus() != JsonStatus.ERROR) {
            try {

                for (ArchiveBean ab : beanList) {

                    KnowledgeBaseBean bean = new KnowledgeBaseBean(ab);

                    // 创建数据
                    bean.setPid(b.getPid());
                    bean.setRemark(b.getRemark());

                    KnowledgeBase entity =
                            this.getViewConverter().toEntity(bean, null, userBean, new Date());

                    this.beforeCreate(entity, bean, paramMap);
                    this.getDataService().create(entity);
                    
                    // KnowledgeBaseBean数据回写至ArchiveBean
                    // id/level/pid/(id)path/relativePath由converter各自设置（SingleGridUploadController需求）
                    ab.setId(bean.getId());
                    ab.setLevel(bean.getLevel());
                    ab.setPid(bean.getPid());
                    ab.setPath(bean.getPath());
                    ab.setRelativePath(bean.getRelativePath());
                }

                // 只有全部的bean都赋值处理之后，才调用postCreate进行写文件
                this.postCreate(null, null, paramMap, locale);

                // 准备JSON结果
                jsonResult.setStatus(JsonStatus.OK);
                jsonResult.setMessage(
                        this.messageSource.getMessage("message.info.record.add.ok", null, locale));
                jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, null);

            } catch (Exception e) {
                this.getLogger().error("Exception creating archives.", e);

                // 在JSON对象内设定服务器处理结果状态
                jsonResult.setStatus(JsonStatus.ERROR);
            }
        }

        try {
            String result = new ObjectMapper().writeValueAsString(jsonResult);
            response.getWriter().write("<textarea>" + result + "</textarea>");
        } catch (Exception e) {
            this.getLogger().error("Exception converting object to json string.", e);
        }
    }

    /**
     * 修改，返回Json格式。不涉及文件上传处理。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "知识库", action = "更新")
    public JsonResultBean update(@ModelAttribute KnowledgeBaseBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
    }

    @Override
    protected void beforeDelete(Map<String, Object> paramMap, String modifier, Date mDatetime) {

        if (paramMap == null || !paramMap.containsKey(KEY_ARCHIVE_IDS_TO_DELETE)) {
            return;
        }

        String idsToDelete = paramMap.get(KEY_ARCHIVE_IDS_TO_DELETE).toString();
        CommonFilter f = new CommonFilter().addWithin("id",
                StringUtils.commaDelimitedListToStringArray(idsToDelete));

        String rootFolderPath =
                this.systemConfigService.getStringConfig(this.getRootFolderPathKey());

        List<KnowledgeBase> list = this.knowledgeBaseService.list(f, null);
        for (KnowledgeBase a : list) {

            if (OnOffEnum.ON.value() == a.getIsDeleted()) {
                continue;
            }

            FileUtils.deleteQuietly(new File(rootFolderPath, a.getRelativePath()));
        }
    }

    /**
     * 删除，返回Json格式。一并删除对应的文件或文件夹，并级联删除所有下级。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "知识库", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);

        return super.delete(idsToDelete, paramMap, userBean, locale);
    }

    /**
     * 下载档案文件。只支持同级目录下的多个文件下载，如果需要支持文件夹下载，则打包压缩的部分，需要另行处理，把每个层级的文件夹名和文件名处理好。
     * 
     * @param idsToDownload
     * @param response
     * @param locale
     * @param userBean
     */
    @RequestMapping("/download")
    @ResponseBody
    public void download(@RequestParam String idsToDownload, HttpServletResponse response,
            Locale locale, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        try {

            String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToDownload);
            CommonFilter f = new CommonFilter().addWithin("id", idArray);

            List<KnowledgeBase> list = this.knowledgeBaseService.list(f, null);
            if (list.isEmpty()) {
                return;
            }

            String rootFolderPath = this.systemConfigService
                    .getStringConfig(SystemConfigKeys.KNOWLEDGE_BASE_ROOT_FOLDER_PATH);

            // 打包压缩
            String tempFolderPath = this.context.getRealPath("/temp");
            File downloadFile = new File(tempFolderPath, UUID.randomUUID().toString() + ".zip");

            List<SimpleEntry<String, String>> files = new ArrayList<>();
            Map<String, String> entryNameMap = new HashMap<>();

            for (KnowledgeBase k : list) {
                String path = rootFolderPath + k.getRelativePath();
                files.add(new SimpleEntry<>(path, ""));
                entryNameMap.put(path, k.getArchiveName());
            }

            ZipUtils.zip(files, entryNameMap, downloadFile.getAbsolutePath(), null);

            // 下载
            ControllerDownloadUtils.downloadFile(downloadFile, response);

            FileUtils.deleteQuietly(downloadFile);

        } catch (Exception e) {
            logger.error("Exception downloading archive file.", e);
        }
    }
}
