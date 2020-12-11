package project.edge.common.constant;

/**
 * 中继段类型的枚举
 *
 */
public enum SegmentTypeEnum {

    /**
     * 城域光缆
     */
    METRO_CABLE(1, "segment.type.metro.cable"),

    /**
     * 高速光缆
     */
    HIGH_SPEED_CABLE(2, "segment.type.high.speed.cable");

    private Integer value;

    private String resourceName;

    private SegmentTypeEnum(Integer value, String resourceName) {
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
        for (SegmentTypeEnum s : SegmentTypeEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        
        //changed by huang 解决空字符串标签
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
