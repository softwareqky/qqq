package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_quality_specification_attachment")
public class QualitySpecificationAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7646855104072583278L;

	private String id = UUID.randomUUID().toString();
	
	private QualitySpecification qualitySpecification;
	
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
    @JoinColumn(name = "quality_specification_id", nullable = false)
	public QualitySpecification getQualitySpecification() {
		return qualitySpecification;
	}

	public void setQualitySpecification(QualitySpecification qualitySpecification) {
		this.qualitySpecification = qualitySpecification;
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
