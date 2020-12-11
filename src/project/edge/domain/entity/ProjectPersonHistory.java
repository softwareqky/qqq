package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_project_person_history")
public class ProjectPersonHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6826048158612104377L;

    private String id = UUID.randomUUID().toString();
    private Project project;
    private ProjectPersonChange projectPersonChange;
    private Date versionDatetime;
    private short isChangePassed;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_person_change_id", nullable = false)
    public ProjectPersonChange getProjectPersonChange() {
        return this.projectPersonChange;
    }


    public void setProjectPersonChange(ProjectPersonChange projectPersonChange) {
        this.projectPersonChange = projectPersonChange;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "version_datetime", nullable = false, length = 29)
    public Date getVersionDatetime() {
        return this.versionDatetime;
    }


    public void setVersionDatetime(Date versionDatetime) {
        this.versionDatetime = versionDatetime;
    }

    @Column(name = "is_change_passed", nullable = false)
    public short getIsChangePassed() {
        return this.isChangePassed;
    }


    public void setIsChangePassed(short isChangePassed) {
        this.isChangePassed = isChangePassed;
    }


}
