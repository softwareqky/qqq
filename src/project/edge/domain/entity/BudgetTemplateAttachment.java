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
@Table(name = "t_budget_template_attachment")
public class BudgetTemplateAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5084270641187405833L;

    private String id = UUID.randomUUID().toString();
	private BudgetTemplate budgetTemplate;
    private Archive archive;


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
    @JoinColumn(name = "template_id", nullable = false)
    public BudgetTemplate getBudgetTemplate() {
		return this.budgetTemplate;
	}

	public void setBudgetTemplate(BudgetTemplate budgetTemplate) {
		this.budgetTemplate = budgetTemplate;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return this.archive;
    }


    public void setArchive(Archive archive) {
        this.archive = archive;
    }

}
