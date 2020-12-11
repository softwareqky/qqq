package project.edge.dao.schedule;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.PlanTaskFavorite;

@Repository
public class PlanTaskFavoriteDaoImpl extends HibernateDaoImpl<PlanTaskFavorite, String> implements PlanTaskFavoriteDao {

	@Override
	protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

		criteria.createAlias("planTask", "task", JoinType.LEFT_OUTER_JOIN).createAlias("planTask.plan", "plan",
				JoinType.LEFT_OUTER_JOIN);

		return super.prepareConditions(criteria, filter);
	}
}