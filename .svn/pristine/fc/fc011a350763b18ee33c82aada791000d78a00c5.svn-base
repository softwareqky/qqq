package project.edge.dao.schedule;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.PlanTask;

/**
 * [t_plan_task]对应的DAO。
 */
@Repository
public class PlanTaskDaoImpl extends HibernateDaoImpl<PlanTask, String> implements PlanTaskDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
        		.createAlias("plan.project", "pro", JoinType.LEFT_OUTER_JOIN)
        		.createAlias("plan.virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site", "ps", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site.province", "psp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site.city", "psc", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site.edgeNodeType", "pse", JoinType.LEFT_OUTER_JOIN)
                .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
            	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("leader", "le", JoinType.LEFT_OUTER_JOIN)
                .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public PlanTask find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        PlanTask planTask = (PlanTask) session().createCriteria(this.type)
                .createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site", "ps", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site.province", "psp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site.city", "psc", JoinType.LEFT_OUTER_JOIN)
                .createAlias("plan.site.edgeNodeType", "pse", JoinType.LEFT_OUTER_JOIN)
                .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
            	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("leader", "le", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return planTask;
    }

    @Override
    public void deleteTasksWithPlanId(String planId) {
        session().createQuery("delete from PlanTask where plan.id = :planId")
                .setString("planId", planId).executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<PlanTask> findOverdueList() {
    	
    	Criteria criteria = session().createCriteria(this.type)
    			.createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
    			.createAlias("leader", "le", JoinType.LEFT_OUTER_JOIN)
    			.createAlias("leader.user", "lee", JoinType.LEFT_OUTER_JOIN);
    	
    	criteria.add(Restrictions.isNull("actualEndDate"));
    	criteria.add(Restrictions.lt("planEndDate", new Date()));
    	
    	return criteria.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanTask> findList(String projectId, String virtualOrgId, String nowDate) {
		
		String startDate = nowDate + "-01-01 00:00:00";
		String endDate = nowDate + "-12-31 23:59:59";
		
		String sql = "";
		
		if (virtualOrgId != null && !"".equals(virtualOrgId)) {
			sql = " select t.* from `t_plan_task` t "
					+ " left outer join `t_plan` p on t.plan_id=p.id "
					+ " left outer join `t_virtual_org` o on o.id=p.virtual_org_id "
					+ " where o.is_echarts_show<>0 and o.project_id=:projectId "
					+ " and p.virtual_org_id=:virtualOrgId "
					+ " and t.plan_end_date>=:startDate and t.plan_end_date<=:endDate ";
		} else {
			sql = " select t.* from `t_plan_task` t "
					+ " left outer join `t_plan` p on t.plan_id = p.id "
					+ " where p.project_id=:projectId "
					+ " and t.plan_end_date>=:startDate and t.plan_end_date<=:endDate ";
		}
		
		String selectSql = String.format(sql);

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(PlanTask.class)
        		.setString("projectId", projectId)
        		.setString("startDate", startDate)
        		.setString("endDate", endDate);
        
        if (virtualOrgId != null && !"".contentEquals(virtualOrgId)) {
        	createQuery.setString("virtualOrgId", virtualOrgId);
        }
        return createQuery.list();
	}

}
