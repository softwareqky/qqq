package project.edge.web.controller.flowable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
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
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.FlowableLocalHistoryBeanConverter;
import project.edge.domain.converter.FlowableSettingBeanConverter;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.view.FlowableLocalHistoryBean;
import project.edge.domain.view.FlowableSettingBean;
import project.edge.service.flowable.FlowableSettingService;
import project.edge.service.project.ProjectPersonService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 流程监控-已办审核的流程画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/flowable/monitor/done-process")
public class FlowableMonitorDoneProcessController
        extends SingleGridController<FlowableSetting, FlowableSettingBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(FlowableMonitorDoneProcessController.class);

    private static final String PID = "P950203";

    @Resource
    private FlowableSettingService flowableSettingService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private ProjectPersonService projectPersonService;

    @Override
    protected Service<FlowableSetting, String> getDataService() {
        return this.flowableSettingService;
    }

    @Override
    protected ViewConverter<FlowableSetting, FlowableSettingBean> getViewConverter() {
        return new FlowableSettingBeanConverter();
    }

    /**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        if (field.isCommonVisible()) {
            return;
        }
    }

    @SuppressWarnings("unchecked")
    protected void putFiledList(Model model, String key, FieldBean f) {
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

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        CommonFilter flowFilter = new CommonFilter();
        flowFilter.addExact("isDisable", (short) 0);

        PageCtrlDto flowPage = new PageCtrlDto();
        flowPage.setCurrentPage(1);
        flowPage.setPageSize(999);
        List<FlowableSetting> flows = flowableSettingService.list(flowFilter, null, flowPage);
        List<ComboboxOptionBean> flowOptions = new ArrayList<>();
        for (FlowableSetting flow : flows) {
            flowOptions.add(new ComboboxOptionBean(flow.getId(), flow.getFlowName()));
        }
        optionMap.put("flowOptions", flowOptions);
        return optionMap;
    }

    @Override
    protected Map<String, String> prepareIdMap() {
        Map<String, String> idMap = super.prepareIdMap();
        return idMap;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.FLOWABLE_DONE_PROCESS.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        return jsMap;
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        return urlMap;
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
    @SuppressWarnings("unchecked")
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
                contextPath + "/flowable/monitor/done-process/list.json?flowableSettingId=" + id;
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

            CommonFilter pFilter = new CommonFilter();
            pFilter.addExact("person.id", userBean.getSessionUserId());

            List<ProjectPerson> persons = this.projectPersonService.list(pFilter, null);

            List<String> groups = new ArrayList<>();
            groups.add(Constants.STRING_EMPTY);
            for (ProjectPerson p : persons) {
                groups.add(p.getProjectRole().getId());
            }

            HistoricTaskInstanceQuery query =
                    this.historyService.createHistoricTaskInstanceQuery().includeProcessVariables();
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
                    .taskAssignee(userBean.getSessionUserId()).endOr().finished();

            List<HistoricTaskInstance> list =
                    query.orderByTaskCreateTime().desc().listPage(page - 1, rows);

            FlowableLocalHistoryBeanConverter converter = new FlowableLocalHistoryBeanConverter();

            List<ViewBean> resultList = new ArrayList<>();

            for (HistoricTaskInstance task : list) {

                Map<String, Object> variables = task.getProcessVariables();

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

            long total = list.size();

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用
        } catch (Exception e) {
            this.getLogger().error("Exception listing the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
