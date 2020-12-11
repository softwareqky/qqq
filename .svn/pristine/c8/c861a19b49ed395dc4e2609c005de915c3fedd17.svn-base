package project.edge.dao.budget;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000
 *         [t_project_person]对应的DAO。
 */
@Repository
public class BudgetEstimateVersionDaoImpl extends HibernateDaoImpl<BudgetEstimateVersion, String>
        implements BudgetEstimateVersionDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
        .createAlias("group", "gp", JoinType.LEFT_OUTER_JOIN)
        .createAlias("budgetMain", "budgetMain", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public BudgetEstimateVersion find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        BudgetEstimateVersion budgetEstimateVersion = (BudgetEstimateVersion) session().createCriteria(this.type)
                .createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("group", "gp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("budgetMain", "budgetMain", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return budgetEstimateVersion;
    }
    
    /**
     * 根据年份和项目获取所有版本
     */
	@Override
	public List<BudgetEstimateVersion> getVersionByYear(int year,String project) {
		String selectSql = String.format("SELECT * from `t_budget_estimate_version` WHERE year=:year and project_id=:project");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(BudgetEstimateVersion.class).setParameter("year", year).setParameter("project", project);

        @SuppressWarnings("unchecked")
        List<BudgetEstimateVersion> list = createQuery.list();

        return list;
	}

    /**
     * 根据年份获取计划所有版本
     */
	@Override
	public List<Plan> getPlanVersionByYear(int year,String project) {
		String selectSql = String.format("select id,plan_version from t_plan where combine_type=1 and plan_year=:year and project_id=:project \r\n" + 
				"union (select history.id,history.plan_version from t_plan_history history LEFT JOIN (select * from t_plan where combine_type=1 and plan_year=:year and project_id=:project) plan on plan.id=history.plan_id)");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(Plan.class).setParameter("year", year).setParameter("project", project);

        @SuppressWarnings("unchecked")
        List<Plan> list = createQuery.list();

        return list;
	}
	
    /**
     * 根据计划id获取该版本下所有任务
     */
	@Override
	public List<PlanTask> getTaskByPlanId(String planId) {
		String selectSql = String.format("SELECT id,task_name FROM `t_plan_task` where plan_id=:planId and task_layer=1");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(PlanTask.class).setParameter("planId", planId);

        @SuppressWarnings("unchecked")
        List<PlanTask> list = createQuery.list();

        return list;
	}
	
    /**
     * 根据任务的创建时间，项目组关键字获取预算金额
     */
	@Override
	public List<BudgetEstimate> getbudgetByGroup(Date cDatetime, String groupId) {
		String selectSql = String.format("select * from t_budget_estimate \r\n" + 
				"where group_id=:groupId and c_datetime<=:cDatetime and CHAR_LENGTH(inner_code)=4 and version!=\"V0.0\" order by c_datetime desc");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(BudgetEstimate.class).setParameter("cDatetime", cDatetime).setParameter("groupId", groupId);

        @SuppressWarnings("unchecked")
        List<BudgetEstimate> list = createQuery.list();

        return list;
	}
	
    /**
     * 根据查找的年份中最新的版本id，查询内部编码是4位的各专业组的预算金额
     */
	@Override
	public List<BudgetEstimate> getLastBudgetByGroup(String year,String projectId, String groupId) {
		String selectSql = String.format("select * from t_budget_estimate \r\n" + 
				"where group_id=:groupId and level=3 and version=(SELECT a.id FROM t_budget_estimate_version a left join t_budget_estimate_main b on a.main_id=b.id \r\n" + 
				"where a.year=:year and a.project_id=:projectId and b.flow_status=2 ORDER BY a.c_datetime desc limit 1)");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(BudgetEstimate.class).setParameter("year", year).setParameter("groupId", groupId).setParameter("projectId", projectId);

        @SuppressWarnings("unchecked")
        List<BudgetEstimate> list = createQuery.list();

        return list;
	}
}
