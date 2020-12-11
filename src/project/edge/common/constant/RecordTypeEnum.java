package project.edge.common.constant;

/**
 * 质量记录类型的枚举
 *
 */
public enum RecordTypeEnum {

    /**
     * 类型1
     */
    RECORD_TYPE_1(1, "quality.record.type.1"),

    /**
     * 类型2
     */
    RECORD_TYPE_2(2, "quality.record.type.2"),

    /**
     * 类型3
     */
    RECORD_TYPE_3(3, "quality.record.type.3"),

    /**
     * 类型4
     */
    RECORD_TYPE_4(4, "quality.record.type.4");

    private Integer value;

    private String resourceName;

    private RecordTypeEnum(Integer value, String resourceName) {
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
        for (RecordTypeEnum s : RecordTypeEnum.values()) {
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
