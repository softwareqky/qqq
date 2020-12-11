/**
 * 
 */
package project.edge.service.archive;

import java.util.Collection;

import garage.origin.service.Service;
import project.edge.domain.entity.KnowledgeBase;


/**
 * @author angel_000
 *
 */
public interface KnowledgeBaseService extends Service<KnowledgeBase, String> {

    /**
     * 根据父节点的id，级联删除所有下级的文件(夹)。
     * 
     * @param pidList
     * @return
     */
    void deleteSub(Collection<String> pidList);

}
