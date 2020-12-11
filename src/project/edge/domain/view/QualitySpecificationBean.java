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
public class QualitySpecificationBean implements ViewBean {

    private String id;

    private String specificationName;
    private String publishingUnit;
    private int recordType;
    private String recordTypeText;
    private String publisher_;
    private String publisherText;
    private String remark;
    
    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }


    public String getSpecificationName() {
        return this.specificationName;
    }


    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }


    public String getPublishingUnit() {
        return this.publishingUnit;
    }


    public void setPublishingUnit(String publishingUnit) {
        this.publishingUnit = publishingUnit;
    }


    public int getRecordType() {
        return this.recordType;
    }


    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }


    public String getRecordTypeText() {
        return this.recordTypeText;
    }


    public void setRecordTypeText(String recordTypeText) {
        this.recordTypeText = recordTypeText;
    }


    public String getPublisher_() {
        return this.publisher_;
    }


    public void setPublisher_(String publisher_) {
        this.publisher_ = publisher_;
    }


    public String getPublisherText() {
        return this.publisherText;
    }


    public void setPublisherText(String publisherText) {
        this.publisherText = publisherText;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
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

    
}
