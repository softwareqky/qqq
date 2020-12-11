package project.edge.common.constant;

/**
 * 权限的枚举
 *
 */
public enum AuthTypeEnum {

    /**
     * 无访问权限
     */
    NO_ACCESS(0, "auth.type.no.access"),

    /**
     * 只读
     */
    READ_ONLY(1, "auth.type.read.only"),

    /**
     * 读写
     */
    READ_WRITE(2, "auth.type.read.write");

    private Integer value;

    private String resourceName;

    private AuthTypeEnum(Integer value, String resourceName) {
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
        for (AuthTypeEnum s : AuthTypeEnum.values()) {
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
