package project.edge.common.constant;

/**
 * 项目组的枚举
 *
 */
public enum ProjectGroupEnum {

    /**
     * 基础网络组
     */
    BASIC_NETWORK(1, "project.group.basic.network"),

    /**
     * 可编程网络组
     */
    PROGRAMMABLE_NETWORK(2, "project.group.programmable.network"),

    /**
     * SDN网络组
     */
    SDN_NETWORK(3, "project.group.sdn.network"),

    /**
     * 大数据组
     */
    LARGE_DATA(4, "project.group.large.data"),
    
    /**
     * 土建及综合保障组
     */
    PROTECTION(5, "project.group.protection");

    private Integer value;

    private String resourceName;

    private ProjectGroupEnum(Integer value, String resourceName) {
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
        for (ProjectGroupEnum s : ProjectGroupEnum.values()) {
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
