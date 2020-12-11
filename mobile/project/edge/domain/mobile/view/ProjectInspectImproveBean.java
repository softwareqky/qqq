package project.edge.domain.mobile.view;

/**
 * @author angel_000
 *         巡查整改提交,每个巡查基本信息
 */
public class ProjectInspectImproveBean {

    private String projectInspectId;
    private String improvePlanDate;
    private String improveActualDate;
    private String improveReq;
    private String improvePlan;
    private String remark;

    public String getProjectInspectId() {
        return this.projectInspectId;
    }

    public void setProjectInspectId(String projectInspectId) {
        this.projectInspectId = projectInspectId;
    }

    public String getImprovePlanDate() {
        return this.improvePlanDate;
    }

    public void setImprovePlanDate(String improvePlanDate) {
        this.improvePlanDate = improvePlanDate;
    }

    public String getImproveActualDate() {
        return this.improveActualDate;
    }

    public void setImproveActualDate(String improveActualDate) {
        this.improveActualDate = improveActualDate;
    }

    public String getImproveReq() {
        return this.improveReq;
    }

    public void setImproveReq(String improveReq) {
        this.improveReq = improveReq;
    }

    public String getImprovePlan() {
        return this.improvePlan;
    }

    public void setImprovePlan(String improvePlan) {
        this.improvePlan = improvePlan;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
