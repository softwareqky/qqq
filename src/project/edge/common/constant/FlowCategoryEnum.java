package project.edge.common.constant;

/**
 * 流程分类的枚举
 *
 */
public enum FlowCategoryEnum {

    /**
     * 立项申请
     */
    PROJECT_APPLICATION(1, "flow.category.project.application"),

    /**
     * 立项变更
     */
    PROJECT_CHANGE(2, "flow.category.project.change");

    private Integer value;

    private String resourceName;

    private FlowCategoryEnum(Integer value, String resourceName) {
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
        for (FlowCategoryEnum s : FlowCategoryEnum.values()) {
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
