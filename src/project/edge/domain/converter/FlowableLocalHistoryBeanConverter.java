package project.edge.domain.converter;

import java.util.Map;

import project.edge.domain.view.FlowableLocalHistoryBean;


public class FlowableLocalHistoryBeanConverter {

    public FlowableLocalHistoryBean fromVariables(Map<String, Object> variables) {
        FlowableLocalHistoryBean bean = new FlowableLocalHistoryBean();

        bean.setStartUserId(
                variables.get("startUserId") != null ? variables.get("startUserId").toString()
                        : null);
        bean.setStartUserName(
                variables.get("startUserName") != null ? variables.get("startUserName").toString()
                        : null);

        bean.setCorrelateDataId(variables.get("correlateDataId") != null
                ? variables.get("correlateDataId").toString()
                : null);
        bean.setCorrelateDataName(variables.get("correlateDataName") != null
                ? variables.get("correlateDataName").toString()
                : null);

        bean.setCorrelateProjectId(variables.get("correlateProjectId") != null
                ? variables.get("correlateProjectId").toString()
                : null);
        bean.setCorrelateProjectName(variables.get("correlateProjectName") != null
                ? variables.get("correlateProjectName").toString()
                : null);

        bean.setFlowId(variables.get("flowableSettingId") != null
                ? variables.get("flowableSettingId").toString()
                : null);
        bean.setFlowName(variables.get("flowableSettingName") != null
                ? variables.get("flowableSettingName").toString()
                : null);

        bean.setBusinessCode(
                variables.get("businessCode") != null ? variables.get("businessCode").toString()
                        : null);
        bean.setBusinessCodeUrl(variables.get("businessCodeUrl") != null
                ? variables.get("businessCodeUrl").toString()
                : null);
        bean.setBusinessCodeName(variables.get("businessCodeName") != null
                ? variables.get("businessCodeName").toString()
                : null);
        bean.setBusinessEntity(variables.get("businessEntity") != null
                ? variables.get("businessEntity").toString()
                : null);

        bean.setParticipantAuditIdList(variables.get("participantAuditIdList") != null
                ? variables.get("participantAuditIdList").toString()
                : null);

        bean.setParticipantAuditNameList(variables.get("participantAuditNameList") != null
                ? variables.get("participantAuditNameList").toString()
                : null);

        bean.setAuditResult(variables.get("auditResult") != null
                ? Integer.valueOf(variables.get("auditResult").toString())
                : null);
        bean.setAuditRemark(
                variables.get("auditRemark") != null ? variables.get("auditRemark").toString()
                        : null);

        return bean;
    }
}
