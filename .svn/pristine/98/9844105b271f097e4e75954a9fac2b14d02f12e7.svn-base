/**
 * 
 */
package project.edge.dao.facility;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.Site;


/**
 * @author angel_000
 *         [t_site]对应的DAO。
 */
@Repository
public class SiteDaoImpl extends HibernateDaoImpl<Site, String> implements SiteDao {

	@Override
	public List<Site> list(DataFilter filter, List<OrderByDto> orders,
			PageCtrlDto page) {
		return getList(filter, orders, page);
	}
	
	@Override
	public List<Site> list(DataFilter filter, List<OrderByDto> orders) {
		return getList(filter, orders, null);
	}
	
	@Override
	protected void setListCriteriaAlias(Criteria criteria) {
		
		criteria.createAlias("province", "p", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("city", "c", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("networkRoomOwnerUnitType", "n", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("edgeNodeType", "e", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("personInCharge", "personInCharge", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("siteDividePersonInCharge", "siteDividePersonInCharge", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("programmablePersonInCharge", "programmablePersonInCharge", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("sdnPersonInCharge", "sdnPersonInCharge", JoinType.LEFT_OUTER_JOIN)
//		        .createAlias("networkRoomContact", "networkRoomContact", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
		    	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("basicNetworkGrantor", "basicNetworkGrantor", JoinType.LEFT_OUTER_JOIN)
		        .createAlias("basicNetworkGrantee", "basicNetworkGrantee", JoinType.LEFT_OUTER_JOIN);
	}
	
    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("province", "p", JoinType.LEFT_OUTER_JOIN)
                .createAlias("city", "c", JoinType.LEFT_OUTER_JOIN)
                .createAlias("networkRoomOwnerUnitType", "n", JoinType.LEFT_OUTER_JOIN)
                .createAlias("edgeNodeType", "e", JoinType.LEFT_OUTER_JOIN)
                .createAlias("personInCharge", "personInCharge", JoinType.LEFT_OUTER_JOIN)
                .createAlias("siteDividePersonInCharge", "siteDividePersonInCharge", JoinType.LEFT_OUTER_JOIN)
                .createAlias("programmablePersonInCharge", "programmablePersonInCharge", JoinType.LEFT_OUTER_JOIN)
                .createAlias("sdnPersonInCharge", "sdnPersonInCharge", JoinType.LEFT_OUTER_JOIN)
//                .createAlias("networkRoomContact", "networkRoomContact", JoinType.LEFT_OUTER_JOIN)
                .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
            	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("basicNetworkGrantor", "basicNetworkGrantor", JoinType.LEFT_OUTER_JOIN)
                .createAlias("basicNetworkGrantee", "basicNetworkGrantee", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public Site find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        Site site = (Site) session().createCriteria(this.type)
                .createAlias("province", "p", JoinType.LEFT_OUTER_JOIN)
                .createAlias("city", "c", JoinType.LEFT_OUTER_JOIN)
                .createAlias("networkRoomOwnerUnitType", "n", JoinType.LEFT_OUTER_JOIN)
                .createAlias("edgeNodeType", "e", JoinType.LEFT_OUTER_JOIN)
                .createAlias("personInCharge", "personInCharge", JoinType.LEFT_OUTER_JOIN)
                .createAlias("programmablePersonInCharge", "programmablePersonInCharge", JoinType.LEFT_OUTER_JOIN)
                .createAlias("sdnPersonInCharge", "sdnPersonInCharge", JoinType.LEFT_OUTER_JOIN)
                .createAlias("siteDividePersonInCharge", "siteDividePersonInCharge", JoinType.LEFT_OUTER_JOIN)
//                .createAlias("networkRoomContact", "networkRoomContact", JoinType.LEFT_OUTER_JOIN)
                .createAlias("basicNetworkGrantor", "basicNetworkGrantor", JoinType.LEFT_OUTER_JOIN)
                .createAlias("basicNetworkGrantee", "basicNetworkGrantee", JoinType.LEFT_OUTER_JOIN)
                .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
            	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return site;
    }


}
