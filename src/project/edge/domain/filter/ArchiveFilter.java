package project.edge.domain.filter;

import garage.origin.domain.filter.CommonFilter;

/**
 * 档案文件过滤器。
 *
 */
public class ArchiveFilter extends CommonFilter {

    /**
     * 文件名或者备注
     */
    private String fileInfo;

    /**
     * 以此id为父节点的所有递归下属节点，即在path中包含了此pid
     */
    private String pidPath;

    public String getFileInfo() {
        return this.fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getPidPath() {
        return this.pidPath;
    }

    public void setPidPath(String pidPath) {
        this.pidPath = pidPath;
    }

}
