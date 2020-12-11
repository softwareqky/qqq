package project.edge.common.constant;

/**
 * 传输站型的枚举
 *
 */
public enum SdnNetworkTransmitStationTypeEnum {
    
//    /**
//     * SDN设备1
//     */
//    SDN_DEVICE1(1, "transmit.station.type.sdn.device1"),
//    
//    /**
//     * SDN设备2
//     */
//    SDN_DEVICE2(2, "transmit.station.type.sdn.device2");
    
    /**
     * 核心节点
     */
    CORE_NODE(1, "site.type.core.node");
    

    private Integer value;

    private String resourceName;

    private SdnNetworkTransmitStationTypeEnum(Integer value, String resourceName) {
        this.value = value;
        this.resourceName = resourceName;
    }

    /**
     * 根据枚举的值获取对应的资源名。
     * 
     * @param value 枚举值
     * @return 资源名
     */
    public static String getResouceName(Integer value) {
        for (SdnNetworkTransmitStationTypeEnum s : SdnNetworkTransmitStationTypeEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        return null;
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
