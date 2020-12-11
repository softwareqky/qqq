/**
 * 
 */
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


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_completed_info")
public class CompletedInfo implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1054640336002815496L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String performanceCheck;
    private String completedContent;
    private Short isGeneratedCert;
    private String remark;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    
    private Set<CompletedInfoAttachment> completedInfoAttachments = new HashSet<>(0);
    private List<CompletedInfoAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public CompletedInfo clone() {
        CompletedInfo p = null;
        try {
            p = (CompletedInfo) super.clone();
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


    @Column(name = "performance_check", nullable = true, length = 1024)
    public String getPerformanceCheck() {
        return this.performanceCheck;
    }


    public void setPerformanceCheck(String performanceCheck) {
        this.performanceCheck = performanceCheck;
    }


    @Column(name = "completed_content", nullable = true, length = 1024)
    public String getCompletedContent() {
        return this.completedContent;
    }


    public void setCompletedContent(String completedContent) {
        this.completedContent = completedContent;
    }

    
    @Column(name = "is_generated_cert", nullable = true)
    public Short getIsGeneratedCert() {
        return this.isGeneratedCert;
    }


    public void setIsGeneratedCert(Short isGeneratedCert) {
        this.isGeneratedCert = isGeneratedCert;
    }

    
    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
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


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "completedInfo")
    public Set<CompletedInfoAttachment> getCompletedInfoAttachments() {
        return this.completedInfoAttachments;
    }

    
    public void setCompletedInfoAttachments(
            Set<CompletedInfoAttachment> completedInfoAttachments) {
        this.completedInfoAttachments = completedInfoAttachments;
    }

    @Transient
    public List<CompletedInfoAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    
    public void setAttachmentsToDelete(List<CompletedInfoAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
