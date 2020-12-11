/**
 * 
 */
package project.edge.web.controller.hr;

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
import project.edge.domain.converter.PostBeanConverter;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Post;
import project.edge.domain.view.PostBean;
import project.edge.service.hr.PostService;
import project.edge.service.org.DeptService;
import project.edge.service.org.OrgService;
import project.edge.web.controller.common.TreeGridController;


/**
 * 岗位管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/hr/post")
public class PostController extends TreeGridController<Post, PostBean> {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private static final String PID = "P17001";

    private static final String MODEL_KEY_DBL_CLICK_ROW_HANDLER = "dblClickRowHandler";

    @Resource
    private OrgService orgService;

    @Resource
    private DeptService deptService;

    @Resource
    private PostService postService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.POST.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Post, String> getDataService() {
        return this.postService;
    }

    @Override
    protected ViewConverter<Post, PostBean> getViewConverter() {
        return new PostBeanConverter();
    }

    @Override
    protected String getSideDataType() {
        return DataTypeEnum.DEPT.value();
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

        List<ComboboxOptionBean> optOrgs = new ArrayList<>();
        // String format = "(%1$s) %2$s";
        List<Org> orgList = this.orgService.list(null, null);
        if (orgList != null) {
            for (Org o : orgList) {
                optOrgs.add(new ComboboxOptionBean(o.getId(), o.getOrgName()));
            }
        }
        optionMap.put("orgOptions", optOrgs);

        List<ComboboxOptionBean> deptOptions = new ArrayList<>();
        List<Dept> deptList = this.deptService.list(null, null);
        if (deptList != null) {
            for (Dept dp : deptList) {
                deptOptions.add(new ComboboxOptionBean(dp.getId(), dp.getDeptName()));
            }
        }
        optionMap.put("deptOptions", deptOptions);

        return optionMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面时，增加回调，处理省市下拉框联动

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', OrgDeptUtils);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT, String.format(
                "CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, OrgDeptUtils);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                urlMap.get(ControllerUrlMapKeys.FIND),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 连续新建
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, OrgDeptUtils);",
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

        model.addAttribute(MODEL_KEY_DBL_CLICK_ROW_HANDLER, "OrgDeptUtils.handleDblClickRow");
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
    @SysLogAnnotation(description = "岗位管理", action = "新增")
    public JsonResultBean create(@ModelAttribute PostBean bean,
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
    @SysLogAnnotation(description = "岗位管理", action = "更新")
    public JsonResultBean update(@ModelAttribute PostBean bean,
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
    @SysLogAnnotation(description = "岗位管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

    /**
     * 根据组织id,用组织下拉框选择后，联动部门的值变更。
     * 
     * @param orgId
     * @param locale
     * @return
     */
    @RequestMapping("/list-dept-type")
    @ResponseBody
    public JsonResultBean getDeptTypeDeptList(@RequestParam String orgId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            if (StringUtils.isEmpty(orgId)) {

                this.getLogger().error("the orgId is empty !");
                jsonResult.setStatus(JsonStatus.ERROR);
                return jsonResult;
            }

            CommonFilter filter = new CommonFilter();

            filter.addExact("o.id", orgId);

            List<Dept> list = this.deptService.list(filter, null);
            List<ComboboxOptionBean> deptOptionList = new ArrayList<>();
            // String format = "(%1$s) %2$s";

            for (Dept d : list) {
                deptOptionList.add(new ComboboxOptionBean(d.getId(), d.getDeptName()));
            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, deptOptionList);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the orgId " + orgId, e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
