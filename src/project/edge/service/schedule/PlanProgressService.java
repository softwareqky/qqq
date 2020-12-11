package project.edge.service.schedule;

import java.util.Locale;

import garage.origin.domain.view.JsonResultBean;
import garage.origin.service.Service;
import project.edge.domain.entity.PlanProgress;

/**
 * @author angel_000
 *         [t_plan_progress]对应的 Service。
 */
public interface PlanProgressService extends Service<PlanProgress, String> {
	public JsonResultBean list(String commonFilterJson, int page, int rows, String sort, String order, Locale locale);

}
