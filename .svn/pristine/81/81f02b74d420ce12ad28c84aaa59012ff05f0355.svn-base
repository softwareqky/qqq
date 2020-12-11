/**
 * 
 */
package project.edge.service.facility;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.SegmentAuthorityDao;
import project.edge.domain.entity.SegmentAuthority;


/**
 * @author angel_000
 *         [t_segment_authority]对应的 Service。
 */
@Service
public class SegmentAuthorityServiceImpl extends GenericServiceImpl<SegmentAuthority, String>
        implements SegmentAuthorityService {

    @Resource
    private SegmentAuthorityDao segmentAuthorityDao;

    @Override
    public Dao<SegmentAuthority, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.segmentAuthorityDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
