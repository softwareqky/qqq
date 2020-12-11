package project.edge.common.constant;

/**
 * 人员的在职离职等状态枚举。
 *
 */
public enum PersonStatusEnum {
    
    /**
     * 试用
     */
    TRY_OUT(0, "person.status.try.out"),

    /**
     * 正式
     */
    FORMAL(1, "person.status.formal"),

    /**
     * 离职
     */
    LEAVE(2, "person.status.leave"),

    /**
     * 退休
     */
    RETIREMENT(3, "person.status.retirement");

    private Integer value;

    private String resourceName;

    private PersonStatusEnum(Integer value, String resourceName) {
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
        for (PersonStatusEnum s : PersonStatusEnum.values()) {
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
