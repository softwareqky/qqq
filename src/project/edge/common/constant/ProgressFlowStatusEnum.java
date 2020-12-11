package project.edge.common.constant;

/**
 * 进度状态的枚举
 *
 */
public enum ProgressFlowStatusEnum {

    /**
     * 未启动
     */
    NOT_STARTED(1, "progress.flow.status.not.started"),

    /**
     * 延期中
     */
    DEFERRED(2, "progress.flow.status.deferred"),

    /**
     * 进行中
     */
    IN_PROGRESS(3, "progress.flow.status.in.progress"),

    /**
     * 完成
     */
    COMPLETED(4, "progress.flow.status.completed"),

    /**
     * 搁置
     */
    SUSPENDED(5, "progress.flow.status.suspended"),
    
    /**
     * 取消
     */
    CANCELED(6, "progress.flow.status.canceled");

    private Integer value;

    private String resourceName;

    private ProgressFlowStatusEnum(Integer value, String resourceName) {
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
        for (ProgressFlowStatusEnum s : ProgressFlowStatusEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        return "";
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }

}
