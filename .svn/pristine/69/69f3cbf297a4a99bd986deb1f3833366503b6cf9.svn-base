package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存项目成员变更申请表的表现层DTO。
 */
public class ProjectPersonChangeBean implements ViewBean {

    private String id;
    private String project_;
    private String projectText;
    private String changeReason;
    private String changeRemark;
    private String flowStartDate;
    private String flowEndDate;
    private int flowStatus;
    private String flowStatusText;
	
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


    public String getChangeReason() {
        return this.changeReason;
    }


    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }


    public String getChangeRemark() {
        return this.changeRemark;
    }


    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }


    public String getFlowStartDate() {
        return this.flowStartDate;
    }


    public void setFlowStartDate(String flowStartDate) {
        this.flowStartDate = flowStartDate;
    }


    public String getFlowEndDate() {
        return this.flowEndDate;
    }


    public void setFlowEndDate(String flowEndDate) {
        this.flowEndDate = flowEndDate;
    }


    public int getFlowStatus() {
        return this.flowStatus;
    }


    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }


    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }

    public void setArchivesList(List<ArchiveBean> archiveList) {
        this.archivesList = archiveList;
    }

    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }

    public void setArchivesReservedList(List<String> archiveIdReservedList) {
        this.archivesReservedList = archiveIdReservedList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlowStatusText() {
        return this.flowStatusText;
    }

    public void setFlowStatusText(String flowStatusText) {
        this.flowStatusText = flowStatusText;
    }
}
