package project.edge.common.constant;

/**
 * 前置类型的枚举
 *
 */
public enum PreTaskTypeEnum {

    /**
     * 完成-开始(FS)
     */
    FINISH_START(1, "pretask.type.finish.start", "0"),

    /**
     * 开始-开始(SS)
     */
    START_START(2, "pretask.type.start.start", "1"),

    /**
     * 完成-完成(FF)
     */
    FINISH_FINISH(3, "pretask.type.finish.finish", "2"),

    /**
     * 开始-完成(SF)
     */
    START_FINISH(4, "pretask.type.start.finish", "3");

    private Integer value;

    private String resourceName;

    private String ganttValue;

    private PreTaskTypeEnum(Integer value, String resourceName, String ganttValue) {
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
        for (PreTaskTypeEnum s : PreTaskTypeEnum.values()) {
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
