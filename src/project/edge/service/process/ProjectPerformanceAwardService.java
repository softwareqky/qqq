package project.edge.service.process;

import garage.origin.service.Service;
import project.edge.domain.entity.ProjectPerformanceAward;

/**
 * @author angel_000
 *         [t_project_performance_award]对应的 Service。
 */
public interface ProjectPerformanceAwardService extends Service<ProjectPerformanceAward, String> {
	public void setData(ProjectPerformanceAward entity);
}
