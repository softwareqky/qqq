package project.edge.dao.facility;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.LinkAuthority;

/**
 * @author angel_000
 *         [t_link_authority]对应的DAO。
 */
@Repository
public class LinkAuthorityDaoImpl extends HibernateDaoImpl<LinkAuthority, String>
        implements LinkAuthorityDao {

}
