package project.edge.domain.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import garage.origin.domain.entity.AbstractDataFields;

@Entity
@Table(name = "t_data_fields",
        uniqueConstraints = @UniqueConstraint(columnNames = {"data_type", "page_id", "field_name"}))
public class DataFields extends AbstractDataFields implements java.io.Serializable {

    private static final long serialVersionUID = -1114455125052679085L;

    private String pageId;
    private String columnName;
    private short isWeb;
    private short isApp;

    // private short isSyn;
    // private int location;
    private String modifier;
    private Date mDatetime;
    // private Integer mLocation;
    private String remark;

    private Set<User> users = new HashSet<User>(0);

    @Column(name = "column_name", nullable = true, length = 100)
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    // @Column(name = "is_syn", nullable = false)
    // public short getIsSyn() {
    // return this.isSyn;
    // }
    //
    // public void setIsSyn(short isSyn) {
    // this.isSyn = isSyn;
    // }
    //
    // @Column(name = "location", nullable = false)
    // public int getLocation() {
    // return this.location;
    // }
    //
    // public void setLocation(int location) {
    // this.location = location;
    // }

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

    // @Column(name = "m_location")
    // public Integer getmLocation() {
    // return this.mLocation;
    // }
    //
    // public void setmLocation(Integer mLocation) {
    // this.mLocation = mLocation;
    // }

    @Column(name = "remark", length = 500)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "page_id", nullable = false, length = 50)
    public String getPageId() {
        return this.pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    @Column(name = "is_web", nullable = false)
    public short getIsWeb() {
        return this.isWeb;
    }

    public void setIsWeb(short isWeb) {
        this.isWeb = isWeb;
    }

    @Column(name = "is_app", nullable = false)
    public short getIsApp() {
        return this.isApp;
    }

    public void setIsApp(short isApp) {
        this.isApp = isApp;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_data_fields",
            joinColumns = {
                    @JoinColumn(name = "data_fields_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", nullable = false, updatable = false)})
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
