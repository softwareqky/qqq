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
import garage.origin.domain.business.UpdateFieldInfoDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.TreeNodeBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.AuthTypeEnum;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.ArchiveBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ArchiveAuthority;
import project.edge.domain.filter.ArchiveFilter;
import project.edge.domain.view.ArchiveBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.service.archive.ArchiveAuthorityService;
import project.edge.web.controller.common.SingleGridUploadController;

/**
 * 档案画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/project-archive")
public class ArchiveController extends SingleGridUploadController<Archive, ArchiveBean> {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveController.class);

    private static final String ARCHIVE_VIEW_NAME = "archive/archiveMain"; // 定制的JSP

    private static final String MODEL_KEY_ADD_FILE_FIELDS = "addFileFields"; // 上传文件画面的字段列表
    private static final String MODEL_KEY_ADD_FOLDER_FIELDS = "addFolderFields"; // 新建文件夹画面的字段列表
    private static final String MODEL_KEY_EDIT_INFO_FIELDS = "editInfoFields"; // 修改信息画面的字段列表

    private static final String URL_MAP_KEY_ADD_FILE = "add-file"; // 上传文件的URL
    private static final String URL_MAP_KEY_ADD_FOLDER = "add-folder"; // 新建文件夹的URL
    private static final String URL_MAP_KEY_DOWNLOAD = "download"; // 下载的URL
    private static final String URL_MAP_KEY_AUTH = "auth"; // 权限的URL
    private static final String URL_MAP_KEY_VERSION = "version"; // 版本的URL

    private static final String ID_MAP_KEY_ADD_FILE_DIALOG = "add-file-dialog"; // 上传文件Dialog的id
    private static final String ID_MAP_KEY_ADD_FOLDER_DIALOG = "add-folder-dialog"; // 新建文件夹Dialog的id
    private static final String ID_MAP_KEY_AUTH_DIALOG = "auth-dialog"; // 权限Dialog的id
    private static final String ID_MAP_KEY_VERSION_DIALOG = "version-dialog"; // 版本Dialog的id

    private static final String JS_MAP_KEY_OPEN_ADD_FILE = "open-add-file"; // 打开上传文件的JS
    private static final String JS_MAP_KEY_CON_ADD_FILE_SUBMIT = "con-add-file-submit"; // 连续新增文件提交的JS
    private static final String JS_MAP_KEY_ADD_FILE_SUBMIT = "add-file-submit"; // 新增文件提交的JS
    private static final String JS_MAP_KEY_OPEN_ADD_FOLDER = "open-add-folder"; // 打开新建文件夹的JS

    private static final String AUTH_DIALOG_ID = "%1$s-%2$s-AuthDialog";
    private static final String VERSION_DIALOG_ID = "%1$s-%2$s-VersionDialog";

    private static final String PID = "P9001";

    @Resource
    private ArchiveAuthorityService archiveAuthorityService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.ARCHIVE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Archive, String> getDataService() {
        return this.archiveService;
    }

    @Override
    protected ViewConverter<Archive, ArchiveBean> getViewConverter() {
        return new ArchiveBeanConverter();
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(ArchiveBean bean) {

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
    protected List<CommonFilter> getUniqueFilter(ArchiveBean bean, Archive oldEntity) {

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
        return ARCHIVE_VIEW_NAME;
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/archive/archiveJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/archive/archiveHidden.jsp";
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        String contextPath = this.context.getContextPath();
        urlMap.put(URL_MAP_KEY_ADD_FOLDER,
                contextPath + "/archive/project-archive/add-folder.json");
        urlMap.put(URL_MAP_KEY_ADD_FILE, contextPath + "/archive/project-archive/add-file.json");
        urlMap.put(URL_MAP_KEY_DOWNLOAD, contextPath + "/archive/project-archive/download.json");
        urlMap.put(URL_MAP_KEY_AUTH, contextPath + "/archive/archive-auth/main.htm");
        urlMap.put(URL_MAP_KEY_VERSION, contextPath + "/archive/archive-version/main.htm");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_ADD_FILE_DIALOG,
                String.format("%1$s-%2$s-AddFileDialog", this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_ADD_FOLDER_DIALOG,
                String.format("%1$s-%2$s-AddFolderDialog", this.getPageId(), this.getDataType()));

        // 权限
        idMap.put(ID_MAP_KEY_AUTH_DIALOG,
                String.format(AUTH_DIALOG_ID, this.getPageId(), this.getDataType()));

        // 版本
        idMap.put(ID_MAP_KEY_VERSION_DIALOG,
                String.format(VERSION_DIALOG_ID, this.getPageId(), this.getDataType()));

        return idMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 检索
        jsMap.put(ControllerJsMapKeys.SEARCH,
                String.format("ARCHIVE.searchSimple('#%1$s', '#%2$s');",
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
                String.format("ARCHIVE.batchDeleteSelected('%1$s', '#%2$s');",
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
                String.format("ARCHIVE.download('%1$s', '#%2$s');",
                        urlMap.get(URL_MAP_KEY_DOWNLOAD),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 分配权限
        jsMap.put(ControllerJsMapKeys.OPEN_AUTHORITY, "ARCHIVE.openAuth()");

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
        archiveNameField.setId("ARCHIVE.archiveName");
        archiveNameField.setFieldName("archiveName");
        archiveNameField.setFieldNameView("archiveName");
        archiveNameField.setCaptionName(
                this.messageSource.getMessage("ui.fields.archive.name", null, locale));
        archiveNameField.setMandatory(true);
        archiveNameField.setWidget("textbox");
        archiveNameField.setValueType(FieldValueType.STRING);
        archiveNameField.setMaxLength("50");
        archiveNameField.setCustomValidType("filename");
        archiveNameField.setValidationPrompt(
                this.messageSource.getMessage("ui.common.maxlength", null, locale) + " 50");

        // 文件内容
        FieldBean archiveContentField = new FieldBean();
        archiveContentField.setId("ARCHIVE.archiveContent");
        archiveContentField.setFieldName("archiveContent");
        archiveContentField.setFieldNameView("archiveContent");
        archiveContentField.setCaptionName(
                this.messageSource.getMessage("ui.fields.archive.content", null, locale));
        archiveContentField.setMandatory(true);
        archiveContentField.setWidget("filebox");
        archiveContentField.setMultiLine(true);
        archiveContentField.setValueType(FieldValueType.STRING);

        // 文件概述
        FieldBean remarkField = new FieldBean();
        remarkField.setId("ARCHIVE.remark");
        remarkField.setFieldName("remark");
        remarkField.setFieldNameView("remark");
        remarkField.setCaptionName(
                this.messageSource.getMessage("ui.fields.archive.remark", null, locale));
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
     * @param userBean
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
            @RequestParam(required = false, defaultValue = "asc") String order,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        /**
         * 0. 一览检索时，一共只对两个字段进行过滤，其一是文件名/备注(共用一个字段)，其二是所在目录(pid)。
         * 对文件名和文件概述(备注)进行模糊检索时要特殊处理，如果检索文件名或备注，则一定对所检索的目标文件夹做限制(递归ArchiveFilter.pidPath)；
         * 如果没有对文件和备注检索，则只需要对一览的所在目录做限制(pid)
         */
        String targetPid = null; // 目标上级节点id
        boolean isTargetPnodeRoot = false; // 目标上级节点是否根节点

        // 筛选未删除且是当前版本的档案文件
        ArchiveFilter afilter = new ArchiveFilter();
        afilter.addExact("isDeleted", OnOffEnum.OFF.value());
        afilter.addNull("versionOf");

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
                    afilter.addExact("pid", Constants.SLASH); // [t_archive]的默认值，单根节点/，是level=0的唯一根节点
                    targetPid = Constants.SLASH;

                } else {
                    if (StringUtils.isEmpty(pidField.getValue())) {
                        pidField.setValue(Constants.SLASH);
                    }
                    afilter.getFilterFieldList().add(pidField);
                    targetPid = pidField.getValue().toString();
                }
            } else {
                afilter.setFileInfo(archiveNameField.getValue().toString()); // 对文件名或备注做模糊查找

                // 如果指定了目标节点，则查找其所有下属节点，否则不做限制，则查找整个树
                if (pidField == null) {
                    targetPid = Constants.SLASH;
                } else {
                    if (StringUtils.isEmpty(pidField.getValue())) {
                        pidField.setValue(Constants.SLASH);
                    }
                    targetPid = pidField.getValue().toString();
                }
                afilter.setPidPath(targetPid);
            }
            isTargetPnodeRoot = Constants.SLASH.equals(targetPid);

        } catch (Exception e) {
            this.getLogger().error("Exception parsing filter.", e);

            // 在JSON对象内设定服务器处理结果状态
            JsonResultBean jsonResult = new JsonResultBean();
            jsonResult.setStatus(JsonStatus.ERROR);
            return jsonResult;
        }

        // 1. 调用父类的list，并获取上级节点
        sort = "isDir," + sort;
        order = OrderByDto.DESC + Constants.COMMA + order;
        JsonResultBean jsonResult = super.list(null, afilter, page, rows, sort, order, locale);

        try {
            Archive pNode = this.archiveService.find(targetPid);

            // 2. 准备面包屑路径
            String queryArchiveName =
                    archiveNameField != null ? archiveNameField.getValue().toString() : null;
            this.doBreadcrumbList(pNode, isTargetPnodeRoot, queryArchiveName, jsonResult, locale);

            // 3. 处理权限，所有文件夹都有浏览(list)权限，因此所有的文件夹都会显示，但是不显示无访问权限的文件
            List<ArchiveBean> beanList = this.doAuth(pNode, jsonResult, userBean);

            // 4. 如果对文件名或文件概述(备注)进行了检索，则填充文件的pname
            if (!StringUtils.isEmpty(queryArchiveName)) {
                this.doPname(beanList, locale);
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
     * 准备面包屑路径。
     * 
     * @param pNode
     * @param isTargetPnodeRoot
     * @param queryArchiveName
     * @param jsonResult
     * @param locale
     */
    private void doBreadcrumbList(Archive pNode, boolean isTargetPnodeRoot, String queryArchiveName,
            JsonResultBean jsonResult, Locale locale) {

        List<ComboboxOptionBean> breadcrumbList = new ArrayList<>();

        // 2.1 根节点"全部文件"
        breadcrumbList.add(new ComboboxOptionBean(Constants.SLASH,
                this.messageSource.getMessage("ui.common.all.files", null, locale)));

        // 2.2 pid不是根节点
        if (!isTargetPnodeRoot) {

            // 父节点的上层节点
            if (!StringUtils.isEmpty(pNode.getPath())) {

                // 去掉第0层的根节点
                String[] idArray = StringUtils.commaDelimitedListToStringArray(pNode.getPath());
                CommonFilter pnFilter = new CommonFilter().addWithin("id", idArray)
                        .addRange("level", FieldValueType.INT, 0, null, true, false);

                List<Archive> pathNodeList = this.archiveService.list(pnFilter,
                        Collections.singletonList(new OrderByDto("level")));
                for (Archive node : pathNodeList) {
                    String nodeName = node.getArchiveName();
                    if (nodeName.length() > 10) {
                        nodeName = nodeName.substring(0, 9) + "...";
                    }
                    breadcrumbList.add(new ComboboxOptionBean(node.getId(), nodeName));
                }
            }

            String pName = pNode.getArchiveName();
            if (pName.length() > 10) {
                pName = pName.substring(0, 9) + "...";
            }

            breadcrumbList.add(new ComboboxOptionBean(pNode.getId(), pName));
        }

        // 2.3 检索了文件名或备注
        if (!StringUtils.isEmpty(queryArchiveName)) {
            // 将 检索: "xxx" 作为最后一个路径
            breadcrumbList.add(new ComboboxOptionBean("",
                    this.messageSource.getMessage("ui.common.search", null, locale)
                            + Constants.COLON + " \"" + queryArchiveName + "\""));
        }

        jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, breadcrumbList);
    }

    /**
     * 处理权限，所有文件夹都有浏览(list)权限，因此所有的文件夹都会显示，但是不显示无访问权限的文件。
     * 
     * @param pNode
     * @param jsonResult
     * @param userBean
     * @return
     */
    private List<ArchiveBean> doAuth(Archive pNode, JsonResultBean jsonResult,
            SessionUserBean userBean) {


        // 3.1 获取从根节点到每个节点的路径节点id，去重
        Map<String, String> nodeIdMap = new HashMap<>();

        nodeIdMap.put(pNode.getId(), pNode.getId());
        String pNodepath = pNode.getPath();
        if (!StringUtils.isEmpty(pNodepath)) {
            String[] idArray = StringUtils.commaDelimitedListToStringArray(pNodepath);
            for (String id : idArray) {
                nodeIdMap.put(id, id);
            }
        }

        @SuppressWarnings("unchecked")
        List<ArchiveBean> list =
                (List<ArchiveBean>) jsonResult.getDataMap().get(JsonResultBean.KEY_EASYUI_ROWS);
        for (ArchiveBean bean : list) {
            nodeIdMap.put(bean.getId(), bean.getId());

            String path = bean.getPath();
            if (!StringUtils.isEmpty(path)) {
                String[] idArray = StringUtils.commaDelimitedListToStringArray(path);
                for (String id : idArray) {
                    nodeIdMap.put(id, id);
                }
            }
        }

        // 3.2 获取所有设定了权限的节点的权限
        Map<String, ArchiveAuthority> map = new HashMap<>(); // key: Archive.id
        CommonFilter authFilter =
                new CommonFilter().addExact("person.id", userBean.getSessionUserId())
                        .addWithin("archive.id", nodeIdMap.values());
        List<ArchiveAuthority> authList = this.archiveAuthorityService.list(authFilter, null);
        for (ArchiveAuthority auth : authList) {
            map.put(auth.getArchive().getId(), auth);
        }

        // 3.3 计算目标上级节点的权限
        int targetPnodeAuth = this.calcNodeAuth(pNode, map);
        jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_VALUE, targetPnodeAuth);

        // 3.4 计算各个子节点的权限，在最终的beanList中，文件夹节点无论权限都要包含，文件节点则去掉那些无访问权限的
        List<ArchiveBean> beanList = new ArrayList<>();
        for (ArchiveBean bean : list) {
            int nodeAuth = this.calcNodeAuth(bean, map);
            bean.setAuthType(nodeAuth);
            if (nodeAuth != AuthTypeEnum.NO_ACCESS.value()
                    || bean.getIsDir() == OnOffEnum.ON.value()) {
                beanList.add(bean);
            }
        }

        jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, beanList);
        jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, beanList.size());

        return beanList;
    }

    /**
     * 计算指定节点的权限。根据继承关系，在文件夹-文件层级树中，按照从指定节点向根节点的方向，确定首个指定的权限。
     * 
     * 如果从叶节点到根节点，整个路径上都没有任何指定了权限的节点，则视为无访问权限。
     * 
     * @param node
     * @param map
     * @return
     */
    private int calcNodeAuth(Archive node, Map<String, ArchiveAuthority> map) {

        String id = node.getId();
        if (map.containsKey(id)) {
            ArchiveAuthority archiveAuth = map.get(id);
            return archiveAuth.getAuthType();
        }

        String path = node.getPath();
        if (!StringUtils.isEmpty(path)) {
            String[] idArray = StringUtils.commaDelimitedListToStringArray(path);
            // 顺序是从叶节点向根节点
            for (int i = idArray.length - 1; i >= 0; i--) {
                String nid = idArray[i];
                if (map.containsKey(nid)) {
                    ArchiveAuthority archiveAuth = map.get(nid);
                    return archiveAuth.getAuthType();
                }
            }
        }

        return AuthTypeEnum.NO_ACCESS.value();
    }

    /**
     * 计算指定节点的权限。根据继承关系，在文件夹-文件层级树中，按照从指定节点向根节点的方向，确定首个指定的权限。
     * 
     * 如果从叶节点到根节点，整个路径上都没有任何指定了权限的节点，则视为无访问权限。
     * 
     * @param node
     * @param map
     * @return
     */
    private int calcNodeAuth(ArchiveBean node, Map<String, ArchiveAuthority> map) {

        String id = node.getId();
        if (map.containsKey(id)) {
            ArchiveAuthority archiveAuth = map.get(id);
            return archiveAuth.getAuthType();
        }

        String path = node.getPath();
        if (!StringUtils.isEmpty(path)) {
            String[] idArray = StringUtils.commaDelimitedListToStringArray(path);
            // 顺序是从叶节点向根节点
            for (int i = idArray.length - 1; i >= 0; i--) {
                String nid = idArray[i];
                if (map.containsKey(nid)) {
                    ArchiveAuthority archiveAuth = map.get(nid);
                    return archiveAuth.getAuthType();
                }
            }
        }

        return AuthTypeEnum.NO_ACCESS.value();
    }

    /**
     * 填充文件的上级节点名pname。
     * 
     * @param beanList
     * @param locale
     */
    private void doPname(List<ArchiveBean> beanList, Locale locale) {

        List<String> pidList = new ArrayList<>();
        for (ArchiveBean bean : beanList) {
            String pid = bean.getPid();
            if (!StringUtils.isEmpty(pid)) {
                pidList.add(pid);
            }
        }

        List<Archive> plist =
                this.archiveService.list(new CommonFilter().addWithin("id", pidList), null);
        Map<String, Archive> pmap = new HashMap<>();
        for (Archive p : plist) {
            pmap.put(p.getId(), p);
        }

        for (ArchiveBean bean : beanList) {
            String pid = bean.getPid();
            if (!Constants.SLASH.equals(pid)) {
                bean.setPname(pmap.get(pid).getArchiveName());
            } else {
                bean.setPname(this.messageSource.getMessage("ui.common.all.files", null, locale));
            }
        }
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
    public JsonResultBean find(@RequestParam String id,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = super.find(id, locale);

        ArchiveBean bean =
                (ArchiveBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);

        // 获取权限
        List<String> idList = new ArrayList<>();

        idList.add(bean.getId());
        String path = bean.getPath();
        if (!StringUtils.isEmpty(path)) {
            String[] idArray = StringUtils.commaDelimitedListToStringArray(path);
            for (int i = idArray.length - 1; i >= 0; i--) {
                idList.add(idArray[i]);
            }
        }

        Map<String, ArchiveAuthority> map = new HashMap<>(); // key: Archive.id
        CommonFilter f = new CommonFilter().addExact("person.id", userBean.getSessionUserId())
                .addWithin("archive.id", idList);
        List<ArchiveAuthority> authList = this.archiveAuthorityService.list(f, null);
        for (ArchiveAuthority auth : authList) {
            map.put(auth.getArchive().getId(), auth);
        }

        for (String nodeId : idList) {
            if (map.containsKey(nodeId)) {
                bean.setAuthType(map.get(nodeId).getAuthType());
                break;
            }
        }
        // 循环之后，如果没有找到设定的权限，则bean.authType默认为0，即无访问权限

        return jsonResult;
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
    public JsonResultBean createFolder(@ModelAttribute ArchiveBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        bean.setIsDir(OnOffEnum.ON.value());
        return super.create(bean, null, userBean, locale);
    }

    /**
     * 根据pid获取此文件(夹)所在目录，然后设置Archive的relativePath，level和path。
     */
    @Override
    protected void beforeCreate(Archive entity, ArchiveBean bean, Map<String, Object> paramMap)
            throws Exception {

        String pid = entity.getPid();
        Archive p = this.archiveService.find(pid);

        String relativePath = p.getRelativePath() + File.separator + entity.getId();
        int level = p.getLevel() + 1;

        String path = null;
        if (StringUtils.isEmpty(p.getPath())) {
            path = p.getId();
        } else {
            path = p.getPath() + Constants.COMMA + p.getId();
        }

        entity.setLevel(level);
        entity.setPath(path);
        entity.setRelativePath(relativePath);

        // 在保存文件前，重新设置bean的id和relativePath，以便在postCreate中支持同名文件覆盖原文件(后改为不覆盖，而是保留版本)
        bean.setId(entity.getId());
        bean.setRelativePath(entity.getRelativePath());

        // 以下bean的字段在后续步骤中并未用到，为保持一致性，也一并设置
        bean.setLevel(entity.getLevel());
        bean.setPath(entity.getPath());
    }

    @Override
    protected void postCreate(Archive entity, ArchiveBean bean, Map<String, Object> paramMap,
            Locale locale) throws IOException {

        super.postCreate(entity, bean, paramMap, locale);
    }

    /**
     * 新建文件，返回Json格式。与普通的新建相比，需要额外处理两块内容，其一是处理上传的附件，包括解析和保存到文件系统，另外需要处理文件的上下层级关系。
     * 
     * 关于上传的附件，解析工作在SingleGridUploadController.parseMultipart完成，并在postCreate中保存到文件系统。文件的层级关系，在beforeCreate处理。
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
            @ModelAttribute ArchiveBean b,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        List<ArchiveBean> beanList = new ArrayList<>();
        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archiveContent", beanList);

        Map<String, Object> paramMap = new HashMap<>();

        JsonResultBean jsonResult =
                this.parseMultipart(request, response, paramMap, fieldNameArchiveListMap);
        if (jsonResult.getStatus() != JsonStatus.ERROR) {
            try {

                List<Archive> entityList = new ArrayList<>();
                Map<String, String> nameMap = new HashMap<>(); // key: archiveName, value: id

                for (ArchiveBean bean : beanList) {

                    // 创建数据，所有的文件都共用相同的pid和remark
                    bean.setPid(b.getPid());
                    bean.setRemark(b.getRemark());

                    Archive entity =
                            this.getViewConverter().toEntity(bean, null, userBean, new Date());

                    this.beforeCreate(entity, bean, paramMap);

                    entityList.add(entity);
                    nameMap.put(entity.getArchiveName(), entity.getId());
                }

                List<SimpleEntry<String, String>> pairList = new ArrayList<>(); // old id : new id

                // 同目录下的同名文件，判断是否有权覆盖，以及版本处理
                CommonFilter f = new CommonFilter().addExact("pid", b.getPid())
                        .addWithin("archiveName", nameMap.keySet())
                        .addExact("isDir", OnOffEnum.OFF.value()).addNull("versionOf");
                List<Archive> dblist = this.archiveService.list(f, null);
                for (Archive dbArchive : dblist) {

                    // TODO 同名的文件覆盖时，需要进一步判断权限，是否所有同名文件都有写权限
                    // hint: this.calcNodeAuth(...)

                    String name = dbArchive.getArchiveName();
                    if (nameMap.containsKey(name)) {
                        pairList.add(new SimpleEntry<String, String>(dbArchive.getId(),
                                nameMap.get(name)));
                    }
                }

                this.archiveService.createAndUpdateVersionOf(entityList, pairList);

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

        try

        {
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
    @SysLogAnnotation(description = "项目档案", action = "更新")
    public JsonResultBean update(@ModelAttribute ArchiveBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
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
    @SysLogAnnotation(description = "项目档案", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 删除时特别处理：文件夹物理删除，文件逻辑删除
        JsonResultBean jsonResult = new JsonResultBean();
        try {
            List<String> idUpdateList = new ArrayList<>();
            List<String> idDeleteList = new ArrayList<>();

            CommonFilter f = new CommonFilter().addWithin("id",
                    StringUtils.commaDelimitedListToStringArray(idsToDelete));
            List<Archive> list = this.archiveService.list(f, null);
            for (Archive a : list) {
                if (OnOffEnum.ON.value() == a.getIsDir()) {
                    idDeleteList.add(a.getId());
                } else {
                    idUpdateList.add(a.getId());
                }
            }

            // 文件类型的档案，进行逻辑删除
            if (!idUpdateList.isEmpty()) {

                UpdateFieldInfoDto field = new UpdateFieldInfoDto("isDeleted", OnOffEnum.ON.value(),
                        FieldValueType.SHORT);
                UpdateFieldInfoDto field_mDatetime =
                        new UpdateFieldInfoDto("mDatetime", new Date(), FieldValueType.DATE);
                UpdateFieldInfoDto field_modifier = new UpdateFieldInfoDto("modifier",
                        userBean.getSessionUserId(), FieldValueType.STRING);

                List<UpdateFieldInfoDto> flist = new ArrayList<>();
                flist.add(field);
                flist.add(field_mDatetime);
                flist.add(field_modifier);

                this.archiveService.update(flist, idUpdateList);
            }

            if (idDeleteList.isEmpty()) {
                // 准备JSON结果
                jsonResult.setStatus(JsonStatus.OK);
                jsonResult.setMessage(this.messageSource.getMessage("message.info.record.delete.ok",
                        null, locale));
                return jsonResult;
            }

            // 重新设置idsToDelete，仅包含文件夹类型的档案id，准备进行物理删除
            idsToDelete = StringUtils.collectionToCommaDelimitedString(idDeleteList);

        } catch (Exception e) {
            this.getLogger().error("Exception deleting the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
            return jsonResult;
        }

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

            List<Archive> list = this.archiveService.list(f, null);
            if (list.isEmpty()) {
                return;
            }

            String rootFolderPath = this.systemConfigService
                    .getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

            // 打包压缩
            String tempFolderPath = this.context.getRealPath("/temp");
            File downloadFile = new File(tempFolderPath, UUID.randomUUID().toString() + ".zip");

            List<SimpleEntry<String, String>> files = new ArrayList<>();
            Map<String, String> entryNameMap = new HashMap<>();

            for (Archive a : list) {
                String path = rootFolderPath + a.getRelativePath();
                files.add(new SimpleEntry<>(path, ""));
                entryNameMap.put(path, a.getArchiveName());
            }

            ZipUtils.zip(files, entryNameMap, downloadFile.getAbsolutePath(), null);

            // 下载
            ControllerDownloadUtils.downloadFile(downloadFile, response);

            FileUtils.deleteQuietly(downloadFile);

        } catch (Exception e) {
            logger.error("Exception downloading archive file.", e);
        }
    }

    /**
     * 获取画面左侧档案文件夹-文件层级树显示的数据，返回JSON格式。
     * 
     * @param id 上级节点id
     * @param locale
     * @return
     */
    @RequestMapping("/tree-side")
    @ResponseBody
    public JsonResultBean getTreeSide(@RequestParam(required = false) String id, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            CommonFilter filter = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value())
                    .addNull("versionOf");
            if (!StringUtils.isEmpty(id)) {
                // 如果是展开某个节点，则获取该节点的直接子节点
                filter.addExact("pid", id);
            } else {
                // 如果是加载全部，则只加载从root开始最高的两层节点，其他节点在点击时再实时加载展开
                filter.addRange("level", FieldValueType.SHORT, 0, 1);
            }

            List<OrderByDto> orderList = new ArrayList<>();
            orderList.add(new OrderByDto("level"));
            orderList.add(new OrderByDto("isDir", true));
            orderList.add(new OrderByDto("archiveName"));
            List<Archive> list = this.archiveService.list(filter, orderList);

            // 借助map来转换并构建树形结构
            Map<String, TreeNodeBean> map = new HashMap<>(); // key: id
            List<TreeNodeBean> resultList = new ArrayList<>();

            for (Archive a : list) {
                TreeNodeBean n = new TreeNodeBean();
                n.setId(a.getId());
                n.setPid(a.getPid());
                n.setText(a.getArchiveName());

                if (OnOffEnum.ON.value() == a.getIsDir()) {
                    n.setIconCls("tree-icon-fa fa fa-fw fa-folder-o");
                } else {
                    n.setIconCls("tree-icon-fa fa fa-fw fa-file-o");
                }

                Map<String, Object> attr = new HashMap<>();
                attr.put("fieldName", "archive_"); // 检索信息中的字段名
                n.setAttributes(attr);

                if (map.containsKey(n.getPid())) {

                    // 只有加载全部时，level=1的节点会到达这里

                    if (OnOffEnum.ON.value() == a.getIsDir()) {
                        n.setState("closed");
                    }

                    TreeNodeBean p = map.get(n.getPid());
                    p.getChildren().add(n);
                    p.setState("open");

                } else {

                    // 在加载全部时的根节点和展开某个节点时，都会到达这里

                    if (OnOffEnum.ON.value() == a.getIsDir()) {
                        n.setState("closed");
                    }
                    resultList.add(n);
                }

                map.put(n.getId(), n);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);


        } catch (Exception e) {
            this.getLogger().error("Exception side-listing the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

}
