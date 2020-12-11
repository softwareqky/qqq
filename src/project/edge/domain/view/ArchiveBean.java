package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * 保存档案文件表的表现层DTO。
 */
public class ArchiveBean implements ViewBean {

    private String id;

    private String archiveName;
    private short isDir;
    private String relativePath;
    private Integer fileSize;
    private String fileSizeText;
    private String fileDigest;
    private String pid;
    private String pname;
    private int level;
    private String path;
    private String remark;
    private String remarkHtmlEscaped;
    private String mDatetime;

    private int authType;

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


    public short getIsDir() {
        return this.isDir;
    }


    public void setIsDir(short isDir) {
        this.isDir = isDir;
    }


    public String getRelativePath() {
        return this.relativePath;
    }


    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
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

    public String getFileDigest() {
        return this.fileDigest;
    }


    public void setFileDigest(String fileDigest) {
        this.fileDigest = fileDigest;
    }


    public String getPid() {
        return this.pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }


    public int getLevel() {
        return this.level;
    }


    public void setLevel(int level) {
        this.level = level;
    }


    public String getPath() {
        return this.path;
    }


    public void setPath(String path) {
        this.path = path;
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


    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getmDatetime() {
        return this.mDatetime;
    }

    public void setmDatetime(String mDatetime) {
        this.mDatetime = mDatetime;
    }

    public int getAuthType() {
        return this.authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

}
