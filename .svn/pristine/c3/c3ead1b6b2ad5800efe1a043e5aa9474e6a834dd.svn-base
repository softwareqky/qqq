package project.edge.dao.bidding;

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
import project.edge.domain.entity.BiddingInformationApproval;

/**
 * [t_bidding_information_approval]对应的DAO。
 */
@Repository
public class BiddingInformationApprovalDaoImpl
        extends HibernateDaoImpl<BiddingInformationApproval, String>
        implements BiddingInformationApprovalDao {

    @Override
    public List<BiddingInformationApproval> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<BiddingInformationApproval> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        // 默认是 inner join，这里显示指定left join
        query.createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("constructor", "constructor", JoinType.LEFT_OUTER_JOIN)
                .createAlias("constructorProjectDept", "constructorProjectDept",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("province", "province", JoinType.LEFT_OUTER_JOIN)
                .createAlias("city", "city", JoinType.LEFT_OUTER_JOIN)
                .createAlias("currency", "currency", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectStatus", "projectStatus", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectType", "projectType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("qualityGrade", "qualityGrade", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectCategory", "projectCategory", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectTracker", "projectTracker", JoinType.LEFT_OUTER_JOIN)
                .createAlias("finalClient", "finalClient", JoinType.LEFT_OUTER_JOIN)
                .createAlias("implementProjectDept", "implementProjectDept",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("operator", "operator", JoinType.LEFT_OUTER_JOIN)
                .createAlias("signSubject", "signSubject", JoinType.LEFT_OUTER_JOIN)
                .createAlias("region", "region", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectDept", "projectDept", JoinType.LEFT_OUTER_JOIN)
                .createAlias("contractUnit", "contractUnit", JoinType.LEFT_OUTER_JOIN)
                .createAlias("professionType", "professionType", JoinType.LEFT_OUTER_JOIN);

    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public BiddingInformationApproval find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        BiddingInformationApproval bidApproval = (BiddingInformationApproval) session()
                .createCriteria(this.type)
                .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("constructor", "constructor", JoinType.LEFT_OUTER_JOIN)
                .createAlias("constructorProjectDept", "constructorProjectDept",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("province", "province", JoinType.LEFT_OUTER_JOIN)
                .createAlias("city", "city", JoinType.LEFT_OUTER_JOIN)
                .createAlias("currency", "currency", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectStatus", "projectStatus", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectType", "projectType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("qualityGrade", "qualityGrade", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectCategory", "projectCategory", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectTracker", "projectTracker", JoinType.LEFT_OUTER_JOIN)
                .createAlias("finalClient", "finalClient", JoinType.LEFT_OUTER_JOIN)
                .createAlias("implementProjectDept", "implementProjectDept",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("operator", "operator", JoinType.LEFT_OUTER_JOIN)
                .createAlias("signSubject", "signSubject", JoinType.LEFT_OUTER_JOIN)
                .createAlias("region", "region", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectDept", "projectDept", JoinType.LEFT_OUTER_JOIN)
                .createAlias("contractUnit", "contractUnit", JoinType.LEFT_OUTER_JOIN)
                .createAlias("professionType", "professionType", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return bidApproval;
    }
}
