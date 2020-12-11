package project.edge.web.controller.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

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
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ProjectRoleBeanConverter;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.view.ProjectRoleBean;
import project.edge.service.project.ProjectRoleService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 项目角色画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/role")
public class ProjectRoleController extends SingleGridController<ProjectRole, ProjectRoleBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectRoleController.class);

    private static final String PID = "P2050";

    @Resource
    private ProjectRoleService projectRoleService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PROJECT_ROLE.value();
    }

    @Override
    protected Service<ProjectRole, String> getDataService() {
        return this.projectRoleService;
    }
    
    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/project/projectRoleJs.jsp";
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<ProjectRole, ProjectRoleBean> getViewConverter() {
        return new ProjectRoleBeanConverter();
    }
    
    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', PROJECT_ROLE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT, String.format(
                "CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, PROJECT_ROLE);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                urlMap.get(ControllerUrlMapKeys.FIND),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 连续新建
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, PROJECT_ROLE);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
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
        
        CommonFilter f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());
        JsonResultBean jsonResult =
                super.list(commonFilterJson, f, page, rows, sort, order, locale);

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
        //return super.find(id, locale);
        JsonResultBean jsonResult = super.find(id, locale);
        try {
            ProjectRoleBean bean =
                    (ProjectRoleBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);

            // 获取上级虚拟组织下拉框的option，以便修改弹出画面上设置
            // 获取数据
            List<ProjectRole> list = this.projectRoleService.list(null, null);
            List<ComboboxOptionBean> resultList = new ArrayList<>();

            for (ProjectRole entity : list) {

                // 过滤掉自身
                if (bean.getId().equals(entity.getId())) {
                    continue;
                }
                resultList.add(new ComboboxOptionBean(entity.getId(), entity.getProjectRoleName()));
            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);


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
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @SysLogAnnotation(description = "项目角色", action = "新增")
    public JsonResultBean create(@ModelAttribute ProjectRoleBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
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
    @SysLogAnnotation(description = "项目角色", action = "更新")
    public JsonResultBean update(@ModelAttribute ProjectRoleBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
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
    @SysLogAnnotation(description = "项目角色", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        
        
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    /**
     * 获取指定项目的虚拟组织列表，用作新建时上级虚拟组织的下拉框内容。
     * 
     * @param project_ 所属的项目id
     * @param locale
     * @return
     */
    @RequestMapping("/list-pid-options")
    @ResponseBody
    public JsonResultBean listPidOptions(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            List<ProjectRole> list = this.projectRoleService.list(null, null);
            List<ComboboxOptionBean> projectRoleOptionsList = new ArrayList<>();
            // String format = "(%1$s) %2$s";

            for (ProjectRole v : list) {
                projectRoleOptionsList.add(new ComboboxOptionBean(v.getId(), v.getProjectRoleName()));
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, projectRoleOptionsList);

        } catch (Exception e) {
            this.getLogger().error("Exception listing the options of " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

}
