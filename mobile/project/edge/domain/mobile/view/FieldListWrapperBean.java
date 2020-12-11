/**
 * 
 */
package project.edge.domain.mobile.view;

import java.util.ArrayList;
import java.util.List;

/**
 * 包装fieldList的DTO。
 *
 */
public class FieldListWrapperBean {

    private String dataId;

    /** 字段数组 */
    private List<FieldBean> fieldList = new ArrayList<>();

    public FieldListWrapperBean() {}

    public FieldListWrapperBean(String dataId) {
        this.dataId = dataId;
    }

    public List<FieldBean> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<FieldBean> fieldList) {
        this.fieldList = fieldList;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

}

