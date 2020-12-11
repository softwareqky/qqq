package project.edge.web.controller.general;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.SessionUserBean;


/**
 * 地图Home画面。
 * 
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class MapHomeController {

    private static final String HOME_VIEW_NAME = "main/mapHomePage";

    @Autowired
    protected ServletContext context;

    @Resource
    private MessageSource messageSource;

    /**
     * 打开主画面。
     */
    @RequestMapping("/map-home")
    public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Model model, HttpServletResponse response, Locale locale) {

        return HOME_VIEW_NAME;
    }
}
