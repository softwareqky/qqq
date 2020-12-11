package project.edge.dao.quality;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.QualitySpecification;

/**
 * @author angel_000
 *         [t_quality_specification]对应的DAO。
 */
@Repository
public class QualitySpecificationDaoImpl extends HibernateDaoImpl<QualitySpecification, String>
        implements QualitySpecificationDao {
	
	@Override
    public List<QualitySpecification> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<QualitySpecification> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }
	
	@Override
	protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("qualitySpecificationAttachments", "qualitySpecificationAttachments", JoinType.LEFT_OUTER_JOIN)
        	.createAlias("qualitySpecificationAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many
	}
	
	@Override
	public QualitySpecification find(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		
		QualitySpecification qualitySpecification = 
				(QualitySpecification) session().createCriteria(this.type)
					.createAlias("qualitySpecificationAttachments", "qualitySpecificationAttachments", JoinType.LEFT_OUTER_JOIN)
					.createAlias("qualitySpecificationAttachments.archive", "archives",
                        JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("id", id)).uniqueResult();
		return qualitySpecification;
	}

}
