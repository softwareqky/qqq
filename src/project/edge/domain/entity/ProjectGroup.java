/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_project_group")
public class ProjectGroup implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1915151127698702774L;

    private String id = UUID.randomUUID().toString();

    private String groupName;
    private String groupDesc;
    private Person leader;
    private String leaderName;
    private String leaderMobile;
    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<Archive> archives = new HashSet<Archive>(0);
    private List<Archive> archivesToDelete = new ArrayList<>();

    private Set<Project> projects = new HashSet<Project>(0);
    private Set<ProjectChange> projectChanges = new HashSet<ProjectChange>(0);
    private Set<ProjectHistory> projectHistory = new HashSet<ProjectHistory>(0);

    @Override
    public ProjectGroup clone() {
        ProjectGroup p = null;
        try {
            p = (ProjectGroup) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "group_name", nullable = false, length = 50)
    public String getGroupName() {
        return this.groupName;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "group_desc", nullable = true, length = 1024)
    public String getGroupDesc() {
        return this.groupDesc;
    }


    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader", nullable = true)
    public Person getLeader() {
        return this.leader;
    }


    public void setLeader(Person leader) {
        this.leader = leader;
    }

    @Column(name = "leader_name", nullable = true, length = 20)
    public String getLeaderName() {
        return this.leaderName;
    }


    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    @Column(name = "leader_mobile", nullable = true, length = 50)
    public String getLeaderMobile() {
        return this.leaderMobile;
    }


    public void setLeaderMobile(String leaderMobile) {
        this.leaderMobile = leaderMobile;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }


    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = true, length = 50)
    public String getModifier() {
        return this.modifier;
    }


    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_project_group",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_id", nullable = false, updatable = false)})
    public Set<Project> getProjects() {
        return this.projects;
    }


    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_group_attachment",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    public Set<Archive> getArchives() {
        return this.archives;
    }


    public void setArchives(Set<Archive> archives) {
        this.archives = archives;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_change_project_group",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_change_id", nullable = false, updatable = false)})
    public Set<ProjectChange> getProjectChanges() {
        return this.projectChanges;
    }


    public void setProjectChanges(Set<ProjectChange> projectChanges) {
        this.projectChanges = projectChanges;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_history_project_group",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_history_id", nullable = false, updatable = false)})
    public Set<ProjectHistory> getProjectHistory() {
        return this.projectHistory;
    }


    public void setProjectHistory(Set<ProjectHistory> projectHistory) {
        this.projectHistory = projectHistory;
    }

    @Transient
    public List<Archive> getArchivesToDelete() {
        return this.archivesToDelete;
    }

    public void setArchivesToDelete(List<Archive> archivesToDelete) {
        this.archivesToDelete = archivesToDelete;
    }

}
