package project.edge.domain.view;

/**
 * @author angel_000
 *
 */
public class MapLinkListBean {

    private String id;

    private String linkName;
    private Double endALng;
    private Double endALat;
    private Double endBLng;
    private Double endBLat;
    private String endA;
    private String endB;
    private String linkType;
    private Integer dataLinkStatus;
    private String dataLinkStatusText;

    private String distance;
    private String transmissionLayerBandwidth;
    private String dataLayerBandwidth;
    private String transmitLinkStatus;
    private Integer involvedProjectGroup;
    private String involvedProjectGroupText;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkName() {
        return this.linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Double getEndALng() {
        return this.endALng;
    }

    public void setEndALng(Double endALng) {
        this.endALng = endALng;
    }

    public Double getEndALat() {
        return this.endALat;
    }

    public void setEndALat(Double endALat) {
        this.endALat = endALat;
    }

    public Double getEndBLng() {
        return this.endBLng;
    }

    public void setEndBLng(Double endBLng) {
        this.endBLng = endBLng;
    }

    public Double getEndBLat() {
        return this.endBLat;
    }

    public void setEndBLat(Double endBLat) {
        this.endBLat = endBLat;
    }

    public String getEndA() {
        return this.endA;
    }

    public void setEndA(String endA) {
        this.endA = endA;
    }

    public String getEndB() {
        return this.endB;
    }

    public void setEndB(String endB) {
        this.endB = endB;
    }

    public String getLinkType() {
        return this.linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public Integer getDataLinkStatus() {
        return this.dataLinkStatus;
    }

    public void setDataLinkStatus(Integer dataLinkStatus) {
        this.dataLinkStatus = dataLinkStatus;
    }

    public String getDataLinkStatusText() {
        return this.dataLinkStatusText;
    }

    public void setDataLinkStatusText(String dataLinkStatusText) {
        this.dataLinkStatusText = dataLinkStatusText;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTransmissionLayerBandwidth() {
        return this.transmissionLayerBandwidth;
    }

    public void setTransmissionLayerBandwidth(String transmissionLayerBandwidth) {
        this.transmissionLayerBandwidth = transmissionLayerBandwidth;
    }

    public String getDataLayerBandwidth() {
        return this.dataLayerBandwidth;
    }

    public void setDataLayerBandwidth(String dataLayerBandwidth) {
        this.dataLayerBandwidth = dataLayerBandwidth;
    }

    public String getTransmitLinkStatus() {
        return this.transmitLinkStatus;
    }

    public void setTransmitLinkStatus(String transmitLinkStatus) {
        this.transmitLinkStatus = transmitLinkStatus;
    }

    public Integer getInvolvedProjectGroup() {
        return this.involvedProjectGroup;
    }

    public void setInvolvedProjectGroup(Integer involvedProjectGroup) {
        this.involvedProjectGroup = involvedProjectGroup;
    }

    public String getInvolvedProjectGroupText() {
        return this.involvedProjectGroupText;
    }

    public void setInvolvedProjectGroupText(String involvedProjectGroupText) {
        this.involvedProjectGroupText = involvedProjectGroupText;
    }

}
