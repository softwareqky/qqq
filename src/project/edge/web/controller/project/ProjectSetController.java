package project.edge.web.controller.project;

import java.util.ArrayList;
import java.util.Date;
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
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.ProjectSetBeanConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.ProjectSet;
import project.edge.domain.view.ProjectSetBean;
import project.edge.service.hr.PersonService;
import project.edge.service.project.ProjectSetService;
import project.edge.service.system.DataOptionService;
import project.edge.service.system.SystemConfigService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 项目集画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/project-set")
public class ProjectSetController extends SingleGridController<ProjectSet, ProjectSetBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectSetController.class);

    private static final String PID = "P2010";

    @Resource
    private ProjectSetService projectSetService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private SystemConfigService systemConfigService;
    
    @Resource
    private PersonService personService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PROJECT_SET.value();
    }

    @Override
    protected Service<ProjectSet, String> getDataService() {
        return this.projectSetService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<ProjectSet, ProjectSetBean> getViewConverter() {
        return new ProjectSetBeanConverter();
    }

    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/project/projectSetJs.jsp";
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面时，增加回调，处理省市下拉框联动

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', PROJECT_SET);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));


        return jsMap;
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
        dataTypeList.add(DataTypeEnum.PROJECT_KIND.value()); // 项目类别

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        // 项目类别
        List<ComboboxOptionBean> projectKindOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);

        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.PROJECT_KIND.value())) {
                projectKindOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

        optionMap.put("ProjectKindOptions", projectKindOptions);

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
     * 获取项目集下拉框的options。
     * 
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/list-options")
    @ResponseBody
    public JsonResultBean listOptions(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            List<ProjectSet> list = this.projectSetService.list(null, null);
            List<ComboboxOptionBean> optionList = new ArrayList<>();
            String format = "(%1$s) %2$s";

            for (ProjectSet ps : list) {
                optionList.add(new ComboboxOptionBean(ps.getId(),
                        String.format(format, ps.getProjectNum(), ps.getProjectName())));
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, optionList);

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
    @SysLogAnnotation(description = "项目集", action = "新增")
    public JsonResultBean create(@ModelAttribute ProjectSetBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        String useid = userBean.getSessionUserId();
        bean.setApplicant_(useid);
        
        Person p = this.personService.find(useid);
        if(p != null) {
            
            bean.setApplicantText(p.getPersonName());
            bean.setApplicantMobile(p.getMobile());
        }
        
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
    @SysLogAnnotation(description = "项目集", action = "更新")
    public JsonResultBean update(@ModelAttribute ProjectSetBean bean,
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
    @SysLogAnnotation(description = "项目集", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    /**
     * 
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/project-num")
    @ResponseBody
    public JsonResultBean makeProjectNum(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {

            String template =
                    this.systemConfigService.getStringConfig(SystemConfigKeys.PROJECT_NUM_TEMPLATE);
            int elStart = template.indexOf("${");
            int elEnd = template.lastIndexOf("}");
            String el = template.substring(elStart + 2, elEnd);
            String v = DateUtils.date2String(new Date(), el);

            CommonFilter f = new CommonFilter().addAnywhere("projectNum", v);
            List<ProjectSet> projectSets = this.projectSetService.list(f, null);
            String retstr = "";
            if(projectSets != null) {
                retstr = String.format("%03d", (projectSets.size() + 1)); 
            }else {
                retstr = "001";
            }
            
            String projectNum = template.substring(0, elStart) + v + retstr;

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, projectNum);

        } catch (Exception e) {
            getLogger().error("Exception making project number.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
