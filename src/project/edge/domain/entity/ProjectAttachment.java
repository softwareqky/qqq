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
 * 项目附件。
 *
 */
@Entity
@Table(name = "t_project_attachment")
public class ProjectAttachment implements Serializable {

    private static final long serialVersionUID = 2270061747213469551L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private Archive archive;

    private int attachmentType;
    private String attachmentRemark;

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
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return this.archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @Column(name = "attachment_type", nullable = false)
    public int getAttachmentType() {
        return this.attachmentType;
    }

    public void setAttachmentType(int attachmentType) {
        this.attachmentType = attachmentType;
    }
    
    @Column(name = "archive_remark", nullable = true)
    public String getAttachmentRemark() {
        return this.attachmentRemark;
    }

    public void setAttachmentRemark(String attachmentRemark) {
        this.attachmentRemark = attachmentRemark;
    }

}
