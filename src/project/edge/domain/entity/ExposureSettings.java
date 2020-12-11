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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_exposure_settings")
public class ExposureSettings implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 3583962211568833178L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private Dept dept;
    private Date exposureTime;
    private String exposureBasis;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<ExposureSettingsAttachment> archives = new HashSet<>(0);
    private List<ExposureSettingsAttachment> archivesToDelete = new ArrayList<>();

    @Override
    public ExposureSettings clone() {
        ExposureSettings p = null;
        try {
            p = (ExposureSettings) super.clone();
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
    @JoinColumn(name = "dept_id", nullable = false)
    public Dept getDept() {
        return this.dept;
    }


    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exposure_time", nullable = false, length = 29)
    public Date getExposureTime() {
        return this.exposureTime;
    }


    public void setExposureTime(Date exposureTime) {
        this.exposureTime = exposureTime;
    }

    @Column(name = "exposure_basis", nullable = true, length = 1024)
    public String getExposureBasis() {
        return this.exposureBasis;
    }


    public void setExposureBasis(String exposureBasis) {
        this.exposureBasis = exposureBasis;
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


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exposureSettings")
	public Set<ExposureSettingsAttachment> getArchives() {
		return this.archives;
	}

	public void setArchives(Set<ExposureSettingsAttachment> archives) {
		this.archives = archives;
	}


	@Transient
	public List<ExposureSettingsAttachment> getArchivesToDelete() {
		return this.archivesToDelete;
	}


	public void setArchivesToDelete(List<ExposureSettingsAttachment> archivesToDelete) {
		this.archivesToDelete = archivesToDelete;
	}
}
