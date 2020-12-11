package project.edge.web.controller.common;

import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;

import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;

/**
 * 共通的Controller，典型的画面包含左侧的tree和右侧的DataGrid，两者联动。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
public abstract class TreeGridController<T, V extends ViewBean> extends SingleGridController<T, V> {

    protected static final String TREE_GRID_VIEW_NAME = "common/page/treeGridPage"; // 默认的JSP

    /**
     * 左侧部分的数据类型。
     * 
     * @return
     */
    protected String getSideDataType() {
        return DataTypeEnum.PROJECT.value();
    }

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return TREE_GRID_VIEW_NAME;
    }

    @Override
    public String main(Map<String, String> paramMap, Model model, SessionUserBean userBean,
            Locale locale) {
        model.addAttribute(ControllerModelKeys.SIDE_DATA_TYPE, this.getSideDataType());
        return super.main(paramMap, model, userBean, locale);
    }
}
