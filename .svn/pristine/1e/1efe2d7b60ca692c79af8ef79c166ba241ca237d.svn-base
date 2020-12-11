package project.edge.service.facility;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.LinkAuthorityDao;
import project.edge.domain.entity.LinkAuthority;

/**
 * @author angel_000
 *         [t_link_authority]对应的 Service。
 */
@Service
public class LinkAuthorityServiceImpl extends GenericServiceImpl<LinkAuthority, String>
        implements LinkAuthorityService {

    @Resource
    private LinkAuthorityDao linkAuthorityDao;

    @Override
    public Dao<LinkAuthority, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.linkAuthorityDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
