package project.edge.domain.mobile.view;

/**
 * @author angel_000
 *         检查验收提交,每个检查基本信息
 */
public class ProjectCheckVerifyBean {

    private String projectCheckId;
    private String verifyResult;
    private String verifyDate;
    private String improveResultVerify;
    private String remark;

    public String getProjectCheckId() {
        return this.projectCheckId;
    }

    public void setProjectCheckId(String projectCheckId) {
        this.projectCheckId = projectCheckId;
    }

    public String getVerifyResult() {
        return this.verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getVerifyDate() {
        return this.verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getImproveResultVerify() {
        return this.improveResultVerify;
    }

    public void setImproveResultVerify(String improveResultVerify) {
        this.improveResultVerify = improveResultVerify;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
