package project.edge.common.constant;

/**
 * 纸质档案借阅管理文件在库状态枚举
 * @author wd983
 *
 */
public enum PaperLibraryStatusEnum {
	IN_LIBRARY(0, "paper.library.status.in"),
	
	REQ_LIBRARY(1, "paper.library.status.req"),
	
	WAIT_TAKE(2, "paper.library.status.wait"),
	
	OUT_LIBRARY(3, "paper.library.status.out");
	
	private Integer value;

    private String resourceName;

    private PaperLibraryStatusEnum(Integer value, String resourceName) {
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
        for (PaperLibraryStatusEnum s : PaperLibraryStatusEnum.values()) {
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
