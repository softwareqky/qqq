package project.edge.web.controller.auth;

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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.UserBeanConverter;
import project.edge.domain.entity.Role;
import project.edge.domain.entity.User;
import project.edge.domain.view.UserBean;
import project.edge.service.auth.RoleService;
import project.edge.service.auth.UserService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 用户管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/auth/user")
public class UserController extends SingleGridController<User, UserBean> {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String PID = "P18005";

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.USER.value();
    }

    @Override
    protected Service<User, String> getDataService() {
        return this.userService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<User, UserBean> getViewConverter() {
        return new UserBeanConverter();
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(UserBean bean) {
        // TODO Auto-generated method stub
        List<CommonFilter> list = new ArrayList<>();
        CommonFilter filter = new CommonFilter().addExact("loginName", bean.getLoginName());
        list.add(filter);
        return list;
    }

    @Override
    protected List<CommonFilter> getUniqueFilter(UserBean bean, User oldEntity) {
        // TODO Auto-generated method stub
        List<CommonFilter> list = new ArrayList<>();
        if (!bean.getId().equals(oldEntity.getId())) {
            CommonFilter filter = new CommonFilter().addExact("loginName", bean.getLoginName());
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

        CommonFilter f = null;

        ArrayList<ComboboxOptionBean> roleOptions = new ArrayList<ComboboxOptionBean>();

        f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());  
        List<Role> roleList = this.roleService.list(f, null);
        if (roleList != null) {
            for (Role r : roleList) {
                roleOptions.add(new ComboboxOptionBean(r.getId(), r.getRoleName()));
            }
        }

        optionMap.put("RoleOptions", roleOptions);

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
    @SysLogAnnotation(description = "用户管理", action = "新增")
    public JsonResultBean create(@ModelAttribute UserBean bean,
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
    @SysLogAnnotation(description = "用户管理", action = "更新")
    public JsonResultBean update(@ModelAttribute UserBean bean,
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
    @SysLogAnnotation(description = "用户管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
