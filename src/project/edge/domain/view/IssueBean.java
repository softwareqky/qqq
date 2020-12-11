/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *         保存问题信息的表现层DTO。
 */
public class IssueBean implements ViewBean {

    private String id;

    private String project_;
    private String projectText;
    private String virtualOrg_;
    private String virtualOrgText;
    private String issueTitle;
    private String issueType_;
    private String issueTypeText;
    private String issuePriority_;
    private String issuePriorityText;
    private String inputDate;
    private String assignTo_;
    private String assignToText;
    private String internalVerify;
    private String internalVerifyFeedback;
    private String externalVerify;
    private String externalVerifyFeedback;
    private String issueDesc;
    private String reassignTo_;
    private String reassignToText;
    private String reassignReason;
    private String solveContent;
    private String solveDatetime;
    private String solveStatus_;
    private String solveStatusText;
    private String creator_;
    private String creatorText;

    private String uploadFileType;

    private List<ArchiveBean> archiveList = new ArrayList<>();
    private List<String> archiveReservedList = new ArrayList<>(); // 修改时，保留的档案文件id列表

    private List<ArchiveBean> slovearchiveList = new ArrayList<>();
    private List<String> solveArchiveReservedList = new ArrayList<>(); // 修改时，保留的解决后档案文件id列表

    public String getId() {
        return this.id;
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


    public String getVirtualOrg_() {
        return this.virtualOrg_;
    }


    public void setVirtualOrg_(String virtualOrg_) {
        this.virtualOrg_ = virtualOrg_;
    }


    public String getVirtualOrgText() {
        return this.virtualOrgText;
    }


    public void setVirtualOrgText(String virtualOrgText) {
        this.virtualOrgText = virtualOrgText;
    }

    public String getIssueTitle() {
        return this.issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueType_() {
        return this.issueType_;
    }

    public void setIssueType_(String issueType_) {
        this.issueType_ = issueType_;
    }

    public String getIssueTypeText() {
        return this.issueTypeText;
    }

    public void setIssueTypeText(String issueTypeText) {
        this.issueTypeText = issueTypeText;
    }

    public String getInputDate() {
        return this.inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getAssignTo_() {
        return this.assignTo_;
    }

    public void setAssignTo_(String assignTo_) {
        this.assignTo_ = assignTo_;
    }

    public String getAssignToText() {
        return this.assignToText;
    }

    public void setAssignToText(String assignToText) {
        this.assignToText = assignToText;
    }

    public String getInternalVerify() {
        return this.internalVerify;
    }

    public void setInternalVerify(String internalVerify) {
        this.internalVerify = internalVerify;
    }

    public String getInternalVerifyFeedback() {
        return this.internalVerifyFeedback;
    }

    public void setInternalVerifyFeedback(String internalVerifyFeedback) {
        this.internalVerifyFeedback = internalVerifyFeedback;
    }

    public String getExternalVerify() {
        return this.externalVerify;
    }

    public void setExternalVerify(String externalVerify) {
        this.externalVerify = externalVerify;
    }

    public String getExternalVerifyFeedback() {
        return this.externalVerifyFeedback;
    }

    public void setExternalVerifyFeedback(String externalVerifyFeedback) {
        this.externalVerifyFeedback = externalVerifyFeedback;
    }

    public String getIssueDesc() {
        return this.issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getReassignTo_() {
        return this.reassignTo_;
    }

    public void setReassignTo_(String reassignTo_) {
        this.reassignTo_ = reassignTo_;
    }

    public String getReassignToText() {
        return this.reassignToText;
    }

    public void setReassignToText(String reassignToText) {
        this.reassignToText = reassignToText;
    }

    public String getReassignReason() {
        return this.reassignReason;
    }

    public void setReassignReason(String reassignReason) {
        this.reassignReason = reassignReason;
    }

    public String getSolveContent() {
        return this.solveContent;
    }

    public void setSolveContent(String solveContent) {
        this.solveContent = solveContent;
    }

    public String getSolveDatetime() {
        return this.solveDatetime;
    }

    public void setSolveDatetime(String solveDatetime) {
        this.solveDatetime = solveDatetime;
    }

    public String getSolveStatus_() {
        return this.solveStatus_;
    }

    public void setSolveStatus_(String solveStatus_) {
        this.solveStatus_ = solveStatus_;
    }

    public String getSolveStatusText() {
        return this.solveStatusText;
    }

    public void setSolveStatusText(String solveStatusText) {
        this.solveStatusText = solveStatusText;
    }

    public String getIssuePriority_() {
        return this.issuePriority_;
    }


    public void setIssuePriority_(String issuePriority_) {
        this.issuePriority_ = issuePriority_;
    }


    public String getIssuePriorityText() {
        return this.issuePriorityText;
    }


    public void setIssuePriorityText(String issuePriorityText) {
        this.issuePriorityText = issuePriorityText;
    }
    
    public String getCreator_() {
		return this.creator_;
	}

	public void setCreator_(String creator_) {
		this.creator_ = creator_;
	}

	public String getCreatorText() {
		return this.creatorText;
	}

	public void setCreatorText(String creatorText) {
		this.creatorText = creatorText;
	}

	public List<ArchiveBean> getArchiveList() {
        return this.archiveList;
    }

    public void setArchiveList(List<ArchiveBean> archiveList) {
        this.archiveList = archiveList;
    }

    public List<String> getArchiveReservedList() {
        return this.archiveReservedList;
    }

    public void setArchiveReservedList(List<String> archiveReservedList) {
        this.archiveReservedList = archiveReservedList;
    }

    public List<ArchiveBean> getSlovearchiveList() {
        return slovearchiveList;
    }

    public void setSlovearchiveList(List<ArchiveBean> slovearchiveList) {
        this.slovearchiveList = slovearchiveList;
    }

    public List<String> getSolveArchiveReservedList() {
        return solveArchiveReservedList;
    }

    public void setSolveArchiveReservedList(List<String> solveArchiveReservedList) {
        this.solveArchiveReservedList = solveArchiveReservedList;
    }

    public String getUploadFileType() {
        return uploadFileType;
    }

    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
    }

}
