package project.edge.dao.budget;

import java.util.List;

import garage.origin.dao.Dao;
import project.edge.domain.entity.BudgetFee;

public interface BudgetFeeDao extends Dao<BudgetFee,String>{
	public List<Object> getBudgetFeeList(String startDate, String endDate);
	public List<Object> getFeeBySite(String startDate, String endDate);
	public List<Object> getSiteNameList(String startDate, String endDate);
	public List<Object> getFeeByYearAndCode(String year);
}
