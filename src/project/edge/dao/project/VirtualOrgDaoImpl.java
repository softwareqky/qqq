package project.edge.dao.project;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.VirtualOrg;

/**
 * @author angel_000
 *         [t_virtual_org]对应的DAO。
 */
@Repository
public class VirtualOrgDaoImpl extends HibernateDaoImpl<VirtualOrg, String>
        implements VirtualOrgDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN).createAlias("leader", "leader", JoinType.LEFT_OUTER_JOIN).createAlias("pid",
                "pid", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public VirtualOrg find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        VirtualOrg virtualOrg = (VirtualOrg) session().createCriteria(this.type)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN).createAlias("leader", "leader", JoinType.LEFT_OUTER_JOIN)
                .createAlias("pid", "pid", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("id", id))
                .uniqueResult();

        return virtualOrg;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<VirtualOrg> findList(String projectId) {
		SQLQuery createQuery = (SQLQuery) session().createSQLQuery("select * from `t_virtual_org` where is_echarts_show<>0 and project_id=:projectId").addEntity(VirtualOrg.class)
        		.setString("projectId", projectId);
		return createQuery.list();
	}

}
