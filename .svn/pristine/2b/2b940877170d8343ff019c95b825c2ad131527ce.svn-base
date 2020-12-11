package project.edge.common.constant;

/**
 * 报告类型的枚举
 *
 */
public enum InvestorEnum {

    /**
     * 国家投
     */
    STATE_INVESTMENT(1, "investor.state.investment"),

    /**
     * 地方投
     */
    LOCAL_INVESTMENT(2, "investor.local.investment");

    private Integer value;

    private String resourceName;

    private InvestorEnum(Integer value, String resourceName) {
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
        for (InvestorEnum s : InvestorEnum.values()) {
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
