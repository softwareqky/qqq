package project.edge.mobile.controller.auth;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.ServletRequestUtils;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.mobile.converter.UserLoginBeanConverter;
import project.edge.domain.mobile.view.UserLoginBean;
import project.edge.service.auth.UserService;

/**
 * @author angel_000
 *         登录API。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class UserLoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    private static final String MODEL_KEY_ERROR_MESSAGE = "errorMsg";

    /** 错误信息资源名，用户名或密码错误 */
    private static final String MESSAGE_WRONG_PWD_USER = "message.error.wrong.pwd.user";

    @Resource
    private MessageSource messageSource;
    @Resource
    private UserService userService;


    /**
     * 登录
     * 
     * @param loginName
     * @param password
     * @param locale
     * @return
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean login(@RequestParam String loginName, @RequestParam String password,
            HttpServletRequest request, Model model, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 参数检查
            if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            SessionUserBean userBean = this.userService.loginMobile(loginName, password);
            if (userBean == null) {

                model.addAttribute(UserLoginController.MODEL_KEY_ERROR_MESSAGE,
                        this.messageSource.getMessage(MESSAGE_WRONG_PWD_USER, null, locale));

                return jsonResult.setErrorMessage("message.error.login.fail", this.messageSource,
                        locale);
            }

            if (StringUtils.isEmpty(userBean.getRoleId())) {
                return jsonResult.setErrorMessage("ui.common.unauthorized", this.messageSource,
                        locale);
            }

            logger.debug("User {} from {} login.", userBean.getSessionLoginName(),
                    ServletRequestUtils.getRemoteHost(request));

            model.addAttribute(Constants.SESSION_USER, userBean);

            // 转换
            UserLoginBeanConverter converter = new UserLoginBeanConverter();
            UserLoginBean bean = converter.fromEntity(userBean, this.messageSource, locale);

            jsonResult.setResult(bean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            request.getSession(); // 登录成功之后，主动在服务端创建session，防止@ResponseBody在自动创建session前就提交了response

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }
}
