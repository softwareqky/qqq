/**
 * 
 */
package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class PrequalificationBean implements ViewBean {

    private String id;

    private String applicant_;
    private String applicantText;
    
    private String applicantDate;
    
    private String project_;
    private String projectText;
    
    private String projectKind_;
    private String projectKindText;
    
    private String constructionUnit;
    private Short isBusinessSupport;
    private String isBusinessSupportText;
    
    private String headman;
    
    private String closingDate;
    private String pretrialDate;
    
    private double qualificationAuditDeposit;
    
    private String registrationDate;
    private String prequalificationInformation;
    
    private String remark;
    private String specialCaseDescription;

    @Override
    public String getId() {
        return this.id;
    }

    
    public String getApplicant_() {
        return this.applicant_;
    }

    
    public void setApplicant_(String applicant_) {
        this.applicant_ = applicant_;
    }

    
    public String getApplicantText() {
        return this.applicantText;
    }

    
    public void setApplicantText(String applicantText) {
        this.applicantText = applicantText;
    }

    
    public String getApplicantDate() {
        return this.applicantDate;
    }

    
    public void setApplicantDate(String applicantDate) {
        this.applicantDate = applicantDate;
    }

    
    public String getProject_() {
        return this.project_;
    }

    
    public void setProject_(String project_) {
        this.project_ = project_;
    }

    
    public String getProjectText() {
        return this.projectText;
    }

    
    public void setProjectText(String projectText) {
        this.projectText = projectText;
    }

    
    public String getProjectKind_() {
        return this.projectKind_;
    }

    
    public void setProjectKind_(String projectKind_) {
        this.projectKind_ = projectKind_;
    }

    
    public String getProjectKindText() {
        return this.projectKindText;
    }

    
    public void setProjectKindText(String projectKindText) {
        this.projectKindText = projectKindText;
    }

    
    public String getConstructionUnit() {
        return this.constructionUnit;
    }

    
    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    
    public Short getIsBusinessSupport() {
        return this.isBusinessSupport;
    }

    
    public void setIsBusinessSupport(Short isBusinessSupport) {
        this.isBusinessSupport = isBusinessSupport;
    }

    
    public String getIsBusinessSupportText() {
        return this.isBusinessSupportText;
    }

    
    public void setIsBusinessSupportText(String isBusinessSupportText) {
        this.isBusinessSupportText = isBusinessSupportText;
    }

    
    public String getHeadman() {
        return this.headman;
    }

    
    public void setHeadman(String headman) {
        this.headman = headman;
    }

    
    
    public String getClosingDate() {
        return this.closingDate;
    }


    
    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }


    
    public String getPretrialDate() {
        return this.pretrialDate;
    }


    
    public void setPretrialDate(String pretrialDate) {
        this.pretrialDate = pretrialDate;
    }


    public double getQualificationAuditDeposit() {
        return this.qualificationAuditDeposit;
    }

    
    public void setQualificationAuditDeposit(double qualificationAuditDeposit) {
        this.qualificationAuditDeposit = qualificationAuditDeposit;
    }

    
    public String getRegistrationDate() {
        return this.registrationDate;
    }

    
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    
    public String getPrequalificationInformation() {
        return this.prequalificationInformation;
    }

    
    public void setPrequalificationInformation(String prequalificationInformation) {
        this.prequalificationInformation = prequalificationInformation;
    }

    
    public String getRemark() {
        return this.remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public String getSpecialCaseDescription() {
        return this.specialCaseDescription;
    }

    
    public void setSpecialCaseDescription(String specialCaseDescription) {
        this.specialCaseDescription = specialCaseDescription;
    }

    
    public void setId(String id) {
        this.id = id;
    }
}
