package project.edge.common.constant;

public enum TenderResultTypeEnum {
	NOT_WIN(0, "ui.common.tender.result.notwin"),

    WIN(1, "ui.common.tender.result.win");

    private Integer value;

    private String resourceName;

    private TenderResultTypeEnum(Integer value, String resourceName) {
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
        for (TenderResultTypeEnum data : TenderResultTypeEnum.values()) {
            if (data.value().equals(value)) {
                return data.resourceName;
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
