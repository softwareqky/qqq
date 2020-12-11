package project.edge.web.controller.system;

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
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.CityBeanConverter;
import project.edge.domain.entity.City;
import project.edge.domain.entity.Province;
import project.edge.domain.view.CityBean;
import project.edge.service.system.CityService;
import project.edge.service.system.ProvinceService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 省市信息画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/system/city")
public class ProvinceCityController extends SingleGridController<City, CityBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceCityController.class);

    private static final String PID = "P19010";

    @Resource
    private CityService cityService;

    @Resource
    private ProvinceService provinceService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.CITY.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<City, String> getDataService() {
        return this.cityService;
    }

    @Override
    protected ViewConverter<City, CityBean> getViewConverter() {
        return new CityBeanConverter();
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();

        ArrayList<ComboboxOptionBean> optProvinces = new ArrayList<ComboboxOptionBean>();

        String format = "(%1$s) %2$s";
        CommonFilter f = null;
        List<Province> provinceList = this.provinceService.list(f, null);
        if (provinceList != null) {
            for (Province p : provinceList) {
                optProvinces.add(new ComboboxOptionBean(p.getId(),
                        String.format(format, p.getProvinceCode(), p.getProvinceName())));
            }
        }

        optionMap.put("provinceOptions", optProvinces);

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
     * 根据省份，获取城市列表。
     * 
     * @param provinceId
     * @param locale
     * @return
     */
    @RequestMapping("/list-options")
    @ResponseBody
    public JsonResultBean listOptions(@RequestParam String provinceId, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 设置过滤信息
            CommonFilter filter = new CommonFilter().addExact("p.id", provinceId);

            // 获取数据
            List<City> list = this.getDataService().list(filter, null);
            List<ComboboxOptionBean> resultList = new ArrayList<>();
            String format = "(%1$s) %2$s";
            for (City entity : list) {
                resultList.add(new ComboboxOptionBean(entity.getId(),
                        String.format(format, entity.getCityCode(), entity.getCityName())));
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用

        } catch (Exception e) {
            this.getLogger().error("Exception listing the options of " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

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
    @SysLogAnnotation(description = "省市信息", action = "新增")
    public JsonResultBean create(@ModelAttribute CityBean bean,
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
    @SysLogAnnotation(description = "省市信息", action = "更新")
    public JsonResultBean update(@ModelAttribute CityBean bean,
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
    @SysLogAnnotation(description = "省市信息", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

    // get site info
    /**
     * 获取区域（省份）信息。
     * 
     * @param provinceId
     * @param locale
     * @return
     */
    @RequestMapping("/list-province")
    @ResponseBody
    public JsonResultBean getProvinces(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            List<ComboboxOptionBean> optProvinces = new ArrayList<>();

            CommonFilter f = null;
            List<Province> provinceList = this.provinceService.list(f, null);

            //String format = "(%1$s) %2$s";
            if (provinceList != null) {
                // optProvinces.add(new ComboboxOptionBean("0", String.format(format, "0",
                // this.messageSource.getMessage("ui.common.all", null, locale))));
                optProvinces.add(new ComboboxOptionBean("0",
                        this.messageSource.getMessage("ui.common.all", null, locale)));
                for (Province p : provinceList) {
                    // optProvinces.add(new ComboboxOptionBean(p.getId(),
                    // String.format(format, p.getProvinceCode(), p.getProvinceName())));
                    optProvinces.add(new ComboboxOptionBean(p.getId(), p.getProvinceName()));
                }
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, optProvinces);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the province", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

}
