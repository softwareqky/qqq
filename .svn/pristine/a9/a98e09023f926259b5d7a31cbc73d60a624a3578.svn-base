package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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

@Entity
@Table(name = "t_project_role")
public class ProjectRole implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4427773007688721984L;

    private String id = UUID.randomUUID().toString();

    private String projectRoleName;
    private String shortName;
    private String projectRoleCode;
    private ProjectRole pid;
    private int level;
    private int projectRoleOrder;
    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<Project> projects = new HashSet<Project>(0);

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

    @Column(name = "project_role_name", nullable = false, length = 50)
    public String getProjectRoleName() {
        return this.projectRoleName;
    }


    public void setProjectRoleName(String projectRoleName) {
        this.projectRoleName = projectRoleName;
    }

    @Column(name = "short_name", nullable = true, length = 25)
    public String getShortName() {
        return this.shortName;
    }


    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "project_role_code", nullable = true, length = 25)
    public String getProjectRoleCode() {
        return this.projectRoleCode;
    }


    public void setProjectRoleCode(String projectRoleCode) {
        this.projectRoleCode = projectRoleCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", nullable = true)
    public ProjectRole getPid() {
        return this.pid;
    }

    
    public void setPid(ProjectRole pid) {
        this.pid = pid;
    }

    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "project_role_order", nullable = false)
    public int getProjectRoleOrder() {
        return this.projectRoleOrder;
    }


    public void setProjectRoleOrder(int projectRoleOrder) {
        this.projectRoleOrder = projectRoleOrder;
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
    @JoinTable(name = "t_project_project_role",
            joinColumns = {
                    @JoinColumn(name = "project_role_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_id", nullable = false, updatable = false)})
    public Set<Project> getProjects() {
        return this.projects;
    }


    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}
