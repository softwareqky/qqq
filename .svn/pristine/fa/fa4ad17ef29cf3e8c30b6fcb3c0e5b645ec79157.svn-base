package project.edge.web.controller.general;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.JwtUtils;
import garage.origin.common.util.ServletRequestUtils;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.business.SystemConfigSettings;
import project.edge.domain.entity.User;
import project.edge.service.auth.UserService;

/**
 * 登录画面。
 * 
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String MODEL_KEY_ERROR_MESSAGE = "errorMsg";

    /** 错误信息资源名，用户名或密码错误 */
    private static final String MESSAGE_WRONG_PWD_USER = "message.error.wrong.pwd.user";

    /** 如果在general文件夹中创建了index.jsp，则此处可以是general/index */
    private static final String LOGIN_VIEW_NAME = "login";

    @Resource
    private UserService userService;

    @Resource
    private MessageSource messageSource;

    @Resource
    private LocaleResolver localeResolver;

    @Resource
    private ThemeResolver themeResolver;

    /**
     * 打开登录画面。
     */
    @RequestMapping("/index")
    public String index(@ModelAttribute User user, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        // 设置session的locale和theme，只需在入口设定一次，仅此一次

        Locale locale = SystemConfigSettings.getInstance().getLocale();
        this.localeResolver.setLocale(request, response, locale);

        this.themeResolver.setThemeName(request, response,
                SystemConfigSettings.getInstance().getTheme());

        return LOGIN_VIEW_NAME;
    }
    
    
    /**
     * App登录，成功则写入jwt
     */
    @PostMapping(path="/login-app")
    @ResponseBody
    public JsonResultBean login(@RequestBody JSONObject json) {
    	
    	
    	String loginName = json.getString("loginName");
    	String loginPwd = json.getString("loginPwd");
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	// 如果没有用户名或密码
    	if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPwd)) {
    		jsonResult.setStatus(JsonStatus.ERROR);
    		jsonResult.setMessage("用户名或密码为空!");
    		return jsonResult;
    	}
    	
    	// 如果用户名或密码错误
    	SessionUserBean userBean = userService.login(loginName.trim(), loginPwd);
    	if (userBean == null) {
    		jsonResult.setStatus(JsonStatus.ERROR);
    		jsonResult.setMessage("用户名或密码不正确!");
    		return jsonResult;
    	}
    	
    	// 登录成功，生成jwt
    	String token = JwtUtils.sign(JSON.toJSONString(userBean), 3600 * 24 * 30);
    	jsonResult.setStatus(JsonStatus.OK);
    	jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_VALUE, token);
    	
    	return jsonResult;
    }

    /**
     * 用户登录，如果登录成功则将用户信息写入Session，并跳转到主画面。
     */
    @RequestMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request, Model model,
            Locale locale) {

        // 在浏览器中直接输入login.htm时，用户名密码都为空
        if (StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getLoginPwd())) {
            return LOGIN_VIEW_NAME;
        }

        SessionUserBean userBean =
                this.userService.login(user.getLoginName().trim(), user.getLoginPwd());
        if (userBean == null) {

            model.addAttribute(LoginController.MODEL_KEY_ERROR_MESSAGE,
                    this.messageSource.getMessage(MESSAGE_WRONG_PWD_USER, null, locale));

            return LOGIN_VIEW_NAME;
        }

        // add login log
        logger.debug("User {} from {} login.", userBean.getSessionLoginName(),
                ServletRequestUtils.getRemoteHost(request));

        model.addAttribute(Constants.SESSION_USER, userBean);

        return "redirect:/main" + Constants.WEB_SUFFIX;
    }

    /**
     * 退出登录，将Session中的用户信息清空，同时回到登录画面。
     */
    @RequestMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/index" + Constants.WEB_SUFFIX;
    }

    /**
     * 因Session过期，重新登录。
     */
    @RequestMapping("/session-expire")
    public String sessionExpire(HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "common/page/sessionExpire";
    }
}
