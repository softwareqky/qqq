/**
 * 
 */
package project.edge.web.controller.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.RelatedUnitBeanConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.RelatedUnit;
import project.edge.domain.view.RelatedUnitBean;
import project.edge.service.org.RelatedUnitService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.SingleGridController;


/**
 * 往来单位画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/org/related-unit")
public class RelatedUnitController extends SingleGridController<RelatedUnit, RelatedUnitBean> {

    private static final Logger logger = LoggerFactory.getLogger(RelatedUnitController.class);

    private static final String PID = "P16003";

    @Resource
    private RelatedUnitService relatedUnitService;

    @Resource
    private DataOptionService dataOptionService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.RELATED_UNIT.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<RelatedUnit, String> getDataService() {
        return this.relatedUnitService;
    }

    @Override
    protected ViewConverter<RelatedUnit, RelatedUnitBean> getViewConverter() {
        return new RelatedUnitBeanConverter();
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.RELATED_UNIT_TYPE.value());
        dataTypeList.add(DataTypeEnum.REGION.value());
        dataTypeList.add(DataTypeEnum.RELATED_UNIT_GROUP.value());
        dataTypeList.add(DataTypeEnum.RELATED_UNIT_PROPERTY.value());
        dataTypeList.add(DataTypeEnum.BUSINESS_TYPE.value());

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        List<ComboboxOptionBean> relatedUnitTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> regionOptions = new ArrayList<>();
        List<ComboboxOptionBean> relatedUnitGroupOptions = new ArrayList<>();
        List<ComboboxOptionBean> relatedUnitPropertyOptions = new ArrayList<>();
        List<ComboboxOptionBean> businessTypeOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);
        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.RELATED_UNIT_TYPE.value())) {
                relatedUnitTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.REGION.value())) {
                regionOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.RELATED_UNIT_GROUP.value())) {
                relatedUnitGroupOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.RELATED_UNIT_PROPERTY.value())) {
                relatedUnitPropertyOptions
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.BUSINESS_TYPE.value())) {
                businessTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

        optionMap.put("RelatedUnitTypeOptions", relatedUnitTypeOptions);
        optionMap.put("RegionOptions", regionOptions);
        optionMap.put("RelatedUnitGroupOptions", relatedUnitGroupOptions);
        optionMap.put("RelatedUnitPropertyOptions", relatedUnitPropertyOptions);
        optionMap.put("BusinessTypeOptions", businessTypeOptions);

        return optionMap;
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
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return super.main(paramMap, model, userBean, locale);
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param page 页数
     * @param rows 每页的记录数
     * @param sort 排序的字段，CSV
     * @param order 顺序，CSV
     * @param locale
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

        return jsonResult;
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    @Override
    public JsonResultBean find(@RequestParam String id, Locale locale) {
        return super.find(id, locale);
    }

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @SysLogAnnotation(description = "往来单位", action = "新增")
    public JsonResultBean create(@ModelAttribute RelatedUnitBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.create(bean, null, userBean, locale);
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "往来单位", action = "更新")
    public JsonResultBean update(@ModelAttribute RelatedUnitBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "往来单位", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
