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

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_project_set")
public class ProjectSet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1915959145163902514L;

    private String id = UUID.randomUUID().toString();

    private String governmentNum;
    private String projectNum;
    private String projectName;
    private DataOption projectKind;
    private Person applicant;
    private String applicantMobile;
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

    @Column(name = "government_project_num", nullable = false, length = 50)
    public String getGovernmentNum() {
        return this.governmentNum;
    }
    
    public void setGovernmentNum(String governmentNum) {
        this.governmentNum = governmentNum;
    }

    @Column(name = "project_num", nullable = false, length = 50)
    public String getProjectNum() {
        return this.projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    @Column(name = "project_name", nullable = false, length = 50)
    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_kind_id", nullable = true)
    public DataOption getProjectKind() {
        return this.projectKind;
    }


    public void setProjectKind(DataOption projectKind) {
        this.projectKind = projectKind;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = true)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }


    @Column(name = "applicant_mobile", nullable = true, length = 50)
    public String getApplicantMobile() {
        return this.applicantMobile;
    }


    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
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
