package project.edge.dao.facility;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.FacilityHistory;

@Repository
public class FacilityHistoryDaoImpl extends HibernateDaoImpl<FacilityHistory, String> implements FacilityHistoryDao {

	@Override
	public List<FacilityHistory> list(DataFilter filter, List<OrderByDto> orders,
			PageCtrlDto page) {
		return getList(filter, orders, page);
	}
	
	@Override
	public List<FacilityHistory> list(DataFilter filter, List<OrderByDto> orders) {
		return getList(filter, orders, null);
	}
	
	@Override
	protected void setListCriteriaAlias(Criteria criteria) {
		criteria.createAlias("createBy", "createBy", JoinType.LEFT_OUTER_JOIN);
	}
	
    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {
    	criteria.createAlias("createBy", "createBy", JoinType.LEFT_OUTER_JOIN);
        return super.prepareConditions(criteria, filter);
    }
}
