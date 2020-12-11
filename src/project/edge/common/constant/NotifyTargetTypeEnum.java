package project.edge.common.constant;

/**
 * 通知提醒对象类型的枚举
 */
public enum NotifyTargetTypeEnum {
	
	/**
	 * 
	 */
	PROJECT_INSPECT(1, "项目巡检"),
	PROGRESS_WARNING(2, "进度预警"),
	NOTICE_ANNOUNCEMENT(3, "公告提醒"),
	PLAN_TASK_PROGRESS(4, "任务进度");
	
	private Integer value;

    private String resourceName;

    private NotifyTargetTypeEnum(Integer value, String resourceName) {
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
        for (NotifyTargetTypeEnum s : NotifyTargetTypeEnum.values()) {
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
        for (NotifyTargetTypeEnum s : NotifyTargetTypeEnum.values()) {
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
