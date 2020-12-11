package project.edge.dao.archive;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.ArchiveAuthority;

/**
 * [t_archive_authority]对应的DAO。
 */
@Repository
public class ArchiveAuthorityDaoImpl extends HibernateDaoImpl<ArchiveAuthority, String>
        implements ArchiveAuthorityDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person", "person", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person.org", "porg", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person.user", "puser", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person.dept", "pdept", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public ArchiveAuthority find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ArchiveAuthority auth = (ArchiveAuthority) session().createCriteria(this.type)
                .createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person", "person", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person.org", "porg", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person.user", "puser", JoinType.LEFT_OUTER_JOIN)
                .createAlias("person.dept", "pdept", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return auth;
    }

    @Override
    public void updateArchiveId(Collection<SimpleEntry<String, String>> pairList) {

        if (pairList.isEmpty()) {
            return;
        }

        Query q = session().createQuery(
                "update ArchiveAuthority set archive.id=:newId where archive.id=:oldId");

        for (SimpleEntry<String, String> pair : pairList) {
            q.setParameter("oldId", pair.getKey()).setParameter("newId", pair.getValue())
                    .executeUpdate();
        }
    }

}
