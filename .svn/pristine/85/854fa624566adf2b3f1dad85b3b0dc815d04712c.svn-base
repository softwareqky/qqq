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
@Table(name = "t_tendering_plan_attachment")
public class TenderingPlanAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5084270641187405833L;

    private String id = UUID.randomUUID().toString();
	private TenderingPlan tenderingPlan;
    private Archive archive;
    private int attachmentType;


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
    @JoinColumn(name = "tendering_plan_id", nullable = false)
	public TenderingPlan getTenderingPlan() {
		return this.tenderingPlan;
	}

	public void setTenderingPlan(TenderingPlan tenderingPlan) {
		this.tenderingPlan = tenderingPlan;
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
}
