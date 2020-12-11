package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/*
 * 中落标管理
 */
public class BidWinningBean implements ViewBean {


    private String id;
    private String projectNo;
    private String projectName;
    private String projectStatus;
    private String projectType;

    private String initApplyDateText;

    private String constructionUnit;

    private String projectManager;

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

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public String getInitApplyDateText() {
        return this.initApplyDateText;
    }

    public String getProcessStatus() {
        return this.processStatus;
    }

    public String getProjectManager() {
        return this.projectManager;
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

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

}
