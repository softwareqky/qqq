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
public class QualityAccidentBean implements ViewBean {

    private String id;
    private String project_;
    private String projectText;
    private String accidentName;
    private String accidentDate;
    private String remark;
    private Integer archiveType;
    private String archiveTypeText;
    private String archiveMaterials;
    private String content;

    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    public String getArchiveMaterials() {
        return this.archiveMaterials;
    }

    public void setArchiveMaterials(String archiveMaterials) {
        this.archiveMaterials = archiveMaterials;
    }

    @Override
    public String getId() {
        return this.id;
    }


    public String getAccidentName() {
        return this.accidentName;
    }



    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }



    public String getRemark() {
        return this.remark;
    }



    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getAccidentDate() {
        return this.accidentDate;
    }



    public void setAccidentDate(String accidentDate) {
        this.accidentDate = accidentDate;
    }


    public Integer getArchiveType() {
        return this.archiveType;
    }


    public void setArchiveType(Integer archiveType) {
        this.archiveType = archiveType;
    }


    public String getArchiveTypeText() {
        return this.archiveTypeText;
    }


    public void setArchiveTypeText(String archiveTypeText) {
        this.archiveTypeText = archiveTypeText;
    }

    public String getContent() {
        return this.content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public void setId(String id) {
        this.id = id;
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
