package project.edge.web.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.domain.entity.DataFields;
import project.edge.domain.entity.Page;
import project.edge.domain.filter.PageFilter;

/**
 * 共通的Controller，典型的画面为表单，如单独打开的新建/修改画面，系统设置画面等等。
 * 
 */
public abstract class FormController extends SingleGridController<String, ViewBean> {

    protected static final String FORM_VIEW_NAME = "common/page/formPage"; // 默认的JSP

    /**
     * 获取数据记录。
     * 
     * @return Map
     */
    protected abstract Map<String, Object> getRecord();

    /**
     * 保存数据记录。
     * 
     * @param record
     */
    protected abstract void saveRecord(Map<String, Object> record);

    /**
     * 不需要此方法。
     */
    @Override
    protected Service<String, String> getDataService() {
        return null;
    }

    /**
     * 不需要此方法。
     */
    @Override
    protected ViewConverter<String, ViewBean> getViewConverter() {
        return null;
    }

    /**
     * 不需要此方法。
     */
    @Override
    protected List<CommonFilter> getUniqueFilter(ViewBean bean) {
        return null;
    }

    /**
     * 不需要此方法。
     */
    @Override
    protected List<CommonFilter> getUniqueFilter(ViewBean bean, String oldEntity) {
        return null;
    }

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return FORM_VIEW_NAME;
    }

    /**
     * 不需要此方法。
     */
    @Override
    protected boolean isUserHasAuthority(SessionUserBean userBean, ViewBean bean) {
        return false;
    }

    @Override
    public String main(Map<String, String> paramMap, Model model, SessionUserBean userBean,
            Locale locale) {

        // 1. CRUD URL Map/id Map/javascript Map
        Map<String, String> idMap = this.prepareIdMap();
        model.addAttribute(ControllerModelKeys.ID_MAP, idMap);

        Map<String, String> urlMap = this.prepareUrlMap();
        model.addAttribute(ControllerModelKeys.URL_MAP, urlMap);

        Map<String, String> jsMap = this.prepareJsMap(idMap, urlMap);
        model.addAttribute(ControllerModelKeys.JS_MAP, jsMap);

        // 2. 下拉列表项
        Map<String, List<ComboboxOptionBean>> optionMap = this.prepareOptionMap(locale);
        if (optionMap != null) {
            model.addAttribute(ControllerModelKeys.OPTION_MAP, optionMap);
        }

        // 3. 功能栏的各个按钮及其对应的js事件处理函数在Controller内统一指定
        PageFilter pageFilter = new PageFilter();

        String pid = this.getPageId();
        pageFilter.addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value())
                .addExact("isFunction", OnOffEnum.ON.value()); // 当前画面的、启用的功能[t_page]

        if (!StringUtils.isEmpty(userBean.getRoleId()) && !userBean.isAccessAllPages()) {
            pageFilter.setRoleId(userBean.getRoleId()); // 用户角色不为空，且该角色只能访问部分画面时，用role id过滤

        }

        List<Page> pages = this.pageService.list(pageFilter, null); // 默认按照pageOrder字段排序
        ArrayList<FunctionBean> functions = new ArrayList<>();
        ArrayList<FunctionBean> searchFunctions = new ArrayList<>();

        this.prepareFunctions(pages, functions, searchFunctions, jsMap, locale);

        ArrayList<FunctionBean> navFunctions = new ArrayList<>();
        for (FunctionBean f : functions) {
            if (!f.isSeperator()) {
                navFunctions.add(f);
            }
        }
        model.addAttribute(ControllerModelKeys.FUNCTIONS, navFunctions);

        // 4. 获取画面所需的各个数据字段
        CommonFilter dataFieldsFilter = new CommonFilter().addExact("dataType", this.getDataType())
                .addExact("pageId", this.getPageId()).addExact("isEnabled", OnOffEnum.ON.value())
                .addExact("isCommonVisible", OnOffEnum.ON.value()); // 根据数据类型获取全局可见的字段

        List<DataFields> fields = this.dataFieldsService.list(dataFieldsFilter, null); // 默认按fieldOrder字段排序

        ArrayList<FieldBean> editFields = new ArrayList<>(); // 修改弹出画面的字段
        String lastFieldGroupEdit = null;

        for (DataFields f : fields) {

            // 转换
            FieldBean fieldBean = new FieldBean(f, this.messageSource, locale);
            this.postFieldBeanInit(fieldBean, paramMap, model, userBean);

            String fieldGroup = f.getFieldGroup();

            // 归类

            if (fieldBean.isEditVisible()) { // 修改字段

                // 判断是否字段组
                if (StringUtils.isEmpty(fieldGroup)) {
                    if (!StringUtils.isEmpty(lastFieldGroupEdit)) {
                        editFields.add(new FieldBean(true));
                    }
                } else {
                    if (!fieldGroup.equals(lastFieldGroupEdit)) {
                        editFields.add(new FieldBean(true));
                    }
                }
                lastFieldGroupEdit = fieldGroup;

                editFields.add(fieldBean);
            }
        }

        model.addAttribute(ControllerModelKeys.EDIT_FIELDS, editFields);

        // 5. 找到数据
        Map<String, Object> record = this.getRecord();
        model.addAttribute(ControllerModelKeys.RECORD, record);

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

        // 8. page title
        String pageTitle = this.pageService.find(this.getPageId()).getPageName();
        model.addAttribute(ControllerModelKeys.PAGE_TITLE,
                this.messageSource.getMessage(pageTitle, null, locale));

        // 9. 返回View Name
        return this.getMainView(paramMap);
    }

    /**
     * 保存Form，返回Json格式。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    public JsonResultBean saveForm(@RequestParam Map<String, Object> paramMap,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {
            // 修改
            this.saveRecord(paramMap);

            // 重新获取实体，及其关联，用于在一览画面显示
            Map<String, Object> recordToRender = this.getRecord();

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, recordToRender);

        } catch (Exception e) {
            getLogger().error("Exception updating the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
