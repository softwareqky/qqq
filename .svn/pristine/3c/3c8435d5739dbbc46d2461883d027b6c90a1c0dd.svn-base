package project.edge.common.constant;

/**
 * 工作周的枚举
 *
 */
public enum WeekdaysEnum {

    /**
     * 周一
     */
    MONDAY(1, "weekdays.monday"),

    /**
     * 周二
     */
    TUESDAY(2, "weekdays.tuesday"),

    /**
     * 周三
     */
    WEDNESDAY(3, "weekdays.wednesday"),

    /**
     * 周四
     */
    THURSDAY(4, "weekdays.thursday"),
    
    /**
     * 周五
     */
    FRIDAY(5, "weekdays.friday"),
    
    /**
     * 周六
     */
    SATURDAY(6, "weekdays.saturday"),
    
    /**
     * 周日
     */
    SUNDAY(7, "weekdays.sunday");

    private Integer value;

    private String resourceName;

    private WeekdaysEnum(Integer value, String resourceName) {
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
        for (WeekdaysEnum s : WeekdaysEnum.values()) {
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
