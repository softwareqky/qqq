/**
 * 
 */
package project.edge.dao.archive;

import java.util.Collection;

import garage.origin.dao.Dao;
import project.edge.domain.entity.KnowledgeBase;


/**
 * @author angel_000
 *
 */
public interface KnowledgeBaseDao extends Dao<KnowledgeBase, String> {

    /**
     * 根据父节点的id，级联删除所有下级的文件(夹)。
     * 
     * @param pidList
     * @return
     */
    void deleteSub(Collection<String> pidList);
    
}
