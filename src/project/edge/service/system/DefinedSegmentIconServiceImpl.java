package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.DefinedSegmentIconDao;
import project.edge.domain.entity.DefinedSegmentIcon;

/**
 * @author angel_000
 *         [t_defined_segment_icon]对应的 Service。
 */
@Service
public class DefinedSegmentIconServiceImpl extends GenericServiceImpl<DefinedSegmentIcon, String>
        implements DefinedSegmentIconService {

    @Resource
    private DefinedSegmentIconDao definedSegmentIconDao;

    @Override
    public Dao<DefinedSegmentIcon, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.definedSegmentIconDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("mDatetime", false);
    }
}
