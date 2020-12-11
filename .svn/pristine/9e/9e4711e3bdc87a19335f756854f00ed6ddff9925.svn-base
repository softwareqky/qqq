/**
 * 
 */
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
@Table(name = "t_project_history_attachment")
public class ProjectHistoryAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6241076159663807415L;
    
    private String id = UUID.randomUUID().toString();
    
    private ProjectHistory projectHistory;
    private Archive archive;
    
    private int attachment_type;

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
    @JoinColumn(name = "project_history_id", nullable = false)
    public ProjectHistory getProjectHistory() {
        return this.projectHistory;
    }

    
    public void setProjectHistory(ProjectHistory projectHistory) {
        this.projectHistory = projectHistory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return this.archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @Column(name = "attachment_type", nullable = false)
    public int getAttachment_type() {
        return this.attachment_type;
    }

    public void setAttachment_type(int attachment_type) {
        this.attachment_type = attachment_type;
    }
}
