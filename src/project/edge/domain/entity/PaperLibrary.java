package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_paper_library")
public class PaperLibrary implements Serializable {
	
	private static final long serialVersionUID = -4724899763219742211L;

    private String id = UUID.randomUUID().toString();

    private String archiveNo;
    private String archiveName;
    private Project project;
    private String relativeBox;    
    private short isCopy;
    private short isSecret;
    private short status;
    private String remark;
    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "archive_no", nullable = false, length = 100)
    public String getArchiveNo() {
        return archiveNo;
    }
    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo;
    }

    @Column(name = "archive_name", nullable = false, length = 200)
    public String getArchiveName() {
        return archiveName;
    }
    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "relative_box", nullable = false, length = 100)
    public String getRelativeBox() {
        return relativeBox;
    }
    public void setRelativeBox(String relativeBox) {
        this.relativeBox = relativeBox;
    }

    @Column(name = "is_copy", nullable = false)
    public short getIsCopy() {
        return this.isCopy;
    }
    public void setIsCopy(short isCopy) {
        this.isCopy = isCopy;
    }

    @Column(name = "is_secret", nullable = false)
    public short getIsSecret() {
        return this.isSecret;
    }
    public void setIsSecret(short isSecret) {
        this.isSecret = isSecret;
    }

    @Column(name = "status", nullable = false)
    public short getStatus() {
        return this.status;
    }
    public void setStatus(short status) {
        this.status = status;
    }
    
    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }
    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    //@ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "creator", nullable = false)
    @Column(name = "creator", nullable = false)
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }   

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return cDatetime;
    }

    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = false, length = 50)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return mDatetime;
    }

    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }
}
