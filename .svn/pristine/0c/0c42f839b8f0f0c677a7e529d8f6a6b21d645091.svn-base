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
@Table(name = "t_quality_examine")
public class QualityExamine implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 7153916949692270507L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String examineCode;
    private QualityReport report;
    private String remark;
    private String examineResult;
    private ProjectPerson examinePerson;
    private Date examineDate;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<QualityExamineAttachment> qualityExamineAttachments = new HashSet<>(0);
    private List<QualityExamineAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public QualityExamine clone() {
        QualityExamine p = null;
        try {
            p = (QualityExamine) super.clone();
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

    @Column(name = "examine_code", nullable = false, length = 50)
    public String getExamineCode() {
        return this.examineCode;
    }


    public void setExamineCode(String examineCode) {
        this.examineCode = examineCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    public QualityReport getReport() {
        return this.report;
    }


    public void setReport(QualityReport report) {
        this.report = report;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "examine_result", nullable = true, length = 50)
    public String getExamineResult() {
        return this.examineResult;
    }


    public void setExamineResult(String examineResult) {
        this.examineResult = examineResult;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examine_person", nullable = true)
    public ProjectPerson getExaminePerson() {
        return this.examinePerson;
    }


    public void setExaminePerson(ProjectPerson examinePerson) {
        this.examinePerson = examinePerson;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "examine_date", nullable = false, length = 29)
    public Date getExamineDate() {
        return this.examineDate;
    }


    public void setExamineDate(Date examineDate) {
        this.examineDate = examineDate;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualityExamine")
    public Set<QualityExamineAttachment> getQualityExamineAttachments() {
        return this.qualityExamineAttachments;
    }


    public void setQualityExamineAttachments(
            Set<QualityExamineAttachment> qualityExamineAttachments) {
        this.qualityExamineAttachments = qualityExamineAttachments;
    }

    @Transient
    public List<QualityExamineAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }


    public void setAttachmentsToDelete(List<QualityExamineAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
