package project.edge.service.project;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.VirtualOrgDao;
import project.edge.domain.entity.VirtualOrg;

/**
 * @author angel_000
 *         [t_virtual_org]对应的 Service。
 */
@Service
public class VirtualOrgServiceImpl extends GenericServiceImpl<VirtualOrg, String>
        implements VirtualOrgService {

    @Resource
    private VirtualOrgDao virtualOrgDao;

    @Override
    public Dao<VirtualOrg, String> getDefaultDao() {
        return this.virtualOrgDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

	@Override
	@Transactional
	public List<VirtualOrg> findList(String projectId) {
		return this.virtualOrgDao.findList(projectId);
	}
}
