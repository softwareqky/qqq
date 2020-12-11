package project.edge.common.constant;

public enum NotifyWayEnum {
	
	NOTICE(1, "站内提醒"),
	MAIL(2, "邮件"),
	NOTICE_MAIL(3, "站内提醒和邮件");

	private Integer value;

    private String resourceName;

    private NotifyWayEnum(Integer value, String resourceName) {
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
        for (NotifyWayEnum s : NotifyWayEnum.values()) {
            if (s.value().equals(value)) {
                return s.resourceName;
            }
        }
        return null;
    }
    
    /**
     * 根据枚举的名获取对应的资源值。
     * 
     * @param value 枚举值
     * @return 资源名
     */
    public static Integer getResouceValue(String name) {
        for (NotifyWayEnum s : NotifyWayEnum.values()) {
            if (s.resourceName().equals(name)) {
                return s.value;
            }
        }
        return 0;
    }

    public Integer value() {
        return this.value;
    }

    public String resourceName() {
        return this.resourceName;
    }
}
