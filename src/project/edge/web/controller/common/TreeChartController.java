/**
 * 
 */
package project.edge.web.controller.common;

import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;

/**
 * 共通的Controller，典型的画面包含左侧的tree和右侧的Chart，两者联动。
 * 
 * 虽然继承了SingleGridController，但是只用到了很少的共通逻辑。如，只使用了main()方法，且其中只用到了idMap、jsMap和UrlMap。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
public abstract class TreeChartController<T, V extends ViewBean>
        extends SingleGridController<T, V> {

    protected static final String TREE_CHART_VIEW_NAME = "common/page/treeChartPage"; // 默认的JSP

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
        return TREE_CHART_VIEW_NAME;
    }

    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @Override
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 2. CRUD URL Map/id Map/javascript Map
        Map<String, String> idMap = this.prepareIdMap();
        model.addAttribute(ControllerModelKeys.ID_MAP, idMap);

        Map<String, String> urlMap = this.prepareUrlMap();
        model.addAttribute(ControllerModelKeys.URL_MAP, urlMap);

        Map<String, String> jsMap = this.prepareJsMap(idMap, urlMap);
        model.addAttribute(ControllerModelKeys.JS_MAP, jsMap);

        // 6. 使用<jsp:include/>嵌入javascript内容时，使用的JSP文件路径，子类JSP扩展点1
        String jsJspPath = this.getJavascriptJspPath();
        if (!StringUtils.isEmpty(jsJspPath)) {
            model.addAttribute(ControllerModelKeys.INCLUDE_JSP_JS_PATH, jsJspPath);
        }

        // 7. 使用<jsp:include/>嵌入的隐藏区JSP路径，用来实现子类特有的弹出画面功能，子类JSP扩展点2
        String hiddenContentJspPath = this.getHiddenContentJspPath();
        if (!StringUtils.isEmpty(hiddenContentJspPath)) {
            model.addAttribute(ControllerModelKeys.INCLUDE_JSP_HIDDEN_CONTENT_PATH,
                    hiddenContentJspPath);
        }

        // ▲ 指定左树的数据源类型
        model.addAttribute(ControllerModelKeys.SIDE_DATA_TYPE, this.getSideDataType());

        // 8. 返回View Name
        return this.getMainView(paramMap);
    }
}
