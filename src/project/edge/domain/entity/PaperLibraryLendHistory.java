package project.edge.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_paper_library_lend_history")
public class PaperLibraryLendHistory implements Serializable {
	private static final long serialVersionUID = -3524299768215742242L;

    private String id = UUID.randomUUID().toString();

    private PaperLibrary paperLibrary;
    private Person lendPerson;
    private String contactMobile;
    private Integer lendDays;
    private String remark;
    private Integer flowStatus;
    private Person getPerson;
    private Date getTime;
    private Person returnPerson;
    private Date returnTime;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    
    //private String actionType;

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_library_id", nullable = false)
    public PaperLibrary getPaperLibrary() {
        return this.paperLibrary;
    }
    public void setPaperLibrary(PaperLibrary paperLibrary) {
        this.paperLibrary = paperLibrary;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lend_person_id", nullable = false)
    public Person getLendPerson() {
        return this.lendPerson;
    }
    public void setLendPerson(Person lendPerson) {
        this.lendPerson = lendPerson;
    }

    @Column(name = "contact_mobile", nullable = false)
    public String getContactMobile() {
        return this.contactMobile;
    }
    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    @Column(name = "lend_days", nullable = false)
    public Integer getLendDays() {
        return this.lendDays;
    }
    public void setLendDays(Integer lendDays) {
        this.lendDays = lendDays;
    }
    
    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "flow_status", nullable = false)
    public Integer getFlowStatus() {
        return this.flowStatus;
    }
    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "get_person_id", nullable = true)
    public Person getGetPerson() {
        return this.getPerson;
    }
    public void setGetPerson(Person getPerson) {
        this.getPerson = getPerson;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "get_time", nullable = true, length = 29)
    public Date getGetTime() {
        return getTime;
    }
    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_person_id", nullable = true)
    public Person getReturnPerson() {
        return this.returnPerson;
    }
    public void setReturnPerson(Person returnPerson) {
        this.returnPerson = returnPerson;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_time", nullable = true, length = 29)
    public Date getReturnTime() {
        return returnTime;
    }
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
    
    @Column(name = "creator", nullable = false)
    public String getCreator() {
        return this.creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }   

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return cDatetime;
    }
    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = true, length = 50)
    public String getModifier() {
        return modifier;
    }
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return mDatetime;
    }
    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

//    public String getActionType() {
//    	return actionType;
//    }
//    
//    public void setActionType(String actionType) {
//    	this.actionType = actionType;
//    }
}
