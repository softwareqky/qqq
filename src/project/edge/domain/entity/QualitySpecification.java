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
@Table(name = "t_quality_specification")
public class QualitySpecification implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -9120176406269557693L;


    private String id = UUID.randomUUID().toString();

    private String specificationName;
    private String publishingUnit;
    private int recordType;
    private Person publisher;
    private String remark;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<QualitySpecificationAttachment> qualitySpecificationAttachments = new HashSet<>(0);
    private List<QualitySpecificationAttachment> archivesToDelete = new ArrayList<>();
    
    @Override
    public QualitySpecification clone() {
        QualitySpecification p = null;
        try {
            p = (QualitySpecification) super.clone();
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

    @Column(name = "specification_name", nullable = false, length = 50)
    public String getSpecificationName() {
        return this.specificationName;
    }


    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    @Column(name = "publishing_unit", nullable = false, length = 50)
    public String getPublishingUnit() {
        return this.publishingUnit;
    }


    public void setPublishingUnit(String publishingUnit) {
        this.publishingUnit = publishingUnit;
    }

    @Column(name = "record_type", nullable = false)
    public int getRecordType() {
        return this.recordType;
    }


    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher", nullable = true)
    public Person getPublisher() {
        return this.publisher;
    }


    public void setPublisher(Person publisher) {
        this.publisher = publisher;
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

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "t_quality_specification_attachment",
//            joinColumns = {@JoinColumn(name = "quality_specification_id", nullable = false,
//                    updatable = false)},
//            inverseJoinColumns = {
//                    @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualitySpecification")
    public Set<QualitySpecificationAttachment> getQualitySpecificationAttachments() {
		return qualitySpecificationAttachments;
	}

	public void setQualitySpecificationAttachments(Set<QualitySpecificationAttachment> qualitySpecificationAttachments) {
		this.qualitySpecificationAttachments = qualitySpecificationAttachments;
	}

    @Transient
    public List<QualitySpecificationAttachment> getArchivesToDelete() {
        return this.archivesToDelete;
    }

	public void setArchivesToDelete(List<QualitySpecificationAttachment> archivesToDelete) {
        this.archivesToDelete = archivesToDelete;
    }
    
    
}
