package project.edge.common.constant;

/**
 * 流程动作的枚举
 *
 */
public enum FlowActionEnum {

    /**
     * 提交审核(审核中)
     */
    REVIEWING(1, "flow.action.reviewing"),

    /**
     * 审核通过
     */
    REVIEW_PASSED(2, "flow.status.review.passed"),

    /**
     * 审核不通过
     */
    REVIEW_UNPASSED(3, "flow.status.review.unpassed"),

    /**
     * 撤回(撤回中)
     */
    WITHDRAWING(4, "flow.action.withdrawing"),
    
    /**
     * 已撤回
     */
    WITHDRAWN(5, "flow.status.withdrawn");

    private Integer value;

    private String resourceName;

    private FlowActionEnum(Integer value, String resourceName) {
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
        for (FlowActionEnum s : FlowActionEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        //屏蔽出差，huang
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
