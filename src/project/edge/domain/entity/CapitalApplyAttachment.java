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
 * 采购订单附件表。
 *
 */
@Entity
@Table(name = "t_capital_apply_attachment")
public class CapitalApplyAttachment implements Serializable {

    private static final long serialVersionUID = 2270062747213362554L;

    private String id = UUID.randomUUID().toString();

    private CapitalApply capitalApply;

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
    @JoinColumn(name = "apply_id", nullable = false)
    public CapitalApply getCapitalApply() {
		return this.capitalApply;
	}

	public void setCapitalApply(CapitalApply capitalApply) {
		this.capitalApply = capitalApply;
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
