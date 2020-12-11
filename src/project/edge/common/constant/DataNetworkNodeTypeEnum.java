package project.edge.common.constant;

public enum DataNetworkNodeTypeEnum {

	/**
     * 核心节点
     */
    CORE_NODE(1, "site.type.core.node"),

    /**
     * 一级核心节点
     */
    HIGH_SPEED_CORE_NODE(2, "site.type.core.node.level1"),

    /**
     * 二级核心节点
     */
    HIGH_SPEED_RELAY_NODE(3, "site.type.core.node.level2");

    private Integer value;

    private String resourceName;

    private DataNetworkNodeTypeEnum(Integer value, String resourceName) {
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
        for (DataNetworkNodeTypeEnum s : DataNetworkNodeTypeEnum.values()) {
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
