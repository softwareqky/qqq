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
import project.edge.domain.entity.PaperLibrary;

/**
 * @author wd983
 *		[t_paper_library]对应DAO。
 */
@Repository
public class PaperLibraryDaoImpl extends HibernateDaoImpl<PaperLibrary, String>
		implements PaperLibraryDao {
	
	@Override
    public List<PaperLibrary> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<PaperLibrary> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }
    
    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN);
    }
    
    @Override
    public PaperLibrary find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        PaperLibrary paperLibrary = (PaperLibrary) session().createCriteria(this.type)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return paperLibrary;
    }
}
