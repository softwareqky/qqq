package project.edge.dao.flowable;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.FlowableSetting;

/**
 * [t_flowable_setting]对应的DAO。
 */
@Repository
public class FlowableSettingDaoImpl extends HibernateDaoImpl<FlowableSetting, String>
        implements FlowableSettingDao {


    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("projectRoles", "prs", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    @Override
    public FlowableSetting find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        FlowableSetting flowableSetting = (FlowableSetting) session().createCriteria(this.type)
                .createAlias("projectRoles", "prs", JoinType.LEFT_OUTER_JOIN)
                .createAlias("page", "p", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("id", id))
                .uniqueResult();

        return flowableSetting;
    }

    /**
     * 获取分页列表。
     * 
     * @param filter 过滤条件
     * @param orders 排序
     * @param page 分页DTO
     * @return 满足过滤条件的分页后的对象列表
     */
    @Override
    public List<FlowableSetting> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {

        query.createAlias("projectRoles", "prs", JoinType.LEFT_OUTER_JOIN);
        query.createAlias("page", "p", JoinType.LEFT_OUTER_JOIN);
    }

    /**
     * 获取列表总数。
     * 
     * @param filter 过滤条件
     * @return 满足过滤条件的数据量
     */
    @Override
    public long count(DataFilter filter) {

        DetachedCriteria query = prepareConditions(DetachedCriteria.forClass(this.type), filter);

        // 查询distinct id行
        long totalCount = ((Long) query.setProjection(Projections.countDistinct("id"))
                .getExecutableCriteria(session()).uniqueResult()).longValue();
        return totalCount;
    }

    @Override
    public void updateFlowableStatus(String businessEntity, String correlateDataId,
            Integer flowStatus) {

        String sqlFormat = "update %1$s set %2$s=:flowStatus where id=:correlateDataId";

        String sql = "";
        if ("Project".equals(businessEntity)) {
            sql = String.format(sqlFormat, businessEntity, "flowStatusProject");
        } else {
            sql = String.format(sqlFormat, businessEntity, "flowStatus");
        }

        session().createQuery(sql).setInteger("flowStatus", flowStatus)
                .setString("correlateDataId", correlateDataId).executeUpdate();
    }
}
