package project.edge.common.constant;

/**
 * 附件类型的枚举
 *
 */
public enum AttachmentTypeEnum {

    /**
     * 任务交付成果附件
     */
    TASK_ATTACHMENT(1, "attachment.type.task_attachment"),

    /**
     * 进度附件
     */
    PROGRESS_ATTACHMENT(2, "attachment.type.progress_attachment");

    private Integer value;

    private String resourceName;

    private AttachmentTypeEnum(Integer value, String resourceName) {
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
        for (AttachmentTypeEnum s : AttachmentTypeEnum.values()) {
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
