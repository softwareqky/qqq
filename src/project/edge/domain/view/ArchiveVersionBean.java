package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * 档案文件版本的表现层DTO。
 */
public class ArchiveVersionBean implements ViewBean {

    private String id;

    private String archiveName;
    private Integer fileSize;
    private String fileSizeText;
    private String remark;
    private String remarkHtmlEscaped;
    private short isDeleted;
    private String isDeletedText;
    private String mDatetime;
    private String modifier;
    private String operation;

    private short isCurrent;

    @Override
    public String getId() {
        return this.id;
    }

    public String getArchiveName() {
        return this.archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }


    public Integer getFileSize() {
        return this.fileSize;
    }


    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }


    public String getFileSizeText() {
        return this.fileSizeText;
    }


    public void setFileSizeText(String fileSizeText) {
        this.fileSizeText = fileSizeText;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getRemarkHtmlEscaped() {
        return this.remarkHtmlEscaped;
    }


    public void setRemarkHtmlEscaped(String remarkHtmlEscaped) {
        this.remarkHtmlEscaped = remarkHtmlEscaped;
    }


    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }


    public String getIsDeletedText() {
        return this.isDeletedText;
    }


    public void setIsDeletedText(String isDeletedText) {
        this.isDeletedText = isDeletedText;
    }


    public String getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(String mDatetime) {
        this.mDatetime = mDatetime;
    }


    public String getModifier() {
        return this.modifier;
    }


    public void setModifier(String modifier) {
        this.modifier = modifier;
    }


    public String getOperation() {
        return this.operation;
    }


    public void setOperation(String operation) {
        this.operation = operation;
    }


    public void setId(String id) {
        this.id = id;
    }


    public short getIsCurrent() {
        return this.isCurrent;
    }


    public void setIsCurrent(short isCurrent) {
        this.isCurrent = isCurrent;
    }

}
