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
@Table(name = "t_person")
public class Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2492284053684041658L;

    private String id = UUID.randomUUID().toString();

    private Org org;
    private Dept dept;
    private Post post;
    private User user;
    private String jobNum;
    private String personName;
    private Integer accountType;
    private String securityLevel;
    private Short gender;
    private Date birthday;
    private String ethnicGroup;
    private String nativePlace;
    private String regResidence;
    private String idCardNum;
    private Short maritalStatus;
    private String politicalStatus;
    private Date beMemberDate;
    private Date bePartyDate;
    private Short isLabourUnion;
    private String major;
    private String educationRecord;
    private String degree;
    private String healthInfo;
    private Double height;
    private Double weight;
    private String residence;
    private String homeAddress;
    private String tempResidentNum;
    private Date contractStartDate;
    private Date contractEndDate;
    private String jobTitle;
    private String jobGroup;
    private String jobCall;
    private String jobLevel;
    private String jobDesc;
    private String managerId;
    private String assistantId;
    private Integer status;
    private String location;
    private String workRoom;
    private String phone;
    private String mobile;
    private String otherPhone;
    private String fax;
    private String email;
    private Archive photo;
    // private String photoPath;
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
    @JoinColumn(name = "org_id", nullable = false)
    public Org getOrg() {
        return this.org;
    }


    public void setOrg(Org org) {
        this.org = org;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    public Dept getDept() {
        return this.dept;
    }


    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    public Post getPost() {
        return this.post;
    }


    public void setPost(Post post) {
        this.post = post;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "job_num", nullable = false, length = 50)
    public String getJobNum() {
        return this.jobNum;
    }


    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    @Column(name = "person_name", nullable = false, length = 20)
    public String getPersonName() {
        return this.personName;
    }


    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Column(name = "account_type", nullable = true)
    public Integer getAccountType() {
        return this.accountType;
    }


    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Column(name = "security_level", nullable = true, length = 20)
    public String getSecurityLevel() {
        return this.securityLevel;
    }


    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
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

    @Column(name = "ethnic_group", nullable = true, length = 20)
    public String getEthnicGroup() {
        return this.ethnicGroup;
    }


    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    @Column(name = "native_place", nullable = true, length = 50)
    public String getNativePlace() {
        return this.nativePlace;
    }


    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    @Column(name = "reg_residence", nullable = true, length = 50)
    public String getRegResidence() {
        return this.regResidence;
    }


    public void setRegResidence(String regResidence) {
        this.regResidence = regResidence;
    }

    @Column(name = "id_card_num", nullable = true, length = 50)
    public String getIdCardNum() {
        return this.idCardNum;
    }


    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    @Column(name = "marital_status", nullable = true)
    public Short getMaritalStatus() {
        return this.maritalStatus;
    }


    public void setMaritalStatus(Short maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Column(name = "political_status", nullable = true, length = 50)
    public String getPoliticalStatus() {
        return this.politicalStatus;
    }


    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "be_member_date", nullable = true, length = 29)
    public Date getBeMemberDate() {
        return this.beMemberDate;
    }


    public void setBeMemberDate(Date beMemberDate) {
        this.beMemberDate = beMemberDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "be_party_date", nullable = true, length = 29)
    public Date getBePartyDate() {
        return this.bePartyDate;
    }


    public void setBePartyDate(Date bePartyDate) {
        this.bePartyDate = bePartyDate;
    }

    @Column(name = "is_labour_union", nullable = true)
    public Short getIsLabourUnion() {
        return this.isLabourUnion;
    }


    public void setIsLabourUnion(Short isLabourUnion) {
        this.isLabourUnion = isLabourUnion;
    }


    @Column(name = "major", nullable = true, length = 50)
    public String getMajor() {
        return this.major;
    }


    public void setMajor(String major) {
        this.major = major;
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

    @Column(name = "health_info", nullable = true, length = 50)
    public String getHealthInfo() {
        return this.healthInfo;
    }


    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
    }

    @Column(name = "height", nullable = true)
    public Double getHeight() {
        return this.height;
    }


    public void setHeight(Double height) {
        this.height = height;
    }

    @Column(name = "weight", nullable = true)
    public Double getWeight() {
        return this.weight;
    }


    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "residence", nullable = true, length = 50)
    public String getResidence() {
        return this.residence;
    }


    public void setResidence(String residence) {
        this.residence = residence;
    }

    @Column(name = "home_address", nullable = true, length = 50)
    public String getHomeAddress() {
        return this.homeAddress;
    }


    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Column(name = "temp_resident_num", nullable = true, length = 50)
    public String getTempResidentNum() {
        return this.tempResidentNum;
    }


    public void setTempResidentNum(String tempResidentNum) {
        this.tempResidentNum = tempResidentNum;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_start_date", nullable = true, length = 29)
    public Date getContractStartDate() {
        return this.contractStartDate;
    }


    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_end_date", nullable = true, length = 29)
    public Date getContractEndDate() {
        return this.contractEndDate;
    }


    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    @Column(name = "job_title", nullable = true, length = 50)
    public String getJobTitle() {
        return this.jobTitle;
    }


    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Column(name = "job_group", nullable = true, length = 50)
    public String getJobGroup() {
        return this.jobGroup;
    }


    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    @Column(name = "job_call", nullable = true, length = 50)
    public String getJobCall() {
        return this.jobCall;
    }


    public void setJobCall(String jobCall) {
        this.jobCall = jobCall;
    }

    @Column(name = "job_level", nullable = true, length = 50)
    public String getJobLevel() {
        return this.jobLevel;
    }


    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    @Column(name = "job_desc", nullable = true, length = 1024)
    public String getJobDesc() {
        return this.jobDesc;
    }


    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Column(name = "manager_id", nullable = true, length = 36)
    public String getManagerId() {
        return this.managerId;
    }


    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    @Column(name = "assistant_id", nullable = true, length = 36)
    public String getAssistantId() {
        return this.assistantId;
    }


    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return this.status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "location", nullable = true, length = 50)
    public String getLocation() {
        return this.location;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "work_room", nullable = true, length = 50)
    public String getWorkRoom() {
        return this.workRoom;
    }


    public void setWorkRoom(String workRoom) {
        this.workRoom = workRoom;
    }

    @Column(name = "phone", nullable = true, length = 50)
    public String getPhone() {
        return this.phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "mobile", nullable = true, length = 50)
    public String getMobile() {
        return this.mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "other_phone", nullable = true, length = 50)
    public String getOtherPhone() {
        return this.otherPhone;
    }


    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    @Column(name = "fax", nullable = true, length = 50)
    public String getFax() {
        return this.fax;
    }


    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo")
    public Archive getPhoto() {
        return this.photo;
    }


    public void setPhoto(Archive photo) {
        this.photo = photo;
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
