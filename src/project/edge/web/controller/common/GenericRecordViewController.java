package project.edge.web.controller.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.domain.entity.DataFields;


/**
 * 共通的Controller，根据[t_data_fields]中获取的字段的信息，生成Page需要的数据。用来生成查看画面。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
@Deprecated
public abstract class GenericRecordViewController<T, V extends ViewBean> {

    /**
     * 默认的查看画面JSP
     */
    protected static final String VIEW_PAGE_NAME = "common/recordView";

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
     * 获取查看画面的Page名。提供默认的实现，如果子类不适用共通的JSP，则必须重写此方法。
     * 
     * @return
     */
    protected String getViewPage() {
        return VIEW_PAGE_NAME;
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
     * 查看画面的入口方法，用于生成JSP。
     * 
     * @param id ID
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    public String view(@RequestParam String id, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 1. 获取画面所需的各个数据字段
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

        // 2. 找到数据
        Map<String, Object> record = this.getDataList().stream()
                .filter(m -> m.containsKey("id") && m.get("id").toString().equals(id)).findFirst()
                .get();
        model.addAttribute(ControllerModelKeys.RECORD, record);

        // 3. 返回View Name
        return this.getViewPage();
    }
}
