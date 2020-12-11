package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_project_check_attachment")
public class ProjectCheckAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6296270289277501817L;

    private String id = UUID.randomUUID().toString();

    private ProjectCheck projectCheck;
    private Archive archive;
    private short isImprove;


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
    @JoinColumn(name = "project_check_id", nullable = false)
    public ProjectCheck getProjectCheck() {
        return this.projectCheck;
    }


    public void setProjectCheck(ProjectCheck projectCheck) {
        this.projectCheck = projectCheck;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return this.archive;
    }


    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @Column(name = "is_improve", nullable = false)
    public short getIsImprove() {
        return this.isImprove;
    }


    public void setIsImprove(short isImprove) {
        this.isImprove = isImprove;
    }


}
