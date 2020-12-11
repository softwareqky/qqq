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
 * 实际进度附件表。
 *
 */
@Entity
@Table(name = "t_plan_progress_attachment")
public class PlanProgressAttachment implements Serializable {

    private static final long serialVersionUID = 2270061747213469551L;

    private String id = UUID.randomUUID().toString();

    private PlanProgress planProgress;
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
    @JoinColumn(name = "plan_progress_id", nullable = false)
    public PlanProgress getPlanProgress() {
        return this.planProgress;
    }

    public void setPlanProgress(PlanProgress planProgress) {
        this.planProgress = planProgress;
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
