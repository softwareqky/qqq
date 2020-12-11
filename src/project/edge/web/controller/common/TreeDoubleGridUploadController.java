package project.edge.web.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.entity.Page;


/**
 * 共通的Controller，典型的画面包含左侧的tree、右上的datagrid和右下的datagrid，三者联动。其中右上的datagrid通常是检索用，约定为SubGrid，右下的datagrid为主Grid。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
public abstract class TreeDoubleGridUploadController<T, V extends ViewBean>
        extends TreeGridUploadController<T, V> {

    protected static final String TREE_DBL_GRID_VIEW_NAME = "common/page/treeDoubleGridPage"; // 默认的JSP

    protected static final String SUB_GRID_FUNCTION_ID_TEMPLATE = "%1$s-SubGrid-%2$s";

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        // 增加SubGrid相关的id
        Map<String, String> subGridIdMap =
                ControllerMapUtils.buildSubGridIdMap(this.getPageId(), this.getSubDataType());
        idMap.putAll(subGridIdMap);

        return idMap;
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        ControllerMapUtils.buildSubGridUrlMap(urlMap);
        return urlMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 增加SubGrid相关的JS
        Map<String, String> subGridJsMap = ControllerMapUtils.buildSubGridJsMap(idMap, urlMap,
                this.subGridUseFile(), this.getSubGridJsCallbackObjName());
        jsMap.putAll(subGridJsMap);

        return jsMap;
    }

    /**
     * 共通JS方法中，SubGrid的callback对象名。如调用CrudUtils.openAddFormDialog时传入的，每个画面必须各不相同，通常是大写的数据类型，例如，立项画面是PROJECT_INIT。
     * 
     * @return
     */
    protected String getSubGridJsCallbackObjName() {
        return null;
    }

    /**
     * SubGrid的新建修改使用弹出对话框时，是否包含文件上传。如果新建修改是弹出另外的画面，则不使用此方法。
     *
     * @return
     */
    protected boolean subGridUseFile() {
        return false;
    }

    /**
     * 获取SubGrid的数据类型，参考[t_data_config].data_type。
     * 
     * @return 数据类型
     */
    public abstract String getSubDataType();

    /**
     * 获取主Grid的title。
     * 
     * @param locale locale
     * @return
     */
    public abstract String getGridTitle(Locale locale);

    /**
     * 获取SubGrid的title。
     * 
     * @param locale locale
     * @return
     */
    public abstract String getSubGridTitle(Locale locale);

    /**
     * 获取SubGrid的默认排序。
     * 
     * @return
     */
    public abstract OrderByDto getSubGridDefaultOrder();

    /**
     * 获取SubGrid点击行时，刷新MainGrid时使用的检索字段名。
     * 
     * @return
     */
    public abstract String getLinkedFilterFieldName();

    /**
     * 获取SubGrid的功能按钮。
     * 
     * @param idMap
     * @param urlMap
     * @param jsMap
     * @return
     */
    public abstract List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale);

    /**
     * 获取SubGrid的检索功能按钮。
     * 
     * @param idMap
     * @param urlMap
     * @param jsMap
     * @return
     */
    public abstract List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale);

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return TREE_DBL_GRID_VIEW_NAME;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String main(Map<String, String> paramMap, Model model, SessionUserBean userBean,
            Locale locale) {

        String view = super.main(paramMap, model, userBean, locale);

        // 1. 双Grid的标题
        model.addAttribute(ControllerModelKeys.GRID_TITLE, this.getGridTitle(locale));
        model.addAttribute(ControllerModelKeys.SUB_GRID_TITLE, this.getSubGridTitle(locale));

        // 2. 检索用Grid的一览的默认排序
        model.addAttribute(ControllerModelKeys.SUB_GRID_DEFAULT_ORDER,
                this.getSubGridDefaultOrder());

        // 3. 检索用Grid的功能栏的各个按钮
        Map<String, Object> modelMap = model.asMap();
        Map<String, String> idMap = (Map<String, String>) modelMap.get(ControllerModelKeys.ID_MAP);
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);
        Map<String, String> jsMap = (Map<String, String>) modelMap.get(ControllerModelKeys.JS_MAP);
        List<FunctionBean> functions =
                this.getSubGridFunctionList(idMap, urlMap, jsMap, userBean, locale);
        List<FunctionBean> searchFunctions =
                this.getSubGridSearchFunctionList(idMap, urlMap, jsMap, userBean, locale);
        model.addAttribute(ControllerModelKeys.SUB_GRID_FUNCTIONS, functions);
        model.addAttribute(ControllerModelKeys.SUB_GRID_SEARCH_FUNCTIONS, searchFunctions);

        boolean canEditData = false;
        for (FunctionBean f : functions) {
            if (!f.isSeperator() && f.getId().endsWith(ControllerFunctionIds.MODIFY)) {
                canEditData = true;
            }
        }
        model.addAttribute(ControllerModelKeys.CAN_SUB_GRID_EDIT_DATA, canEditData);

        // 4. 获取检索用Grid的各个数据字段
        CommonFilter dataFieldsFilter = new CommonFilter()
                .addExact("dataType", this.getSubDataType()).addExact("pageId", this.getPageId())
                .addExact("isEnabled", OnOffEnum.ON.value()); // 根据数据类型获取全局可见的字段

        boolean isAdvSearchFieldsEmpty =
                this.processFields(dataFieldsFilter, true, paramMap, model, userBean, locale);

        // 修正高级检索按钮，在没有高级检索字段时，不显示该按钮
        if (isAdvSearchFieldsEmpty && searchFunctions.size() == 3) {
            searchFunctions.remove(2);
        }

        // 5. 获取SubGrid点击行时，刷新MainGrid时使用的检索字段名
        model.addAttribute(ControllerModelKeys.SUB_GRID_LINKED_FILTER_FIELD_NAME,
                this.getLinkedFilterFieldName());

        return view;
    }


    /**
     * 准备功能按钮。
     * 
     * @param pages
     * @param functions
     * @param searchFunctions
     * @param jsMap
     * @param locale
     * @return 是否有权限双击datagrid弹出修改画面
     */
    protected boolean prepareSubGridFunctions(List<Page> pages, ArrayList<FunctionBean> functions,
            ArrayList<FunctionBean> searchFunctions, Map<String, String> jsMap, Locale locale) {

        Map<String, String> funcGroupMap = new HashMap<String, String>(); // 用来控制按钮的分隔的添加
        String crudFuncGroup = "crudFuncGroup";
        String auditFuncGroup = "auditFuncGroup";

        boolean canEditData = false;
        for (Page p : pages) {

            String pageId = p.getId();

            if (pageId.endsWith(ControllerFunctionIds.ADD)) { // 新建

                this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
                        jsMap.get(ControllerJsMapKeys.SUB_GRID_OPEN_ADD), locale);

            } else if (pageId.endsWith(ControllerFunctionIds.DELETE)) { // 删除

                this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
                        jsMap.get(ControllerJsMapKeys.SUB_GRID_OPEN_DELETE), locale);

            } else if (pageId.endsWith(ControllerFunctionIds.MODIFY)) { // 修改

                this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
                        jsMap.get(ControllerJsMapKeys.SUB_GRID_OPEN_EDIT), locale);
                canEditData = true;

            } else if (pageId.endsWith(ControllerFunctionIds.VIEW)) { // 查看

                this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
                        jsMap.get(ControllerJsMapKeys.SUB_GRID_OPEN_VIEW), locale);

            } else if (pageId.endsWith(ControllerFunctionIds.SEARCH)) { // 检索

                this.parseFunction(p, searchFunctions, null, null,
                        jsMap.get(ControllerJsMapKeys.SUB_GRID_SEARCH), locale);

            } else if (pageId.endsWith(ControllerFunctionIds.CLEAR_SEARCH)) { // 清空检索

                this.parseFunction(p, searchFunctions, null, null,
                        jsMap.get(ControllerJsMapKeys.SUB_GRID_CLEAR_SEARCH), locale);

            } else if (pageId.endsWith(ControllerFunctionIds.SUBMIT_AUDIT)) { // 提交审核

                if (isOaFlow()) {
                    this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
                        jsMap.get(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT), locale);
                } else {
                    this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
                        jsMap.get(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT), locale);
                }
                
            } else if (pageId.endsWith(ControllerFunctionIds.AUDIT_LOG)) { // 审核记录

            	if (isOaFlow()) {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
							jsMap.get(ControllerJsMapKeys.OPEN_AUDIT_LOG), locale);
				} else {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
							jsMap.get(ControllerJsMapKeys.OPEN_LOCAL_AUDIT_LOG), locale);
				}
            }else if (pageId.endsWith(ControllerFunctionIds.WITHDRAW)) { //撤回
                if(isOaFlow()) {
                    this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
                            jsMap.get(ControllerJsMapKeys.OPEN_WITHDRAW), locale);
                }else {
                    this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
                            jsMap.get(ControllerJsMapKeys.OPEN_LOCAL_WITHDRAW), locale);
                }
                
            } else {
                this.createFunctionBean(p, functions, searchFunctions, funcGroupMap, jsMap, locale); // 供子类扩展
            }
        }
        if (!functions.isEmpty()) {
            functions.add(new FunctionBean(true));
        }
        return canEditData;
    }

}
