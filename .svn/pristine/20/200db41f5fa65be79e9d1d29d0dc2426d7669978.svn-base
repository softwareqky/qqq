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
@Table(name = "t_quality_accident_attachment")
public class QualityAccidentAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5385977438501555439L;

	private String id = UUID.randomUUID().toString();
	
	private QualityAccident qualityAccident;
	
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
	@JoinColumn(name = "quality_accident_id", nullable = false)
	public QualityAccident getQualityAccident() {
		return qualityAccident;
	}

	public void setQualityAccident(QualityAccident qualityAccident) {
		this.qualityAccident = qualityAccident;
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
