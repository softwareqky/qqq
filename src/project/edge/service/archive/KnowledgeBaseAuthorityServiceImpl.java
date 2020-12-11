/**
 * 
 */
package project.edge.service.archive;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.archive.KnowledgeBaseAuthorityDao;
import project.edge.domain.entity.KnowledgeBaseAuthority;


/**
 * @author angel_000
 *
 */
@Service
public class KnowledgeBaseAuthorityServiceImpl
        extends GenericServiceImpl<KnowledgeBaseAuthority, String>
        implements KnowledgeBaseAuthorityService {

    @Resource
    private KnowledgeBaseAuthorityDao knowledgeBaseAuthorityDao;

    @Override
    public Dao<KnowledgeBaseAuthority, String> getDefaultDao() {
        return this.knowledgeBaseAuthorityDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("accountType", false);
    }

}
