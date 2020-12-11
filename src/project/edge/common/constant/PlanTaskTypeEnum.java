package project.edge.common.constant;

/**
 * 任务类型的枚举
 *
 */
public enum PlanTaskTypeEnum {

    /**
     * 摘要
     */
    PROJECT("project", ""),

    /**
     * 里程碑
     */
    MILESTONE("milestone", ""),

    /**
     * 普通任务
     */
    TASK("task", ""),

    /**
     * 关键任务
     */
    CRITICAL_TASK("critical_task", ""),

    /**
     * 站点任务
     */
    SITE_TASK("site_task", "");


    private String value;

    private String resourceName;

    private PlanTaskTypeEnum(String value, String resourceName) {
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
        for (PlanTaskTypeEnum s : PlanTaskTypeEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        return null;
    }

    public String value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
