package project.edge.service.schedule;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanCalendarDao;
import project.edge.dao.schedule.PlanDao;
import project.edge.dao.schedule.PlanTaskDao;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000 [t_plan]对应的 Service。
 */
@Service
public class PlanServiceImpl extends GenericServiceImpl<Plan, String> implements PlanService {

	@Resource
	private PlanDao planDao;

	@Resource
	private PlanCalendarDao planCalendarDao;

	@Resource
	private PlanTaskDao planTaskDao;

	// 1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
	private static final String DEFAULT_WORK_DAYS = "1,2,3,4,5,6,7";

	@Override
	public Dao<Plan, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return this.planDao;
	}

	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("planName", false);
	}

	@Override
	@Transactional
	public void create(Plan entity) {

		PlanCalendar planCalendar = this.createPlanCalendar(entity);

		this.planCalendarDao.create(planCalendar);

		entity.setPlanCalendar(planCalendar);

		this.planDao.create(entity);
	}

	// 创建该计划的专属日历信息
	private PlanCalendar createPlanCalendar(Plan entity) {
		PlanCalendar planCalendar = new PlanCalendar();

		planCalendar.setCalendarName(entity.getPlanName());
		planCalendar.setcDatetime(entity.getcDatetime());
		planCalendar.setCreator(entity.getCreator().getId());
		planCalendar.setIsDeleted(OnOffEnum.ON.value());
		planCalendar.setmDatetime(entity.getmDatetime());
		planCalendar.setModifier(entity.getModifier());

		planCalendar.setWeekdays(DEFAULT_WORK_DAYS);

		return planCalendar;
	}

	@Override
	@Transactional
	public void createPresetPlan(Plan plan, List<PlanTask> planTasks) {

		this.create(plan);
		this.planTaskDao.create(planTasks);
	}

	@Transactional
	public void setData(Plan entity) {
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
			return;
		}
		super.update(entity);
	}

	@Override
	@Transactional
	public JsonResultBean list(String commonFilterJson, int page, int rows, String sort, String order, Locale locale) {
		return planDao.list(commonFilterJson, page, rows, sort, order, locale);
	}
	
	@Override
	@Transactional
	public List<Plan> findList(String projectId, String virtualOrgId, String nowDate) {
		return planDao.findList(projectId, virtualOrgId, nowDate);
	}
}
