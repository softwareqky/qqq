package project.edge.dao.archive;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

import garage.origin.dao.Dao;
import project.edge.domain.entity.Archive;

/**
 * [t_archive]对应的DAO。
 */
public interface ArchiveDao extends Dao<Archive, String> {

    /**
     * 根据父节点的id，级联删除所有下级的文件(夹)。
     * 
     * @param pidList
     * @return
     */
    void deleteSub(Collection<String> pidList);

    /**
     * 更新档案文件的预览版本字段。
     * 
     * @param a
     */
    void updatePreviewVersion(Archive a);

    /**
     * 更新原有记录的"历史版本源自"字段。
     * 
     * @param pairList old id : new id
     */
    void updateVersionOf(Collection<SimpleEntry<String, String>> pairList);
}
