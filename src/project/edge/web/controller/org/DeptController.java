/**
 * 
 */
package project.edge.web.controller.org;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.TreeNodeBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.DeptBeanConverter;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.view.DeptBean;
import project.edge.service.org.DeptService;
import project.edge.service.org.OrgService;
import project.edge.web.controller.common.SingleGridController;


/**
 * 部门管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/org/dept")
public class DeptController extends SingleGridController<Dept, DeptBean> {

    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    private static final String PID = "P16010";

    @Resource
    private OrgService orgService;

    @Resource
    private DeptService deptService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.DEPT.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Dept, String> getDataService() {
        return this.deptService;
    }

    @Override
    protected ViewConverter<Dept, DeptBean> getViewConverter() {
        return new DeptBeanConverter();
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

        CommonFilter f = null;

        ArrayList<ComboboxOptionBean> orgOptions = new ArrayList<ComboboxOptionBean>();
        f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());
        List<Org> orgList = this.orgService.list(f, null);
        if (orgList != null) {
            for (Org o : orgList) {
                orgOptions.add(new ComboboxOptionBean(o.getId(), o.getOrgName()));
            }
        }

        optionMap.put("OrgOptions", orgOptions);

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
     * 获取画面左侧公司部门树的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param page 页数
     * @param rows 每页的记录数
     * @param sort 排序的字段，CSV
     * @param order 顺序，CSV
     * @param locale
     * @return
     */
    @RequestMapping("/tree-side")
    @ResponseBody
    public JsonResultBean getTreeSide(Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 查询未删除的公司和部门，并按层级排序
            CommonFilter filter = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

            List<OrderByDto> orgOrders = new ArrayList<OrderByDto>();
            orgOrders.add(new OrderByDto("level"));
            orgOrders.add(new OrderByDto("orgOrder"));

            List<Org> orgList = this.orgService.list(filter, orgOrders);

            List<OrderByDto> deptOrders = new ArrayList<OrderByDto>();
            orgOrders.add(new OrderByDto("level"));
            orgOrders.add(new OrderByDto("deptOrder"));

            List<Dept> deptList = this.deptService.list(filter, deptOrders);

            // 借助map来转换并构建树形结构
            Map<String, TreeNodeBean> map = new HashMap<>(); // key: id
            List<TreeNodeBean> resultList = new ArrayList<>();

            TreeNodeBean root = new TreeNodeBean();
            root.setId(Constants.ALL);
            root.setText(this.messageSource.getMessage("ui.common.all", null, locale));
            root.setName(root.getText());
            resultList.add(root);

            for (Org o : orgList) {
                TreeNodeBean n = new TreeNodeBean();
                n.setId(o.getId());
                n.setPid(o.getPid());
                n.setText(o.getOrgName());
                n.setName(n.getText());

                n.setIconCls("tree-icon-fa fa fa-fw fa-building-o");

                Map<String, Object> attr = new HashMap<>();
                attr.put("isDept", false);
                attr.put("fieldName", "org_"); // 检索信息中的字段名
                n.setAttributes(attr);

                map.put(n.getId(), n);
                if (map.containsKey(n.getPid())) {
                    map.get(n.getPid()).getChildren().add(n);
                } else {
                    root.getChildren().add(n);
                }
            }

            for (Dept d : deptList) {
                TreeNodeBean n = new TreeNodeBean();
                n.setId(d.getId());
                n.setPid(d.getPid());
                n.setText(d.getDeptName());
                n.setName(n.getText());

                n.setIconCls("tree-icon-fa fa fa-fw fa-users");

                Map<String, Object> attr = new HashMap<>();
                attr.put("isDept", true);
                attr.put("fieldName", "dept_"); // 检索信息中的字段名
                n.setAttributes(attr);

                map.put(n.getId(), n);
                if (map.containsKey(n.getPid())) {
                    map.get(n.getPid()).getChildren().add(n);
                } else if (map.containsKey(d.getOrg().getId())) {
                    map.get(d.getOrg().getId()).getChildren().add(n);
                } else {
                    root.getChildren().add(n);
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);

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
    @SysLogAnnotation(description = "部门管理", action = "新增")
    public JsonResultBean create(@ModelAttribute DeptBean bean,
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
    @SysLogAnnotation(description = "部门管理", action = "更新")
    public JsonResultBean update(@ModelAttribute DeptBean bean,
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
    @SysLogAnnotation(description = "部门管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
