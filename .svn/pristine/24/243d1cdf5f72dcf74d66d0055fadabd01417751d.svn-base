package project.edge.dao.archive;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.PaperLibraryLendHistory;

@Repository
public class PaperLibraryLendHistoryDaoImpl extends HibernateDaoImpl<PaperLibraryLendHistory, String>
		implements PaperLibraryLendHistoryDao {
	@Override
    public List<PaperLibraryLendHistory> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<PaperLibraryLendHistory> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }
    
    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("paperLibrary", "pl", JoinType.LEFT_OUTER_JOIN);
        query.createAlias("lendPerson", "lp", JoinType.LEFT_OUTER_JOIN);
        query.createAlias("getPerson", "gp", JoinType.LEFT_OUTER_JOIN);
        query.createAlias("returnPerson", "rp", JoinType.LEFT_OUTER_JOIN);
    }
    
    @Override
    public PaperLibraryLendHistory find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        PaperLibraryLendHistory paperLibraryLendHistory = (PaperLibraryLendHistory) session().createCriteria(this.type)
            .createAlias("paperLibrary", "pl", JoinType.LEFT_OUTER_JOIN)
            .createAlias("lendPerson", "lp", JoinType.LEFT_OUTER_JOIN)
            .createAlias("getPerson", "gp", JoinType.LEFT_OUTER_JOIN)
            .createAlias("returnPerson", "rp", JoinType.LEFT_OUTER_JOIN)
            .add(Restrictions.eq("id", id)).uniqueResult();
        return paperLibraryLendHistory;
    }
}
