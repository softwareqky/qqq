package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PlanTaskAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PlanTaskBean;
import project.edge.service.system.DataOptionService;

/**
 * @author angel_000 转换计划明细表对应的view和entity的转换器。
 *
 */
public class PlanTaskBeanConverter implements ViewConverter<PlanTask, PlanTaskBean> {

	@Resource
	private DataOptionService dataOptionService;

	@Override
	public PlanTaskBean fromEntity(PlanTask entity, MessageSource messageSource, Locale locale) {

		PlanTaskBean bean = new PlanTaskBean();

		bean.setId(entity.getId());

		Plan plan = entity.getPlan();

		if (plan != null) {
			bean.setPlan_(plan.getId());
			bean.setPlanText(plan.getPlanName());
		}

		bean.setTaskNum(entity.getTaskNum());
		bean.setPid(entity.getPid());
		bean.setTaskLayer(entity.getTaskLayer());
		bean.setTaskName(entity.getTaskName());
		bean.setIsSummary(entity.getIsSummary());
		bean.setIsSummaryText(
				entity.getIsSummary() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
						: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

		bean.setIsMilestone(entity.getIsMilestone());
		bean.setIsMilestoneText(
				entity.getIsMilestone() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
						: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

		bean.setIsCritical(entity.getIsCritical());
		bean.setIsCriticalText(
				entity.getIsCritical() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
						: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

		bean.setDurationDays(entity.getDurationDays());

		if (entity.getPlanStartDate() != null) {
			bean.setPlanStartDate(DateUtils.date2String(entity.getPlanStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getPlanEndDate() != null) {
			bean.setPlanEndDate(DateUtils.date2String(entity.getPlanEndDate(), Constants.DATE_FORMAT));
		}

		bean.setDueProgressDays(entity.getDueProgressDays());
		bean.setWbs(entity.getWbs());
		bean.setWorkload(entity.getWorkload());
		bean.setWeight(entity.getWeight());
		bean.setPriority(entity.getPriority());

		Person leader = entity.getLeader();
		if (leader != null) {
			bean.setLeader_(leader.getId());
			bean.setLeaderText(leader.getPersonName());
		}

		if (entity.getDeadlineDate() != null) {
			bean.setDeadlineDate(DateUtils.date2String(entity.getDeadlineDate(), Constants.DATE_FORMAT));
		}

		bean.setConstraintType(entity.getConstraintType());

		if (entity.getConstraintDate() != null) {
			bean.setConstraintDate(DateUtils.date2String(entity.getConstraintDate(), Constants.DATE_FORMAT));
		}

		bean.setIsSiteTask(entity.getIsSiteTask());
		bean.setIsSiteTaskText(
				entity.getIsSiteTask() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
						: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

		bean.setConstructionStatus(entity.getConstructionStatus());
		bean.setSiteSegmentId(entity.getSiteSegmentId());
		bean.setParticipantList(entity.getParticipantList());
		bean.setParticipantNameList(entity.getParticipantNameList());

		bean.setTaskType(entity.getTaskType());

		if (entity.getActualStartDate() != null) {
			bean.setActualStartDate(DateUtils.date2String(entity.getActualStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getActualEndDate() != null) {
			bean.setActualEndDate(DateUtils.date2String(entity.getActualEndDate(), Constants.DATE_FORMAT));
		}

		bean.setActualDurationDays(entity.getActualDurationDays());
		bean.setLastProgress(entity.getLastProgress());
		bean.setProgress(entity.getProgress());
		bean.setProgressDays(entity.getProgressDays());
		if (entity.getIsWarning() != null) {
			bean.setIsWarning(entity.getIsWarning());
			bean.setIsWarningText(
					entity.getIsWarning() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		if (entity.getIsBehindPlanStart() != null) {
			bean.setIsBehindPlanStart(entity.getIsBehindPlanStart());
			bean.setIsBehindPlanStartText(entity.getIsBehindPlanStart() == 1
					? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
					: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		if (entity.getIsBehindPlanEnd() != null) {
			bean.setIsBehindPlanEnd(entity.getIsBehindPlanEnd());
			bean.setIsBehindPlanEndText(entity.getIsBehindPlanEnd() == 1
					? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
					: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		bean.setIsBehindSchedule(entity.getIsBehindSchedule());
		bean.setIsBehindScheduleText(
				entity.getIsBehindSchedule() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
						: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

		if (entity.getIsDelay() != null) {
			bean.setIsDelay(entity.getIsDelay());
			bean.setIsDelayText(
					entity.getIsDelay() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		bean.setDelayCause(entity.getDelayCause());

		if (entity.getProgressDate() != null) {
			bean.setProgressDate(DateUtils.date2String(entity.getProgressDate(), Constants.DATE_FORMAT));
		}
		bean.setScore(entity.getScore());
		bean.setProgressRemark(entity.getProgressRemark());
		bean.setAchievement(entity.getAchievement());
		bean.setDelivery(entity.getDelivery());

		bean.setIsComment(entity.getIsComment());
		bean.setIsCommentText(
				entity.getIsComment() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
						: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		bean.setRemark(entity.getRemark());

		bean.setIssueType(entity.getIssueType());

		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
		CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
		List<DataOption> list = this.dataOptionService.list(f, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value())) {
				if (o.getId().equals(bean.getIssueType())) {
					bean.setIssueTypeText(o.getOptionName());
				}
			}
		}
		return bean;
	}

	@Override
	public PlanTask toEntity(PlanTaskBean bean, PlanTask oldEntity, AbstractSessionUserBean user, Date now) {

		PlanTask entity = oldEntity;
		if (entity == null) { // 表示新建
			entity = new PlanTask();

			entity.setCreator(user.getSessionUserId());
			entity.setcDatetime(now);

		} else {
			entity.setModifier(user.getSessionUserId());
		}

		bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);

		if (!StringUtils.isEmpty(bean.getPlan_())) {
			Plan plan = new Plan();
			plan.setId(bean.getPlan_());
			entity.setPlan(plan);
		}

		entity.setTaskNum(bean.getTaskNum());
		entity.setPid(bean.getPid());
		entity.setTaskLayer(bean.getTaskLayer());
		entity.setTaskName(bean.getTaskName());
		entity.setIsSummary(bean.getIsSummary());
		entity.setIsMilestone(bean.getIsMilestone());
		entity.setIsCritical(bean.getIsCritical());
		entity.setDurationDays(bean.getDurationDays());

		if (!StringUtils.isEmpty(bean.getPlanStartDate())) {
			try {
				entity.setPlanStartDate(DateUtils.string2Date(bean.getPlanStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getPlanEndDate())) {
			try {
				entity.setPlanEndDate(DateUtils.string2Date(bean.getPlanEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setDueProgressDays(bean.getDueProgressDays());
		entity.setWbs(bean.getWbs());
		entity.setWorkload(bean.getWorkload());
		entity.setWeight(bean.getWeight());
		entity.setPriority(bean.getPriority());

		if (!StringUtils.isEmpty(bean.getLeader_())) {
			Person leader = new Person();
			leader.setId(bean.getLeader_());
			entity.setLeader(leader);
		}

		entity.setParticipantNameList(bean.getParticipantNameList());

		if (!StringUtils.isEmpty(bean.getDeadlineDate())) {
			try {
				entity.setDeadlineDate(DateUtils.string2Date(bean.getDeadlineDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}
		entity.setConstraintType(bean.getConstraintType());

		if (!StringUtils.isEmpty(bean.getConstraintDate())) {
			try {
				entity.setConstraintDate(DateUtils.string2Date(bean.getConstraintDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}
		entity.setIsSiteTask(bean.getIsSiteTask());
		entity.setConstructionStatus(bean.getConstructionStatus());
		entity.setSiteSegmentId(bean.getSiteSegmentId());
		entity.setParticipantList(bean.getParticipantList());
		entity.setParticipantNameList(bean.getParticipantNameList());

		if (!StringUtils.isEmpty(bean.getActualStartDate())) {
			try {
				entity.setActualStartDate(DateUtils.string2Date(bean.getActualStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getActualEndDate())) {
			try {
				entity.setActualEndDate(DateUtils.string2Date(bean.getActualEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setActualDurationDays(bean.getActualDurationDays());
		entity.setProgress(bean.getProgress());
		entity.setProgressDays(bean.getProgressDays());
		entity.setIsWarning(bean.getIsWarning());
		entity.setIsBehindPlanStart(bean.getIsBehindPlanStart());
		entity.setIsBehindPlanEnd(bean.getIsBehindPlanEnd());
		entity.setIsBehindSchedule(bean.getIsBehindSchedule());
		entity.setIsDelay(bean.getIsDelay());
		entity.setDelayCause(bean.getDelayCause());

		if (!StringUtils.isEmpty(bean.getProgressDate())) {
			try {
				entity.setProgressDate(DateUtils.string2Date(bean.getProgressDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setTaskType(bean.getTaskType());
		entity.setScore(bean.getScore());
		entity.setProgressRemark(bean.getProgressRemark());
		entity.setAchievement(bean.getAchievement());
		entity.setDelivery(bean.getDelivery());
		entity.setIsComment(bean.getIsComment());
		entity.setRemark(bean.getRemark());

		return entity;
	}

	public PlanTask toImportEntity(PlanTaskBean bean, PlanTask oldEntity, AbstractSessionUserBean user, Date now) {

		PlanTask entity = oldEntity;

		entity.setId(bean.getId()); // ID必须赋值
		entity.setmDatetime(now);
		entity.setIsDeleted(OnOffEnum.OFF.value());
		entity.setFlowStatus(bean.getFlowStatus());
		entity.setLastProgress(bean.getLastProgress());
		entity.setProgress(bean.getProgress());
		if (!StringUtils.isEmpty(bean.getActualStartDate())) {
			try {
				entity.setActualStartDate(DateUtils.string2Date(bean.getActualStartDate(), Constants.DATE_FORMAT));
			} catch (Exception e) {
			}
		}
		if (!StringUtils.isEmpty(bean.getActualEndDate())) {
			// 实际结束时间
			try {
				entity.setActualEndDate(DateUtils.string2Date(bean.getActualEndDate(), Constants.DATE_FORMAT));
			} catch (Exception e) {
			}
		}
		entity.setCumulativeProgress(bean.getCumulativeProgress());
		entity.setCurrentWeekProgress(bean.getCurrentWeekProgress());
		entity.setIssueType(bean.getIssueType());
		entity.setDescription(bean.getDescription());
		entity.setNextWeekGoals(bean.getNextWeekGoals());
		entity.setmDatetime(new Date());
		return entity;
	}
	
	public PlanTaskAttachment toAttachmentEntity(ArchiveBean ab,
	    ArchiveBeanConverter abConverter, PlanTask entity, int attachmentType,
	    SessionUserBean user, Date now) {
	    
	    PlanTaskAttachment attachment = new PlanTaskAttachment();
	    attachment.setPlanTask(entity);
	    
	    ab.setLevel(1);
	    
	    ab.setPid(FolderPath.PLAN_PROGRESS);
	    ab.setPath(Constants.SLASH + "_" + FolderPath.PLAN_PROGRESS);
	    
	    Archive aEntity = abConverter.toEntity(ab, null, user, now);
	    String relativePath = File.separator + FolderPath.PLAN_PROGRESS + File.separator + aEntity.getId();
	    ab.setRelativePath(relativePath);
	    aEntity.setRelativePath(relativePath);
	    
	    attachment.setArchive(aEntity);
	    return attachment;
    }
}
