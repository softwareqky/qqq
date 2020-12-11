package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_link")
public class Link implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7149008661490889943L;

    private String id = UUID.randomUUID().toString();
    private String linkName;
    private Site endA;
    private Site endB;
    // private Short isProgrammableLinkType;
    // private Short isSdnLinkType;
    private String linkType;
    private Integer dataLinkStatus;
    private String distance;
    private String transmissionLayerBandwidth;
    private String dataLayerBandwidth;
    private String transmitLinkStatus;
    private Integer involvedProjectGroup;
    private String remark;
    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    private Person personInCharge;
    private Date flowStartDate;
    private Date flowEndDate;
    private Integer flowStatus;

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
    @JoinColumn(name = "person_in_charge", nullable = true)
    public Person getPersonInCharge() {
        return this.personInCharge;
    }


    public void setPersonInCharge(Person personInCharge) {
        this.personInCharge = personInCharge;
    }

    @Column(name = "link_name", nullable = true, length = 50)
    public String getLinkName() {
        return this.linkName;
    }


    public void setLinkName(String linkName) {
        this.linkName = linkName;
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

    // @Column(name = "is_programmable_link_type", nullable = true)
    // public Short getIsProgrammableLinkType() {
    // return this.isProgrammableLinkType;
    // }
    //
    //
    // public void setIsProgrammableLinkType(Short isProgrammableLinkType) {
    // this.isProgrammableLinkType = isProgrammableLinkType;
    // }
    //
    // @Column(name = "is_sdn_link_type", nullable = true)
    // public Short getIsSdnLinkType() {
    // return this.isSdnLinkType;
    // }
    //
    //
    // public void setIsSdnLinkType(Short isSdnLinkType) {
    // this.isSdnLinkType = isSdnLinkType;
    // }

    @Column(name = "link_type", nullable = true, length = 50)
    public String getLinkType() {
        return this.linkType;
    }


    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }


    @Column(name = "data_link_status", nullable = true)
    public Integer getDataLinkStatus() {
        return this.dataLinkStatus;
    }


    public void setDataLinkStatus(Integer dataLinkStatus) {
        this.dataLinkStatus = dataLinkStatus;

    }

    @Column(name = "distance", nullable = true, length = 50)
    public String getDistance() {
        return this.distance;
    }


    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Column(name = "transmission_layer_bandwidth", nullable = true, length = 50)
    public String getTransmissionLayerBandwidth() {
        return this.transmissionLayerBandwidth;
    }


    public void setTransmissionLayerBandwidth(String transmissionLayerBandwidth) {
        this.transmissionLayerBandwidth = transmissionLayerBandwidth;
    }

    @Column(name = "data_layer_bandwidth", nullable = true, length = 50)
    public String getDataLayerBandwidth() {
        return this.dataLayerBandwidth;
    }


    public void setDataLayerBandwidth(String dataLayerBandwidth) {
        this.dataLayerBandwidth = dataLayerBandwidth;
    }

    @Column(name = "transmit_link_status", nullable = true, length = 50)
    public String getTransmitLinkStatus() {
        return this.transmitLinkStatus;
    }


    public void setTransmitLinkStatus(String transmitLinkStatus) {
        this.transmitLinkStatus = transmitLinkStatus;
    }

    @Column(name = "involved_project_group", nullable = true)
    public Integer getInvolvedProjectGroup() {
        return this.involvedProjectGroup;
    }


    public void setInvolvedProjectGroup(Integer involvedProjectGroup) {
        this.involvedProjectGroup = involvedProjectGroup;
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
    @Column(name = "flow_start_date", nullable = true, length = 29)
    public Date getFlowStartDate() {
        return this.flowStartDate;
    }

    public void setFlowStartDate(Date flowStartDate) {
        this.flowStartDate = flowStartDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "flow_end_date", nullable = true, length = 29)
    public Date getFlowEndDate() {
        return this.flowEndDate;
    }

    public void setFlowEndDate(Date flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    @Column(name = "flow_status", nullable = true)
    public Integer getFlowStatus() {
        return this.flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

}
