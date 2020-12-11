package project.edge.service.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanChangeDao;
import project.edge.dao.schedule.PlanChangeTaskDao;
import project.edge.dao.schedule.PlanChangeTaskPreTaskDao;
import project.edge.dao.schedule.PlanDao;
import project.edge.dao.schedule.PlanTaskDao;
import project.edge.dao.schedule.PlanTaskPreTaskDao;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanChange;
import project.edge.domain.entity.PlanChangeTask;
import project.edge.domain.entity.PlanChangeTaskPreTask;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PlanTaskPreTask;


/**
 * [t_plan_change]对应的 Service。
 */
@Service
public class PlanChangeServiceImpl extends GenericServiceImpl<PlanChange, String>
        implements PlanChangeService {

    @Resource
    private PlanChangeDao planChangeDao;

    @Resource
    private PlanDao planDao;

    @Resource
    private PlanTaskDao planTaskDao;

    @Resource
    private PlanChangeTaskDao planChangeTaskDao;

    @Resource
    private PlanTaskPreTaskDao planTaskPreTaskDao;

    @Resource
    private PlanChangeTaskPreTaskDao planChangeTaskPreTaskDao;



    @Override
    public Dao<PlanChange, String> getDefaultDao() {
        return this.planChangeDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("planName", false);
    }

    /**
     * 新建时，把[t_plan]关联的[t_plan_task]和[t_plan_task_pre_task]复制到[t_plan_change_task]和[t_plan_change_task_pre_task]。
     */
    @Override
    @Transactional
    public void create(PlanChange entity) {

        Plan p = this.planDao.find(entity.getPlan().getId());
        entity.setProject(p.getProject());
        entity.setPlanCalendar(p.getPlanCalendar());
        this.planChangeDao.create(entity);

        // 将PlanTask转换成PlanChangeTask，并保存
        CommonFilter filter = new CommonFilter().addExact("plan.id", p.getId());

        List<PlanTask> taskList = this.planTaskDao.list(filter, null);

        List<PlanChangeTask> planChangeTaskList = new ArrayList<>();
        List<PlanChangeTaskPreTask> changePreTaskList = new ArrayList<>();

        // key:PlanTask.id, value:PlanChangeTask
        Map<String, PlanChangeTask> taskIdMap = new HashMap<>();

        for (PlanTask task : taskList) {
            PlanChangeTask changeTask = this.generatePlanChangeTask(task, entity);
            taskIdMap.put(task.getId(), changeTask);

            planChangeTaskList.add(changeTask);
        }
        for (PlanChangeTask changeTask : planChangeTaskList) {
            String pid = changeTask.getPid();
            if (!"0".equals(pid)) { // 顶层是"0"
                PlanChangeTask parentChangeTask = taskIdMap.get(pid);
                changeTask.setPid(parentChangeTask.getId());
            }
        }

        // 将PlanTaskPreTask转换成PlanChangeTaskPreTask，并保存
        List<PlanTaskPreTask> preTaskList = this.planTaskPreTaskDao.list(filter, null);

        for (PlanTaskPreTask preTask : preTaskList) {
            PlanChangeTaskPreTask changeTaskPreTask =
                    this.generateChangeTaskPreTask(preTask, entity, taskIdMap);

            changePreTaskList.add(changeTaskPreTask);
        }

        if (!planChangeTaskList.isEmpty()) {
            this.planChangeTaskDao.create(planChangeTaskList);
        }

        if (!changePreTaskList.isEmpty()) {
            this.planChangeTaskPreTaskDao.create(changePreTaskList);
        }
    }

    @Transactional
    public void setData(PlanChange entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
    
    private PlanChangeTask generatePlanChangeTask(PlanTask task, PlanChange planChange) {
        PlanChangeTask changeTask = new PlanChangeTask();

        changeTask.setPlan(planChange.getPlan());

        changeTask.setPlanChange(planChange);
        changeTask.setPlanTask(task);

        changeTask.setPid(task.getPid());

        changeTask.setAchievement(task.getAchievement());
        changeTask.setTaskNum(task.getTaskNum());
        changeTask.setTaskLayer(task.getTaskLayer());
        changeTask.setTaskName(task.getTaskName());
        changeTask.setTaskType(task.getTaskType());
        changeTask.setIsSummary(task.getIsSummary());
        changeTask.setIsMilestone(task.getIsMilestone());
        changeTask.setIsCritical(task.getIsCritical());
        changeTask.setDurationDays(task.getDurationDays());
        changeTask.setPlanStartDate(task.getPlanStartDate());
        changeTask.setPlanEndDate(task.getPlanEndDate());
        changeTask.setWbs(task.getWbs());
        changeTask.setWorkload(task.getWorkload());
        changeTask.setWeight(task.getWeight());
        changeTask.setPriority(task.getPriority());
        changeTask.setLeader(task.getLeader());
        changeTask.setDeadlineDate(task.getDeadlineDate());
        changeTask.setConstraintType(task.getConstraintType());
        changeTask.setConstraintDate(task.getConstraintDate());
        changeTask.setIsSiteTask(task.getIsSiteTask());
        changeTask.setConstructionStatus(task.getConstructionStatus());
        changeTask.setSiteSegmentId(task.getSiteSegmentId());
        changeTask.setParticipantList(task.getParticipantList());
        changeTask.setParticipantNameList(task.getParticipantNameList());
        changeTask.setRemark(task.getRemark());
        changeTask.setCreator(planChange.getCreator().getId());
        changeTask.setcDatetime(planChange.getcDatetime());
        changeTask.setModifier(planChange.getModifier());
        changeTask.setmDatetime(planChange.getmDatetime());

        return changeTask;
    }

    private PlanChangeTaskPreTask generateChangeTaskPreTask(PlanTaskPreTask preTask,
            PlanChange planChange, Map<String, PlanChangeTask> taskIdMap) {
        PlanChangeTaskPreTask changePreTask = new PlanChangeTaskPreTask();

        changePreTask.setLagDays(preTask.getLagDays());
        changePreTask.setPreTaskType(preTask.getPreTaskType());
        changePreTask.setPlanChange(planChange);

        PlanChangeTask planChangeTask = taskIdMap.get(preTask.getPlanTask().getId());
        changePreTask.setPlanChangeTask(planChangeTask);

        PlanChangeTask prePlanChangeTask = taskIdMap.get(preTask.getPreTask().getId());
        changePreTask.setPreTask(prePlanChangeTask);

        return changePreTask;
    }

}
