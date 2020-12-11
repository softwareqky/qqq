/**
 * 
 */
package project.edge.service.project;

import garage.origin.service.Service;
import project.edge.domain.entity.ProjectChange;


/**
 * @author angel_000
 *         [t_project_change]对应的Service。
 */
public interface ProjectChangeService extends Service<ProjectChange, String> {
	public void setData(ProjectChange entity);
}
