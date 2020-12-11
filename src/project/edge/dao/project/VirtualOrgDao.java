package project.edge.dao.project;

import java.util.List;

import garage.origin.dao.Dao;
import project.edge.domain.entity.VirtualOrg;

/**
 * @author angel_000
 *         [t_virtual_org]对应的DAO。
 */
public interface VirtualOrgDao extends Dao<VirtualOrg, String> {

	List<VirtualOrg> findList (String projectId);
}
