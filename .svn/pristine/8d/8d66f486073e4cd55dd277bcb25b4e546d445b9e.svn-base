package project.edge.service.schedule;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.schedule.PlanDao;
import project.edge.dao.schedule.PlanProgressDao;
import project.edge.dao.schedule.PlanProgressTaskDao;
import project.edge.dao.schedule.PlanTaskAttachmentDao;
import project.edge.dao.schedule.PlanTaskDao;
import project.edge.dao.schedule.PlanTaskPreTaskDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.PlanProgress;
import project.edge.domain.entity.PlanProgressTask;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PlanTaskAttachment;
import project.edge.domain.entity.PlanTaskPreTask;

/**
 * @author angel_000 [t_plan_task]对应的 Service。
 */
@Service
public class PlanTaskServiceImpl extends GenericServiceImpl<PlanTask, String> implements PlanTaskService {

	@Resource
	private PlanTaskDao planTaskDao;

	@Resource
	private PlanTaskPreTaskDao planTaskPreTaskDao;

	@Resource
	private PlanDao planDao;

	@Resource
	private PlanProgressDao planProgressDao;

	@Resource
	private PlanProgressTaskDao planProgressTaskDao;
	
	@Resource
	private PlanTaskAttachmentDao attachmentDao;
	
	@Resource
	private ArchiveDao archiveDao;

	@Override
	public Dao<PlanTask, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return this.planTaskDao;
	}

	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("cDatetime", false);
	}

	@Override
	@Transactional
	public void createTasksAndLinks(String planId, List<PlanTask> planTasks, List<PlanTaskPreTask> planTaskPreTasks) throws IllegalAccessException, InvocationTargetException {

//		// delete all history
//		this.planTaskDao.deleteTasksWithPlanId(planId);
//		this.planTaskPreTaskDao.deletePreTaskWithPlanId(planId);
//
//		for (PlanTask task : planTasks) {
//			System.out.println(task.getIssueType());
//		}
//
//		// create tasks and links
//		if (!planTasks.isEmpty()) {
//			this.planTaskDao.create(planTasks);
//		}
//
//		if (!planTaskPreTasks.isEmpty()) {
//			this.planTaskPreTaskDao.create(planTaskPreTasks);
//		}
		
		// 20200715更新，对比planTasks和planTaskPreTasks，不存在则新增，存在则更新，否则删除
		
		CommonFilter filter = new CommonFilter().addExact("plan.id", planId);
		List<PlanTask> prevPlanTasks = this.planTaskDao.list(filter, null);
		for (int i = 0; i < planTasks.size(); i++) {
			PlanTask task = planTasks.get(i);
			
			// 如果存在于已有的任务中，则更新
			Optional<PlanTask> taskOptional = prevPlanTasks.stream().filter(prevTask -> prevTask.getId().equals(task.getId())).findFirst();
			
			// 更新
			if (taskOptional.isPresent()) {
				PlanTask curPlanTask = taskOptional.get();
				prevPlanTasks.remove(curPlanTask);
				task.setLastProgress(curPlanTask.getLastProgress());
				task.setFlowStatus(curPlanTask.getFlowStatus());
				BeanUtils.copyProperties(task, curPlanTask, getNullPropertyNames(task));
				this.planTaskDao.update(curPlanTask);
				
			} 
			// 新增
			else {
				this.planTaskDao.create(task);
			}
		}
		
		// 剩余的都是要删除的
		if (prevPlanTasks.size() > 0) {
			for (int i = 0; i < prevPlanTasks.size(); i++) {
				this.planTaskDao.delete(prevPlanTasks.get(i));
			}
//			this.planTaskDao.batchDelete(prevPlanTasks);
		}
		
		List<PlanTaskPreTask> prenPlanTaskPreTasks = this.planTaskPreTaskDao.list(filter, null);
		for (int i = 0; i < planTaskPreTasks.size(); i++) {
			PlanTaskPreTask preTask = planTaskPreTasks.get(i);
			
			// 如果存在于已有的任务中，则更新
			Optional<PlanTaskPreTask> taskOptional = prenPlanTaskPreTasks.stream().filter(prevTask -> prevTask.getId().equals(preTask.getId())).findFirst();
			
			// 更新
			if (taskOptional.isPresent()) {
				PlanTaskPreTask curPlanTaskPreTask = taskOptional.get();
				planTaskPreTasks.remove(curPlanTaskPreTask);
				BeanUtils.copyProperties(preTask, curPlanTaskPreTask, getNullPropertyNames(preTask));
				this.planTaskPreTaskDao.update(curPlanTaskPreTask);
			} 
			// 新增
			else {
				this.planTaskPreTaskDao.create(preTask);
			}
		}
		
		// 剩余的都是要删除的
		if (planTaskPreTasks.size() > 0) {
			for (int i = 0; i < planTaskPreTasks.size(); i++) {
				this.planTaskPreTaskDao.delete(planTaskPreTasks.get(i));
			}
//			this.planTaskPreTaskDao.batchDelete(planTaskPreTasks);
		}
	}
	
	private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

	@Override
	@Transactional
	public void createPlanProgress(PlanProgress planProgress, List<PlanProgressTask> progressTasks,
			List<PlanTask> planTasks) {

		this.planProgressDao.create(planProgress);

		if (!progressTasks.isEmpty()) {
			this.planProgressTaskDao.create(progressTasks);
		}

		if (!planTasks.isEmpty()) {
			this.planTaskDao.update(planTasks);
		}
	}

	@Override
	@Transactional
	public List<PlanTask> findList(String projectId, String virtualOrgId, String nowDate) {
		return this.planTaskDao.findList(projectId, virtualOrgId, nowDate);
	}
	
	@Override
	@Transactional
	public List<PlanTask> findOverdueList() {
		return this.planTaskDao.findOverdueList();
	}

	@Override
	@Transactional
	public void update(PlanTask entity) {
		
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
			return;
		}
		
		if (this.archiveDao.find(FolderPath.PLAN_PROGRESS) == null) {
			this.archiveDao.create(this.getArchive(FolderPath.PLAN_PROGRESS));
		}
		
		for (PlanTaskAttachment a: entity.getAttachments()) {
			if (this.archiveDao.find(a.getArchive().getId()) == null) {
				this.archiveDao.create(a.getArchive());
				this.attachmentDao.create(a);
			}
		}
		
		super.update(entity);
		
		for (PlanTaskAttachment a: entity.getAttachmentsToDelete()) {
			this.attachmentDao.delete(a);
//			this.archiveDao.delete(a.getArchive());
		}
		
	}
	
	/**
     * 创建对应名称的Archive。
     * 
     * @return
     */
    private Archive getArchive(String id) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(id);
        dir.setArchiveName("计划进度");
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(Constants.SLASH);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(1);
        dir.setPath(Constants.SLASH);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
}
