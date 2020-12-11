package project.edge.common.constant;

/**
 * 报告类型的枚举
 *
 */
public enum DateTypeEnum {

    /**
     * 周报
     */
    WEEKLY(1, "report.type.weekly"),

    /**
     * 月报
     */
    MONTHLY(2, "report.type.monthly"),

    /**
     * 季报
     */
    QUARTERLY(3, "report.type.quarterly"),

    /**
     * 半年报
     */
    HALF_A_YEAR(4, "report.type.half.a.year"),

    /**
     * 年报
     */
    YEAR(5, "report.type.year");

    private Integer value;

    private String resourceName;

    private DateTypeEnum(Integer value, String resourceName) {
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
        for (DateTypeEnum s : DateTypeEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
