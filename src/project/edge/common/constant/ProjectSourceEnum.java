package project.edge.common.constant;

/**
 * 项目来源的枚举
 *
 */
public enum ProjectSourceEnum {

    /**
     * 手工立项
     */
    MANUAL_PROJECT(1, "project.source.manual.project"),

    /**
     * 投标立项
     */
    BIDDING_PROJECT(2, "project.source.bidding.project");

    private Integer value;

    private String resourceName;

    private ProjectSourceEnum(Integer value, String resourceName) {
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
        for (ProjectSourceEnum s : ProjectSourceEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        //屏蔽出错,huang
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }
    
}
