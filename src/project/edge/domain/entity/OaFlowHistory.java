/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_oa_flow_history")
public class OaFlowHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -377587205731930156L;

    private String id = UUID.randomUUID().toString();

    private int category;
    private String relatedFormId;
    private int flowAction;
    private Date flowDatetime;
    private String remark;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

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



    @Column(name = "category", nullable = false)
    public int getCategory() {
        return this.category;
    }


    public void setCategory(int category) {
        this.category = category;
    }

    @Column(name = "related_form_id", nullable = false, length = 1000)
    public String getRelatedFormId() {
        return this.relatedFormId;
    }


    public void setRelatedFormId(String relatedFormId) {
        this.relatedFormId = relatedFormId;
    }

    @Column(name = "flow_action", nullable = false)
    public int getFlowAction() {
        return this.flowAction;
    }


    public void setFlowAction(int flowAction) {
        this.flowAction = flowAction;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "flow_datetime", nullable = false, length = 29)
    public Date getFlowDatetime() {
        return this.flowDatetime;
    }


    public void setFlowDatetime(Date flowDatetime) {
        this.flowDatetime = flowDatetime;
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

}
