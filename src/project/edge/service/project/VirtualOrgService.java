package project.edge.service.project;

import java.util.List;

import garage.origin.service.Service;
import project.edge.domain.entity.VirtualOrg;

/**
 * @author angel_000
 *         [t_virtual_org]对应的 Service。
 */
public interface VirtualOrgService extends Service<VirtualOrg, String> {
	List<VirtualOrg> findList (String projectId);
}
