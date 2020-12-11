package project.edge.web.controller.general;

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
import garage.origin.domain.view.SessionUserBean;


/**
 * TODO 包含提交审核、撤回、流程状态和审核记录这4个功能。
 *
 */
@Deprecated
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/audit")
public class AuditController {

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);


    /**
     * 审核记录的画面入口方法，用于生成JSP。
     * 
     * @param paramMap
     * @param model
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/view-record")
    public String viewRecord(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return "audit/auditRecord";
    }

}
