package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存项目巡查表的表现层DTO。
 */
public class ProjectInspectBean implements ViewBean {

    private String id;

    private String site_;
    private String siteText;
    private String inspectType_;
    private String inspectTypeText;
    private String project_;
    private String projectText;
    private String inspectPlan;
    private String inspectArea;
    private String inspectBasis;
    private String inspectContent;
    private String archiveText;
    private String inspector_;
    private String inspectorText;
    private Short isImprove;
    private String isImproveText;
    private String inspectResult_;
    private String inspectResultText;
    private String verifyResult_;
    private String verifyResultText;
    private String inspectDate;
    private String improveReqDate;
    private String improvePlanDate;
    private String improveActualDate;
    private String inspectResultContent;
    private String improveReq;
    private String improvePlan;
//    private List<String> improvearchive_ = new ArrayList<>();
//    private List<String> archive_ = new ArrayList<>();
//    private String improvearchiveText;
    private String verifyDate;
    private String improveResultVerify;
    private String remark;
    private String flowStartDate;
    private String flowEndDate;
    private int flowStatus;
    private String flowStatusText;
    
    private String inspectStatus_;
    private String inspectStatusText;
    
    private String uploadFileType;

    private List<ArchiveBean> archiveList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archiveReservedList = new ArrayList<>();

    private List<ArchiveBean> improvearchiveList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> improvearchiveReservedList = new ArrayList<>();


//    private List<ArchiveBean> archivesList = new ArrayList<>();
//
//    /**
//     * 修改时，保留的档案文件id列表
//     */
//    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }


    public String getSite_() {
        return this.site_;
    }


    public void setSite_(String site_) {
        this.site_ = site_;
    }


    public String getSiteText() {
        return this.siteText;
    }


    public void setSiteText(String siteText) {
        this.siteText = siteText;
    }


    public String getInspectType_() {
        return this.inspectType_;
    }


    public void setInspectType_(String inspectType_) {
        this.inspectType_ = inspectType_;
    }


    public String getInspectTypeText() {
        return this.inspectTypeText;
    }


    public void setInspectTypeText(String inspectTypeText) {
        this.inspectTypeText = inspectTypeText;
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


    public String getInspectPlan() {
        return this.inspectPlan;
    }


    public void setInspectPlan(String inspectPlan) {
        this.inspectPlan = inspectPlan;
    }


    public String getInspectArea() {
        return this.inspectArea;
    }


    public void setInspectArea(String inspectArea) {
        this.inspectArea = inspectArea;
    }


    public String getInspectBasis() {
        return this.inspectBasis;
    }


    public void setInspectBasis(String inspectBasis) {
        this.inspectBasis = inspectBasis;
    }


    public String getInspectContent() {
        return this.inspectContent;
    }


    public void setInspectContent(String inspectContent) {
        this.inspectContent = inspectContent;
    }

    public String getInspector_() {
        return this.inspector_;
    }


    public void setInspector_(String inspector_) {
        this.inspector_ = inspector_;
    }
    
    public String getInspectorText() {
        return this.inspectorText;
    }


    public void setInspectorText(String inspectorText) {
        this.inspectorText = inspectorText;
    }


    public Short getIsImprove() {
        return this.isImprove;
    }


    public void setIsImprove(Short isImprove) {
        this.isImprove = isImprove;
    }


    public String getIsImproveText() {
        return this.isImproveText;
    }


    public void setIsImproveText(String isImproveText) {
        this.isImproveText = isImproveText;
    }


    public String getInspectResult_() {
        return this.inspectResult_;
    }


    public void setInspectResult_(String inspectResult_) {
        this.inspectResult_ = inspectResult_;
    }


    public String getInspectResultText() {
        return this.inspectResultText;
    }


    public void setInspectResultText(String inspectResultText) {
        this.inspectResultText = inspectResultText;
    }


    public String getVerifyResult_() {
        return this.verifyResult_;
    }


    public void setVerifyResult_(String verifyResult_) {
        this.verifyResult_ = verifyResult_;
    }


    public String getVerifyResultText() {
        return this.verifyResultText;
    }


    public void setVerifyResultText(String verifyResultText) {
        this.verifyResultText = verifyResultText;
    }


    public String getInspectDate() {
        return this.inspectDate;
    }


    public void setInspectDate(String inspectDate) {
        this.inspectDate = inspectDate;
    }


    public String getImproveReqDate() {
        return this.improveReqDate;
    }


    public void setImproveReqDate(String improveReqDate) {
        this.improveReqDate = improveReqDate;
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


    public String getInspectResultContent() {
        return this.inspectResultContent;
    }


    public void setInspectResultContent(String inspectResultContent) {
        this.inspectResultContent = inspectResultContent;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getArchiveText() {
        return this.archiveText;
    }

    public void setArchiveText(String archiveText) {
        this.archiveText = archiveText;
    }

//    public List<String> getImprovearchive_() {
//        return this.improvearchive_;
//    }
//
//
//
//    public void setImprovearchive_(List<String> improvearchive_) {
//        this.improvearchive_ = improvearchive_;
//    }
//
//
//
//    public String getImprovearchiveText() {
//        return this.improvearchiveText;
//    }
//
//
//
//    public void setImprovearchiveText(String improvearchiveText) {
//        this.improvearchiveText = improvearchiveText;
//    }
//
//
//    public List<String> getArchive_() {
//        return archive_;
//    }
//
//
//    
//    public void setArchive_(List<String> archive_) {
//        this.archive_ = archive_;
//    }


    public String getFlowStatusText() {
        return this.flowStatusText;
    }



    public void setFlowStatusText(String flowStatusText) {
        this.flowStatusText = flowStatusText;
    }


//    public List<ArchiveBean> getArchivesList() {
//        return this.archivesList;
//    }
//
//    public void setArchivesList(List<ArchiveBean> archiveList) {
//        this.archivesList = archiveList;
//    }
//
//    public List<String> getArchivesReservedList() {
//        return this.archivesReservedList;
//    }
//
//    public void setArchivesReservedList(List<String> archiveIdReservedList) {
//        this.archivesReservedList = archiveIdReservedList;
//    }

    public String getInspectStatus_() {
        return this.inspectStatus_;
    }

    public void setInspectStatus_(String inspectStatus_) {
        this.inspectStatus_ = inspectStatus_;
    }

    public String getInspectStatusText() {
        return this.inspectStatusText;
    }

    public void setInspectStatusText(String inspectStatusText) {
        this.inspectStatusText = inspectStatusText;
    }


    
    public String getUploadFileType() {
        return this.uploadFileType;
    }


    
    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
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


    
    public List<ArchiveBean> getImprovearchiveList() {
        return this.improvearchiveList;
    }


    
    public void setImprovearchiveList(List<ArchiveBean> improvearchiveList) {
        this.improvearchiveList = improvearchiveList;
    }


    
    public List<String> getImprovearchiveReservedList() {
        return this.improvearchiveReservedList;
    }


    
    public void setImprovearchiveReservedList(List<String> improvearchiveReservedList) {
        this.improvearchiveReservedList = improvearchiveReservedList;
    }
    
}
