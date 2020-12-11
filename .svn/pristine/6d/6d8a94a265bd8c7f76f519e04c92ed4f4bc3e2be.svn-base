package project.edge.web.controller.schedule;

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
import project.edge.common.constant.FacilityStatusEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.domain.converter.PresetTaskBeanConverter;
import project.edge.domain.entity.PresetTask;
import project.edge.domain.view.PresetTaskBean;
import project.edge.service.schedule.PresetTaskService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 预设任务画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/preset-task")
public class PresetTaskController extends SingleGridController<PresetTask, PresetTaskBean> {

    private static final Logger logger = LoggerFactory.getLogger(PresetTaskController.class);

    private static final String PID = "P5035";

    @Resource
    private PresetTaskService presetTaskService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PRESET_TASK.value();
    }

    @Override
    protected Service<PresetTask, String> getDataService() {
        return this.presetTaskService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<PresetTask, PresetTaskBean> getViewConverter() {
        return new PresetTaskBeanConverter();
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(PresetTaskBean bean) {
        // TODO Auto-generated method stub
        List<CommonFilter> list = new ArrayList<>();
        CommonFilter filter = new CommonFilter().addExact("id", bean.getId());
        list.add(filter);
        return list;
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(PresetTaskBean bean, PresetTask oldEntity) {
        // TODO Auto-generated method stub
        List<CommonFilter> list = new ArrayList<>();
        if (!bean.getId().equals(oldEntity.getId())) {
            CommonFilter filter = new CommonFilter().addExact("id", bean.getId());
            list.add(filter);
        }
        return list;
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
        // TODO Auto-generated method stub
        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();

        ArrayList<ComboboxOptionBean> projectGroupOptions = new ArrayList<ComboboxOptionBean>();

        for (ProjectGroupEnum p : ProjectGroupEnum.values()) {
            projectGroupOptions.add(new ComboboxOptionBean(p.value().toString(),
                    messageSource.getMessage(p.resourceName(), null, locale)));
        }

        optionMap.put("projectGroupOptions", projectGroupOptions);

        ArrayList<ComboboxOptionBean> FacilityConstructionStatus =
                new ArrayList<ComboboxOptionBean>();

/*        for (FacilityConstructionStatusEnum f : FacilityConstructionStatusEnum.values()) {
            FacilityConstructionStatus.add(new ComboboxOptionBean(f.value().toString(),
                    messageSource.getMessage(f.resourceName(), null, locale)));
        }*/
        
        for (FacilityStatusEnum s : FacilityStatusEnum.values()) {
        	FacilityConstructionStatus.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }

        optionMap.put("FacilityConstructionStatus", FacilityConstructionStatus);

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
    	
    	sort = "projectGroup,isSiteTask,taskNum";

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
    @SysLogAnnotation(description = "预设任务", action = "新增")
    public JsonResultBean create(@ModelAttribute PresetTaskBean bean,
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
    @SysLogAnnotation(description = "预设任务", action = "更新")
    public JsonResultBean update(@ModelAttribute PresetTaskBean bean,
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
    @SysLogAnnotation(description = "预设任务", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
