/**
 * 
 */
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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_segment")
public class Segment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5552286310349933725L;

    private String id = UUID.randomUUID().toString();

    private Site endA;
    private Site endB;
    private String reuseSegment;
    private SegmentSystemName systemName;
    private int segmentType;
    private DataOption fibreType;
    private Double segmentLength;
    private Double segmentAttenuation;
    private Double formedAttenuation;
    private Double maintainanceRemain;
    private Double designAttenuation;
    private Date initMaterialArchieveDate;
    private Short isFibreTest;
    private String fibreTest;
    private Short isFibreImprove;
    private String fibreImprove;
    private Short isOpticalLayerIntegrate;
    private String opticalLayerIntegrate;
    private Short isBusinessIntegrate;
    private String businessIntegrate;
    private Short isDataTest;
    private String dataTest;
    private Short isTransmitBusinessOn;
    private String transmitBusinessOn;
    private int segmentStatus;
    private Integer manualSegmentStatus;
    private String remark;
    private String progress;
    private Person personInCharge;
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
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_a", nullable = true)
    public Site getEndA() {
        return this.endA;
    }


    public void setEndA(Site endA) {
        this.endA = endA;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_b", nullable = true)
    public Site getEndB() {
        return this.endB;
    }


    public void setEndB(Site endB) {
        this.endB = endB;
    }

    @Column(name = "reuse_segment", nullable = true, length = 50)
    public String getReuseSegment() {
        return this.reuseSegment;
    }


    public void setReuseSegment(String reuseSegment) {
        this.reuseSegment = reuseSegment;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_name", nullable = true)
    @NotFound(action=NotFoundAction.IGNORE) 
    public SegmentSystemName getSystemName() {
        return this.systemName;
    }


    public void setSystemName(SegmentSystemName systemName) {
        this.systemName = systemName;
    }

    @Column(name = "segment_type", nullable = false)
    public int getSegmentType() {
        return this.segmentType;
    }


    public void setSegmentType(int segmentType) {
        this.segmentType = segmentType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fibre_type", nullable = true)
    public DataOption getFibreType() {
        return this.fibreType;
    }


    public void setFibreType(DataOption fibreType) {
        this.fibreType = fibreType;
    }

    @Column(name = "segment_length", nullable = true)
    public Double getSegmentLength() {
        return this.segmentLength;
    }


    public void setSegmentLength(Double segmentLength) {
        this.segmentLength = segmentLength;
    }

    @Column(name = "segment_attenuation", nullable = true)
    public Double getSegmentAttenuation() {
        return this.segmentAttenuation;
    }


    public void setSegmentAttenuation(Double segmentAttenuation) {
        this.segmentAttenuation = segmentAttenuation;
    }

    @Column(name = "formed_attenuation", nullable = true)
    public Double getFormedAttenuation() {
        return this.formedAttenuation;
    }


    public void setFormedAttenuation(Double formedAttenuation) {
        this.formedAttenuation = formedAttenuation;
    }

    @Column(name = "maintainance_remain", nullable = true)
    public Double getMaintainanceRemain() {
        return this.maintainanceRemain;
    }


    public void setMaintainanceRemain(Double maintainanceRemain) {
        this.maintainanceRemain = maintainanceRemain;
    }

    @Column(name = "design_attenuation", nullable = true)
    public Double getDesignAttenuation() {
        return this.designAttenuation;
    }


    public void setDesignAttenuation(Double designAttenuation) {
        this.designAttenuation = designAttenuation;
    }


    @Column(name = "is_fibre_test", nullable = true)
    public Short getIsFibreTest() {
        return this.isFibreTest;
    }


    public void setIsFibreTest(Short isFibreTest) {
        this.isFibreTest = isFibreTest;
    }

    @Column(name = "fibre_test", nullable = true, length = 1024)
    public String getFibreTest() {
        return this.fibreTest;
    }


    public void setFibreTest(String fibreTest) {
        this.fibreTest = fibreTest;
    }

    @Column(name = "is_fibre_improve", nullable = true)
    public Short getIsFibreImprove() {
        return this.isFibreImprove;
    }


    public void setIsFibreImprove(Short isFibreImprove) {
        this.isFibreImprove = isFibreImprove;
    }

    @Column(name = "fibre_improve", nullable = true, length = 1024)
    public String getFibreImprove() {
        return this.fibreImprove;
    }


    public void setFibreImprove(String fibreImprove) {
        this.fibreImprove = fibreImprove;
    }

    @Column(name = "is_optical_layer_integrate", nullable = true)
    public Short getIsOpticalLayerIntegrate() {
        return this.isOpticalLayerIntegrate;
    }


    public void setIsOpticalLayerIntegrate(Short isOpticalLayerIntegrate) {
        this.isOpticalLayerIntegrate = isOpticalLayerIntegrate;
    }

    @Column(name = "optical_layer_integrate", nullable = true, length = 1024)
    public String getOpticalLayerIntegrate() {
        return this.opticalLayerIntegrate;
    }


    public void setOpticalLayerIntegrate(String opticalLayerIntegrate) {
        this.opticalLayerIntegrate = opticalLayerIntegrate;
    }

    @Column(name = "is_business_integrate", nullable = true)
    public Short getIsBusinessIntegrate() {
        return this.isBusinessIntegrate;
    }


    public void setIsBusinessIntegrate(Short isBusinessIntegrate) {
        this.isBusinessIntegrate = isBusinessIntegrate;
    }

    @Column(name = "business_integrate", nullable = true, length = 1024)
    public String getBusinessIntegrate() {
        return this.businessIntegrate;
    }


    public void setBusinessIntegrate(String businessIntegrate) {
        this.businessIntegrate = businessIntegrate;
    }

    @Column(name = "is_data_test", nullable = true)
    public Short getIsDataTest() {
        return this.isDataTest;
    }


    public void setIsDataTest(Short isDataTest) {
        this.isDataTest = isDataTest;
    }

    @Column(name = "data_test", nullable = true, length = 1024)
    public String getDataTest() {
        return this.dataTest;
    }


    public void setDataTest(String dataTest) {
        this.dataTest = dataTest;
    }

    @Column(name = "is_transmit_business_on", nullable = true)
    public Short getIsTransmitBusinessOn() {
        return this.isTransmitBusinessOn;
    }


    public void setIsTransmitBusinessOn(Short isTransmitBusinessOn) {
        this.isTransmitBusinessOn = isTransmitBusinessOn;
    }

    @Column(name = "transmit_business_on", nullable = true, length = 1024)
    public String getTransmitBusinessOn() {
        return this.transmitBusinessOn;
    }


    public void setTransmitBusinessOn(String transmitBusinessOn) {
        this.transmitBusinessOn = transmitBusinessOn;
    }

    @Column(name = "segment_status", nullable = false)
    public int getSegmentStatus() {
        return this.segmentStatus;
    }


    public void setSegmentStatus(int segmentStatus) {
        this.segmentStatus = segmentStatus;
    }

    @Column(name = "manual_segment_status", nullable = true)
    public Integer getManualSegmentStatus() {
        return this.manualSegmentStatus;
    }


    public void setManualSegmentStatus(Integer manualSegmentStatus) {
        this.manualSegmentStatus = manualSegmentStatus;
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

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "init_material_archieve_date", nullable = false, length = 29)
    public Date getInitMaterialArchieveDate() {
        return this.initMaterialArchieveDate;
    }

    
    public void setInitMaterialArchieveDate(Date initMaterialArchieveDate) {
        this.initMaterialArchieveDate = initMaterialArchieveDate;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_in_charge", nullable = true)
    public Person getPersonInCharge() {
        return this.personInCharge;
    }

    
    public void setPersonInCharge(Person personInCharge) {
        this.personInCharge = personInCharge;
    }

    @Column(name = "progress", nullable = true)
	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
    
}
