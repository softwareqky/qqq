/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *         保存专家信息的表现层DTO。
 */
public class ExpertBean implements ViewBean {

    private String id;

    private String person_;
    private String personText;
    private short isInternal;
    private String isInternalText;
    private short isBlackList;
    private String isBlackListText;
    
    private String expertName;
    private Short gender;
    private String genderText;
    private String birthday;
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
    private String photo_;
    private String photoText;
    private String expertDesc;
    private String expertDomain_;
    private String expertDomainText;
    private String speciality;
    private Integer professionalAge;
    private String remark;
    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerson_() {
        return this.person_;
    }

    public void setPerson_(String person_) {
        this.person_ = person_;
    }

    public String getPersonText() {
        return this.personText;
    }

    public void setPersonText(String personText) {
        this.personText = personText;
    }

    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }

    public void setArchivesReservedList(List<String> archiveIdReservedList) {
        this.archivesReservedList = archiveIdReservedList;
    }

    public short getIsInternal() {
        return this.isInternal;
    }

    public void setIsInternal(short isInternal) {
        this.isInternal = isInternal;
    }

    public String getIsInternalText() {
        return this.isInternalText;
    }

    public void setIsInternalText(String isInternalText) {
        this.isInternalText = isInternalText;
    }

    public String getExpertName() {
        return this.expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public Short getGender() {
        return this.gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getGenderText() {
        return this.genderText;
    }

    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdCardNum() {
        return this.idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
    public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getPoliticalStatus() {
        return this.politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getGraduatedFrom() {
        return this.graduatedFrom;
    }

    public void setGraduatedFrom(String graduatedFrom) {
        this.graduatedFrom = graduatedFrom;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getJobMajor() {
		return jobMajor;
	}

	public void setJobMajor(String jobMajor) {
		this.jobMajor = jobMajor;
	}

	public String getEducationRecord() {
        return this.educationRecord;
    }

    public void setEducationRecord(String educationRecord) {
        this.educationRecord = educationRecord;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_() {
        return this.photo_;
    }

    public void setPhoto_(String photo_) {
        this.photo_ = photo_;
    }

    public String getPhotoText() {
        return this.photoText;
    }

    public void setPhotoText(String photoText) {
        this.photoText = photoText;
    }

    public String getExpertDesc() {
        return this.expertDesc;
    }

    public void setExpertDesc(String expertDesc) {
        this.expertDesc = expertDesc;
    }

    public String getExpertDomain_() {
        return this.expertDomain_;
    }

    public void setExpertDomain_(String expertDomain_) {
        this.expertDomain_ = expertDomain_;
    }

    public String getExpertDomainText() {
        return this.expertDomainText;
    }

    public void setExpertDomainText(String expertDomainText) {
        this.expertDomainText = expertDomainText;
    }

    public String getSpeciality() {
        return this.speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getProfessionalAge() {
        return this.professionalAge;
    }

    public void setProfessionalAge(Integer professionalAge) {
        this.professionalAge = professionalAge;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }

    public void setArchivesList(List<ArchiveBean> archiveList) {
        this.archivesList = archiveList;
    }

    
    public short getIsBlackList() {
        return this.isBlackList;
    }

    
    public void setIsBlackList(short isBlackList) {
        this.isBlackList = isBlackList;
    }

    
    public String getIsBlackListText() {
        return this.isBlackListText;
    }

    
    public void setIsBlackListText(String isBlackListText) {
        this.isBlackListText = isBlackListText;
    }
    
}
