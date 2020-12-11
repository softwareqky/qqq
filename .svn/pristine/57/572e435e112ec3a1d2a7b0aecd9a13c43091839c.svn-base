package project.edge.mobile.controller.init;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.DataOption;
import project.edge.service.system.DataOptionService;

/**
 * @author angel_000
 *         APP初始化。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class InitController {

    private static final Logger logger = LoggerFactory.getLogger(InitController.class);

    @Resource
    private MessageSource messageSource;

    @Resource
    private DataOptionService dataOptionService;
    
    @RequestMapping(value = "/init/OptionList", method = RequestMethod.GET)
    @ResponseBody
    public MobileJsonResultBean getOptionList(@RequestParam(required = false, defaultValue = "") String projectId,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	 MobileJsonResultBean jsonResult = new MobileJsonResultBean();
         try {

             // 过滤
             CommonFilter filter = new CommonFilter();
           
             List<DataOption> optionlist = dataOptionService.list(filter, null);

             jsonResult.setResult(optionlist);
             
             // 准备JSON结果
             jsonResult.setStatus(JsonStatus.OK);

         } catch (Exception e) {
             logger.error("Exception while getting the settings.", e);

             // 在JSON对象内设定服务器处理结果状态
             jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
         }

         return jsonResult;

     }

}
