package project.edge.common.constant;

/**
 * 项目附件类型枚举。
 *
 */
public enum ProjectAttachmentTypeEnum {

    /**
     * 项目附件
     */
    ARCHIVE(0, "ui.fields.archive"),

    /**
     * 项目建议书
     */
    PROPOSAL(1, "ui.fields.project.proposal.file"),

    /**
     * 可研文档
     */
    FEASIBILITY(2, "ui.fields.project.feasibility.file"),

    /**
     * 环评材料
     */
    EIA(3, "ui.fields.project.eia.file"),

    /**
     * 能评材料
     */
    ENERGY_ASSESSMENT(4, "ui.fields.project.energy.assessment.file"),

    /**
     * 土地材料
     */
    LAND(5, "ui.fields.project.land.file"),

    /**
     * 初设文件
     */
    PRELIMINARY_DESIGN(6, "ui.fields.project.preliminary.design.file");

    private Integer value;

    private String resourceName;

    private ProjectAttachmentTypeEnum(Integer value, String resourceName) {
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
        for (ProjectAttachmentTypeEnum s : ProjectAttachmentTypeEnum.values()) {
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
