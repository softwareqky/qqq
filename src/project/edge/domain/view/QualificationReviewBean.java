package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/*
 * 资格审核
 */
public class QualificationReviewBean implements ViewBean {

    private String id;
    private String projectNo;
    private String projectName;
    private String projectStatus;
    private String projectType;

    private String initApplyDateText;

    private String constructionUnit;

    private String projectTracker;


    private String constructionCost;


    private String engineeringCycle;


    private String processStatus;


    public String getConstructionCost() {
        return this.constructionCost;
    }


    public String getConstructionUnit() {
        return this.constructionUnit;
    }


    public String getEngineeringCycle() {
        return this.engineeringCycle;
    }


    public String getId() {
        return this.id;
    }


    public String getInitApplyDateText() {
        return this.initApplyDateText;
    }


    public String getProcessStatus() {
        return this.processStatus;
    }


    public String getProjectName() {
        return this.projectName;
    }


    public String getProjectNo() {
        return this.projectNo;
    }


    public String getProjectStatus() {
        return this.projectStatus;
    }


    public String getProjectTracker() {
        return this.projectTracker;
    }


    public String getProjectType() {
        return this.projectType;
    }


    public void setConstructionCost(String constructionCost) {
        this.constructionCost = constructionCost;
    }


    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }


    public void setEngineeringCycle(String engineeringCycle) {
        this.engineeringCycle = engineeringCycle;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setInitApplyDateText(String initApplyDateText) {
        this.initApplyDateText = initApplyDateText;
    }


    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void setProjectTracker(String projectTracker) {
        this.projectTracker = projectTracker;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
