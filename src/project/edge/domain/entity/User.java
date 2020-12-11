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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "t_user", uniqueConstraints = @UniqueConstraint(columnNames = "login_name"))
public class User implements java.io.Serializable {

    private static final long serialVersionUID = -7350452085045476059L;

    private String id = UUID.randomUUID().toString();

    private Role role;
    private Person person;

    private String loginName;
    private String loginPwd;
    // private String userName;
    private short isSystem;
    // private String phone;
    // private String email;
    private short isSuper;
    private Date passwordChangeTime;
    private short isDeleted;

    private String creator;
    private Date cDatetime;
    // private int cLocation;
    private String modifier;
    private Date mDatetime;
    // private Integer mLocation;
    // private String remark;

    private Set<DataFields> dataFields = new HashSet<DataFields>(0);

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

    @Column(name = "login_name", unique = true, nullable = false, length = 50)
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "login_pwd", nullable = false, length = 255)
    public String getLoginPwd() {
        return this.loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    // @Column(name = "user_name", length = 50)
    // public String getUserName() {
    // return this.userName;
    // }
    //
    // public void setUserName(String userName) {
    // this.userName = userName;
    // }
    //
    // @Column(name = "phone", length = 50)
    // public String getPhone() {
    // return this.phone;
    // }
    //
    // public void setPhone(String phone) {
    // this.phone = phone;
    // }
    //
    // @Column(name = "email", length = 50)
    // public String getEmail() {
    // return this.email;
    // }
    //
    // public void setEmail(String email) {
    // this.email = email;
    // }

    @Column(name = "is_super", nullable = false)
    public short getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(short isSuper) {
        this.isSuper = isSuper;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    public Role getRole() {
        return this.role;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = true)
    public Person getPerson() {
        return this.person;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "password_change_time", nullable = true, length = 29)
    public Date getPasswordChangeTime() {
        return this.passwordChangeTime;
    }


    public void setPasswordChangeTime(Date passwordChangeTime) {
        this.passwordChangeTime = passwordChangeTime;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_data_fields",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "data_fields_id", nullable = false, updatable = false)})
    public Set<DataFields> getDataFields() {
        return this.dataFields;
    }


    public void setDataFields(Set<DataFields> dataFields) {
        this.dataFields = dataFields;
    }


    // @Column(name = "m_location")
    // public Integer getmLocation() {
    // return this.mLocation;
    // }
    //
    // public void setmLocation(Integer mLocation) {
    // this.mLocation = mLocation;
    // }
    //
    // @Column(name = "remark", length = 500)
    // public String getRemark() {
    // return this.remark;
    // }
    //
    // public void setRemark(String remark) {
    // this.remark = remark;
    // }



}
