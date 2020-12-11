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
public class QualityExamineBean implements ViewBean {

    private String id;

    private String project_;
    private String projectText;

    private String examineCode;
    private String report_;
    private String reportText;
    private String remark;
    private String examineResult;
    private String examinePerson_;
    private String examinePersonText;
    private String examineDate;

    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }


    public String getExamineCode() {
        return this.examineCode;
    }


    public void setExamineCode(String examineCode) {
        this.examineCode = examineCode;
    }


    public String getReport_() {
        return this.report_;
    }


    public void setReport_(String report_) {
        this.report_ = report_;
    }


    public String getReportText() {
        return this.reportText;
    }


    public void setReportText(String reportText) {
        this.reportText = reportText;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getExamineResult() {
        return this.examineResult;
    }


    public void setExamineResult(String examineResult) {
        this.examineResult = examineResult;
    }


    public String getExaminePerson_() {
        return this.examinePerson_;
    }


    public void setExaminePerson_(String examinePerson_) {
        this.examinePerson_ = examinePerson_;
    }


    public String getExaminePersonText() {
        return this.examinePersonText;
    }


    public void setExaminePersonText(String examinePersonText) {
        this.examinePersonText = examinePersonText;
    }


    public String getExamineDate() {
        return this.examineDate;
    }


    public void setExamineDate(String examineDate) {
        this.examineDate = examineDate;
    }


    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }


    public void setArchivesList(List<ArchiveBean> archivesList) {
        this.archivesList = archivesList;
    }


    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }


    public void setArchivesReservedList(List<String> archivesReservedList) {
        this.archivesReservedList = archivesReservedList;
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

}
