package project.edge.flowable;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FlowNodeTypeEnum;
import garage.origin.common.constant.FlowableSettingNodeTypeEnum;
import garage.origin.domain.view.FlowDetailBean;
import garage.origin.domain.view.FlowNodeBean;
import project.edge.domain.entity.FlowableSettingNode;
import project.edge.domain.view.FlowableSettingBean;

@Component
public class FlowableManager {

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    private static final String BPMN_SUFFIX = ".bpmn20.xml";

    private static final String WITHDRAW_CODE = "WITHDRAW";

    private static final String PREV_START_EVENT = "START_EVENT_";

    private static final String PREV_END_EVENT = "END_EVENT_";

    private static final String PREV_SEQ_FLOW = "SEQ_FLOW_";

    private static final String PREV_USER_TASK_SINGLE_AUDIT = "USER_TASK_SINGLE_AUDIT_";

    private static final String PREV_EXCLUSIVE_GATEWAY = "EXCLUSIVE_GATEWAY_";

    public static final String KEY_SELF_AUDIT_REMARK = "selfAuditRemark";
    public static final String KEY_SELF_AUDIT_RESULT = "selfAuditResult";
    public static final String KEY_SELF_AUDIT_PERSON_ID = "selfAuditPersonId";
    public static final String KEY_SELF_AUDIT_PERSON_NAME = "selfAuditPersonName";


    // 将数据库流程节点转换成流程引擎节点
    private FlowDetailBean generateFlowableDetailBeans(FlowableSettingBean flowableSettingBean,
            List<FlowableSettingNode> dbNodes) {

        FlowDetailBean bean = new FlowDetailBean();

        bean.setId(flowableSettingBean.getId());
        bean.setBusinessCode(flowableSettingBean.getPage_());
        bean.setName(flowableSettingBean.getFlowName());

        List<FlowNodeBean> tempNodes = new ArrayList<>();

        String startEventId = PREV_START_EVENT + UUID.randomUUID().toString();

        String endEventId = PREV_END_EVENT + UUID.randomUUID().toString();
        FlowNodeBean prevBean = null;
        for (int i = 0; i < dbNodes.size(); i++) {

            if (tempNodes.size() != 0) {
                prevBean = tempNodes.get(tempNodes.size() - 1);
            }

            FlowableSettingNode dbNode = dbNodes.get(i);

            Integer nodeType = dbNode.getNodeType();

            if (FlowableSettingNodeTypeEnum.START_NODE.value().equals(nodeType)) {

                FlowNodeBean startBean = new FlowNodeBean();

                startBean.setId(startEventId);
                startBean.setNodeType(FlowNodeTypeEnum.START_EVENT.value());
                startBean.getOutgoingFlows().add(PREV_SEQ_FLOW + UUID.randomUUID().toString());

                tempNodes.add(startBean);
            } else if (FlowableSettingNodeTypeEnum.END_NODE.value().equals(nodeType)) {

                FlowNodeBean seqBean = new FlowNodeBean();

                seqBean.setId(prevBean.getOutgoingFlows().get(0));
                seqBean.setSourceRef(prevBean.getId());
                seqBean.setTargetRef(endEventId);
                seqBean.setNodeType(FlowNodeTypeEnum.SEQUENCE_FLOW.value());

                tempNodes.add(seqBean);

                FlowNodeBean endBean = new FlowNodeBean();

                endBean.setId(endEventId);
                endBean.setNodeType(FlowNodeTypeEnum.END_EVENT.value());

                endEventId = seqBean.getTargetRef();

                tempNodes.add(endBean);
            } else if (FlowableSettingNodeTypeEnum.SINGLE_AUDIT_NODE.value().equals(nodeType)
                    || FlowableSettingNodeTypeEnum.ROLE_AUDIT_NODE.value().equals(nodeType)) {

                FlowNodeBean seqBean = new FlowNodeBean();

                String userTaskId = PREV_USER_TASK_SINGLE_AUDIT + UUID.randomUUID().toString();

                seqBean.setId(prevBean.getOutgoingFlows().get(0));
                seqBean.setSourceRef(prevBean.getId());
                seqBean.setTargetRef(userTaskId);
                seqBean.setNodeType(FlowNodeTypeEnum.SEQUENCE_FLOW.value());

                tempNodes.add(seqBean);

                FlowNodeBean userTaskBean = new FlowNodeBean();
                userTaskBean.setId(userTaskId);
                userTaskBean.setName(dbNode.getNodeName());
                if (FlowableSettingNodeTypeEnum.SINGLE_AUDIT_NODE.value().equals(nodeType)) {
                    userTaskBean.setAssignee(dbNode.getParticipantAuditList());
                } else if (FlowableSettingNodeTypeEnum.ROLE_AUDIT_NODE.value().equals(nodeType)) {

                    String[] groupArray = StringUtils
                            .commaDelimitedListToStringArray(dbNode.getParticipantAuditList());

                    for (String group : groupArray) {
                        userTaskBean.getCandidateGroups().add(group);
                    }
                }
                userTaskBean.setNodeType(FlowNodeTypeEnum.USER_TASK.value());
                userTaskBean.getOutgoingFlows().add(PREV_SEQ_FLOW + UUID.randomUUID().toString());

                // 表示上个节点，为节点设置排他网关，如果是驳回，直接进入结束
                if (prevBean != null && prevBean.getId().startsWith(PREV_USER_TASK_SINGLE_AUDIT)) {

                    seqBean.setTargetRef(PREV_EXCLUSIVE_GATEWAY + UUID.randomUUID().toString());

                    FlowNodeBean exclusiveGatewayBean = new FlowNodeBean();
                    exclusiveGatewayBean.setId(seqBean.getTargetRef());
                    exclusiveGatewayBean.setNodeType(FlowNodeTypeEnum.EXCLUSIVE_GATEWAY.value());
                    exclusiveGatewayBean.setName("FlowAuditResultEnum 1|2");

                    String userTaskFlowId = PREV_SEQ_FLOW + UUID.randomUUID().toString();
                    String endFlowId = PREV_SEQ_FLOW + UUID.randomUUID().toString();
                    exclusiveGatewayBean.getOutgoingFlows().add(userTaskFlowId);
                    exclusiveGatewayBean.getOutgoingFlows().add(endFlowId);

                    FlowNodeBean userTaskFlow = new FlowNodeBean();
                    userTaskFlow.setId(userTaskFlowId);
                    userTaskFlow.setSourceRef(exclusiveGatewayBean.getId());
                    userTaskFlow.setTargetRef(userTaskId);
                    userTaskFlow.setNodeType(FlowNodeTypeEnum.SEQUENCE_FLOW.value());
                    userTaskFlow.setConditionExpression("${auditResult == 1}");

                    FlowNodeBean endFlow = new FlowNodeBean();
                    endFlow.setId(endFlowId);
                    endFlow.setSourceRef(exclusiveGatewayBean.getId());
                    endFlow.setTargetRef(endEventId);
                    endFlow.setNodeType(FlowNodeTypeEnum.SEQUENCE_FLOW.value());
                    endFlow.setConditionExpression("${auditResult == 2}");

                    tempNodes.add(exclusiveGatewayBean);
                    tempNodes.add(userTaskFlow);
                    tempNodes.add(endFlow);
                }

                tempNodes.add(userTaskBean);
            }
        }

        if (tempNodes.size() > 0) {
            bean.setFlowNodeBeans(tempNodes);
        }

        return bean;
    }

    public boolean generateFlowFromDb(String userId, FlowableSettingBean flowableSettingBean,
            List<FlowableSettingNode> dbNodes) {

        FlowDetailBean flowDetailBean =
                this.generateFlowableDetailBeans(flowableSettingBean, dbNodes);

        BpmnModel bpmnModel = this.buildBpmnModdel(flowDetailBean);
        if (bpmnModel == null) {
            return false;
        }

        // GraphicInfo graphicInfo = new GraphicInfo();
        // bpmnModel.addGraphicInfo("", graphicInfo);
        // P00101_xxxx_xxxx_xxxx
        String keyResource =
                flowDetailBean.getBusinessCode() + Constants.UNDERSCORE + flowDetailBean.getId();

        // 预先删除历史部署资源，保证只有一个部署资源在使用
        List<ProcessDefinition> processDefinitionList = this.repositoryService
                .createProcessDefinitionQuery().processDefinitionKey(keyResource).list();

        for (ProcessDefinition processDefinition : processDefinitionList) {
            this.repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        }

        // P00101_xxxx_xxxx_xxxx.bpmn20.xml
        this.repositoryService.createDeployment().addBpmnModel(keyResource + BPMN_SUFFIX, bpmnModel)
                .deploy();

        return true;
    }

    public void runProcessInstance(FlowDetailBean flowDetailBean) {

        try {
            Map<String, Object> variables = new HashMap<>();

            variables.put("startUserId", flowDetailBean.getStartUserId());
            variables.put("startUserName", flowDetailBean.getStartUserName());

            variables.put("correlateDataId", flowDetailBean.getCorrelateDataId());
            variables.put("correlateDataName", flowDetailBean.getCorrelateDataName());

            variables.put("correlateProjectId", flowDetailBean.getCorrelateProjectId());
            variables.put("correlateProjectName", flowDetailBean.getCorrelateProjectName());

            variables.put("flowableSettingId", flowDetailBean.getFlowableSettingId());
            variables.put("flowableSettingName", flowDetailBean.getFlowableSettingName());

            variables.put("businessCode", flowDetailBean.getBusinessCode());
            variables.put("businessCodeUrl", flowDetailBean.getBusinessCodeUrl());
            variables.put("businessCodeName", flowDetailBean.getBusinessCodeName());
            variables.put("businessEntity", flowDetailBean.getBusinessEntity());

            variables.put("participantAuditIdList", flowDetailBean.getParticipantAuditIdList());
            variables.put("participantAuditNameList", flowDetailBean.getParticipantAuditNameList());


            // 历史记录删除 例如审核驳回后，可以直接提交审核
            this.deleteHistoricData(flowDetailBean.getCorrelateDataId());

            // 设置发起人
            Authentication.setAuthenticatedUserId(flowDetailBean.getStartUserId());

            // 特别处理 P0001_xxxx-xxxx-xxxx
            this.runtimeService.startProcessInstanceByKey(flowDetailBean.getId(), variables);
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    private BpmnModel buildBpmnModdel(FlowDetailBean flowDetailBean) {

        String processId = flowDetailBean.getId();
        String businessCode = flowDetailBean.getBusinessCode();

        if (StringUtils.isEmpty(processId) || flowDetailBean.getFlowNodeBeans().isEmpty()) {
            return null;
        }

        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();

        process.setId(businessCode + Constants.UNDERSCORE + processId);
        process.setName(flowDetailBean.getName());

        ExtensionElement eventElement = new ExtensionElement();
        eventElement.setName("eventListener");
        eventElement.setNamespace("http://flowable.org/bpmn");
        eventElement.setNamespacePrefix("flowable");

        List<ExtensionAttribute> attributes = new ArrayList<>();

        ExtensionAttribute attribute = new ExtensionAttribute();

        attribute.setName("delegateExpression");
        attribute.setValue("${flowableEventListener}");

        // attribute.setName("class");
        // attribute.setValue("project.edge.flowable.FlowableEventListener");

        attributes.add(attribute);

        eventElement.addAttribute(attribute);

        process.addExtensionElement(eventElement);

        List<SequenceFlow> sequenceFlows = new ArrayList<>();

        for (FlowNodeBean nodeBean : flowDetailBean.getFlowNodeBeans()) {

            if (FlowNodeTypeEnum.SEQUENCE_FLOW.value().equals(nodeBean.getNodeType())) {
                SequenceFlow sequenceFlow = new SequenceFlow();
                sequenceFlow.setId(nodeBean.getId());
                sequenceFlow.setSourceRef(nodeBean.getSourceRef());
                sequenceFlow.setTargetRef(nodeBean.getTargetRef());

                // 设置顺序流条件，与排他网关一起使用
                if (!StringUtils.isEmpty(nodeBean.getConditionExpression())) {
                    sequenceFlow.setConditionExpression(nodeBean.getConditionExpression());
                }

                sequenceFlows.add(sequenceFlow);
                process.addFlowElement(sequenceFlow);
            } else if (FlowNodeTypeEnum.EXCLUSIVE_GATEWAY.value().equals(nodeBean.getNodeType())) {
                ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
                exclusiveGateway.setId(nodeBean.getId());
                exclusiveGateway.setName(nodeBean.getName());

                process.addFlowElement(exclusiveGateway);
            }
        }

        for (FlowNodeBean nodeBean : flowDetailBean.getFlowNodeBeans()) {

            if (FlowNodeTypeEnum.SEQUENCE_FLOW.value().equals(nodeBean.getNodeType())) {
                continue;
            }

            if (FlowNodeTypeEnum.START_EVENT.value().equals(nodeBean.getNodeType())) {

                StartEvent startEvent = new StartEvent();

                startEvent.setId(nodeBean.getId());
                startEvent.setName(nodeBean.getName());

                List<SequenceFlow> flows =
                        this.generateSeqenceFlows(nodeBean.getOutgoingFlows(), sequenceFlows);

                if (!flows.isEmpty()) {
                    startEvent.setOutgoingFlows(flows);
                }

                process.addFlowElement(startEvent);
            } else if (FlowNodeTypeEnum.USER_TASK.value().equals(nodeBean.getNodeType())) {

                UserTask userTask = new UserTask();

                userTask.setId(nodeBean.getId());
                userTask.setName(nodeBean.getName());

                if (!StringUtils.isEmpty(nodeBean.getAssignee())) {
                    userTask.setAssignee(nodeBean.getAssignee());
                }

                if (!nodeBean.getCandidateUsers().isEmpty()) {
                    userTask.setCandidateUsers(nodeBean.getCandidateUsers());
                }

                if (!nodeBean.getCandidateGroups().isEmpty()) {
                    userTask.setCandidateGroups(nodeBean.getCandidateGroups());
                }

                List<SequenceFlow> incomingFlows =
                        this.generateSeqenceFlows(nodeBean.getIncomingFlows(), sequenceFlows);

                if (!incomingFlows.isEmpty()) {
                    userTask.setIncomingFlows(incomingFlows);
                }

                List<SequenceFlow> outgoingFlows =
                        this.generateSeqenceFlows(nodeBean.getOutgoingFlows(), sequenceFlows);

                if (!outgoingFlows.isEmpty()) {
                    userTask.setOutgoingFlows(outgoingFlows);
                }

                process.addFlowElement(userTask);
            } else if (FlowNodeTypeEnum.END_EVENT.value().equals(nodeBean.getNodeType())) {

                EndEvent endEvent = new EndEvent();
                endEvent.setId(nodeBean.getId());
                endEvent.setName(nodeBean.getName());


                List<SequenceFlow> incomingFlows =
                        this.generateSeqenceFlows(nodeBean.getIncomingFlows(), sequenceFlows);

                if (!incomingFlows.isEmpty()) {
                    endEvent.setIncomingFlows(incomingFlows);
                }

                endEvent.setIncomingFlows(incomingFlows);

                process.addFlowElement(endEvent);
            }
        }

        bpmnModel.addProcess(process);

        return bpmnModel;
    }

    private List<SequenceFlow> generateSeqenceFlows(List<String> flowIds,
            List<SequenceFlow> flowEntites) {

        List<SequenceFlow> flows = new ArrayList<>();

        for (String flowId : flowIds) {

            for (SequenceFlow seqFlow : flowEntites) {
                if (flowId.equals(seqFlow.getId())) {
                    flows.add(seqFlow);
                    break;
                }
            }
        }

        return flows;
    }

    public void deleteProcessDefintion(List<String> keys) {

        if (!keys.isEmpty()) {

            for (String key : keys) {

                List<ProcessDefinition> processDefinitionList = this.repositoryService
                        .createProcessDefinitionQuery().processDefinitionKey(key).list();

                for (ProcessDefinition processDefinition : processDefinitionList) {
                    this.repositoryService.deleteDeployment(processDefinition.getDeploymentId(),
                            true);
                }
            }
        }
    }

    // 获取审核参与人
    public String getAssigneeIdList(String key) {

        if (!StringUtils.isEmpty(key)) {

            StringBuilder sb = new StringBuilder();

            ProcessDefinition processDefinition = this.repositoryService
                    .createProcessDefinitionQuery().processDefinitionKey(key).singleResult();

            if (processDefinition != null) {
                List<Process> processes = this.repositoryService
                        .getBpmnModel(processDefinition.getId()).getProcesses();

                if (!CollectionUtils.isEmpty(processes)) {
                    for (Process process : processes) {
                        Collection<FlowElement> flowElements = process.getFlowElements();
                        if (!CollectionUtils.isEmpty(flowElements)) {
                            for (FlowElement flowElement : flowElements) {
                                if (flowElement instanceof UserTask) {
                                    UserTask userTask = (UserTask) flowElement;

                                    sb.append(userTask.getAssignee()).append(Constants.COMMA);
                                }
                            }
                        }
                    }
                }
            }

            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
        }
        return null;
    }

    public String getCandidateGroupsIdList(String key) {

        if (!StringUtils.isEmpty(key)) {

            StringBuilder sb = new StringBuilder();

            ProcessDefinition processDefinition = this.repositoryService
                    .createProcessDefinitionQuery().processDefinitionKey(key).singleResult();

            if (processDefinition != null) {
                List<Process> processes = this.repositoryService
                        .getBpmnModel(processDefinition.getId()).getProcesses();

                if (!CollectionUtils.isEmpty(processes)) {
                    for (Process process : processes) {
                        Collection<FlowElement> flowElements = process.getFlowElements();
                        if (!CollectionUtils.isEmpty(flowElements)) {
                            for (FlowElement flowElement : flowElements) {
                                if (flowElement instanceof UserTask) {
                                    UserTask userTask = (UserTask) flowElement;

                                    for (String group : userTask.getCandidateGroups()) {
                                        sb.append(group).append(Constants.COMMA);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
        }
        return null;
    }

    public boolean completeTask(FlowDetailBean flowDetailBean) {

        Map<String, Object> variables = new HashMap<>();

        variables.put("auditRemark", flowDetailBean.getAuditRemark());
        variables.put("auditResult", flowDetailBean.getAuditResult());

        Map<String, Object> selfVariables = new HashMap<>();

        selfVariables.put(KEY_SELF_AUDIT_REMARK, flowDetailBean.getAuditRemark());
        selfVariables.put(KEY_SELF_AUDIT_RESULT, flowDetailBean.getAuditResult());
        selfVariables.put(KEY_SELF_AUDIT_PERSON_ID, flowDetailBean.getAuditPersonId());
        selfVariables.put(KEY_SELF_AUDIT_PERSON_NAME, flowDetailBean.getAuditPersonName());

        variables.put(flowDetailBean.getAuditTaskId(), selfVariables);

        if (flowDetailBean.getAuditResult() != null) {
            this.taskService.complete(flowDetailBean.getAuditTaskId(), variables);
            return true;
        }

        return false;
    }

    public String getTaskNodeName(String processDefinitionId, String taskDefinitionKey) {

        BpmnModel model = this.repositoryService.getBpmnModel(processDefinitionId);

        if (model != null) {
            List<Process> processes = model.getProcesses();
            if (!processes.isEmpty()) {
                FlowElement element = processes.get(0).getFlowElement(taskDefinitionKey);

                if (element != null) {
                    return element.getName();
                }
            }
        }

        return null;
    }


    public List<HistoricTaskInstance> getHistoricTaskInstances(String correlateDataId) {

        List<HistoricTaskInstance> taskInstances =
                this.historyService.createHistoricTaskInstanceQuery().includeProcessVariables()
                        .processVariableExists("correlateDataId")
                        .processVariableValueEquals(correlateDataId)
                        .orderByHistoricTaskInstanceStartTime().asc().list();
        return taskInstances;
    }

    public String queryFlowableSettingIdWithCorrelateDataId(String correlateDataId) {
        HistoricProcessInstance instance = this.historyService.createHistoricProcessInstanceQuery()
                .includeProcessVariables().variableExists("correlateDataId")
                .variableValueEquals(correlateDataId).singleResult();

        if (instance != null) {
            Map<String, Object> map = instance.getProcessVariables();

            if (map != null) {
                return (String) map.get("flowableSettingId");
            }
        }

        return null;
    }

    public void withdraw(String correlateDataId) {
        List<ProcessInstance> instances = this.runtimeService.createProcessInstanceQuery()
                .includeProcessVariables().variableExists("correlateDataId")
                .variableValueEquals(correlateDataId).list();

        if (!instances.isEmpty()) {
            // 运行时流程删除
            for (ProcessInstance i : instances) {
                this.runtimeService.deleteProcessInstance(i.getId(), WITHDRAW_CODE);
            }

            // 历史记录删除
            this.deleteHistoricData(correlateDataId);
        }
    }

    // 历史记录删除
    private void deleteHistoricData(String correlateDataId) {
        List<HistoricProcessInstance> processInstances = this.historyService
                .createHistoricProcessInstanceQuery().includeProcessVariables()
                .variableExists("correlateDataId").variableValueEquals(correlateDataId).list();

        if (!processInstances.isEmpty()) {
            for (HistoricProcessInstance hpi : processInstances) {
                this.historyService.deleteHistoricProcessInstance(hpi.getId());
            }
        }

        List<HistoricTaskInstance> taskInstances =
                this.historyService.createHistoricTaskInstanceQuery().includeProcessVariables()
                        .processVariableExists("correlateDataId")
                        .processVariableValueEquals(correlateDataId).list();

        if (!taskInstances.isEmpty()) {
            for (HistoricTaskInstance ht : taskInstances) {
                this.historyService.deleteHistoricTaskInstance(ht.getId());
            }
        }
    }

    public Map<String, Object> getRunVariablesWithCorrelateDataId(String correlateDataId) {

        Map<String, Object> returnMap = new HashMap<>();

        ProcessInstance instances = this.runtimeService.createProcessInstanceQuery()
                .includeProcessVariables().variableExists("correlateDataId")
                .variableValueEquals(correlateDataId).singleResult();

        if (instances != null) {
            Map<String, Object> runMap = instances.getProcessVariables();

            if (runMap != null) {
                returnMap.put("businessEntity", runMap.get("businessEntity"));
            }
        }

        return returnMap;
    }

    public boolean isFlowableFinish(String correlateDataId) {

        HistoricProcessInstance instance = this.historyService.createHistoricProcessInstanceQuery()
                .includeProcessVariables().variableExists("correlateDataId")
                .variableValueEquals(correlateDataId).singleResult();

        if (instance.getEndTime() != null) {
            return true;
        }

        return false;
    }

    public boolean isFlowableHasRunning(String settingId) {
        List<ProcessInstance> instances =
                this.runtimeService.createProcessInstanceQuery().includeProcessVariables()
                        .variableExists("flowableSettingId").variableValueEquals(settingId).list();

        if (!instances.isEmpty()) {
            return true;
        }

        return false;
    }
    // public InputStream generateDiagram(FlowableSetting setting) {
    //
    // String key = setting.getPage().getId() + Constants.UNDERSCORE + setting.getId();
    //
    // ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
    // .processDefinitionKey(key).singleResult();
    //
    // BpmnModel bpmnModel = this.repositoryService.getBpmnModel(processDefinition.getId());
    //
    // InputStream in = this.processEngine.getProcessEngineConfiguration()
    // .getProcessDiagramGenerator().generateJpgDiagram(bpmnModel);
    //
    // return in;
    // }
    // public Integer queryCurrentTaskStatus(HistoricProcessInstance history) {
    // List<HistoricTaskInstance> taskInstances =
    // this.historyService.createHistoricTaskInstanceQuery().includeProcessVariables()
    // .processVariableValueEquals("_", "").desc().list();
    //
    // return null;
    // }
}

