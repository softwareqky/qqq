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
@Table(name = "t_prequalification")
public class Prequalification implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4091972787328270771L;

    private String id = UUID.randomUUID().toString();

    private Person applicant;
    private Date applicantDate;
    private Project project;
    private DataOption projectKind;
    private String constructionUnit;
    private Short isBusinessSupport;
    private String headman;
    private Date closingDate;
    private Date pretrialDate;
    private double qualificationAuditDeposit;
    private Date registrationDate;
    private String prequalificationInformation;
    private String remark;
    private String specialCaseDescription;
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
    @JoinColumn(name = "applicant", nullable = false)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applicant_date", nullable = false, length = 29)
    public Date getApplicantDate() {
        return this.applicantDate;
    }


    public void setApplicantDate(Date applicantDate) {
        this.applicantDate = applicantDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_kind", nullable = true)
    public DataOption getProjectKind() {
        return this.projectKind;
    }


    public void setProjectKind(DataOption projectKind) {
        this.projectKind = projectKind;
    }

    @Column(name = "construction_unit", nullable = true, length = 50)
    public String getConstructionUnit() {
        return this.constructionUnit;
    }


    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    @Column(name = "is_business_support", nullable = true)
    public Short getIsBusinessSupport() {
        return this.isBusinessSupport;
    }


    public void setIsBusinessSupport(Short isBusinessSupport) {
        this.isBusinessSupport = isBusinessSupport;
    }

    @Column(name = "headman", nullable = false, length = 36)
    public String getHeadman() {
        return this.headman;
    }


    public void setHeadman(String headman) {
        this.headman = headman;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "closing_date", nullable = false, length = 29)
    public Date getClosingDate() {
        return this.closingDate;
    }


    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pretrial_date", nullable = false, length = 29)
    public Date getPretrialDate() {
        return this.pretrialDate;
    }


    public void setPretrialDate(Date pretrialDate) {
        this.pretrialDate = pretrialDate;
    }

    @Column(name = "qualification_audit_deposit", nullable = false)
    public double getQualificationAuditDeposit() {
        return this.qualificationAuditDeposit;
    }


    public void setQualificationAuditDeposit(double qualificationAuditDeposit) {
        this.qualificationAuditDeposit = qualificationAuditDeposit;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false, length = 29)
    public Date getRegistrationDate() {
        return this.registrationDate;
    }


    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Column(name = "prequalification_information", nullable = true, length = 1024)
    public String getPrequalificationInformation() {
        return this.prequalificationInformation;
    }


    public void setPrequalificationInformation(String prequalificationInformation) {
        this.prequalificationInformation = prequalificationInformation;
    }


    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "special_case_description", nullable = true, length = 1024)
    public String getSpecialCaseDescription() {
        return this.specialCaseDescription;
    }


    public void setSpecialCaseDescription(String specialCaseDescription) {
        this.specialCaseDescription = specialCaseDescription;
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
