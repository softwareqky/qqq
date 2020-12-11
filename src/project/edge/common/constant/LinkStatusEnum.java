package project.edge.common.constant;

/**
 * 链路状态的枚举
 *
 */
public enum LinkStatusEnum {

    /**
     * 未具备
     */
    NOT_BUILT(1, "link.status.not.possessed"),

    /**
     * 已具备
     */
    BUILDING(2, "link.status.alreadypossessed");

    private Integer value;

    private String resourceName;

    private LinkStatusEnum(Integer value, String resourceName) {
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
        for (LinkStatusEnum s : LinkStatusEnum.values()) {
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
