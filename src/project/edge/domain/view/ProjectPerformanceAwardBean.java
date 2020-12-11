package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

public class ProjectPerformanceAwardBean implements ViewBean {

    private String id;
    private String project_;
    private String projectText;
    private String person_;
    private String personText;
    private String rewardsType_;
    private String rewardsTypeText;
    private Double amount;
    private String executeTime;
    private String executeBasis;
    private String executeReason;
    private String remark;

    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();
    

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getProject_() {
        return project_;
    }

    public void setProject_(String project_) {
        this.project_ = project_;
    }

    public String getProjectText() {
        return projectText;
    }

    public void setProjectText(String projectText) {
        this.projectText = projectText;
    }

    public String getPerson_() {
        return person_;
    }

    public void setPerson_(String person_) {
        this.person_ = person_;
    }

    public String getPersonText() {
        return personText;
    }

    public void setPersonText(String personText) {
        this.personText = personText;
    }

    public String getRewardsType_() {
        return rewardsType_;
    }

    public void setRewardsType_(String rewardsType_) {
        this.rewardsType_ = rewardsType_;
    }

    public String getRewardsTypeText() {
        return rewardsTypeText;
    }

    public void setRewardsTypeText(String rewardsTypeText) {
        this.rewardsTypeText = rewardsTypeText;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getExecuteBasis() {
        return executeBasis;
    }

    public void setExecuteBasis(String executeBasis) {
        this.executeBasis = executeBasis;
    }

    public String getExecuteReason() {
        return executeReason;
    }

    public void setExecuteReason(String executeReason) {
        this.executeReason = executeReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }

    public void setArchivesReservedList(List<String> archiveIdReservedList) {
        this.archivesReservedList = archiveIdReservedList;
    }

    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }

    public void setArchivesList(List<ArchiveBean> archiveList) {
        this.archivesList = archiveList;
    }

}
