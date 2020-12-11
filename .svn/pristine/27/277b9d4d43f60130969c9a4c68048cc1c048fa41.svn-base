package project.edge.common.constant;

/**
 * 建设状态的枚举（任务完成后的建设状态、站点建设状态、手动站点建设状态、中继段建设状态、手动中继段建设状态）
 *
 */
public enum FacilityConstructionStatusEnum {
    
    /**
     * 红
     */
    RED(0, "facility.construction.status.red"),

    /**
     * 黄
     */
    YELLOW(1, "facility.construction.status.yellow"),

    /**
     * 绿
     */
    GREEN(2, "facility.construction.status.green");

    private Integer value;

    private String resourceName;

    private FacilityConstructionStatusEnum(Integer value, String resourceName) {
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
        for (FacilityConstructionStatusEnum s : FacilityConstructionStatusEnum.values()) {
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
