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
@Table(name = "t_quality_objective")
public class QualityObjective implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4831419590104633194L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String taskCode;
    private String taskName;
    private int objecttiveType;
    private ProjectPerson leader;
    private String remark;
    private QualitySpecification specification;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<QualityObjectiveAttachment> attachments = new HashSet<>(0);
    private List<QualityObjectiveAttachment> attachmentsToDelete = new ArrayList<>();

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

    @Column(name = "task_code", nullable = false, length = 50)
    public String getTaskCode() {
        return this.taskCode;
    }


    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    @Column(name = "task_name", nullable = false, length = 1000)
    public String getTaskName() {
        return this.taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "objecttive_type", nullable = false)
    public int getObjecttiveType() {
        return this.objecttiveType;
    }


    public void setObjecttiveType(int objectiveType) {
        this.objecttiveType = objectiveType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "leader", nullable = true)
    public ProjectPerson getLeader() {
        return this.leader;
    }


    public void setLeader(ProjectPerson leader) {
        this.leader = leader;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_id", nullable = true)
    public QualitySpecification getSpecification() {
        return this.specification;
    }


    public void setSpecification(QualitySpecification specification) {
        this.specification = specification;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualityObjective")
	public Set<QualityObjectiveAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<QualityObjectiveAttachment> attachments) {
		this.attachments = attachments;
	}

	@Transient
	public List<QualityObjectiveAttachment> getAttachmentsToDelete() {
		return attachmentsToDelete;
	}

	public void setAttachmentsToDelete(List<QualityObjectiveAttachment> attachmentsToDelete) {
		this.attachmentsToDelete = attachmentsToDelete;
	}
    
}
