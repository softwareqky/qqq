package project.edge.service.bidding;

import garage.origin.service.Service;
import project.edge.domain.entity.TenderingPlan;

/**
 * @author angel_000
 *         [t_tendering_plan]对应的 Service。
 */
public interface TenderingPlanService extends Service<TenderingPlan, String> {
	public void setData(TenderingPlan entity);
}
