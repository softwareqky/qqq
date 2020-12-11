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
@Table(name = "t_project_registration")
public class ProjectRegistration implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6412011082544165264L;

    private String id = UUID.randomUUID().toString();

    private Person applicant;
    private Date applicantDate;
    private Date registrationDate;
    private Double fegistrationFee;
    private String registrationInformation;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = true, length = 29)
    public Date getRegistrationDate() {
        return this.registrationDate;
    }


    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Column(name = "fegistration_fee", nullable = true)
    public Double getFegistrationFee() {
        return this.fegistrationFee;
    }


    public void setFegistrationFee(Double fegistrationFee) {
        this.fegistrationFee = fegistrationFee;
    }

    @Column(name = "registration_information", nullable = true, length = 1024)
    public String getRegistrationInformation() {
        return this.registrationInformation;
    }


    public void setRegistrationInformation(String registrationInformation) {
        this.registrationInformation = registrationInformation;
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
