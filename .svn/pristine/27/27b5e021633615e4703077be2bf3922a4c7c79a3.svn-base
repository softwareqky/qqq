package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_plan_history_task_comment")
public class PlanHistoryTaskComment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3911465353555471798L;

    private String id = UUID.randomUUID().toString();

    private PlanHistoryTask historyTask;
    private PlanHistory planHistory;
    private Person person;
    private String commentContent;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;


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
    @JoinColumn(name = "history_task_id", nullable = false)
    public PlanHistoryTask getHistoryTask() {
        return this.historyTask;
    }


    public void setHistoryTask(PlanHistoryTask historyTask) {
        this.historyTask = historyTask;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_history_id", nullable = false)
    public PlanHistory getPlanHistory() {
        return this.planHistory;
    }


    public void setPlanHistory(PlanHistory planHistory) {
        this.planHistory = planHistory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    public Person getPerson() {
        return this.person;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    @Column(name = "comment_content", nullable = false, length = 1024)
    public String getCommentContent() {
        return this.commentContent;
    }


    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }


    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = false, length = 50)
    public String getModifier() {
        return this.modifier;
    }


    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

}
