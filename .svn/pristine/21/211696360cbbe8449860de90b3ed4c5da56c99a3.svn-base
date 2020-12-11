package project.edge.web.controller.hr;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.entity.Person;
import project.edge.domain.view.PersonBean;
import project.edge.web.controller.common.TreeDoubleGridSelectorController;

/**
 * 人员弹出选择画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/hr/person-selector")
public class PersonPopupController extends TreeDoubleGridSelectorController<Person, PersonBean> {

    private static final Logger logger = LoggerFactory.getLogger(PersonPopupController.class);

    private static final String PID = "P17006";

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
    protected String getSideDataType() {
        return DataTypeEnum.DEPT.value();
    }

    @Override
    protected OrderByDto getGridDefaultOrder() {
        return new OrderByDto("personName");
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        // 人员一览的URL
        String contextPath = this.context.getContextPath();
        urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST, contextPath + "/hr/person/list.json");

        return urlMap;
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


}
