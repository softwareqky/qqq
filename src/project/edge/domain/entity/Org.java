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

@Entity
@Table(name = "t_org")
public class Org implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -542957006071155420L;

    
    private String id = UUID.randomUUID().toString();
    
    private String orgName;
    private String shortName;
    private String orgCode;
    private String pid;
    private int level;
    private int orgOrder;
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

    @Column(name = "org_name", nullable = false, length = 50)
    public String getOrgName() {
        return this.orgName;
    }

    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(name = "short_name", nullable = true, length = 25)
    public String getShortName() {
        return this.shortName;
    }

    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "org_code", nullable = true, length = 25)
    public String getOrgCode() {
        return this.orgCode;
    }

    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "pid", nullable = true, length = 36)
    public String getPid() {
        return this.pid;
    }

    
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }

    
    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "org_order", nullable = false)
    public int getOrgOrder() {
        return this.orgOrder;
    }

    
    public void setOrgOrder(int orgOrder) {
        this.orgOrder = orgOrder;
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
