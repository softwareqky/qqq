package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "t_quality_report")
public class QualityReport implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1781398105085516241L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String reportCode;
    private QualityObjective objective;
    private String reportResult;
    private String remark;
    private ProjectPerson reporter;
    private Date reportDate;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<QualityReportAttachment> qualityReportAttachments = new HashSet<>(0);
    private List<QualityReportAttachment> attachmentsToDelete = new ArrayList<>();


    @Override
    public QualityReport clone() {
        QualityReport p = null;
        try {
            p = (QualityReport) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

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
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "report_code", nullable = false, length = 50)
    public String getReportCode() {
        return this.reportCode;
    }


    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objective_id", nullable = false)
    public QualityObjective getObjective() {
        return this.objective;
    }


    public void setObjective(QualityObjective objective) {
        this.objective = objective;
    }

    @Column(name = "report_result", nullable = false, length = 1024)
    public String getReportResult() {
        return this.reportResult;
    }


    public void setReportResult(String reportResult) {
        this.reportResult = reportResult;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter", nullable = true)
    public ProjectPerson getReporter() {
        return this.reporter;
    }


    public void setReporter(ProjectPerson reporter) {
        this.reporter = reporter;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "report_date", nullable = false, length = 29)
    public Date getReportDate() {
        return this.reportDate;
    }


    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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

    @Column(name = "modifier", nullable = true, length = 50)
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
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualityReport")
    public Set<QualityReportAttachment> getQualityReportAttachments() {
        return this.qualityReportAttachments;
    }

    public void setQualityReportAttachments(Set<QualityReportAttachment> qualityReportAttachments) {
        this.qualityReportAttachments = qualityReportAttachments;
    }

    @Transient
    public List<QualityReportAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    
    public void setAttachmentsToDelete(List<QualityReportAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
