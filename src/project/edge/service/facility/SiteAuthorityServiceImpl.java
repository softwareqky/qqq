/**
 * 
 */
package project.edge.service.facility;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.SiteAuthorityDao;
import project.edge.domain.entity.SiteAuthority;


/**
 * @author angel_000
 *         [t_site_authority]对应的 Service。
 */
@Service
public class SiteAuthorityServiceImpl extends GenericServiceImpl<SiteAuthority, String>
        implements SiteAuthorityService {

    @Resource
    private SiteAuthorityDao siteAuthorityDao;


    @Override
    public Dao<SiteAuthority, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.siteAuthorityDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
