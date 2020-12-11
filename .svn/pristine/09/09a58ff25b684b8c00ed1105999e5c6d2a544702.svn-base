/**
 * 
 */
package project.edge.service.budget;

import java.util.List;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetEstimateDao;
import project.edge.domain.entity.BudgetEstimate;


/**
 * @author wwy
 *         [t_budget_estimate]对应的 Service。
 */
@Service
public class BudgetEstimateServiceImpl extends GenericServiceImpl<BudgetEstimate, String> implements BudgetEstimateService {

    @Resource
    private BudgetEstimateDao budgetEstimateDao;

    @Override
    public Dao<BudgetEstimate, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetEstimateDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
    
    /**
     * 年度使用计划
     */
    @Override
    @Transactional(readOnly=true)
    public List<BudgetEstimate> getBudgetByYear(int year,String project) {
    	return this.budgetEstimateDao.getBudgetByYear(year,project);
    }
    
    /**
     * 根据版本查询预算
     */
    @Override
    @Transactional(readOnly=true)
    public List<BudgetEstimate> getBudgetByVersion(String version) {
    	return this.budgetEstimateDao.getBudgetByVersion(version);
    }

	@Override
	public void deleteByCode(String code, Locale locale) {
		budgetEstimateDao.deleteByCode(code, locale);
	}

	@Override
	@Transactional
	public void setData(BudgetEstimate entity) {
		// TODO Auto-generated method stub
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
	}
	
	@Override
	@Transactional
	public void updateCapitalPlan(String projectId,String code,String versionId,String planRemark,int year) {
		budgetEstimateDao.updateCapitalPlan(projectId,code,versionId,planRemark,year);
	}

}
