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
@Table(name = "t_plan_calendar_exceptions")
public class PlanCalendarExceptions implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3950760388297665072L;

    private String id = UUID.randomUUID().toString();

    private PlanCalendar calendar;
    private String exceptionName;
    private short isWorkday;
    private Date startDate;
    private Date endDate;


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
    @JoinColumn(name = "calendar_id", nullable = false)
    public PlanCalendar getCalendar() {
        return this.calendar;
    }


    public void setCalendar(PlanCalendar calendar) {
        this.calendar = calendar;
    }

    @Column(name = "exception_name", nullable = false, length = 50)
    public String getExceptionName() {
        return this.exceptionName;
    }


    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    @Column(name = "is_workday", nullable = false)
    public short getIsWorkday() {
        return this.isWorkday;
    }


    public void setIsWorkday(short isWorkday) {
        this.isWorkday = isWorkday;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, length = 29)
    public Date getStartDate() {
        return this.startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false, length = 29)
    public Date getEndDate() {
        return this.endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
