package project.edge.dao.budget;

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
import project.edge.domain.entity.BudgetTemplate;

/**
 * @author angel_000
 *         [t_project_person]对应的DAO。
 */
@Repository
public class BudgetTemplateDaoImpl extends HibernateDaoImpl<BudgetTemplate, String>
        implements BudgetTemplateDao {
	
    @Override
    public List<BudgetTemplate> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<BudgetTemplate> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
        .createAlias("group", "gp", JoinType.LEFT_OUTER_JOIN)
        .createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN);

        query.createAlias("archives", "archives", JoinType.LEFT_OUTER_JOIN)
                .createAlias("archives.archive", "archive", JoinType.LEFT_OUTER_JOIN); // one-to-many

    }
    

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public BudgetTemplate find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        
        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        BudgetTemplate budgetTemplate = (BudgetTemplate) session().createCriteria(this.type)
                .createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("archives", "archives", JoinType.LEFT_OUTER_JOIN)
                .createAlias("archives.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("group", "gp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();

        return budgetTemplate;
    }

}
