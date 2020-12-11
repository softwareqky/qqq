package project.edge.service.archive;

import java.util.Collection;
import java.util.AbstractMap.SimpleEntry;

import garage.origin.service.Service;
import project.edge.domain.entity.Archive;

/**
 * @author angel_000
 *         [t_archive]对应的 Service。
 */
public interface ArchiveService extends Service<Archive, String> {

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
     * 批量创建档案文件，并更新原有记录的"历史版本源自"字段，以及相关的档案权限。
     * 
     * @param entities 新增的档案文件
     * @param pairList old id : new id
     */
    void createAndUpdateVersionOf(Collection<Archive> entities,
            Collection<SimpleEntry<String, String>> pairList);
}
