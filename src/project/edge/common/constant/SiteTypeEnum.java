package project.edge.common.constant;

/**
 * 站点类型的枚举
 *
 */
public enum SiteTypeEnum {

    /**
     * 核心节点
     */
    CORE_NODE(1, "site.type.core.node"),

    /**
     * 高速传输核心节点
     */
    HIGH_SPEED_CORE_NODE(2, "site.type.high.speed.core.node"),

    /**
     * 高速传输中继节点
     */
    HIGH_SPEED_RELAY_NODE(3, "site.type.high.speed.relay.node"),
    
    /**
     * 边缘节点
     */
    EDGE_NODE(4, "site.type.edge.node");

    private Integer value;

    private String resourceName;

    private SiteTypeEnum(Integer value, String resourceName) {
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
        for (SiteTypeEnum s : SiteTypeEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        
        //changed by huang 解决空字符串标签
//        return null;
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
