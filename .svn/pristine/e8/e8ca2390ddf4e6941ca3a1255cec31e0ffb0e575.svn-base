package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_expert")
public class Expert implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -729859855153697763L;

    private String id = UUID.randomUUID().toString();

    private Person person;
    private short isInternal;
    private String expertName;
    private Short gender;
    private Date birthday;
    private String idCardNum;
    private String bankCardNum;
    private String politicalStatus;
    private String graduatedFrom;
    private String major;
    private String jobMajor;
    private String educationRecord;
    private String degree;
    private String company;
    private String jobTitle;
    private String region;
    private String mobile;
    private String email;
    private Archive photo;
    private String expertDesc;
    private DataOption expertDomain;
    private String speciality;
    private Integer professionalAge;
    private String remark;
    private short isDeleted;
    private short isBlacklist;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<Review> reviews = new HashSet<Review>(0);
    private Set<ExpertAttachment> expertAttachments = new HashSet<>(0);
    private List<ExpertAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public Expert clone() {
        Expert p = null;
        try {
            p = (Expert) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

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
    @JoinColumn(name = "person_id", nullable = true)
    public Person getPerson() {
        return this.person;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    @Column(name = "is_internal", nullable = false)
    public short getIsInternal() {
        return this.isInternal;
    }


    public void setIsInternal(short isInternal) {
        this.isInternal = isInternal;
    }

    @Column(name = "expert_name", nullable = false, length = 20)
    public String getExpertName() {
        return this.expertName;
    }


    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    @Column(name = "gender", nullable = true)
    public Short getGender() {
        return this.gender;
    }


    public void setGender(Short gender) {
        this.gender = gender;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday", nullable = true, length = 29)
    public Date getBirthday() {
        return this.birthday;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    @Column(name = "id_card_num", nullable = true, length = 50)
    public String getIdCardNum() {
        return this.idCardNum;
    }


    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
    @Column(name = "bank_card_num", nullable = true, length = 50)
    public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	@Column(name = "political_status", nullable = true, length = 50)
    public String getPoliticalStatus() {
        return this.politicalStatus;
    }


    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    @Column(name = "graduated_from", nullable = true, length = 50)
    public String getGraduatedFrom() {
        return this.graduatedFrom;
    }


    public void setGraduatedFrom(String graduatedFrom) {
        this.graduatedFrom = graduatedFrom;
    }

    @Column(name = "major", nullable = true, length = 50)
    public String getMajor() {
        return this.major;
    }


    public void setMajor(String major) {
        this.major = major;
    }
    

    @Column(name = "job_major", nullable = true, length = 50)
    public String getJobMajor() {
		return jobMajor;
	}

	public void setJobMajor(String jobMajor) {
		this.jobMajor = jobMajor;
	}

	@Column(name = "education_record", nullable = true, length = 50)
    public String getEducationRecord() {
        return this.educationRecord;
    }


    public void setEducationRecord(String educationRecord) {
        this.educationRecord = educationRecord;
    }

    @Column(name = "degree", nullable = true, length = 50)
    public String getDegree() {
        return this.degree;
    }


    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Column(name = "company", nullable = true, length = 250)
    public String getCompany() {
        return this.company;
    }


    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "job_title", nullable = true, length = 50)
    public String getJobTitle() {
        return this.jobTitle;
    }


    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Column(name = "region", nullable = true, length = 50)
    public String getRegion() {
        return this.region;
    }


    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "mobile", nullable = true, length = 50)
    public String getMobile() {
        return this.mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo", nullable = true)
    public Archive getPhoto() {
        return this.photo;
    }


    public void setPhoto(Archive photo) {
        this.photo = photo;
    }

    @Column(name = "expert_desc", nullable = true, length = 1024)
    public String getExpertDesc() {
        return this.expertDesc;
    }


    public void setExpertDesc(String expertDesc) {
        this.expertDesc = expertDesc;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_domain", nullable = true)
    public DataOption getExpertDomain() {
        return this.expertDomain;
    }


    public void setExpertDomain(DataOption expertDomain) {
        this.expertDomain = expertDomain;
    }

    @Column(name = "speciality", nullable = true, length = 50)
    public String getSpeciality() {
        return this.speciality;
    }


    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Column(name = "professional_age", nullable = true)
    public Integer getProfessionalAge() {
        return this.professionalAge;
    }


    public void setProfessionalAge(Integer professionalAge) {
        this.professionalAge = professionalAge;
    }

    @Column(name = "remark", nullable = true, length = 1024)
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
    
    @Column(name = "is_blacklist", nullable = false)
    public short getIsBlacklist() {
        return this.isBlacklist;
    }

    
    public void setIsBlacklist(short isBlacklist) {
        this.isBlacklist = isBlacklist;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_review_expert",
            joinColumns = {@JoinColumn(name = "expert_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "review_id", nullable = false, updatable = false)})
    public Set<Review> getReviews() {
        return this.reviews;
    }


    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "expert")
    public Set<ExpertAttachment> getExpertAttachments() {
        return this.expertAttachments;
    }

    public void setExpertAttachments(Set<ExpertAttachment> expertAttachments) {
        this.expertAttachments = expertAttachments;
    }

    @Transient
    public List<ExpertAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<ExpertAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
