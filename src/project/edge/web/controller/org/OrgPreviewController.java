/**
 * 
 */
package project.edge.web.controller.org;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.SessionUserBean;


/**
 * @author angel_000
 *         架构预览
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/org/preview")
public class OrgPreviewController {

    private static final String PRE_VIEW_NAME = "main/organization";

    /**
     * 打开主画面。
     */
    @RequestMapping("/main")
    public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Model model, HttpServletResponse response, Locale locale) {

        return PRE_VIEW_NAME;
    }

}
