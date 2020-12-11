package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.DefinedSiteIconDao;
import project.edge.domain.entity.DefinedSiteIcon;

/**
 * @author angel_000
 *         [t_defined_site_icon]对应的 Service。
 */
@Service
public class DefinedSiteIconServiceImpl extends GenericServiceImpl<DefinedSiteIcon, String>
        implements DefinedSiteIconService {

    @Resource
    private DefinedSiteIconDao definedSiteIconDao;

    @Override
    public Dao<DefinedSiteIcon, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.definedSiteIconDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("mDatetime", false);
    }
}
