package project.edge.web.controller.project;

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
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.TreeNodeBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.VirtualOrgBean;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeChartController;


/**
 * 虚拟组织图画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/virtual-org-chart")
public class VirtualOrgChartController extends TreeChartController<VirtualOrg, VirtualOrgBean> {

    private static final Logger logger = LoggerFactory.getLogger(VirtualOrgChartController.class);

    private static final String PID = "P2040";

    @Resource
    private VirtualOrgService virtualOrgService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.VIRTUAL_ORG.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<VirtualOrg, String> getDataService() {
        return this.virtualOrgService;
    }

    @Override
    protected ViewConverter<VirtualOrg, VirtualOrgBean> getViewConverter() {
        return null;
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/project/virtualOrgChartJs.jsp";
    }

    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
        jsMap.put(ControllerJsMapKeys.LINKED_FUNC, "VIRTUAL_ORG_CHART.onSelectProjectTreeNode");
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
     * 获取用来生成虚拟组织图的数据。
     */
    @RequestMapping("/chart")
    @ResponseBody
    public JsonResultBean getChart(
            @RequestParam(required = false, defaultValue = "") String projectId, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 查询未删除的公司和部门，并按层级排序
            CommonFilter filter = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

            if (!StringUtils.isEmpty(projectId)) {
                filter.addExact("project.id", projectId);
            }

            List<OrderByDto> orgOrders = new ArrayList<OrderByDto>();
            orgOrders.add(new OrderByDto("level"));
            orgOrders.add(new OrderByDto("orgOrder"));

            List<VirtualOrg> orgList = this.virtualOrgService.list(filter, orgOrders);

            // 借助map来转换并构建树形结构
            Map<String, TreeNodeBean> map = new HashMap<>(); // key: id
            List<TreeNodeBean> resultList = new ArrayList<>();

            TreeNodeBean root = new TreeNodeBean();
            root.setId(Constants.ALL);
            root.setText(this.messageSource.getMessage("ui.common.all", null, locale));
            root.setName(root.getText());
            Map<String, Object> attrAll = new HashMap<>();
            attrAll.put("isVirtualOrg", false);
            attrAll.put("fieldName", "all"); // 检索信息中的字段名
            root.setAttributes(attrAll);
            resultList.add(root);

            for (VirtualOrg o : orgList) {
                TreeNodeBean n = new TreeNodeBean();
                n.setId(o.getId());
                if (o.getPid() != null) {
                    n.setPid(o.getPid().getId());
                } else {
                    n.setPid(null);
                }

                n.setText(o.getVirtualOrgName());
                n.setName(n.getText());

                n.setIconCls("tree-icon-fa fa fa-fw fa-users");

                Map<String, Object> attr = new HashMap<>();
                attr.put("isVirtualOrg", true);
                attr.put("fieldName", "virtual_org_name"); // 检索信息中的字段名
                n.setAttributes(attr);

                map.put(n.getId(), n);
                if (map.containsKey(n.getPid())) {
                    map.get(n.getPid()).getChildren().add(n);
                } else {
                    root.getChildren().add(n);
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);

        } catch (Exception e) {

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

}
