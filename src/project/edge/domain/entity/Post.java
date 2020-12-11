package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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

@Entity
@Table(name = "t_post")
public class Post implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1369249973866662750L;
    
    private String id = UUID.randomUUID().toString();
    
    private Org org;
    private Dept dept;
    private String postName;
    private String shortName;
    private String responsibility;
    private String qualification;
    private String relatedDoc;
    private String remark;
    private short isDeleted;
    private int syncStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = true)
    public Org getOrg() {
        return this.org;
    }

    
    public void setOrg(Org org) {
        this.org = org;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = true)
    public Dept getDept() {
        return this.dept;
    }

    
    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Column(name = "post_name", nullable = false, length = 50)
    public String getPostName() {
        return this.postName;
    }

    
    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Column(name = "short_name", nullable = true, length = 25)
    public String getShortName() {
        return this.shortName;
    }

    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "responsibility", nullable = true, length = 1024)
    public String getResponsibility() {
        return this.responsibility;
    }

    
    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    @Column(name = "qualification", nullable = true, length = 1024)
    public String getQualification() {
        return this.qualification;
    }

    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Column(name = "related_doc", nullable = true, length = 500)
    public String getRelatedDoc() {
        return this.relatedDoc;
    }

    
    public void setRelatedDoc(String relatedDoc) {
        this.relatedDoc = relatedDoc;
    }

    @Column(name = "remark", nullable = true, length = 500)
    public String getRemark() {
        return this.remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }

    
    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Column(name = "sync_status", nullable = false)
    public int getSyncStatus() {
        return this.syncStatus;
    }

    
    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
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
