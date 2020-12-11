package project.edge.service.budget;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetEstimateVersionDao;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
@Service
public class BudgetEstimateVersionServiceImpl extends GenericServiceImpl<BudgetEstimateVersion, String>
        implements BudgetEstimateVersionService {

    @Resource
    private BudgetEstimateVersionDao budgetEstimateVersionDao;

    @Override
    public Dao<BudgetEstimateVersion, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetEstimateVersionDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
    
    /**
     * 根据年份和项目获取所有版本
     */
    @Override
    @Transactional(readOnly=true)
    public List<BudgetEstimateVersion> getVersionByYear(int year,String project) {
    	return this.budgetEstimateVersionDao.getVersionByYear(year,project);
    }
    
    /**
     * 根据年份获取总计划所有版本
     */
    @Override
    @Transactional(readOnly=true)
    public List<Plan> getPlanVersionByYear(int year,String project) {
    	return this.budgetEstimateVersionDao.getPlanVersionByYear(year,project);
    }
    
    /**
     * 根据计划id获取该版本下所有任务
     */
    @Override
    @Transactional(readOnly=true)
    public List<PlanTask> getTaskByPlanId(String planId) {
    	return this.budgetEstimateVersionDao.getTaskByPlanId(planId);
    }
    
    /**
     * 根据任务的创建时间，项目组关键字获取预算金额
     */
    @Override
    @Transactional(readOnly=true)
    public List<BudgetEstimate> getbudgetByGroup(Date cDatetime, String groupKey) {
    	return this.budgetEstimateVersionDao.getbudgetByGroup(cDatetime, groupKey);
    }
    
    /**
     * 根据任务的创建时间，项目组关键字获取预算金额
     */
    @Override
    @Transactional(readOnly=true)
    public List<BudgetEstimate> getLastBudgetByGroup(String year,String projectId, String groupId) {
    	return this.budgetEstimateVersionDao.getLastBudgetByGroup(year, projectId, groupId);
    }

}
