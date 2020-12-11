/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *
 */
@Entity
@Table(name = "t_flowable_setting_node")
public class FlowableSettingNode implements Serializable {

    private static final long serialVersionUID = -5390159695815137672L;

    private String id = UUID.randomUUID().toString();

    private FlowableSetting flowableSetting;
    
    private String nodeName;
    private Integer nodeType;
    private String participantAuditList;
    private String participantAuditNameList;
    
    private Integer nodeOrder;
    
    private String auditRemark;

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flowable_setting_id", nullable = true)
    public FlowableSetting getFlowableSetting() {
        return this.flowableSetting;
    }


    public void setFlowableSetting(FlowableSetting flowableSetting) {
        this.flowableSetting = flowableSetting;
    }

    @Column(name = "node_name", nullable = false, length = 100)
    public String getNodeName() {
        return this.nodeName;
    }


    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Column(name = "node_type", nullable = false)
    public Integer getNodeType() {
        return this.nodeType;
    }


    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    @Column(name = "participant_audit_list")
    public String getParticipantAuditList() {
        return this.participantAuditList;
    }

    public void setParticipantAuditList(String participantAuditList) {
        this.participantAuditList = participantAuditList;
    }

    @Column(name = "participant_audit_name_list")
    public String getParticipantAuditNameList() {
        return this.participantAuditNameList;
    }


    public void setParticipantAuditNameList(String participantAuditNameList) {
        this.participantAuditNameList = participantAuditNameList;
    }

    @Column(name = "audit_remark", nullable = true, length = 1024)
    public String getAuditRemark() {
        return this.auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    @Column(name = "node_order", nullable = true, length = 1024)
    public Integer getNodeOrder() {
        return this.nodeOrder;
    }

    
    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }
}
