package project.edge.service.budget;

import garage.origin.service.Service;
import project.edge.domain.entity.CapitalApply;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
public interface CapitalApplyService extends Service<CapitalApply, String> {
	
	public void setData(CapitalApply entity);

}
