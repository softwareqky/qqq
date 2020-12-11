/**
 * 
 */
package project.edge.service.org;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.org.OrgDao;
import project.edge.domain.entity.Org;


/**
 * @author angel_000
 *         [t_org]对应的 Service。
 */
@Service
public class OrgServiceImpl extends GenericServiceImpl<Org, String> implements OrgService {

    @Resource
    private OrgDao orgDao;

    @Override
    public Dao<Org, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.orgDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("orgName", false);
    }
}
