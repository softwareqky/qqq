package project.edge.dao.archive;

import java.util.Collection;
import java.util.AbstractMap.SimpleEntry;

import garage.origin.dao.Dao;
import project.edge.domain.entity.ArchiveAuthority;

/**
 * @author angel_000
 *         [t_archive_authority]对应的DAO。
 */
public interface ArchiveAuthorityDao extends Dao<ArchiveAuthority, String> {

    /**
     * 更新档案文件的关联。
     * 
     * @param pairList old id : new id
     */
    void updateArchiveId(Collection<SimpleEntry<String, String>> pairList);
}
