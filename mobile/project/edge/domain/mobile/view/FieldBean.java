/**
 * 
 */
package project.edge.domain.mobile.view;

import garage.origin.common.util.CheckUtils;

/**
 * @author angel_000
 *         用来保存字段信息。
 */
public class FieldBean {

    /** 字段显示的名称 */
    protected String captionName;

    /** 字段值 */
    protected String fieldValue;

    public FieldBean() {}

    public FieldBean(String captionName) {
        this.captionName = captionName;
    }

    public FieldBean(String captionName, String fieldValue) {
        this.captionName = captionName;
        this.fieldValue = CheckUtils.checkString(fieldValue);
    }

    public String getCaptionName() {
        return this.captionName;
    }

    public void setCaptionName(String captionName) {
        this.captionName = captionName;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = CheckUtils.checkString(fieldValue);
    }
}
