package project.edge.web.controller.system;

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
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.SystemLogBeanConverter;
import project.edge.domain.entity.SystemLog;
import project.edge.domain.view.SystemLogBean;
import project.edge.service.system.SystemLogService;
import project.edge.web.controller.common.SingleGridController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/system/log")
public class SystemLogController extends SingleGridController<SystemLog, SystemLogBean>{

	private static final Logger logger = LoggerFactory.getLogger(SystemLogController.class);
	private static final String PID = "P20001";
	
	@Resource
	private SystemLogService systemLogService;
	
	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.SYSTEM_LOG.value();
	}

	@Override
	protected Service<SystemLog, String> getDataService() {
		// TODO Auto-generated method stub
		return this.systemLogService;
	}

	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return this.PID;
	}

	@Override
	protected ViewConverter<SystemLog, SystemLogBean> getViewConverter() {
		// TODO Auto-generated method stub
		return new SystemLogBeanConverter();
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

        String r = super.main(paramMap, model, userBean, locale);
        
        return r;
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

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

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
}
