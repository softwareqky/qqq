package project.edge.common.constant;

/**
 * 滞后原因的枚举
 *
 */
public enum DelayCauseEnum {

    /**
     * 需求增加
     */
    ADD_DEMAND(1, "delay.cause.add.demand"),

    /**
     * 方案更新
     */
    UPDATE_PLAN(2, "delay.cause.update.plan"),

    /**
     * 人员变动
     */
    CHANGE_PERSON(3, "delay.cause.change.person"),

    /**
     * 资金预算紧缺
     */
    LESS_CAPITAL(4, "delay.cause.less.capital");

    private Integer value;

    private String resourceName;

    private DelayCauseEnum(Integer value, String resourceName) {
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
        for (DelayCauseEnum s : DelayCauseEnum.values()) {
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
