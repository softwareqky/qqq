package project.edge.common.constant;

/**
 * 简报类型的枚举
 *
 */
public enum ReportTypeEnum {

    /**
     * 内部简报
     */
    INTERNAL_REPORT(1, "report.type.internal.report"),

    /**
     * 建设简报
     */
    CONSTRUCTION_REPORT(2, "report.type.construction.report"),

    /**
     * 资金使用简报
     */
    MONEY_COST_REPORT(3, "report.type.money.cost.report"),

    /**
     * 建设进展简报
     */
    CONSTRUCTION_PROGRESS_REPORT(4, "report.type.construction.progress.report"),
	
	/**
     * 个人简报
     */
    PERSONAL_REPORT(5, "report.type.personal.report");

    private Integer value;

    private String resourceName;

    private ReportTypeEnum(Integer value, String resourceName) {
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
        for (ReportTypeEnum s : ReportTypeEnum.values()) {
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
