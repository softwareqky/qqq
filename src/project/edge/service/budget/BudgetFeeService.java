package project.edge.service.budget;

import java.util.List;

import garage.origin.domain.view.JsonResultBean;
import garage.origin.service.Service;
import project.edge.domain.entity.BudgetFee;



/**
 * 
 * @author zjy
 *[t_budget_fee]对应的 Service。
 */
public interface BudgetFeeService extends Service<BudgetFee,String>{
 
	public List<Object> getBudgetFeeList(String startDate, String endDate);
	
	public List<Object> getFeeBySite(String startDate, String endDate);
	
	public List<Object> getSiteNameList(String startDate, String endDate);
	
	public List<Object> getFeeByYearAndCode(String year);
	
	public JsonResultBean parseSyncData(String data);

}
