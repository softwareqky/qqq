package project.edge.service.archive;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.archive.ArchiveAuthorityDao;
import project.edge.dao.archive.ArchiveDao;
import project.edge.domain.entity.Archive;

/**
 * [t_archive]对应的 Service。
 */
@Service
public class ArchiveServiceImpl extends GenericServiceImpl<Archive, String>
        implements ArchiveService {

    @Resource
    private ArchiveDao archiveDao;

    @Resource
    private ArchiveAuthorityDao archiveAuthorityDao;

    @Override
    public Dao<Archive, String> getDefaultDao() {
        return this.archiveDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("archiveName", false);
    }

    @Override
    @Transactional
    public void deleteSub(Collection<String> pidList) {
        this.archiveDao.deleteSub(pidList);
    }

    @Override
    @Transactional
    public void updatePreviewVersion(Archive a) {
        this.archiveDao.updatePreviewVersion(a);
    }

    @Override
    @Transactional
    public void createAndUpdateVersionOf(Collection<Archive> entities,
            Collection<SimpleEntry<String, String>> pairList) {

        this.archiveDao.create(entities);
        this.archiveDao.updateVersionOf(pairList);
        this.archiveAuthorityDao.updateArchiveId(pairList);
    }


}
