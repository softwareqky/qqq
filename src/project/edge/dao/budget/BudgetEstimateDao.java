/**
 * 
 */
package project.edge.dao.budget;

import java.util.List;
import java.util.Locale;

import garage.origin.dao.Dao;
import project.edge.domain.entity.BudgetEstimate;


/**
 * @author wwy
 *        [t_budget_estimate]对应的DAO。
 */
public interface BudgetEstimateDao extends Dao<BudgetEstimate, String> {

	void deleteByCode(String code, Locale locale);
	public List<BudgetEstimate> getBudgetByYear(int year,String project);
	public List<BudgetEstimate> getBudgetByVersion(String version);
	public void updateCapitalPlan(String projectId,String code,String versionId,String planRemark,int year);

}
