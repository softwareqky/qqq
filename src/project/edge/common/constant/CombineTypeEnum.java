package project.edge.common.constant;


public enum CombineTypeEnum {
    /**
     * 未合并
     */
    NOT_COMBINE(0, "combine.type.not.combine"),

    /**
     * 合并
     */
    COMBINE(1, "combine.type.combine");


    private Integer value;

    private String resourceName;

    private CombineTypeEnum(Integer value, String resourceName) {
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
        for (CombineTypeEnum s : CombineTypeEnum.values()) {
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
