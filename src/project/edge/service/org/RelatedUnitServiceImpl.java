/**
 * 
 */
package project.edge.service.org;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.org.RelatedUnitDao;
import project.edge.domain.entity.RelatedUnit;


/**
 * @author angel_000
 *         [t_related_unit]对应的 Service。
 */
@Service
public class RelatedUnitServiceImpl extends GenericServiceImpl<RelatedUnit, String>
        implements RelatedUnitService {

    @Resource
    private RelatedUnitDao relatedUnitDao;

    @Override
    public Dao<RelatedUnit, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.relatedUnitDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("unitName", false);
    }
}
