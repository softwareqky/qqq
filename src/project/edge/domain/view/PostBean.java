package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存岗位信息表的表现层DTO。
 */
public class PostBean implements ViewBean {

    private String id;

    private String org_;
    private String orgText;
    private String dept_;
    private String deptText;
    private String postName;
    private String shortName;
    private String responsibility;
    private String qualification;
    private String relatedDoc;
    private String remark;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrg_() {
        return this.org_;
    }


    public void setOrg_(String org_) {
        this.org_ = org_;
    }


    public String getOrgText() {
        return this.orgText;
    }


    public void setOrgText(String orgText) {
        this.orgText = orgText;
    }


    public String getDept_() {
        return this.dept_;
    }


    public void setDept_(String dept_) {
        this.dept_ = dept_;
    }


    public String getDeptText() {
        return this.deptText;
    }


    public void setDeptText(String deptText) {
        this.deptText = deptText;
    }


    public String getPostName() {
        return this.postName;
    }


    public void setPostName(String postName) {
        this.postName = postName;
    }


    public String getShortName() {
        return this.shortName;
    }


    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    public String getResponsibility() {
        return this.responsibility;
    }


    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }


    public String getQualification() {
        return this.qualification;
    }


    public void setQualification(String qualification) {
        this.qualification = qualification;
    }


    public String getRelatedDoc() {
        return this.relatedDoc;
    }


    public void setRelatedDoc(String relatedDoc) {
        this.relatedDoc = relatedDoc;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }



}
