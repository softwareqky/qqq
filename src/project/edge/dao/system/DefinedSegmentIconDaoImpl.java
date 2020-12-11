package project.edge.dao.system;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.DefinedSegmentIcon;

/**
 * @author angel_000
 *         [t_defined_segment_icon]对应的DAO。
 */
@Repository
public class DefinedSegmentIconDaoImpl extends HibernateDaoImpl<DefinedSegmentIcon, String>
        implements DefinedSegmentIconDao {

}
