/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 */
@Entity
@Table(name = "t_flowable_setting")
public class FlowableSetting implements Serializable {

    private static final long serialVersionUID = -4213821133713011990L;

    private String id = UUID.randomUUID().toString();

    private Page page;
    private String flowName;
    private Short isDisable;
    private Short isComplete;
    private String remark;

    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<ProjectRole> projectRoles = new HashSet<>(0);
    
    private Set<FlowableSettingNode> flowableSettingNodes = new HashSet<>(0);

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
    @JoinColumn(name = "page_id", nullable = true)
    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Column(name = "flow_name", nullable = false, length = 150)
    public String getFlowName() {
        return this.flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @Column(name = "is_disable", nullable = true)
    public Short getIsDisable() {
        return this.isDisable;
    }

    public void setIsDisable(Short isDisable) {
        this.isDisable = isDisable;
    }

    @Column(name = "is_complete", nullable = true)
    public Short getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }

    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = true, length = 50)
    public String getModifier() {
        return this.modifier;
    }


    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_flowable_setting_project_role",
            joinColumns = {
                    @JoinColumn(name = "flowable_setting_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_role_id", nullable = false, updatable = false)})
    public Set<ProjectRole> getProjectRoles() {
        return this.projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flowableSetting")
    public Set<FlowableSettingNode> getFlowableSettingNodes() {
        return this.flowableSettingNodes;
    }

    
    public void setFlowableSettingNodes(Set<FlowableSettingNode> flowableSettingNodes) {
        this.flowableSettingNodes = flowableSettingNodes;
    }
}
