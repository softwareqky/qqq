package project.edge.common.constant;

/**
 * 传输站型的枚举
 *
 */
public enum ProgrammableNetworkTransmitStationTypeEnum {

//    /**
//     * 可编程设备1
//     */
//    PROGRAMMABLE_DEVICE1(1, "transmit.station.type.programmable.device1"),
//
//    /**
//     * 可编程设备2
//     */
//    PROGRAMMABLE_DEVICE2(2, "transmit.station.type.programmable.device2");
    
    /**
     * 核心节点
     */
    CORE_NODE(1, "site.type.core.node");

    private Integer value;

    private String resourceName;

    private ProgrammableNetworkTransmitStationTypeEnum(Integer value, String resourceName) {
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
        for (ProgrammableNetworkTransmitStationTypeEnum s : ProgrammableNetworkTransmitStationTypeEnum.values()) {
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
