/**
 * 
 */
package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class ProjectRegistrationBean implements ViewBean {

    private String id;

    private String applicant_;
    private String applicantText;

    private String applicantDate;
    private String registrationDate;

    private Double fegistrationFee;
    private String registrationInformation;
    private String remark;

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


    public String getRegistrationDate() {
        return this.registrationDate;
    }


    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }


    public Double getFegistrationFee() {
        return this.fegistrationFee;
    }


    public void setFegistrationFee(Double fegistrationFee) {
        this.fegistrationFee = fegistrationFee;
    }


    public String getRegistrationInformation() {
        return this.registrationInformation;
    }


    public void setRegistrationInformation(String registrationInformation) {
        this.registrationInformation = registrationInformation;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public void setId(String id) {
        this.id = id;
    }

}
