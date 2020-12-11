package project.edge.common.constant;

/**
 * 关联账户类型的枚举
 *
 */
public enum AuthAccountTypeEnum {

    /**
     * 人员
     */
    PERSON(0, "ui.fields.project.person.person"),

    /**
     * 部门
     */
    DEPT(1, "ui.datatype.dept"),

    /**
     * 项目组
     */
    GROUP(2, "ui.menu.item.project.group");

    private Integer value;

    private String resourceName;

    private AuthAccountTypeEnum(Integer value, String resourceName) {
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
        for (AuthAccountTypeEnum s : AuthAccountTypeEnum.values()) {
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
