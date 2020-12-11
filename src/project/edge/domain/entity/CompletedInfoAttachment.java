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
@Table(name = "t_completed_info_attachment")
public class CompletedInfoAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2631329350787924413L;

    private String id = UUID.randomUUID().toString();

    private CompletedInfo completedInfo;
    private Archive archive;
    private short archiveType;


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
    @JoinColumn(name = "completed_info_id", nullable = false)
    public CompletedInfo getCompletedInfo() {
        return this.completedInfo;
    }


    public void setCompletedInfo(CompletedInfo completedInfo) {
        this.completedInfo = completedInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return this.archive;
    }


    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @Column(name = "archive_type", nullable = false)
    public short getArchiveType() {
        return this.archiveType;
    }


    public void setArchiveType(short archiveType) {
        this.archiveType = archiveType;
    }



}
