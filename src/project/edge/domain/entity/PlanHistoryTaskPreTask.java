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
@Table(name = "t_plan_history_task_pre_task")
public class PlanHistoryTaskPreTask implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6268258442161558077L;

    private String id = UUID.randomUUID().toString();

    private PlanHistoryTask planHistoryTask;
    private PlanHistoryTask preTask;
    private int preTaskType;
    private int lagDays;


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
    @JoinColumn(name = "plan_history_task_id", nullable = false)
    public PlanHistoryTask getPlanHistoryTask() {
        return this.planHistoryTask;
    }


    public void setPlanHistoryTask(PlanHistoryTask planHistoryTask) {
        this.planHistoryTask = planHistoryTask;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_task_id", nullable = false)
    public PlanHistoryTask getPreTask() {
        return this.preTask;
    }


    public void setPreTask(PlanHistoryTask preTask) {
        this.preTask = preTask;
    }

    @Column(name = "pre_task_type", nullable = false)
    public int getPreTaskType() {
        return this.preTaskType;
    }


    public void setPreTaskType(int preTaskType) {
        this.preTaskType = preTaskType;
    }

    @Column(name = "lag_days", nullable = false)
    public int getLagDays() {
        return this.lagDays;
    }


    public void setLagDays(int lagDays) {
        this.lagDays = lagDays;
    }



}
