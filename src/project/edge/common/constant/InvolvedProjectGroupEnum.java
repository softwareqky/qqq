package project.edge.common.constant;

/**
 * @author angel_000
 *         链路所属项目组的枚举
 */
public enum InvolvedProjectGroupEnum {
    /**
     * 可编程
     */
    PROGRAMMABLE_NETWORK(1, "involved.project.group.programmable"),

    /**
     * SDN
     */
    SDN_NETWORK(2, "involved.project.group.sdn");

    private Integer value;

    private String resourceName;

    private InvolvedProjectGroupEnum(Integer value, String resourceName) {
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
        for (InvolvedProjectGroupEnum s : InvolvedProjectGroupEnum.values()) {
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
