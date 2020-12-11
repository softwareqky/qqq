package project.edge.web.controller.system;

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
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.entity.SystemConfig;
import project.edge.service.system.SystemConfigService;
import project.edge.web.controller.common.FormController;

/**
 * 系统设定画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/system/config")
public class SystemConfigController extends FormController {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

    private static final String PID = "P19001";

    @Resource
    private SystemConfigService systemConfigService;

    @Override
    protected Map<String, Object> getRecord() {
        List<SystemConfig> configList = this.systemConfigService.list(null, null);
        Map<String, Object> record = new HashMap<>();
        for (SystemConfig c : configList) {
            
            record.put(c.getId(), c.getConfigValue());
            
//            if(c.getId().equals("_IS_OA_FLOW")) {
//                if(!StringUtils.isEmpty(c.getConfigValue())) {
//                    record.put(c.getId(), Integer.parseInt(c.getConfigValue()));
//                }                
//            }else {
//                record.put(c.getId(), c.getConfigValue());
//            }
        }

        return record;
    }

    @Override
    protected void saveRecord(Map<String, Object> record) {
        List<SystemConfig> configList = this.systemConfigService.list(null, null);
        List<SystemConfig> updateList = new ArrayList<>();
        for (SystemConfig c : configList) {
            if (record.containsKey(c.getId())) {
                c.setConfigValue(record.get(c.getId()).toString());
                updateList.add(c);
            }
        }

        this.systemConfigService.update(updateList);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.SYSTEM_CONFIG.value();
    }

    @Override
    protected String getPageId() {
        return PID;
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
     * 保存Form，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public JsonResultBean saveForm(@RequestParam Map<String, Object> paramMap,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.saveForm(paramMap, userBean, locale);
    }
}
