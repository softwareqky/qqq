package project.edge.service.archive;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.archive.ArchiveAuthorityDao;
import project.edge.domain.entity.ArchiveAuthority;

/**
 * [t_archive_authority]对应的 Service。
 */
@Service
public class ArchiveAuthorityServiceImpl extends GenericServiceImpl<ArchiveAuthority, String>
        implements ArchiveAuthorityService {

    @Resource
    private ArchiveAuthorityDao archiveAuthorityDao;

    @Override
    public Dao<ArchiveAuthority, String> getDefaultDao() {
        return this.archiveAuthorityDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        // 字段名与ArchiveAuthorityDaoImpl.prepareConditions中createAlias的别名一致
        return new OrderByDto("person.personName", false);
    }
}
