package project.edge.dao.contract;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.ContractCategory;

/**
 * [t_contract_category]对应的DAO。
 */
@Repository
public class ContractCategoryDaoImpl extends HibernateDaoImpl<ContractCategory, String>
        implements ContractCategoryDao {

    @Override
    public List<ContractCategory> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<ContractCategory> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }


}
