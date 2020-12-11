package project.edge.dao.schedule;

import java.util.Locale;

import garage.origin.dao.Dao;
import garage.origin.domain.view.JsonResultBean;
import project.edge.domain.entity.PlanProgress;

/**
 * @author angel_000
 *         [t_plan_progress]对应的DAO。
 */
public interface PlanProgressDao extends Dao<PlanProgress, String> {
	public JsonResultBean list(String commonFilterJson, int page, int rows, String sort, String order, Locale locale);

}
