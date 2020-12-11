/**
 * 
 */
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

/**
 * @author wwy
 *
 */
@Entity
@Table(name = "t_capital_apply")
public class CapitalApply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768735084291714738L;

	private String id = UUID.randomUUID().toString();

	private Project project;
	private Double applyAmount;
	private DataOption source;
	private String applyReason;
	private VirtualOrg group;
	private int flowStatus;
	private Person creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	
    private Set<CapitalApplyAttachment> capitalApplyAttachments = new HashSet<>(0);
    private List<CapitalApplyAttachment> attachmentsToDelete = new ArrayList<>();

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
    @JoinColumn(name = "group_id", nullable = true)
	public VirtualOrg getGroup() {
		return this.group;
	}

	public void setGroup(VirtualOrg group) {
		this.group = group;
	}

	@Column(name = "apply_amount", nullable = false)
	public Double getApplyAmount() {
		return this.applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source", nullable = true)
	public DataOption getSource() {
		return this.source;
	}

	public void setSource(DataOption source) {
		this.source = source;
	}

	@Column(name = "apply_reason", nullable = false)
	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	
    @Column(name = "flow_status", nullable = false)
    public int getFlowStatus() {
        return this.flowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "capitalApply")
    public Set<CapitalApplyAttachment> getCapitalApplyAttachments() {
		return this.capitalApplyAttachments;
	}

	public void setCapitalApplyAttachments(Set<CapitalApplyAttachment> capitalApplyAttachments) {
		this.capitalApplyAttachments = capitalApplyAttachments;
	}

    @Transient
	public List<CapitalApplyAttachment> getAttachmentsToDelete() {
		return this.attachmentsToDelete;
	}

	public void setAttachmentsToDelete(List<CapitalApplyAttachment> attachmentsToDelete) {
		this.attachmentsToDelete = attachmentsToDelete;
	}
    
}
