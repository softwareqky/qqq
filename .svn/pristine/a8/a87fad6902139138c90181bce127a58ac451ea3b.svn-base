/**
 * 
 */
package project.edge.service.facility;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.SegmentDao;
import project.edge.domain.entity.Segment;


/**
 * @author angel_000
 *         [t_segment]对应的 Service。
 */
@Service
public class SegmentServiceImpl extends GenericServiceImpl<Segment, String>
        implements SegmentService {

    @Resource
    private SegmentDao segmentDao;

    @Override
    public Dao<Segment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.segmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
