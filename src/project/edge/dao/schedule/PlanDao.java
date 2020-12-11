package project.edge.dao.schedule;

import java.util.List;
import java.util.Locale;

import garage.origin.dao.Dao;
import garage.origin.domain.view.JsonResultBean;
import project.edge.domain.entity.Plan;

/**
 * @author angel_000 [t_plan]对应的DAO。
 */
public interface PlanDao extends Dao<Plan, String> {

	public JsonResultBean list(String commonFilterJson, int page, int rows, String sort, String order, Locale locale);

	public List<Plan> findList(String projectId, String virtualOrgId, String nowDate);
}
