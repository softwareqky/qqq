/**
 * 
 */
package project.edge.dao.facility;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.SiteAuthority;


/**
 * @author angel_000
 *       [t_site_authority]对应的DAO。
 */
@Repository
public class SiteAuthorityDaoImpl extends HibernateDaoImpl<SiteAuthority, String>
        implements SiteAuthorityDao {

}
