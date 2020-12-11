package project.edge.common.constant;

/**
 * 同步状态的枚举
 *
 */
public enum SyncStatusEnum {

    /**
     * 待发送
     */
    UNSENDED(0, "sync.status.unsended"),

    /**
     * 已发送
     */
    SENT(1, "sync.status.sent"),

    /**
     * 已同步
     */
    SYNCHRONIZED(2, "sync.status.synchronized"),

    /**
     * 无同步数据
     */
    NO_DATA_TO_SYNC(3, "sync.status.no.data.to.sync");

    private Integer value;

    private String resourceName;

    private SyncStatusEnum(Integer value, String resourceName) {
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
        for (SyncStatusEnum s : SyncStatusEnum.values()) {
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
