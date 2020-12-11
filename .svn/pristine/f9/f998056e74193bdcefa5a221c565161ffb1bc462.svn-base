package project.edge.dao.process;

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
import project.edge.domain.entity.CompletedInfo;
import project.edge.domain.entity.CompletedInfoAttachment;
import project.edge.domain.entity.ReviewExpert;

/**
 * @author angel_000
 *         [t_completed_info_attachment]对应的DAO。
 */
@Repository
public class CompletedInfoAttachmentDaoImpl
        extends HibernateDaoImpl<CompletedInfoAttachment, String>
        implements CompletedInfoAttachmentDao {
	
	@Override
    public List<CompletedInfoAttachment> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<CompletedInfoAttachment> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }
    
    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("completedInfo", "completedInfo", JoinType.LEFT_OUTER_JOIN)
        		.createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN);
        
        //query.createAlias("completedInfoAttachment", "completedInfoAttachment",
        //        JoinType.LEFT_OUTER_JOIN).createAlias("completedInfoAttachment.archive",
        //                "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many
    }
    
    @Override
    public CompletedInfoAttachment find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        CompletedInfoAttachment completedInfoAttachment = (CompletedInfoAttachment) session().createCriteria(this.type)
        		.createAlias("completedInfo", "ci", JoinType.LEFT_OUTER_JOIN)
                .createAlias("archive", "a", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return completedInfoAttachment;
    }
}
