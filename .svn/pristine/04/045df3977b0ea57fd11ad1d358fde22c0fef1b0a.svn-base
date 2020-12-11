package project.edge.web.controller.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.entity.DataFields;


/**
 * 共通的Controller，根据[t_data_fields]中获取的字段的信息，生成Page需要的数据。用来生成新建画面。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
@Deprecated
public abstract class GenericRecordAddController<T, V extends ViewBean> {

    /**
     * 默认的新建画面JSP
     */
    protected static final String ADD_PAGE_NAME = "common/recordAdd";

    @Resource
    protected MessageSource messageSource;

    @Autowired
    protected ServletContext context;

    @Autowired
    protected ResourceLoader resourceLoader;

    /**
     * 获得子类的logger。
     * 
     * @return
     */
    protected abstract Logger getLogger();

    /**
     * 获取Controller管理的数据类型，参考[t_data_config].data_type，如PLU、ITEM_GROUP...
     * 
     * 画面Open的入口方法将根据数据类型从[t_data_fields]中获取相关的记录。
     * 
     * @return 数据类型
     */
    protected abstract String getDataType();

    /**
     * 获取Controller对应的画面id，参考[t_page].id
     * 
     * 画面Open的入口方法将根据画面id从[t_page]中获取功能对应的记录。
     * 
     * @return 画面id
     */
    protected abstract String getPageId();

    /**
     * 获取新建画面的Page名。提供默认的实现，如果子类不适用共通的JSP，则必须重写此方法。
     * 
     * @return
     */
    protected String getAddPage(Map<String, String> paramMap) {
        return ADD_PAGE_NAME;
    }

    /**
     * 画面使用<jsp:include/>嵌入javascript内容时，指定使用的JSP文件路径。子类JSP的第1个扩展点，必要时，子类应重写此方法。
     * 
     * @return
     */
    protected String getJavascriptJspPath() {
        return null;
    }

    /**
     * 画面使用<jsp:include/>嵌入隐藏区内容(弹出画面)时，指定使用的JSP文件路径。子类JSP的第2个扩展点，必要时，子类应重写此方法。
     * 
     * @return 返回null表示不需要增加额外的<jsp:include/>
     */
    protected String getHiddenContentJspPath() {
        return null;
    }

    /**
     * 在main(...)方法中，当字段从[t_data_fields]加载并转换为FieldBean对象之后调用。必要时，子类应重写此方法，如PLU的价格要
     * 根据系统设置来确定精度，而不是来自[t_data_fields]的设定。
     * 
     * @param field
     */
    protected void postFieldBeanInit(FieldBean field,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
        return;
    }

    /**
     * 新建修改使用弹出对话框时，是否包含文件上传。
     * 
     * @return
     */
    protected boolean useFile() {
        return false;
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
        return null;
    }

    /**
     * CRUD对应的URL是按照规则生成的，通过模板构建相关操作的URL，用来在画面上使用。使用本方法获取CRUD对应的URL Map。子类应重写此方法。
     * 
     * @return URL Map key由ControllerUrlMapKeys定义
     */
    protected abstract Map<String, String> prepareUrlMap();

    /**
     * 创建画面使用的HTML元素的id的Map。子类应重写此方法。
     * 
     * @return id Map key由ControllerIdMapKeys定义
     */
    protected Map<String, String> prepareIdMap() {
        return ControllerMapUtils.buildIdMap(this.getPageId(), this.getDataType());
    }

    /**
     * 创建画面使用的javascript函数的Map。子类应重写此方法。
     * 
     * @return id Map key由ControllerJsMapKeys定义
     */
    protected abstract Map<String, String> prepareJsMap();

    /**
     * temporary
     * 
     * @return
     */
    protected abstract List<DataFields> getDataFieldsList();

    /**
     * temporary
     * 
     * @return
     */
    protected List<Map<String, Object>> getDataList() {
        Map<String, Object> row = new HashMap<>();
        return Collections.singletonList(row);
    }

    /**
     * 新建画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    public String openAdd(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 1. CRUD URL Map/id Map/javascript Map
        Map<String, String> urlMap = this.prepareUrlMap();
        model.addAttribute(ControllerModelKeys.URL_MAP, urlMap);

        Map<String, String> idMap = this.prepareIdMap();
        model.addAttribute(ControllerModelKeys.ID_MAP, idMap);

        Map<String, String> jsMap = this.prepareJsMap();
        model.addAttribute(ControllerModelKeys.JS_MAP, jsMap);

        // 2. 下拉列表项
        Map<String, List<ComboboxOptionBean>> optionMap = this.prepareOptionMap(locale);
        if (optionMap != null) {
            model.addAttribute(ControllerModelKeys.OPTION_MAP, optionMap);
        }

        // 3. 获取画面所需的各个数据字段
        // CommonFilter dataFieldsFilter = new CommonFilter().addExact("dataType",
        // this.getDataType())
        // .addExact("isEnabled", OnOffEnum.ON.value()); // 根据数据类型获取全局可见的字段
        //
        // List<DataFields> fields = this.dataFieldsService.list(dataFieldsFilter, null); //
        // 默认按fieldOrder字段排序
        List<DataFields> fields = this.getDataFieldsList();

        ArrayList<FieldBean> addEditFields = new ArrayList<FieldBean>(); // 新建/修改弹出画面的字段

        for (DataFields f : fields) {

            if (OnOffEnum.OFF.value().equals(f.getIsCommonVisible())) {
                continue;
            }

            // 转换
            FieldBean fieldBean = new FieldBean(f, this.messageSource, locale);
            this.postFieldBeanInit(fieldBean, userBean);

            if (fieldBean.isEditVisible()) { // 新增修改字段
                addEditFields.add(fieldBean);
            }
        }
        model.addAttribute(ControllerModelKeys.ADD_FIELDS, addEditFields);

        // 4. 使用<jsp:include/>嵌入javascript内容时，使用的JSP文件路径，子类JSP扩展点1
        String jsJspPath = this.getJavascriptJspPath();
        if (!StringUtils.isEmpty(jsJspPath)) {
            model.addAttribute(ControllerModelKeys.INCLUDE_JSP_JS_PATH, jsJspPath);
        }

        // 5. 使用<jsp:include/>嵌入的隐藏区JSP路径，用来实现子类特有的弹出画面功能，子类JSP扩展点2
        String hiddenContentJspPath = this.getHiddenContentJspPath();
        if (!StringUtils.isEmpty(hiddenContentJspPath)) {
            model.addAttribute(ControllerModelKeys.INCLUDE_JSP_HIDDEN_CONTENT_PATH,
                    hiddenContentJspPath);
        }

        // 6. 是否需要在新建、修改时上传文件
        // model.addAttribute(ControllerModelKeys.USE_FILE, this.useFile());

        // 7. 返回View Name
        return this.getAddPage(paramMap);
    }

    /**
     * 新建，返回Json格式。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    public JsonResultBean addSubmit(@RequestParam Map<String, String> paramMap,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // // 检查是否有相应的权限
            // if (!this.isUserHasAuthority(userBean, bean)) {
            // jsonResult.setStatus(JsonStatus.ERROR);
            // jsonResult.setMessage(
            // messageSource.getMessage("ui_common_error_unauthorized", null, locale));
            // return jsonResult;
            // }
            //
            // // 检查是否存在相同的记录
            // DataFilter filter = this.getUniqueFilter(bean);
            // if (this.getDataService().exist(filter)) {
            // jsonResult.setStatus(JsonStatus.ERROR);
            // jsonResult.setMessage(messageSource.getMessage(getUniqueViolationMessageResource(),
            // null, locale));
            // return jsonResult;
            // }

            // 创建数据
            // T entity = this.getViewConverter().toEntity(bean, null, userBean, new Date());
            // this.getDataService().create(entity);

            // this.postCreate(entity);

            // 重新获取实体，及其关联，用于在一览画面显示
            // T newEntity = this.getDataService().find(bean.getId());
            // ViewBean beanToRender =
            // this.getViewConverter().fromEntity(newEntity, this.messageSource, locale);

            Map<String, Object> record = new HashMap<>();
            record.put("id", UUID.randomUUID().toString());
            for (String key : paramMap.keySet()) {
                record.put(key, paramMap.get(key));
            }

            this.getDataList().add(record);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(this.messageSource.getMessage("记录创建成功。", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, record);

        } catch (Exception e) {
            getLogger().error("Exception while creating the " + getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 创建后处理，如创建秤后，通过JMS发送立即在线监测的任务摘要。默认什么都不做。
     * 
     * @param entity 创建的数据库实体
     */
    protected void postCreate(T entity) {
        return;
    }
}
