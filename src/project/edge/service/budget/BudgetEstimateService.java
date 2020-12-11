/**
 * 
 */
package project.edge.service.budget;

import java.util.List;
import java.util.Locale;

import org.springframework.web.bind.annotation.RequestParam;

import garage.origin.service.Service;
import project.edge.domain.entity.BudgetEstimate;


/**
 * @author wwy [t_budget_estimate]对应的 Service。
 */
public interface BudgetEstimateService extends Service<BudgetEstimate, String> {
	void deleteByCode(@RequestParam String code, Locale locale);
	
    /**
     * 年度使用计划
     */
	public List<BudgetEstimate> getBudgetByYear(int year,String project);
	
    /**
     * 根据版本查询预算
     */
	public List<BudgetEstimate> getBudgetByVersion(String version);
	
	public void setData(BudgetEstimate entity);
	
	public void updateCapitalPlan(String projectId,String code,String versionId,String planRemark,int year);
}
