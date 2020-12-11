/**
 * 
 */
package project.edge.dao.budget;

import java.util.List;
import java.util.Locale;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.BudgetEstimate;


/**
 * @author wwy
 *         [t_budget_estimate]对应的DAO。
 */
@Repository
public class BudgetEstimateDaoImpl extends HibernateDaoImpl<BudgetEstimate, String>
        implements BudgetEstimateDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
        .createAlias("group", "gp", JoinType.LEFT_OUTER_JOIN)
        .createAlias("budgetType", "budgetType", JoinType.LEFT_OUTER_JOIN)
        .createAlias("budgetEstimateMain", "budgetEstimateMain", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public BudgetEstimate find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        BudgetEstimate b = (BudgetEstimate) session().createCriteria(this.type)
        		.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("group", "gp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("budgetType", "budgetType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("budgetEstimateMain", "budgetEstimateMain", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return b;
    }

	@Override
	public void deleteByCode(String code, Locale locale) {
		session().createQuery(String.format("delete from BudgetEstimate where code = :code"))
        .setParameter("code", code).executeUpdate();
	}
	
    /**
     * 年度使用计划
     * 根据年份和项目id获取当年最新版本的预算表
     */
	@Override
	public List<BudgetEstimate> getBudgetByYear(int year,String project) {
		String selectSql = String.format("SELECT budget.* from (SELECT id,year,version,c_datetime as maxDate FROM t_budget_estimate_version \r\n" + 
				"WHERE year=:year and project_id=:project ORDER BY c_datetime desc limit 1) version  \r\n" + 
				"LEFT JOIN t_budget_estimate budget on version.id=budget.version order by budget.code asc");
		
        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(BudgetEstimate.class).setParameter("year", year).setParameter("project", project);

        @SuppressWarnings("unchecked")
        List<BudgetEstimate> list = createQuery.list();

        return list;
	}

    /**
     * 根据版本查询预算
     */
	@Override
	public List<BudgetEstimate> getBudgetByVersion(String version) {
		String selectSql = String.format("SELECT * from `t_budget_estimate` WHERE version=:version order by code asc");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(BudgetEstimate.class).setParameter("version", version);

        @SuppressWarnings("unchecked")
        List<BudgetEstimate> list = createQuery.list();

        return list;
	}
	
	@Override
	public void updateCapitalPlan(String projectId,String code,String versionId,String planRemark,int year) {
		String sql = "UPDATE t_budget_estimate SET plan_remark="+planRemark+" WHERE year='"+year+"' AND project_id='"+projectId+"' AND code='"+code+"' AND (version='"+versionId+"' OR version='V0.0')";  
		SQLQuery  q = (SQLQuery) session().createSQLQuery(sql);  
		q.executeUpdate(); 
	}
}
