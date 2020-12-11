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
@Table(name = "t_site_attachment")
public class SiteAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3688273426182249246L;

	
	private String id = UUID.randomUUID().toString();
	
	private Site site;
	private Archive archive;
	private short type;
	
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
    @JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return site;
	}
	
	public void setSite(Site site) {
		this.site = site;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
	public Archive getArchive() {
		return archive;
	}
	
	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	
	@Column(name = "type", nullable = false)
	public short getType() {
		return type;
	}
	
	public void setType(short type) {
		this.type = type;
	}
	
	
}
