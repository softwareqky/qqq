package project.edge.common.constant;

/**
 * 项目组的枚举
 *
 */
public enum ProjectPersonOnDutyEnum {

    /**
     * 在岗
     */
    ON_DUTY(0, "project.person.on.duty"),

    /**
     * 不在岗
     */
    NOT_ON_DUTY(1, "project.person.not.on.duty");

    private Integer value;

    private String resourceName;

    private ProjectPersonOnDutyEnum(Integer value, String resourceName) {
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
        for (ProjectPersonOnDutyEnum s : ProjectPersonOnDutyEnum.values()) {
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
