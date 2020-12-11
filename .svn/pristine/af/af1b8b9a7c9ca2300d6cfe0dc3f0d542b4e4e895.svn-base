package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "t_system_log")
public class SystemLog implements java.io.Serializable {

    private static final long serialVersionUID = -1320462045345276238L;

    private String id = UUID.randomUUID().toString();

    private String loginName;
    private String userIp;
    private Person person;
    private String personName;
    private String action;
    private String description;
    private String detailInfo;
    private Date cDatetime;
    private String remark;

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

    @Column(name = "login_name", nullable = false)
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "user_ip", nullable = true, length = 50)
    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = true)
    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Column(name = "person_name", nullable = true)
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Column(name = "action", nullable = false, length = 255)
    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Column(name = "description", nullable = false, length = 255)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "detail_info", nullable = true, length = 500)
    public String getDetailInfo() {
        return this.detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }

    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "remark", nullable = true, length = 500)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
