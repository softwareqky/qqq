package project.edge.dao.system;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.DefinedSiteIcon;

/**
 * @author angel_000
 *         [t_defined_site_icon]对应的DAO。
 */
@Repository
public class DefinedSiteIconDaoImpl extends HibernateDaoImpl<DefinedSiteIcon, String>
        implements DefinedSiteIconDao {

}
