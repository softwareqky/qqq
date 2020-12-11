/**
 * 
 */
package project.edge.common.constant;


/**
 * @author angel_000
 * 链路类型的枚举
 */
public enum LinkTypeEnum {

    /**
     * 可编程网络组传输设备
     */
    PROGRAMMABLE_NETWORK(1, "station.device.type.programmable.network"),

    /**
     * SDN网络组传输设备
     */
    SDN_NETWORK(2, "station.device.type.sdn.network");

    private Integer value;

    private String resourceName;

    private LinkTypeEnum(Integer value, String resourceName) {
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
        for (LinkTypeEnum s : LinkTypeEnum.values()) {
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
