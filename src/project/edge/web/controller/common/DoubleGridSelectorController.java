package project.edge.web.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.domain.entity.Page;

/**
 * 用于弹出选择画面的共通的Controller，典型的画面包含左侧的备选DataGrid和右侧的选择结果DataGrid，二者联动。其中左侧的datagrid通常是检索用，约定为SubGrid，右侧的datagrid为选择结果MainGrid。
 * 
 * 本画面大多数情况下是只读的，虽然继承了TreeDoubleGridUploadController，但子类一般无需文件上传，也不需要增删改查，只要实现main()即可。
 * 
 * 另外，MainGrid和SubGrid的数据类型、默认排序字段在大多数情况下完全一样。MainGrid甚至不需要检索。SubGrid的一览list()也是由其他Controller实现。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
public abstract class DoubleGridSelectorController<T, V extends ViewBean>
        extends TreeDoubleGridUploadController<T, V> {

    protected static final String TREE_GRID_SELECTOR_VIEW_NAME =
            "common/page/doubleGridSelectorPage"; // 默认的JSP

    /**
     * SubGrid的一览list()由其他Controller实现。
     */
    @Override
    protected Service<T, String> getDataService() {
        return null;
    }

    /**
     * SubGrid的一览list()由其他Controller实现。
     */
    @Override
    protected ViewConverter<T, V> getViewConverter() {
        return null;
    }

    @Override
    protected boolean useFile() {
        return false;
    }

    @Override
    public String getSubDataType() {
        return this.getDataType();
    }

    @Override
    public String getGridTitle(Locale locale) {
        return this.messageSource.getMessage("ui.common.select.expert", null, locale);
    }

    @Override
    public String getSubGridTitle(Locale locale) {
        return null;
    }

    @Override
    protected abstract OrderByDto getGridDefaultOrder();

    @Override
    public OrderByDto getSubGridDefaultOrder() {
        return this.getGridDefaultOrder();
    }

    @Override
    public String getLinkedFilterFieldName() {
        return null;
    }

    /**
     * SubGrid无需增删改。
     */
    @Override
    public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        return new ArrayList<>();
    }

    /**
     * SubGrid的检索和清空检索。
     */
    @Override
    public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {

        List<FunctionBean> searchFunctions = new ArrayList<>();

        // 检索和清空检索
        String pid = this.getPageId();
        CommonFilter filter =
                new CommonFilter().addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value())
                        .addExact("isFunction", OnOffEnum.ON.value()); // 当前画面的、启用的功能[t_page]

        List<Page> pages = this.pageService.list(filter, null);
        for (Page p : pages) {
            FunctionBean f = new FunctionBean(p, this.messageSource, locale);
            f.setId(String.format(SUB_GRID_FUNCTION_ID_TEMPLATE, this.getPageId(),
                    f.getId().substring(f.getId().indexOf("F")))); // 重新设定id

            if (f.getId().endsWith(ControllerFunctionIds.SEARCH)) { // 检索
                f.setHandler(jsMap.get(ControllerJsMapKeys.SUB_GRID_SEARCH));
            } else if (f.getId().endsWith(ControllerFunctionIds.CLEAR_SEARCH)) { // 清空检索
                f.setHandler(jsMap.get(ControllerJsMapKeys.SUB_GRID_CLEAR_SEARCH));
            }

            searchFunctions.add(f);
        }

        return searchFunctions;
    }

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return TREE_GRID_SELECTOR_VIEW_NAME;
    }
}
