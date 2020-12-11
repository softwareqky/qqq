package project.edge.web.controller.flowable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.FlowableLocalHistoryBeanConverter;
import project.edge.domain.converter.FlowableSettingBeanConverter;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.FlowableLocalHistoryBean;
import project.edge.domain.view.FlowableSettingBean;
import project.edge.service.flowable.FlowableSettingService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 流程监控-待审核的流程画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/flowable/monitor/pending-process")
public class FlowableMonitorPendingProcessController
        extends SingleGridController<FlowableSetting, FlowableSettingBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(FlowableMonitorPendingProcessController.class);

    private static final String PID = "P950202";

    @Resource
    private FlowableSettingService flowableSettingService;

    @Resource
    private TaskService taskService;

    @Resource
    private ProjectPersonService projectPersonService;

    @Resource
    private VirtualOrgService virtualOrgService;

    @Override
    protected Service<FlowableSetting, String> getDataService() {
        return this.flowableSettingService;
    }

    @Override
    protected ViewConverter<FlowableSetting, FlowableSettingBean> getViewConverter() {
        return new FlowableSettingBeanConverter();
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

        CommonFilter flowFilter = new CommonFilter();
        flowFilter.addExact("isDisable", (short) 0);

        PageCtrlDto flowPage = new PageCtrlDto();
        flowPage.setCurrentPage(1);
        flowPage.setPageSize(999);
        List<FlowableSetting> flows = flowableSettingService.list(flowFilter, null, flowPage);
        List<ComboboxOptionBean> flowOptions = new ArrayList<>();
        for (FlowableSetting flow : flows) {
            ComboboxOptionBean option = new ComboboxOptionBean(flow.getId(), flow.getFlowName());
            flowOptions.add(option);
        }
        optionMap.put("flowOptions", flowOptions);
        return optionMap;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.FLOWABLE_PENDING_PROCESS.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开流程审核
        jsMap.put(ControllerJsMapKeys.OPEN_FLOW_AUDIT,
                String.format("FlowableUtils.openFlowAuditDialog('#%1$s');",
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
            @RequestParam(defaultValue = "") String id,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        // 单选
        model.addAttribute(ControllerModelKeys.IS_SINGLE_SELECT, true);
        model.addAttribute("flowableSettingId", id);
        String r = super.main(paramMap, model, userBean, locale);
        Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);

        String contextPath = this.context.getContextPath();
        String listUrl =
                contextPath + "/flowable/monitor/pending-process/list.json?flowableSettingId=" + id;
        urlMap.put(ControllerUrlMapKeys.LIST, listUrl);
        return r;
    }

    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(defaultValue = "") String flowableSettingId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {

            List<String> groups = new ArrayList<>();

            // 特殊处理副经理流程
            // CommonFilter managerFilter = new CommonFilter();
            // managerFilter.addExact("leader.id", userBean.getSessionUserId());
            // List<VirtualOrg> virtualOrgs = this.virtualOrgService.list(managerFilter, null);
            //
            // if (!virtualOrgs.isEmpty()) {
            // groups.add(FlowableSettingController.DEPUTY_MANAGER_ROLE_ID);
            // }

            CommonFilter pFilter = new CommonFilter();
            pFilter.addExact("person.id", userBean.getSessionUserId());

            List<ProjectPerson> persons = this.projectPersonService.list(pFilter, null);

            groups.add(Constants.STRING_EMPTY);
            for (ProjectPerson p : persons) {
                groups.add(p.getProjectRole().getId());
            }

            TaskQuery query = this.taskService.createTaskQuery().includeProcessVariables();
            if (!"".equals(flowableSettingId)) {
                query = query.processVariableValueEquals("flowableSettingId", flowableSettingId);
            }
            if (commonFilterJson != null && !"".equals(commonFilterJson)) {
                JSONObject json = JSONObject.parseObject(commonFilterJson);
                JSONArray filterFieldList = json.getJSONArray("filterFieldList");
                for (int i = 0; i < filterFieldList.size(); i++) {
                    JSONObject field = filterFieldList.getJSONObject(i);
                    if (field.getString("fieldName").equalsIgnoreCase("correlateProjectName")) {
                        query = query.processVariableValueLikeIgnoreCase("correlateProjectName",
                                "%" + field.getString("value") + "%");
                    }
                    if (field.getString("fieldName").equalsIgnoreCase("flowName")) {
                        query = query.processVariableValueEquals("flowableSettingId",
                                field.getString("value"));
                    }
                    if (field.getString("fieldName").equalsIgnoreCase("startUserName")) {
                        query = query.processVariableValueLikeIgnoreCase("startUserName",
                                "%" + field.getString("value") + "%");
                    }
                    if (field.getString("fieldName").equalsIgnoreCase("submitDatetime")) {
                        if (field.get("from") != null && !"".equals(field.getString("from"))) {
                            query = query.taskCreatedAfter(DateUtils.string2Date(
                                    field.getString("from") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
                        }
                        if (field.get("to") != null && !"".equals(field.getString("to"))) {
                            query = query.taskCreatedBefore(DateUtils.string2Date(
                                    field.getString("to") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
                        }
                    }
                }
            }

            query = query.or().taskCandidateGroupIn(groups)
                    .taskAssignee(userBean.getSessionUserId()).endOr();

            List<Task> tasks = query.listPage(page - 1, rows);

            FlowableLocalHistoryBeanConverter converter = new FlowableLocalHistoryBeanConverter();

            List<ViewBean> resultList = new ArrayList<>();

            for (Task task : tasks) {

                Map<String, Object> variables = task.getProcessVariables();

                String startUserId = (String) variables.get("startUserId");

                // 特殊处理待审核记录，只允许[相同项目组的成员]审批
                // 如果存在副经理角色审批时需要特殊处理，因为副经理可能与发起人不在同一组里
                boolean isNeed = this.isNeedAudit(startUserId, userBean.getSessionUserId());

                if (!StringUtils.isEmpty(task.getAssignee())
                        && !task.getAssignee().equals(userBean.getSessionUserId())) {
                    if (!isNeed) {
                        continue;
                    }
                }

                System.out.println(variables);
                FlowableLocalHistoryBean bean = converter.fromVariables(variables);

                StringBuilder sb = new StringBuilder();

                if (!StringUtils.isEmpty(bean.getBusinessCodeName())) {
                    sb.append(Constants.LEFT_PARENTHESIS).append(bean.getBusinessCodeName())
                            .append(Constants.RIGHT_PARENTHESIS);
                }

                if (!StringUtils.isEmpty(bean.getFlowName())) {
                    sb.append(Constants.SPACE).append(bean.getFlowName());
                }

                if (!StringUtils.isEmpty(bean.getCorrelateProjectName())) {
                    sb.append(Constants.SPACE).append(bean.getCorrelateProjectName());
                }

                if (sb.length() > 0) {
                    bean.setPendingMatters(sb.toString());
                }

                if (task.getCreateTime() != null) {
                    bean.setSubmitDatetimeText(
                            DateUtils.date2String(task.getCreateTime(), Constants.DATE_FORMAT));
                }

                bean.setAuditTaskId(task.getId());

                resultList.add(bean);
            }

            // long total = tasks.size();

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, resultList.size());// easyui-datagrid分页用
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用
        } catch (Exception e) {
            this.getLogger().error("Exception listing the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    private boolean isNeedAudit(String startUserId, String sessionUserId) {

        CommonFilter pFilter = new CommonFilter();
        pFilter.addExact("person.id", startUserId);
        List<ProjectPerson> startpersons = this.projectPersonService.list(pFilter, null);

        CommonFilter aFilter = new CommonFilter();
        aFilter.addExact("person.id", sessionUserId);
        List<ProjectPerson> auditpersons = this.projectPersonService.list(aFilter, null);

        if (!startpersons.isEmpty() && !auditpersons.isEmpty()) {
            for (ProjectPerson p : startpersons) {

                for (ProjectPerson a : auditpersons) {
                    if (p.getVirtualOrg().getId().equals(a.getVirtualOrg().getId())) {
                        return true;
                    } else {
                        // 发起人的组信息
                        VirtualOrg org = p.getVirtualOrg();

                        // 该组的副经理与角色权限匹配时
                        if (org != null && org.getLeader() != null) {
                            if (org.getLeader().getId().equals(a.getPerson().getId())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
