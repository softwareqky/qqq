package project.edge.common.constant;

/**
 * 数据来源定义
 *
 */
public enum DataSourceEnum {

	OWNER_CREATE(0, "data.source.owner.create"),
	
	FROM_OA(1, "data.source.from.oa");
	
	private Integer value;
	
	private String resourceName;
	
	private DataSourceEnum(Integer value, String resourceName) {
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
        for (DataSourceEnum s : DataSourceEnum.values()) {
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
