package project.edge.common.constant;

/**
 * 预警枚举
 *
 */
public enum PredefinedRulePeriodEnum {

    /**
     * 年初1-2月
     */
    BEGGING_YEAR(0, "ui.fields.predefined.rule.beginning_year"),
    
    /**
     * 半年
     */
    HALF_YEAR(1, "ui.fields.predefined.rule.half_year"),

    /**
     * 年底11-12
     */
    ENDING_YEAR(2, "ui.fields.predefined.rule.ending_year");

    private Integer value;

    private String resourceName;

    private PredefinedRulePeriodEnum(Integer value, String resourceName) {
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
        for (PredefinedRulePeriodEnum s : PredefinedRulePeriodEnum.values()) {
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
