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
@Table(name = "t_plan_change_task_pre_task")
public class PlanChangeTaskPreTask implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1823554448379647099L;

    private String id = UUID.randomUUID().toString();

    private PlanChange planChange;
    private PlanChangeTask planChangeTask;
    private PlanChangeTask preTask;
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
    @JoinColumn(name = "plan_change_id", nullable = false)
    public PlanChange getPlanChange() {
        return this.planChange;
    }

    
    public void setPlanChange(PlanChange planChange) {
        this.planChange = planChange;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_change_task_id", nullable = false)
    public PlanChangeTask getPlanChangeTask() {
        return this.planChangeTask;
    }


    public void setPlanChangeTask(PlanChangeTask planChangeTask) {
        this.planChangeTask = planChangeTask;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_task_id", nullable = false)
    public PlanChangeTask getPreTask() {
        return this.preTask;
    }


    public void setPreTask(PlanChangeTask preTask) {
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
