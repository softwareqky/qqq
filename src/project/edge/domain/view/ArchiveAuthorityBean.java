package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * 保存档案文件权限表的表现层DTO。
 */
public class ArchiveAuthorityBean implements ViewBean {

    private String id;

    private String archive_;
    private String archiveText;
    private String person_;
    private String personText;
    private int authType;
    private String authTypeText;
    private Short isInherit;

    private String inheritFrom;
    private String org;
    private String dept;
    private String user;

    // 来自Archive.path，顺序是从根节点开始
    private List<String> pathList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }

    public String getArchive_() {
        return this.archive_;
    }

    public void setArchive_(String archive_) {
        this.archive_ = archive_;
    }

    public String getArchiveText() {
        return this.archiveText;
    }

    public void setArchiveText(String archiveText) {
        this.archiveText = archiveText;
    }

    public String getPerson_() {
        return this.person_;
    }

    public void setPerson_(String person_) {
        this.person_ = person_;
    }

    public String getPersonText() {
        return this.personText;
    }

    public void setPersonText(String personText) {
        this.personText = personText;
    }

    public int getAuthType() {
        return this.authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public Short getIsInherit() {
        return this.isInherit;
    }

    public void setIsInherit(Short isInherit) {
        this.isInherit = isInherit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthTypeText() {
        return this.authTypeText;
    }

    public void setAuthTypeText(String authTypeText) {
        this.authTypeText = authTypeText;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getInheritFrom() {
        return this.inheritFrom;
    }

    public void setInheritFrom(String inheritFrom) {
        this.inheritFrom = inheritFrom;
    }

    public List<String> getPathList() {
        return this.pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }
}
