package project.edge.common.constant;

/**
 * 质量目标类型的枚举
 *
 */
public enum ObjectiveTypeEnum {

    /**
     * 类型1
     */
    OBJECTIVE_TYPE_1(1, "quality.objective.type.1"),

    /**
     * 类型2
     */
    OBJECTIVE_TYPE_2(2, "quality.objective.type.2"),

    /**
     * 类型3
     */
    OBJECTIVE_TYPE_3(3, "quality.objective.type.3"),

    /**
     * 类型4
     */
    OBJECTIVE_TYPE_4(4, "quality.objective.type.4");

    private Integer value;

    private String resourceName;

    private ObjectiveTypeEnum(Integer value, String resourceName) {
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
        for (ObjectiveTypeEnum s : ObjectiveTypeEnum.values()) {
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
