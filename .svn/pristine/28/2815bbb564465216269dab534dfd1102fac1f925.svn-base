package project.edge.dao.budget;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.entity.BudgetMainfee;

@Repository
public class BudgetFeeDaoImpl extends HibernateDaoImpl<BudgetFee, String> implements BudgetFeeDao {
	@Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("mainfee", "mainfee", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public BudgetFee find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        BudgetFee c = (BudgetFee) session().createCriteria(this.type)
                .createAlias("mainfee", "mainfee", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return c;
    }
    
	@Override
	public List<Object> getBudgetFeeList(String startDate, String endDate) {
		String selectSql = String.format("select bh,sum(bxjey),sum(sl) from t_budget_fee where c_datetime>:startDate and c_datetime <:endDate group by bh");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).setParameter("startDate", startDate).setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Object> list = createQuery.list();

        return list;
	}
	
    /**
     * 查询某个时间段，各站点每个编号的费用总和
     */
	
	@Override
	public List<Object> getFeeBySite(String startDate, String endDate) {
		String selectSql = String.format("select zdmc,bh,SUM(sl),SUM(bxjey) from t_budget_fee where c_datetime>=:startDate and c_datetime <=:endDate group by zdmc,bh order by zdmc");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).setParameter("startDate", startDate).setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Object> list = createQuery.list();

        return list;
	}
	
    /**
     * 查询有报销记录的站点名称
     */
	
	@Override
	public List<Object> getSiteNameList(String startDate, String endDate) {
		String selectSql = String.format("select zdmc from t_budget_fee where zdmc!='无' and zdmc!='' and c_datetime>=:startDate and c_datetime <=:endDate group by zdmc order by zdmc");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).setParameter("startDate", startDate).setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Object> list = createQuery.list();

        return list;
	}
	
    /**
     * 查询五年中每年每个编号的费用总和
     */
	
	@Override
	public List<Object> getFeeByYearAndCode(String year) {
		String selectSql = String.format("SELECT bh,SUM(bxjey),count(id) FROM `t_budget_fee` where  FIND_IN_SET(SUBSTRING(c_datetime, 1, 4),:year) GROUP BY bh");

        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).setParameter("year", year);

        @SuppressWarnings("unchecked")
        List<Object> list = createQuery.list();

        return list;
	}
}
