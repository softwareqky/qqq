package project.edge.web.controller.notice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.NotifyBeanConverter;
import project.edge.domain.converter.UserNotifyBeanConverter;
import project.edge.domain.entity.Notify;
import project.edge.domain.entity.NotifySubscription;
import project.edge.domain.entity.UserNotify;
import project.edge.domain.view.NotifyBean;
import project.edge.domain.view.UserNotifyBean;
import project.edge.service.notice.NoticeNotifyService;
import project.edge.service.notice.NoticeNotifySubscriptionService;
import project.edge.service.notice.NoticeUserNotifyService;
import project.edge.web.controller.common.SingleGridController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/notice/notify")
public class NoticeNotifyController extends SingleGridController<Notify, NotifyBean> {

	private static final Logger logger = LoggerFactory.getLogger(NoticeNotifyController.class);
	
	private static final String PID = "P13999"; // 暂未在页面中使用
	
	@Resource
	private NoticeNotifyService notifyService;
	@Resource
	private NoticeNotifySubscriptionService subscriptionService;
	@Resource
	private NoticeUserNotifyService userNotifyService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.NOTICE_NOTIFY.value();
	}

	@Override
	protected Service<Notify, String> getDataService() {
		return notifyService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected ViewConverter getViewConverter() {
		return new NotifyBeanConverter();
	}
	
	/**
	 * 取得所有未读列表（不分页）
	 * @param userBean
	 * @return
	 */
	@RequestMapping("/unread")
	@ResponseBody
	public JsonResultBean unread(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
		
		// 抽出所有需要推送给用户的新提醒
    	String userId = userBean.getSessionUserId();
    	this.pullNewUserNotifyList(userId);
    	
    	// 返回列表
    	CommonFilter filter = new CommonFilter();
    	FilterFieldInfoDto userFilterDto = new FilterFieldInfoDto();
    	userFilterDto.setFieldName("userId");
    	userFilterDto.setValue(userId);
    	filter.getFilterFieldList().add(userFilterDto);
    	
    	FilterFieldInfoDto readFilterDto = new FilterFieldInfoDto();
    	readFilterDto.setFieldName("read");
    	readFilterDto.setValue(0);
    	filter.getFilterFieldList().add(readFilterDto);
    	
    	List<OrderByDto> orders = new ArrayList<OrderByDto>();
    	OrderByDto order = new OrderByDto();
    	order.setColumnName("createAt");
    	order.setDesc(true);
    	orders.add(order);
    	
    	// 查询列表
    	List<UserNotify> userNotifyList = userNotifyService.list(filter, orders);
    	
    	// 返回结果
    	JsonResultBean jsonResult = new JsonResultBean();
    	jsonResult.setStatus(JsonStatus.OK);
    	jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, userNotifyList.size());
    	jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, userNotifyList);
    	
    	return jsonResult;
	}

	/**
     * 获取要推送给用户的提醒的分页数据，返回JSON格式。
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
            @RequestParam(required = false, defaultValue = "asc") String order,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Locale locale) {

    	// 抽出所有需要推送给用户的新提醒
    	String userId = userBean.getSessionUserId();
    	this.pullNewUserNotifyList(userId);
    	
    	// 取得需要推送给用户的提醒列表
    	return this.listUserNotify(userId, page, rows, locale);
    }
    
    /**
     * 取得用户提醒的分页列表
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    private JsonResultBean listUserNotify(String userId, int page, int rows, Locale locale) {
    	
    	CommonFilter filter = new CommonFilter();
    	FilterFieldInfoDto filterDto = new FilterFieldInfoDto();
    	filterDto.setFieldName("userId");
    	filterDto.setValue(userId);
    	filter.getFilterFieldList().add(filterDto);
    	
    	List<OrderByDto> orders = new ArrayList<OrderByDto>();
    	OrderByDto order = new OrderByDto();
    	order.setColumnName("createAt");
    	order.setDesc(true);
    	orders.add(order);
    	
    	PageCtrlDto pageCtrl = new PageCtrlDto();
    	if (page > 0 && rows > 0) {
            pageCtrl.setCurrentPage(page);
            pageCtrl.setPageSize(rows);
        }
    	
    	List<UserNotify> userNotifyList = userNotifyService.list(filter, orders, pageCtrl);
    	long total = pageCtrl.getRecordAmount();
    	
    	List<UserNotifyBean> resultList = new ArrayList<>();
    	UserNotifyBeanConverter converter = new UserNotifyBeanConverter();
    	for (UserNotify entity : userNotifyList) {
    		resultList.add(converter.fromEntity(entity, this.messageSource, locale));
    	}
    	
    	// 返回结果
    	JsonResultBean jsonResult = new JsonResultBean();
    	jsonResult.setStatus(JsonStatus.OK);
    	jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);
    	jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
    	
    	return jsonResult;
    }
    
    /**
     * 取得推送给用户的最新提醒列表
     * @param userId
     * @return
     */
    private void pullNewUserNotifyList(String userId) {
    	
    	logger.debug("取得推送给用户的最新提醒列表，用户ID=" + userId);
    	
    	// 取得推送给用户的提醒的最大日期
    	CommonFilter filter = new CommonFilter();
    	FilterFieldInfoDto filterDto = new FilterFieldInfoDto();
    	filterDto.setFieldName("userId");
    	filterDto.setValue(userId);
    	filter.getFilterFieldList().add(filterDto);
    	
    	List<OrderByDto> orders = new ArrayList<OrderByDto>();
    	OrderByDto order = new OrderByDto();
    	order.setColumnName("createAt");
    	order.setDesc(true);
    	orders.add(order);
    	
    	PageCtrlDto page = new PageCtrlDto();
    	page.setPageSize(1);
    	page.setCurrentPage(1);
    	
    	List<UserNotify> lastUserNotifyList = userNotifyService.list(filter, orders, page);
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, 2000);
    	Date lastNotifyDate = c.getTime();
    	if (lastUserNotifyList.size() > 0) {
    		lastNotifyDate = lastUserNotifyList.get(0).getCreateAt();
    	}
    	
    	// 查找已推送提醒最大日期后的新提醒列表
    	
    	List<NotifySubscription> subscriptionList = subscriptionService.list(filter, orders);
    	List<Notify> notifyList = new ArrayList<>();
    	if (subscriptionList.size() > 0) {
    		
        	filter = new CommonFilter();
        	filterDto.setFieldName("createAt");
        	filterDto.setFrom(lastNotifyDate);
        	filter.getFilterFieldList().add(filterDto);
        	
        	// 取得订阅目标列表（非全局通知）
        	List<Object> withinList = new ArrayList<>();
        	for (NotifySubscription s : subscriptionList) {
        		withinList.add(s.getTargetId());
        	}
        	
        	if (withinList.size() > 0) {
        		FilterFieldInfoDto subscriptionFilterDto = new FilterFieldInfoDto();
            	subscriptionFilterDto.setFieldName("id");
            	subscriptionFilterDto.setWithinList(withinList);
            	filter.getFilterFieldList().add(subscriptionFilterDto);
            	
            	notifyList = notifyService.list(filter, orders);
        	}
    	}
    	
    	// 查找订阅目标列表（全局通知）
    	filter = new CommonFilter();
    	FilterFieldInfoDto createAtFilterDto = new FilterFieldInfoDto();
    	createAtFilterDto.setFieldName("createAt");
    	createAtFilterDto.setFrom(lastNotifyDate);
    	filter.getFilterFieldList().add(createAtFilterDto);
    	
    	FilterFieldInfoDto typeFilterDto = new FilterFieldInfoDto();
    	typeFilterDto.setFieldName("type");
    	typeFilterDto.setValue(2);
    	typeFilterDto.setValueType(FieldValueType.SHORT);
    	filter.getFilterFieldList().add(typeFilterDto);
    	List<Notify> systemNotifyList = notifyService.list(filter, orders);
    	
    	
    	// 判断是否有新通知，没有新通知则返回
    	notifyList.addAll(systemNotifyList);
    	if (notifyList.size() == 0) {
    		return;
    	}
    	
    	// 将找到的新通知保存到用户的订阅列表中
    	List<UserNotify> userNotifyList = new ArrayList<UserNotify>();
    	Date createAt = new Date();
    	for (Notify notify: notifyList) {
    		UserNotify userNotify = new UserNotify();
    		userNotify.setNotify(notify);
    		userNotify.setRead(new Integer(0));
    		userNotify.setUserId(userId);
    		userNotify.setCreateAt(createAt);
    		userNotifyList.add(userNotify);
    	}
    	userNotifyService.batchCreate(userNotifyList);
    }
    
    /**
     * 修改提醒的已读状态
     * @return
     */
    @PostMapping("/read")
    @ResponseBody
    public JsonResultBean read(HttpServletRequest request, HttpServletResponse response,
    							@ModelAttribute UserNotifyBean bean,
    							@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
    							Locale locale) {
    	
    	// 查出数据
    	UserNotify entity = userNotifyService.find(bean.getId());
    	if (entity == null) {
    		JsonResultBean jsonResult = new JsonResultBean();
    		jsonResult.setStatus(JsonStatus.ERROR);
    		return jsonResult;
    	}
    	
    	entity.setRead(new Integer(1));
    	userNotifyService.update(entity);
    	
    	// 返回结果
    	JsonResultBean jsonResult = new JsonResultBean();
    	jsonResult.setStatus(JsonStatus.OK);
    	return jsonResult;
    }
}
