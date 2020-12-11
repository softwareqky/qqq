package project.edge.web.controller.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.MenuBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.domain.converter.PersonBeanConverter;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Person;
import project.edge.domain.view.PersonBean;
import project.edge.service.auth.PageService;
import project.edge.service.hr.PersonService;
import project.edge.web.controller.general.popup.BudgetEstimateSumManager;
import project.edge.web.controller.general.popup.LinkManager;
import project.edge.web.controller.general.popup.PaperLibraryPopupManager;
import project.edge.web.controller.general.popup.PersonPopupManager;
import project.edge.web.controller.general.popup.PlanPopupManager;
import project.edge.web.controller.general.popup.PostPopupManager;
import project.edge.web.controller.general.popup.ProjectPersonPopupManager;
import project.edge.web.controller.general.popup.ProjectPopupManager;
import project.edge.web.controller.general.popup.ProjectRolePopupManager;
import project.edge.web.controller.general.popup.RelatedUnitPopupManager;
import project.edge.web.controller.general.popup.SegmentPopupManager;
import project.edge.web.controller.general.popup.SitePopupManager;

/**
 * 主画面。
 * 
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private static final String MAIN_VIEW_NAME = "main/main";

    private static final String MODEL_KEY_PAGE_LIST = "pageList"; // 菜单对应的page列表


    @Autowired
    protected ServletContext context;

    @Resource
    private MessageSource messageSource;

    @Resource
    private PageService pageService;

    @Resource
    private PersonService personService;

    @Resource
    private PersonPopupManager personPopupManager;
    
    @Resource
    private LinkManager linkManager;
    
    @Resource
    private BudgetEstimateSumManager budgetEstimateSumManager;
    
    @Resource
    private ProjectPersonPopupManager projectPersonManager;

    @Resource
    private SitePopupManager sitePopupManager;

    @Resource
    private SegmentPopupManager segmentPopupManager;

    @Resource
    private PostPopupManager postPopupManager;

    @Resource
    private RelatedUnitPopupManager relatedUnitPopupManager;

    @Resource
    private ProjectPopupManager projectPopupManager;

    @Resource
    private PlanPopupManager planPopupManager;
    
    @Resource
    private PaperLibraryPopupManager paperLibraryPopupManager;
    
    @Resource
    private ProjectRolePopupManager projectRolePopupManager;

    /**
     * 打开主画面。
     */
    @RequestMapping("/main")
    public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Model model, HttpServletResponse response, Locale locale) {

        List<Page> pageList = this.pageService.loadPages(userBean);

        // 所有画面都无权访问时，返回"未授权"画面
        if (pageList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "common/page/unauthorized";
        }

        List<MenuBean> resultList = new ArrayList<MenuBean>(); // 顶层MenuBean
        Map<String, MenuBean> map = new HashMap<>();
        for (Page p : pageList) {
            MenuBean m = new MenuBean(p, this.messageSource, locale);
            if (StringUtils.isEmpty(m.getPid())) {
                resultList.add(m);
            } else {
                if (map.containsKey(m.getPid())) {
                    map.get(m.getPid()).getChildren().add(m);
                }
            }
            if (!m.isLeaf()) {
                map.put(m.getId(), m);
            }
        }

        model.addAttribute(MODEL_KEY_PAGE_LIST, resultList);

        // 生成共通的弹出选择画面，包括：人员，项目，站点，中继段，往来单位，岗位
        this.personPopupManager.preparePopup(userBean, model, locale);
        this.linkManager.preparePopup(userBean, model, locale);
        this.budgetEstimateSumManager.preparePopup(userBean, model, locale);
        this.projectPersonManager.preparePopup(userBean, model, locale);
        this.sitePopupManager.preparePopup(userBean, model, locale);
        this.segmentPopupManager.preparePopup(userBean, model, locale);
        this.postPopupManager.preparePopup(userBean, model, locale);
        this.relatedUnitPopupManager.preparePopup(userBean, model, locale);
        this.projectPopupManager.preparePopup(userBean, model, locale);
        this.planPopupManager.preparePopup(userBean, model, locale);
        this.paperLibraryPopupManager.preparePopup(userBean, model, locale);
        this.projectRolePopupManager.preparePopup(userBean, model, locale);
        
        this.cleanModel(model);

        return MAIN_VIEW_NAME;
    }

    /**
     * 删除Model中不需要的数据。
     */
    private void cleanModel(Model model) {
        Map<String, Object> modelMap = model.asMap();
        modelMap = model.asMap();
        modelMap.remove(ControllerModelKeys.DEFAULT_ORDER);
        modelMap.remove(ControllerModelKeys.ID_MAP);
        // modelMap.remove(ControllerModelKeys.URL_MAP);
        modelMap.remove(ControllerModelKeys.JS_MAP);
        modelMap.remove(ControllerModelKeys.OPTION_MAP);
        modelMap.remove(ControllerModelKeys.FUNCTIONS);
        modelMap.remove(ControllerModelKeys.SEARCH_FUNCTIONS);
        modelMap.remove(ControllerModelKeys.PAGES);
        modelMap.remove(ControllerModelKeys.CAN_EDIT_DATA);
        modelMap.remove(ControllerModelKeys.EDIT_DATA_FIELDS);
        modelMap.remove(ControllerModelKeys.SEARCH_FIELDS);
        modelMap.remove(ControllerModelKeys.ADV_SEARCH_FIELDS);
        modelMap.remove(ControllerModelKeys.LIST_FIELDS);
        modelMap.remove(ControllerModelKeys.LIST_FROZEN_FIELDS);
        modelMap.remove(ControllerModelKeys.ADD_FIELDS);
        modelMap.remove(ControllerModelKeys.EDIT_FIELDS);
        modelMap.remove(ControllerModelKeys.VIEW_FIELDS);
        modelMap.remove(ControllerModelKeys.BATCH_EDIT_FIELDS);
        modelMap.remove(ControllerModelKeys.HAS_COPY_RESERVE);
        modelMap.remove(ControllerModelKeys.IS_FIELD_GROUPED);
        modelMap.remove(ControllerModelKeys.INCLUDE_JSP_JS_PATH);
        modelMap.remove(ControllerModelKeys.INCLUDE_JSP_HIDDEN_CONTENT_PATH);

    }

    @RequestMapping("/in-progress")
    public String inProgress() {
        return "common/page/inProgress";
    }

    /**
     * 主画面用户信息。
     * 
     * TODO 主画面渲染时填充，不采用Ajax方式
     */
    @RequestMapping("/login-user")
    @ResponseBody
    public JsonResultBean getLoginInfo(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        try {
            String userId = userBean.getSessionUserId();

            CommonFilter filter = new CommonFilter();
            filter.addExact("u.id", userId);
            List<Person> personlist = this.personService.list(filter, null);

            PersonBean bean = null;
            if ((personlist != null) && (personlist.size() > 0)) {
                PersonBeanConverter converter = new PersonBeanConverter();
                bean = converter.fromEntity(personlist.get(0), this.messageSource, locale);
            } else {
                bean = new PersonBean();
                bean.setPersonName(userBean.getSessionLoginName());
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, bean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
        } catch (Exception e) {
            logger.error("Exception main.", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
    
    /**
     * 主画面用户信息。
     * 
     * TODO 主画面渲染时填充，不采用Ajax方式
     */
    @RequestMapping("/app-user")
    @ResponseBody
    public JsonResultBean getLoginInfoByApp(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        try {
            String userId = userBean.getSessionUserId();

            CommonFilter filter = new CommonFilter();
            filter.addExact("u.id", userId);
            List<Person> personlist = this.personService.list(filter, null);

            PersonBean bean = null;
            if ((personlist != null) && (personlist.size() > 0)) {
                PersonBeanConverter converter = new PersonBeanConverter();
                bean = converter.fromEntity(personlist.get(0), this.messageSource, locale);
            } else {
                bean = new PersonBean();
                bean.setPersonName(userBean.getSessionLoginName());
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, bean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
        } catch (Exception e) {
            logger.error("Exception main.", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
