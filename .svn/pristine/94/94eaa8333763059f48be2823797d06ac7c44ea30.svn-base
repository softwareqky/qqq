package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "t_role", uniqueConstraints = @UniqueConstraint(columnNames = "role_name"))
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = -7675652677011487551L;

    private String id = UUID.randomUUID().toString();
    private String roleName;
    private short isSystem;
    private short isSuper;
    private short isDeleted;
    private String remark;

    // private int location;
    // private short accessAllA;
    // private short accessAllB;

    private String creator;
    private Date cDatetime;
    // private int cLocation;
    private String modifier;
    private Date mDatetime;
    // private Integer mLocation;

    private Set<Page> pages = new HashSet<Page>(0);
    

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

    @Column(name = "is_system", nullable = false)
    public short getIsSystem() {
        return this.isSystem;
    }

    public void setIsSystem(short isSystem) {
        this.isSystem = isSystem;
    }

    // @Column(name = "location", nullable = false)
    // public int getLocation() {
    // return this.location;
    // }
    //
    // public void setLocation(int location) {
    // this.location = location;
    // }

    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "remark", nullable = true, length = 500)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // @Column(name = "access_all_a", nullable = false)
    // public short getAccessAllA() {
    // return this.accessAllA;
    // }
    //
    // public void setAccessAllA(short accessAllA) {
    // this.accessAllA = accessAllA;
    // }
    //
    // @Column(name = "access_all_b", nullable = false)
    // public short getAccessAllB() {
    // return this.accessAllB;
    // }
    //
    // public void setAccessAllB(short accessAllB) {
    // this.accessAllB = accessAllB;
    // }

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

    // @Column(name = "c_location", nullable = false)
    // public int getcLocation() {
    // return this.cLocation;
    // }
    //
    // public void setcLocation(int cLocation) {
    // this.cLocation = cLocation;
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



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_page",
            joinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "page_id", nullable = false, updatable = false)})
    public Set<Page> getPages() {
        return this.pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    @Column(name = "is_super", nullable = false)
    public short getIsSuper() {
        return this.isSuper;
    }


    public void setIsSuper(short isSuper) {
        this.isSuper = isSuper;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

}
