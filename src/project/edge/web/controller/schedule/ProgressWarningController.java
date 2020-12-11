package project.edge.web.controller.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.EmailConfig;
import garage.origin.common.util.MailUtils;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.domain.converter.PlanBeanConverter;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.Site;
import project.edge.domain.view.PlanBean;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.service.schedule.PlanService;
import project.edge.web.apiService.notify.NotifyApiService;
import project.edge.web.apiService.plan.PlanApiService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 进度预警画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/progress-warning")
public class ProgressWarningController extends TreeGridController<Plan, PlanBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProgressWarningController.class);

    private static final String PID = "P5020";

    @Resource
    private SiteService siteService;
    
    @Resource
    private PlanApiService planApiService;
    
    @Resource
    private PlanService planService;

    @Resource
	private PersonService personService;
	
	@Resource
	private NotifyApiService notifApiService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PLAN_WARNING.value();
    }

    @Override
    protected Service<Plan, String> getDataService() {
        return this.planService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<Plan, PlanBean> getViewConverter() {
        return new PlanBeanConverter();
    }
    
    
    /**
     * 每日更新所有Site的进度
     */
    @Scheduled(cron="0 0 1 * * ?")
//    @Scheduled(cron="0 */1 * * * ?")
    public void updateAllSiteProgress() {
    	
    	List<Site> siteList = siteService.list(null, null);
    	for (Site site : siteList) {
    		planApiService.updateSiteProgress(site.getId());
    	}
    }
    
    @Scheduled(cron="0 0 1 * * ?")
//    @Scheduled(cron="0 */1 * * * ?")
    public void notifyOverdueSchedules() {
    	
    	// 取得所有逾期的计划列表
    	List<Plan> planList = getAllOverduePlanList();
    	
    	// 如果没有逾期的计划，返回
    	if (planList.size() == 0) {
    		return;
    	}
    	
    	// 取得所有Leader集合
    	Set<String> leaderIdSet = new HashSet<>();
    	for (int i = 0; i < planList.size(); i++) {
    		Plan plan = planList.get(i);
    		Person leader = plan.getProject().getMainLeader();
    		leaderIdSet.add(leader.getId());
    	}
    	
    	// 针对每个Leader，发送聚合信息
    	Iterator<String> it = leaderIdSet.iterator();
    	while (it.hasNext()) {
    		
    		String leaderId = it.next();
    		List<Plan> leaderPlanList = planList.stream()
    											.filter((Plan plan) -> plan.getProject().getMainLeader().getId().equals(leaderId))
    											.collect(Collectors.toList());
    		
    		String subject = "当前有" + leaderPlanList.size() + "个项目逾期";
    		StringBuffer buffer = new StringBuffer();
    		for (int i = 0; i < planList.size(); i++) {
    			Plan plan = planList.get(i);
    			String detail = "【" + plan.getPlanName() + "】 计划完成日期：" + DateUtils.date2String(plan.getPlanEndDate());
    			buffer.append(detail + "<br />");
    		}
    		String message = buffer.toString();
    		
    		// 找到对应的email
    		
    		Person mainLeader = personService.find(leaderId);
    		String leaderEmail = mainLeader.getEmail();
    		
    		// 发送邮件提醒
    		if (!StringUtils.isEmpty(leaderEmail)) {
    			sendEmail(leaderEmail, subject, message);
    		}
    		
    		sendNotify(mainLeader, subject, message);
    	}
    }
    
    
    /**
     * 发送Email提醒
     * @param emailAddress
     * @param subject
     * @param message
     */
    private void sendEmail(String emailAddress, String subject, String message) {
    	
    	MailUtils mailer = new MailUtils();
    	EmailConfig config = new EmailConfig();
    	config.setRecieveEmail(emailAddress);
    	
    	try {
    		mailer.sendMail(subject,  "<html>" + message  + "</html>", config);
    	} catch (Exception e) {
    		logger.warn("发送计划逾期的提醒邮件失败，邮件地址=" + emailAddress);
    	}
    	
    }
    
    /**
     * 发送系统提醒
     * @param person
     * @param subject
     * @param message
     */
    private void sendNotify(Person person, String subject, String message) {
		notifApiService.sendNotify(person.getUser(), subject, message, NotifyTargetTypeEnum.PROGRESS_WARNING.value());
    }
    
    
    
    /**
     * 取得所有逾期的计划列表
     * @return
     */
    private List<Plan> getAllOverduePlanList() {
    	
    	// 找出所有逾期的进度（状态未结束，计划完成日期小于今日）
    	CommonFilter filter = new CommonFilter();
    	FilterFieldInfoDto planEndDateDto = new FilterFieldInfoDto();
    	planEndDateDto.setFieldName("planEndDate");
    	planEndDateDto.setTo(DateUtils.getDateByDaysDiff(new Date(), -1));
    	FilterFieldInfoDto isCompleteDto = new FilterFieldInfoDto();
    	isCompleteDto.setFieldName("isComplete");
    	isCompleteDto.setValue(Short.valueOf("0"));
    	filter.getFilterFieldList().add(planEndDateDto);
    	filter.getFilterFieldList().add(isCompleteDto);
    	
    	List<OrderByDto> order = new ArrayList<OrderByDto>();
    	OrderByDto orderDto = new OrderByDto();
    	orderDto.setColumnName("planEndDate");
    	orderDto.setDesc(false);
    	order.add(orderDto);
    	List<Plan> planList = this.planService.list(filter, order);
    	
    	return planList;
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

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @SysLogAnnotation(description = "进度预警", action = "新增")
    public JsonResultBean create(@ModelAttribute PlanBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.create(bean, null, userBean, locale);
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "进度预警", action = "更新")
    public JsonResultBean update(@ModelAttribute PlanBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "进度预警", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
