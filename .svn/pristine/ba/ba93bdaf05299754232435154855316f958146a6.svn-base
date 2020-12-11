package project.edge.web.controller.general.popup;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;

import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.util.ControllerMapUtils;
import project.edge.web.controller.common.SingleGridController;

/**
 * 共通的选择弹出画面。
 *
 */
public abstract class AbstractPopupManager {

    /**
     * 弹出的dialog的id模板
     */
    private static final String POPUP_DIALOG_ID_TEMPLATE = "Global_P00000_%1$s-ControlDialog";

    /**
     * 弹出的dialog的id
     */
    private static final String MODEL_KEY_POPUP_DIALOG_ID = "PopupDialogId";

    /**
     * 一览URL
     */
    private static final String MODEL_KEY_LIST_URL = "ControlListUrl";

    /**
     * 一览字段
     */
    private static final String MODEL_KEY_LIST_FIELDS = "ControlListFields";

    /**
     * 一览固定字段
     */
    private static final String MODEL_KEY_LIST_FROZEN_FIELDS = "ControlListFrozenFields";

    /**
     * 一览默认排序
     */
    private static final String MODEL_KEY_DEFAULT_ORDER = "ControlDefaultOrder";

    /**
     * 检索按钮
     */
    private static final String MODEL_KEY_SEARCH_FUNCTIONS = "ControlSearchFunctions";

    /**
     * 检索字段
     */
    private static final String MODEL_KEY_SEARCH_FIELDS = "ControlSearchFields";

    /**
     * ComboBox option map
     */
    private static final String MODEL_KEY_OPTION_MAP = "ControlOptionMap";

    /**
     * 获得Controller。
     * 
     * @return
     */
    protected abstract SingleGridController<?, ?> getController();


    /**
     * 在Model中，准备选择弹出画面中，各个元素的数据。
     * 
     * @param userBean
     * @param model
     * @param locale
     */
    public void preparePopup(SessionUserBean userBean, Model model, Locale locale) {

        this.getController().main(null, model, userBean, locale);
        Map<String, Object> modelMap = model.asMap();

        String dataType = this.getController().getDataType().toLowerCase();

        model.addAttribute(dataType + MODEL_KEY_DEFAULT_ORDER,
                modelMap.get(ControllerModelKeys.DEFAULT_ORDER));

        @SuppressWarnings("unchecked")
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);
        model.addAttribute(dataType + MODEL_KEY_LIST_URL, urlMap.get(ControllerUrlMapKeys.LIST));

        model.addAttribute(dataType + MODEL_KEY_OPTION_MAP,
                modelMap.get(ControllerModelKeys.OPTION_MAP));

        @SuppressWarnings("unchecked")
        List<FunctionBean> searchFunctions =
                (List<FunctionBean>) modelMap.get(ControllerModelKeys.SEARCH_FUNCTIONS);
        if (searchFunctions.size() == 3) { // 去掉高级检索按钮
            searchFunctions.remove(2);
        }

        String popupDialogId =
                String.format(POPUP_DIALOG_ID_TEMPLATE, this.getController().getDataType());
        model.addAttribute(dataType + MODEL_KEY_POPUP_DIALOG_ID, popupDialogId);

        String gridId = popupDialogId + "-MainDatagrid";
        String filterFormId = popupDialogId + "-FilterForm";

        if (searchFunctions.size() == 2) {
            searchFunctions.get(0)
                    .setHandler(String.format(ControllerMapUtils.SEARCH_JS, gridId, filterFormId));
            searchFunctions.get(1).setHandler(
                    String.format(ControllerMapUtils.CLEAR_SEARCH_JS, gridId, filterFormId, null));
            model.addAttribute(dataType + MODEL_KEY_SEARCH_FUNCTIONS, searchFunctions);
        }

        model.addAttribute(dataType + MODEL_KEY_SEARCH_FIELDS,
                modelMap.get(ControllerModelKeys.SEARCH_FIELDS));

        model.addAttribute(dataType + MODEL_KEY_LIST_FIELDS,
                modelMap.get(ControllerModelKeys.LIST_FIELDS));
        model.addAttribute(dataType + MODEL_KEY_LIST_FROZEN_FIELDS,
                modelMap.get(ControllerModelKeys.LIST_FROZEN_FIELDS));
    }
}
