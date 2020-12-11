package project.edge.service.project;

import garage.origin.service.Service;
import project.edge.domain.entity.ProjectPerson;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
public interface ProjectPersonService extends Service<ProjectPerson, String> {
	public void setData(ProjectPerson entity);
	ProjectPerson findByUserId(String userId);
}
