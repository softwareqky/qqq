package project.edge.web.controller.hr;

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
import garage.origin.common.constant.GenderEnum;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.PersonStatusEnum;
import project.edge.domain.converter.PersonBeanConverter;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.User;
import project.edge.domain.view.PersonBean;
import project.edge.service.auth.UserService;
import project.edge.service.hr.PersonService;
import project.edge.service.org.DeptService;
import project.edge.service.org.OrgService;
import project.edge.web.controller.common.TreeGridController;


/**
 * 人员管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/hr/person")
public class PersonController extends TreeGridController<Person, PersonBean> {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private static final String PID = "P17005";

    @Resource
    private OrgService orgService;

    @Resource
    private UserService userService;

    @Resource
    private PersonService personService;

    @Resource
    private DeptService deptService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PERSON.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Person, String> getDataService() {
        return this.personService;
    }

    @Override
    protected ViewConverter<Person, PersonBean> getViewConverter() {
        return new PersonBeanConverter();
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
        ArrayList<ComboboxOptionBean> genderOptions = new ArrayList<>();
        for (GenderEnum g : GenderEnum.values()) {
            genderOptions.add(new ComboboxOptionBean(g.value().toString(),
                    this.messageSource.getMessage(g.resourceName(), null, locale)));
        }
        optionMap.put("GenderOptions", genderOptions);

        ArrayList<ComboboxOptionBean> statusOptions = new ArrayList<>();
        for (PersonStatusEnum p : PersonStatusEnum.values()) {
            statusOptions.add(new ComboboxOptionBean(p.value().toString(),
                    this.messageSource.getMessage(p.resourceName(), null, locale)));
        }
        optionMap.put("StatusOptions", statusOptions);

        List<ComboboxOptionBean> optOrgs = new ArrayList<>();
        // String format = "(%1$s) %2$s";
        List<Org> orgList = this.orgService.list(null, null);
        if (orgList != null) {
            for (Org o : orgList) {
                optOrgs.add(new ComboboxOptionBean(o.getId(), o.getOrgName()));
            }
        }
        optionMap.put("OrgOptions", optOrgs);

        List<ComboboxOptionBean> deptOptions = new ArrayList<>();
        List<Dept> deptList = this.deptService.list(null, null);
        if (deptList != null) {
            for (Dept dp : deptList) {
                deptOptions.add(new ComboboxOptionBean(dp.getId(), dp.getDeptName()));
            }
        }
        optionMap.put("DeptOptions", deptOptions);

        List<ComboboxOptionBean> UserOptions = new ArrayList<>();
        List<User> usersList = this.userService.list(null, null);
        if (usersList != null) {
            for (User user : usersList) {
                UserOptions.add(new ComboboxOptionBean(user.getId(), user.getLoginName()));
            }
        }
        optionMap.put("UserOptions", UserOptions);
        return optionMap;
    }

    @Override
    protected String getSideDataType() {
        return DataTypeEnum.DEPT.value();
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

        CommonFilter f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());
        JsonResultBean jsonResult =
                super.list(commonFilterJson, f, page, rows, sort, order, locale);

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
    @SysLogAnnotation(description = "人员管理", action = "新增")
    public JsonResultBean create(@ModelAttribute PersonBean bean,
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
    @SysLogAnnotation(description = "人员管理", action = "更新")
    public JsonResultBean update(@ModelAttribute PersonBean bean,
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
    @SysLogAnnotation(description = "人员管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
