package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存项目组的表现层DTO。
 */
public class ProjectGroupBean implements ViewBean {

    private String id;
    private String groupName;
    private String groupDesc;
    private String leader_;
    private String leaderText;
    private String leaderName;
    private String leaderMobile;

    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return this.groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
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

    public String getLeaderName() {
        return this.leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderMobile() {
        return this.leaderMobile;
    }

    public void setLeaderMobile(String leaderMobile) {
        this.leaderMobile = leaderMobile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }

    public void setArchivesList(List<ArchiveBean> archiveList) {
        this.archivesList = archiveList;
    }

    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }

    public void setArchivesReservedList(List<String> archiveIdReservedList) {
        this.archivesReservedList = archiveIdReservedList;
    }

}
