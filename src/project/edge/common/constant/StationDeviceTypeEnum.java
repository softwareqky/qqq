package project.edge.common.constant;

/**
 * 局站内设备类型的枚举
 *
 */
public enum StationDeviceTypeEnum {

    /**
     * 基础网络组传输设备
     */
    BASIC_NETWORK_DEVICE(1, "station.device.type.basic.network.device"),

    /**
     * 可编程网络组传输设备
     */
    PROGRAMMABLE_NETWORK_DEVICE(2, "station.device.type.programmable.network.device"),

    /**
     * SDN网络组传输设备
     */
    SDN_NETWORK_DEVICE(3, "station.device.type.sdn.network.device");

    private Integer value;

    private String resourceName;

    private StationDeviceTypeEnum(Integer value, String resourceName) {
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
        for (StationDeviceTypeEnum s : StationDeviceTypeEnum.values()) {
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
