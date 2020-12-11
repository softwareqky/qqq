package project.edge.web.controller.notice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.common.constant.NotifyWayEnum;
import project.edge.domain.converter.NotifySettingBeanConverter;
import project.edge.domain.entity.NotifySetting;
import project.edge.domain.view.NotifySettingBean;
import project.edge.service.notice.NotifySettingService;
import project.edge.web.controller.common.SingleGridController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/notice/setting")
public class NotifySettingController extends SingleGridController<NotifySetting, NotifySettingBean> {

	private static final Logger logger = LoggerFactory.getLogger(NotifySettingController.class);
	
	private static final String PID = "P13002";
	
	@Resource
	private NotifySettingService notifySettingService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.NOTIFY_SETTING.value();
	}

	@Override
	protected Service<NotifySetting, String> getDataService() {
		return notifySettingService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<NotifySetting, NotifySettingBean> getViewConverter() {
		return new NotifySettingBeanConverter();
	}
	
	@Override
	protected List<CommonFilter> getUniqueFilter(NotifySettingBean bean) {
		List<CommonFilter> list = new ArrayList<>();
		CommonFilter filter = new CommonFilter().addExact("notifyTargetType", bean.getNotifyTargetType());
		list.add(filter);
		return list;
	}
	
	@Override
	protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
		
		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<String, List<ComboboxOptionBean>>();

		List<ComboboxOptionBean> targetTypeOptions = new ArrayList<>();
		for (NotifyTargetTypeEnum t: NotifyTargetTypeEnum.values()) {
			targetTypeOptions.add(new ComboboxOptionBean(t.value().toString(),
					this.messageSource.getMessage(t.resourceName(), null, locale)));
		}
		optionMap.put("notifyTargetTypeOptions", targetTypeOptions);
		
		List<ComboboxOptionBean> wayOptions = new ArrayList<>();
		for (NotifyWayEnum t: NotifyWayEnum.values()) {
			wayOptions.add(new ComboboxOptionBean(t.value().toString(),
					this.messageSource.getMessage(t.resourceName(), null, locale)));
		}
		optionMap.put("notifyWayOptions", wayOptions);
		
		return optionMap;
		
	}
	
	@RequestMapping("/main")
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.main(paramMap, model, userBean, locale);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {
		
		return super.list(commonFilterJson, null, page, rows, sort, order, locale);
	}
	
	
	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id     ID
	 * @param locale
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	@Override
	public JsonResultBean find(@RequestParam String id, Locale locale) {
		return super.find(id, locale);
	}
	
	/**
	 * 新建，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */

	@RequestMapping("/add")
	@ResponseBody
	@SysLogAnnotation(description = "提醒设置", action = "新增")
	public JsonResultBean create(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute NotifySettingBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.create(bean, null, userBean, locale);
	}
	
	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "提醒设置", action = "更新")
	public JsonResultBean update(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute NotifySettingBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.update(bean, null, userBean, locale);
	}
	
	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "提醒设置", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}
}
