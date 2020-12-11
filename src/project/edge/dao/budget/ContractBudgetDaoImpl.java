/**
 * 
 */
package project.edge.dao.budget;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.ContractBudget;


/**
 * @author wwy
 *         [t_contract_budget]对应的DAO。
 */
@Repository
public class ContractBudgetDaoImpl extends HibernateDaoImpl<ContractBudget, String>
        implements ContractBudgetDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public ContractBudget find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ContractBudget b = (ContractBudget) session().createCriteria(this.type)
        		.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return b;
    }

}
