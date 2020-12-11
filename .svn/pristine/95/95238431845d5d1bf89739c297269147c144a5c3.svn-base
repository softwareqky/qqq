/**
 * 
 */
package project.edge.dao.archive;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.KnowledgeBase;
import project.edge.domain.filter.ArchiveFilter;


/**
 * @author angel_000
 *
 */
@Repository
public class KnowledgeBaseDaoImpl extends HibernateDaoImpl<KnowledgeBase, String>
        implements KnowledgeBaseDao {
    
    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria = super.prepareConditions(criteria, filter);

        if (filter == null) {
            return criteria;
        }

        ArchiveFilter f = null;
        if (filter instanceof ArchiveFilter) {
            f = (ArchiveFilter) filter;
        }

        if (f == null) {
            return criteria;
        }

        // 对文件名或备注做模糊查找
        String fileInfo = f.getFileInfo();
        if (!StringUtils.isEmpty(fileInfo)) {
            criteria.add(Restrictions.disjunction(
                    Restrictions.like("archiveName", fileInfo, MatchMode.ANYWHERE),
                    Restrictions.like("remark", fileInfo, MatchMode.ANYWHERE)));
        }

        // 查找所有下属节点
        String pidPath = f.getPidPath();
        if (!StringUtils.isEmpty(pidPath)) {
            criteria.add(Restrictions.like("path", pidPath, MatchMode.ANYWHERE));
        }

        return criteria;
    }

    @Override
    public void deleteSub(Collection<String> pidList) {

        if (pidList == null || pidList.size() == 0) {
            return;
        }

        for (String pid : pidList) {
            session().createQuery("delete from Archive where path like :pid")
                    .setParameter("pid", "%" + pid + "%").executeUpdate();
        }
    }

}
