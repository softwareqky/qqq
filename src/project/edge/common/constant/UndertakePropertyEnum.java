package project.edge.common.constant;

/**
 * 承担性质的枚举
 *
 */
public enum UndertakePropertyEnum {
    /**
     * 独立
     */
    INDEPENDENT(0, "undertake.property.independent"),

    /**
     * 牵头
     */
    INITIATOR(1, "undertake.property.initiator"),

    /**
     * 参与
     */
    PARTICIPATION(2, "undertake.property.participation");

    private Integer value;

    private String resourceName;

    private UndertakePropertyEnum(Integer value, String resourceName) {
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
        for (UndertakePropertyEnum s : UndertakePropertyEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        // 屏蔽出错,huang
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }
}
