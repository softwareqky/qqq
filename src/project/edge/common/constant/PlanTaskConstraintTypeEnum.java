package project.edge.common.constant;

/**
 * 限制类型的枚举
 *
 */
public enum PlanTaskConstraintTypeEnum {

    /**
     * 越早越好
     */
    EARLY(1, "plan.task.constraint.type.early", "asap"),

    /**
     * 越晚越好
     */
    LATE(2, "plan.task.constraint.type.late", "alap"),

    /**
     * 必须开始于
     */
    MUST_START(3, "plan.task.constraint.type.must.start", "mso"),

    /**
     * 必须完成于
     */
    MUST_FINISH(4, "plan.task.constraint.type.must.finish", "mfo"),

    /**
     * 不得早于…开始
     */
    NO_EARLIER_TO_START(5, "plan.task.constraint.type.no.earlier.to.start", "snet"),

    /**
     * 不得晚于…开始
     */
    NO_LATER_TO_START(6, "plan.task.constraint.type.no.later.to.start", "snlt"),
    /**
     * 不得早于…完成
     */
    NO_EARLIER_TO_FINISH(7, "plan.task.constraint.type.no.earlier.to.finish", "fnet"),

    /**
     * 不得晚于…完成
     */
    NO_LATER_TO_FINISH(8, "plan.task.constraint.type.no.later.to.finish", "fnlt");

    private Integer value;

    private String resourceName;

    private String ganttValue;

    private PlanTaskConstraintTypeEnum(Integer value, String resourceName, String ganttValue) {
        this.value = value;
        this.resourceName = resourceName;
        this.ganttValue = ganttValue;
    }

    /**
     * 根据枚举的值获取对应的资源名。
     * 
     * @param value 枚举值
     * @return 资源名
     */
    public static String getResouceName(Integer value) {
        for (PlanTaskConstraintTypeEnum s : PlanTaskConstraintTypeEnum.values()) {
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

    public String ganttValue() {
        return this.ganttValue;
    }
}
