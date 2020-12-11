package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_quality_accident")
public class QualityAccident implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2924076666902445884L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String accidentName;
    private Date accidentDate;
    private String remark;
    private Integer archiveType;
    private String archiveMaterials;
    private String content;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<QualityAccidentAttachment> attachments = new HashSet<>(0);
    private List<QualityAccidentAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public QualityAccident clone() {
        QualityAccident p = null;
        try {
            p = (QualityAccident) super.clone();
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

    @Column(name = "accident_name", nullable = false, length = 50)
    public String getAccidentName() {
        return this.accidentName;
    }


    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "accident_date", nullable = false, length = 29)
    public Date getAccidentDate() {
        return this.accidentDate;
    }


    public void setAccidentDate(Date accidentDate) {
        this.accidentDate = accidentDate;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "archive_type", nullable = true)
    public Integer getArchiveType() {
        return this.archiveType;
    }


    public void setArchiveType(Integer archiveType) {
        this.archiveType = archiveType;
    }

    @Column(name = "archive_materials", nullable = true, length = 50)
    public String getArchiveMaterials() {
        return this.archiveMaterials;
    }

    public void setArchiveMaterials(String archiveMaterials) {
        this.archiveMaterials = archiveMaterials;
    }

    @Column(name = "content", nullable = true, length = 1024)
    public String getContent() {
        return this.content;
    }


    public void setContent(String content) {
        this.content = content;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualityAccident")
    public Set<QualityAccidentAttachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<QualityAccidentAttachment> attachments) {
        this.attachments = attachments;
    }

    @Transient
    public List<QualityAccidentAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<QualityAccidentAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }
}
