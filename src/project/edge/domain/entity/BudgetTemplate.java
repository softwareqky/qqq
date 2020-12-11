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
@Table(name = "t_budget_template")
public class BudgetTemplate implements Serializable,Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -4430129926699961159L;

    private String id = UUID.randomUUID().toString();
    
    private Project project;
	private String templateName;
    private String templateDesc;
    private VirtualOrg group;
    private Person creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<BudgetTemplateAttachment> archives = new HashSet<>(0);
	private List<BudgetTemplateAttachment> archivesToDelete = new ArrayList<>();
	
    @Override
    public BudgetTemplate clone() {
    	BudgetTemplate p = null;
        try {
            p = (BudgetTemplate) super.clone();
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
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
	@Column(name = "template_name", nullable = true, length = 50)
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "template_desc", nullable = true, length = 1024)
	public String getTemplateDesc() {
		return templateDesc;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
	public VirtualOrg getGroup() {
		return this.group;
	}

	public void setGroup(VirtualOrg group) {
		this.group = group;
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
		return cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	@Column(name = "modifier", nullable = true, length = 50)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "budgetTemplate")
    public Set<BudgetTemplateAttachment> getArchives() {
		return this.archives;
	}

	public void setArchives(Set<BudgetTemplateAttachment> archives) {
		this.archives = archives;
	}

	@Transient
	public List<BudgetTemplateAttachment> getArchivesToDelete() {
		return this.archivesToDelete;
	}

	public void setArchivesToDelete(List<BudgetTemplateAttachment> archivesToDelete) {
		this.archivesToDelete = archivesToDelete;
	}

}
