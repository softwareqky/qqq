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
@Table(name = "t_issue")
public class Issue implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 3848366252692735069L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private VirtualOrg virtualOrg;
    private String issueTitle;
    private DataOption issueType;
    private DataOption issuePriority;
    private Date inputDate;
    private ProjectPerson assignTo;
    private String internalVerify;
    private String internalVerifyFeedback;
    private String externalVerify;
    private String externalVerifyFeedback;
    private String issueDesc;
    private ProjectPerson reassignTo;
    private String reassignReason;
    private String solveContent;
    private Date solveDatetime;
    private DataOption solveStatus;
    private Person creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<IssueAttachment> issueAttachments = new HashSet<>(0);
    private List<IssueAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public Issue clone() {
        Issue p = null;
        try {
            p = (Issue) super.clone();
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = false)
    public VirtualOrg getVirtualOrg() {
        return this.virtualOrg;
    }


    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
    }

    @Column(name = "issue_title", nullable = false, length = 250)
    public String getIssueTitle() {
        return this.issueTitle;
    }


    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_type", nullable = false)
    public DataOption getIssueType() {
        return this.issueType;
    }


    public void setIssueType(DataOption issueType) {
        this.issueType = issueType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "input_date", nullable = true, length = 29)
    public Date getInputDate() {
        return this.inputDate;
    }


    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assign_to", nullable = true)
    public ProjectPerson getAssignTo() {
        return this.assignTo;
    }


    public void setAssignTo(ProjectPerson assignTo) {
        this.assignTo = assignTo;
    }

    @Column(name = "internal_verify", nullable = true, length = 1024)
    public String getInternalVerify() {
        return this.internalVerify;
    }


    public void setInternalVerify(String internalVerify) {
        this.internalVerify = internalVerify;
    }

    @Column(name = "internal_verify_feedback", nullable = true, length = 1024)
    public String getInternalVerifyFeedback() {
        return this.internalVerifyFeedback;
    }


    public void setInternalVerifyFeedback(String internalVerifyFeedback) {
        this.internalVerifyFeedback = internalVerifyFeedback;
    }

    @Column(name = "external_verify", nullable = true, length = 1024)
    public String getExternalVerify() {
        return this.externalVerify;
    }


    public void setExternalVerify(String externalVerify) {
        this.externalVerify = externalVerify;
    }

    @Column(name = "external_verify_feedback", nullable = true, length = 1024)
    public String getExternalVerifyFeedback() {
        return this.externalVerifyFeedback;
    }


    public void setExternalVerifyFeedback(String externalVerifyFeedback) {
        this.externalVerifyFeedback = externalVerifyFeedback;
    }

    @Column(name = "issue_desc", nullable = true, length = 1024)
    public String getIssueDesc() {
        return this.issueDesc;
    }


    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reassign_to", nullable = true)
    public ProjectPerson getReassignTo() {
        return this.reassignTo;
    }


    public void setReassignTo(ProjectPerson reassignTo) {
        this.reassignTo = reassignTo;
    }

    @Column(name = "reassign_reason", nullable = true, length = 1024)
    public String getReassignReason() {
        return this.reassignReason;
    }


    public void setReassignReason(String reassignReason) {
        this.reassignReason = reassignReason;
    }

    @Column(name = "solve_content", nullable = true, length = 1024)
    public String getSolveContent() {
        return this.solveContent;
    }


    public void setSolveContent(String solveContent) {
        this.solveContent = solveContent;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "solve_datetime", nullable = true, length = 29)
    public Date getSolveDatetime() {
        return this.solveDatetime;
    }


    public void setSolveDatetime(Date solveDatetime) {
        this.solveDatetime = solveDatetime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solve_status", nullable = true)
    public DataOption getSolveStatus() {
        return this.solveStatus;
    }


    public void setSolveStatus(DataOption solveStatus) {
        this.solveStatus = solveStatus;
    }

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_priority", nullable = false)
    public DataOption getIssuePriority() {
        return this.issuePriority;
    }


    public void setIssuePriority(DataOption issuePriority) {
        this.issuePriority = issuePriority;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    public Set<IssueAttachment> getIssueAttachments() {
        return this.issueAttachments;
    }

    public void setIssueAttachments(Set<IssueAttachment> issueAttachment) {
        this.issueAttachments = issueAttachment;
    }

    @Transient
    public List<IssueAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<IssueAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }


}
