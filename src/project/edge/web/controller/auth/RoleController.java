package project.edge.web.controller.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

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

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.TreeNodeBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.RoleBeanConverter;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Role;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.RoleBean;
import project.edge.service.auth.RoleService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 角色管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/auth/role")
public class RoleController extends SingleGridController<Role, RoleBean> {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private static final String PID = "P18001";

    private static final String MODEL_KEY_ADD_ROLE_FIELDS = "addRoleFields"; // 新增角色对应的fields列表
    private static final String MODEL_KEY_EDIT_ROLE_FIELDS = "editRoleFields"; // 修改角色对应的fields列表
    private static final String MODEL_KEY_VIEW_ROLE_FIELDS = "viewRoleFields"; // 查看角色对应的fields列表

    @Resource
    private RoleService roleService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.ROLE.value();
    }

    @Override
    protected Service<Role, String> getDataService() {
        return this.roleService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<Role, RoleBean> getViewConverter() {
        return new RoleBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/auth/roleJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/auth/roleHidden.jsp";
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(RoleBean bean) {
        List<CommonFilter> list = new ArrayList<>();
        CommonFilter filter = new CommonFilter().addExact("roleName", bean.getRoleName());
        list.add(filter);
        return list;
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(RoleBean bean, Role oldEntity) {
        if (!bean.getRoleName().equals(oldEntity.getRoleName())) {
            return this.getUniqueFilter(bean);
        }
        return new ArrayList<>();
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面，以及连续新建保存时，增加回调，刷新page树的内容
        // 新建和修改保存时，增加回调，处理page树的选中

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', ROLE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 新建保存
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, ROLE);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 连续新建
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, ROLE);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
                String.format("CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, ROLE);",
                        idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 修改保存
        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, ROLE);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开查看
        jsMap.put(ControllerJsMapKeys.OPEN_VIEW,
                String.format("CrudUtils.openViewDialog('#%1$s', '%2$s', '#%3$s', ROLE);",
                        idMap.get(ControllerIdMapKeys.VIEW_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

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
    @SuppressWarnings("unchecked")
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        String viewName = super.main(paramMap, model, userBean, locale);

        // 将默认的新增修改用的字段移除，重新添加字段Map，这样就不会生成默认的新建/修改弹出框
        Map<String, Object> modelMap = model.asMap();

        ArrayList<FieldBean> addFields =
                (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.ADD_FIELDS);
        ArrayList<FieldBean> editFields =
                (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.EDIT_FIELDS);
        ArrayList<FieldBean> viewFields =
                (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.VIEW_FIELDS);
        modelMap.remove(ControllerModelKeys.ADD_FIELDS);
        modelMap.remove(ControllerModelKeys.EDIT_FIELDS);
        modelMap.remove(ControllerModelKeys.VIEW_FIELDS);

        model.addAttribute(MODEL_KEY_ADD_ROLE_FIELDS, addFields);
        model.addAttribute(MODEL_KEY_EDIT_ROLE_FIELDS, editFields);
        model.addAttribute(MODEL_KEY_VIEW_ROLE_FIELDS, viewFields);

        // Datagrid行双击事件，增加回调，用来刷新项目集下拉框的内容
        model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER, "ROLE.handleDblClickRow");

        return viewName;
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

        // 不显示系统角色
        CommonFilter filter = new CommonFilter().addExact("isSystem", OnOffEnum.OFF.value());
        return super.list(commonFilterJson, filter, page, rows, sort, order, locale);
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
    @SysLogAnnotation(description = "角色管理", action = "新增")
    public JsonResultBean create(@ModelAttribute RoleBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        this.setRolePages(bean);
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
    @SysLogAnnotation(description = "角色管理", action = "更新")
    public JsonResultBean update(@ModelAttribute RoleBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        this.setRolePages(bean);
        return super.update(bean, null, userBean, locale);
    }

    private void setRolePages(RoleBean bean) {

        // 角色赋权限时需将查询、返回等默认菜单加入
        CommonFilter filter = new CommonFilter().addExact("isFunction", OnOffEnum.ON.value())
                .addExact("isEnabled", OnOffEnum.ON.value())
                .addExact("isCommonVisible", OnOffEnum.ON.value())
                .addExact("isRoleVisible", OnOffEnum.OFF.value())
                .addWithin("pid", bean.getPageIdList());
        List<Page> pageList = this.pageService.list(filter, null);

        for (Page page : pageList) {
            if (page.getId().endsWith(ControllerFunctionIds.SEARCH)
                    || page.getId().endsWith(ControllerFunctionIds.SEARCH_MORE)
                    || page.getId().endsWith(ControllerFunctionIds.CLEAR_SEARCH)) {
                bean.getPageIdList().add(page.getId());
            }
        }
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
    @SysLogAnnotation(description = "角色管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

    /**
     * 根据角色的id，获取对应的page树的数据，返回JSON格式。
     * 
     * @param roleId 角色id，可以为null(新建角色时)
     * @param locale
     * @return
     */
    @RequestMapping("/page-tree")
    @ResponseBody
    public JsonResultBean pageTree(@RequestParam(required = false, defaultValue = "") String roleId,
            Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 获取角色对应的page，如果有的话
            Map<String, String> rolePageIdMap = new HashMap<String, String>();
            if (!StringUtils.isEmpty(roleId)) {
                Role role = this.roleService.find(roleId);
                if (role != null) {
                    for (Page page : role.getPages()) {
                        rolePageIdMap.put(page.getId(), page.getId());
                    }
                }
            }

            // 获取所有可自定义的page，检索/清空检索/高级检索的isRoleVisible等于1，因此这里并不会查询获取
            PageFilter filter = new PageFilter();
            filter.addExact("isEnabled", OnOffEnum.ON.value())
                    .addExact("isCommonVisible", OnOffEnum.ON.value())
                    .addExact("isRoleVisible", OnOffEnum.ON.value());

            List<Page> pages = this.pageService.list(filter, null); // 使用默认排序

            // 借助map来转换并构建树形结构
            Map<String, TreeNodeBean> nodeMap = new HashMap<>(); // 非功能节点加入map，key是pageId
            List<TreeNodeBean> resultList = new ArrayList<>();

            // 单根"全部"
            TreeNodeBean root = new TreeNodeBean();
            root.setId(Constants.ALL);
            root.setText(this.messageSource.getMessage("ui.common.all", null, locale));
            resultList.add(root);

            List<String> checkedLeaves = new ArrayList<String>(); // 画面叶节点，便于找出那些选中的但没有功能节点的画面叶节点

            for (Page page : pages) {
                TreeNodeBean node = new TreeNodeBean();
                node.setId(page.getId());

                String text = this.messageSource.getMessage(page.getPageName(), null, locale);
                node.setText(text);

                // easyui中，选中某个节点后，所有其下级节点会自动选中，因此这里只设定叶节点的选中
                if (rolePageIdMap.containsKey(page.getId())) {
                    if (OnOffEnum.ON.value().equals(page.getIsFunction())) {
                        node.setChecked(true);
                    } else if (OnOffEnum.ON.value().equals(page.getIsLeafPage())) {
                        checkedLeaves.add(page.getId());
                    }
                }

                String pid = page.getPid();
                if (StringUtils.isEmpty(pid) || !nodeMap.containsKey(pid)) {
                    // 没有直接父节点，则作为顶层node
                    root.getChildren().add(node);
                } else {
                    nodeMap.get(pid).getChildren().add(node);
                }

                // 非功能节点加入map
                if (OnOffEnum.OFF.value().equals(page.getIsFunction())) {
                    nodeMap.put(page.getId(), node);
                }
            }

            // 选中没有功能节点的画面叶节点
            for (String leaf : checkedLeaves) {
                TreeNodeBean l = nodeMap.get(leaf);
                if (l.getChildren().isEmpty()) {
                    l.setChecked(true);
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);

        } catch (Exception e) {
            logger.error("Exception while getting the page tree.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
