/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class QualityObjectiveBean implements ViewBean {

    private String id;

    private String project_;
    private String projectText;

    private String taskCode;
    private String taskName;
    private int objecttiveType;
    private String objecttiveTypeText;
    private String leader_;
    private String leaderText;
    private String remark;
    private String specification_;
    private String specificationText;
    
    // 附件列表&
    private List<ArchiveBean> archivesList = new ArrayList<>();
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }


    public String getTaskCode() {
        return this.taskCode;
    }


    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }


    public String getTaskName() {
        return this.taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public int getObjecttiveType() {
        return this.objecttiveType;
    }


    public void setObjecttiveType(int objecttiveType) {
        this.objecttiveType = objecttiveType;
    }


    public String getObjecttiveTypeText() {
        return this.objecttiveTypeText;
    }

    public void setObjecttiveTypeText(String objecttiveTypeText) {
        this.objecttiveTypeText = objecttiveTypeText;
    }

    public String getLeader_() {
        return this.leader_;
    }


    public void setLeader_(String leader_) {
        this.leader_ = leader_;
    }


    public String getLeaderText() {
        return this.leaderText;
    }


    public void setLeaderText(String leaderText) {
        this.leaderText = leaderText;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getSpecification_() {
        return this.specification_;
    }


    public void setSpecification_(String specification_) {
        this.specification_ = specification_;
    }


    public String getSpecificationText() {
        return this.specificationText;
    }


    public void setSpecificationText(String specificationText) {
        this.specificationText = specificationText;
    }


    public void setId(String id) {
        this.id = id;
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


	public List<ArchiveBean> getArchivesList() {
		return archivesList;
	}


	public void setArchivesList(List<ArchiveBean> archivesList) {
		this.archivesList = archivesList;
	}


	public List<String> getArchivesReservedList() {
		return archivesReservedList;
	}


	public void setArchivesReservedList(List<String> archivesReservedList) {
		this.archivesReservedList = archivesReservedList;
	}

}
