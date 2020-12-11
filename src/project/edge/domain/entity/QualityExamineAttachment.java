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
@Table(name = "t_quality_examine_attachment")
public class QualityExamineAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6115165769400871864L;

    private String id = UUID.randomUUID().toString();

    private QualityExamine qualityExamine;

    private Archive archive;

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_examine_id", nullable = false)
    public QualityExamine getQualityExamine() {
        return this.qualityExamine;
    }

    public void setQualityExamine(QualityExamine qualityExamine) {
        this.qualityExamine = qualityExamine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
    
}
