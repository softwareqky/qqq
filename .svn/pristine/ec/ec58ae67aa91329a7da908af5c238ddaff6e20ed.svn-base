package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_project_person")
public class ProjectPerson implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4430129926699961159L;


    private String id = UUID.randomUUID().toString();

    private Project project;
    private Person person;
    private ProjectRole projectRole;
    private VirtualOrg virtualOrg;
    private Post post;
    private String jobCall;
    private String major;
    private String educationRecord;
    private String inductionTraining;
    private String pastPerformance;
    private String remark;
    private Person creator;
    private Date cDatetime;
    private Person modifier;
    private Date mDatetime;
    private int isOnDuty;

    private Set<Archive> archives = new HashSet<Archive>(0);


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    public Person getPerson() {
        return this.person;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_role_id", nullable = true)
    public ProjectRole getProjectRole() {
        return this.projectRole;
    }


    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    public Post getPost() {
        return this.post;
    }


    public void setPost(Post post) {
        this.post = post;
    }

    @Column(name = "job_call", nullable = true, length = 50)
    public String getJobCall() {
        return this.jobCall;
    }


    public void setJobCall(String jobCall) {
        this.jobCall = jobCall;
    }

    @Column(name = "major", nullable = true, length = 50)
    public String getMajor() {
        return this.major;
    }


    public void setMajor(String major) {
        this.major = major;
    }

    @Column(name = "education_record", nullable = true, length = 50)
    public String getEducationRecord() {
        return this.educationRecord;
    }


    public void setEducationRecord(String educationRecord) {
        this.educationRecord = educationRecord;
    }

    @Column(name = "induction_training", nullable = true, length = 1024)
    public String getInductionTraining() {
        return this.inductionTraining;
    }


    public void setInductionTraining(String inductionTraining) {
        this.inductionTraining = inductionTraining;
    }

    @Column(name = "past_performance", nullable = true, length = 1024)
    public String getPastPerformance() {
        return this.pastPerformance;
    }


    public void setPastPerformance(String pastPerformance) {
        this.pastPerformance = pastPerformance;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", nullable = true)
    public Person getCreator() {
        return this.creator;
    }


    public void setCreator(Person creator) {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier", nullable = true)
    public Person getModifier() {
        return this.modifier;
    }

    public void setModifier(Person modifier) {
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_person_attachment",
            joinColumns = {
                    @JoinColumn(name = "project_person_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    public Set<Archive> getArchives() {
        return this.archives;
    }


    public void setArchives(Set<Archive> archives) {
        this.archives = archives;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = false)
    public VirtualOrg getVirtualOrg() {
        return this.virtualOrg;
    }

    
    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
    }

    @Column(name = "is_on_duty", nullable = false)
	public int getIsOnDuty() {
		return isOnDuty;
	}

	public void setIsOnDuty(int isOnDuty) {
		this.isOnDuty = isOnDuty;
	}

}
