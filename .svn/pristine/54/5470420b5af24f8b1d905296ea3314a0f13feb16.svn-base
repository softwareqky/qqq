package project.edge.dao.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.DataFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.ViewBean;
import project.edge.domain.converter.PlanBeanConverter;
import project.edge.domain.entity.Plan;

/**
 * @author angel_000 [t_plan]对应的DAO。
 */
@Repository
public class PlanDaoImpl extends HibernateDaoImpl<Plan, String> implements PlanDao {

	@Resource
	protected MessageSource messageSource;

	@Override
	protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

		criteria.createAlias("project", "p", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site", "si", JoinType.LEFT_OUTER_JOIN)
				.createAlias("personInCharge", "person", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.province", "psp", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.city", "sc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.edgeNodeType", "sedge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment", "se", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endA", "sA", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endA.province", "sAp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment.endA.city", "sAc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endB", "sB", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endB.province", "sBp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment.endB.city", "sBc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.fibreType", "sf", JoinType.LEFT_OUTER_JOIN)
				.createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
				.createAlias("planCalendar", "pc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN);

		return super.prepareConditions(criteria, filter);
	}

	/**
	 * 获取单条实体。
	 * 
	 * @param id 主键
	 * @return 实体对象，如果没有找到实体，则返回null
	 */
	@Override
	public Plan find(String id) {

		if (StringUtils.isEmpty(id)) {
			return null;
		}

		// 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
		Plan plan = (Plan) session().createCriteria(this.type).createAlias("project", "p", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site", "si", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.province", "psp", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.city", "sc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.edgeNodeType", "sedge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment", "se", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endA", "sA", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endA.province", "sAp", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endA.city", "sAc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endB", "sB", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.endB.province", "sBp", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment.endB.city", "sBc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.fibreType", "sf", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
				.createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
				.createAlias("planCalendar", "pc", JoinType.LEFT_OUTER_JOIN)
				.createAlias("personInCharge", "person", JoinType.LEFT_OUTER_JOIN)
				.createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("id", id))
				.uniqueResult();

		return plan;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JsonResultBean list(String commonFilterJson, int page, int rows, String sort, String order, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 设置过滤信息

			// 设置分页信息
			int currentPage = (page - 1) * rows;
			Criteria criteria = session().createCriteria(this.type)
					.createAlias("project", "p", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site", "si", JoinType.LEFT_OUTER_JOIN)
					.createAlias("personInCharge", "person", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.province", "psp", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.city", "sc", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.edgeNodeType", "sedge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment", "se", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.endA", "sA", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.endA.province", "sAp", JoinType.LEFT_OUTER_JOIN)
	                .createAlias("segment.endA.city", "sAc", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.endB", "sB", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.endB.province", "sBp", JoinType.LEFT_OUTER_JOIN)
	                .createAlias("segment.endB.city", "sBc", JoinType.LEFT_OUTER_JOIN)
					.createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
					.createAlias("planCalendar", "pc", JoinType.LEFT_OUTER_JOIN)
					.createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN);
			Criteria count = session().createCriteria(this.type).createAlias("project", "p", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site", "si", JoinType.LEFT_OUTER_JOIN)
					.createAlias("personInCharge", "person", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.province", "psp", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.city", "sc", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.edgeNodeType", "sedge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment", "se", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.endA", "sA", JoinType.LEFT_OUTER_JOIN)
					.createAlias("segment.endB", "sB", JoinType.LEFT_OUTER_JOIN)
					.createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
					.createAlias("planCalendar", "pc", JoinType.LEFT_OUTER_JOIN)
					.createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN);

			criteria.add(Restrictions.eq("isDeleted", OnOffEnum.OFF.value()));

			if (commonFilterJson != null && !"".equals(commonFilterJson)) {
				JSONObject json = JSONObject.parseObject(commonFilterJson);
				JSONArray filterFieldList = json.getJSONArray("filterFieldList");
				for (int i = 0; i < filterFieldList.size(); i++) {
					JSONObject field = filterFieldList.getJSONObject(i);
					if (field.getString("fieldName").equalsIgnoreCase("planType")) {
						// 计划类型
						String type = field.getString("value");
						switch (type) {
						case "0":
							// 年度建设工作计划
							criteria.add(Restrictions.isNull("site.id"));
							criteria.add(Restrictions.isNull("segment.id"));
							count.add(Restrictions.isNull("site.id"));
							count.add(Restrictions.isNull("segment.id"));
							break;
						case "1":
							// 站点计划
							criteria.add(Restrictions.like("site.id", "-", MatchMode.ANYWHERE));
							count.add(Restrictions.like("site.id", "-", MatchMode.ANYWHERE));
							break;
						case "2":
							// 中继段计划
							criteria.add(Restrictions.like("segment.id", "-", MatchMode.ANYWHERE));
							count.add(Restrictions.like("segment.id", "-", MatchMode.ANYWHERE));
							break;
						default:
							break;
						}
					} else if (field.getString("fieldName").equalsIgnoreCase("personInCharge_")) {
						// 负责人，站点负责人或者中继段负责人，或者两者都没有的情况下的创建人
						criteria.add(Restrictions.or(Restrictions.and(Restrictions.eq("charge.id", field.getString("value")),Restrictions.eq("virtualOrg.id", "ae26cdfd-ac69-4de4-a44e-8a2b4cee6282")),
								Restrictions.and(Restrictions.eq("pCharge.id", field.getString("value")),Restrictions.eq("virtualOrg.id", "74526f1e-b232-4c9c-8b61-a95172496cf4")),
								Restrictions.and(Restrictions.eq("sCharge.id", field.getString("value")),Restrictions.eq("virtualOrg.id", "710a9408-ac98-456a-b731-332dd91f69a9")),
								Restrictions.and(Restrictions.eq("creator.id", field.getString("value")), Restrictions.eq("isYear", 1)),
								Restrictions.and(Restrictions.eq("charge2.id", field.getString("value")), Restrictions.isNotNull("segment.id"))));
						count.add(Restrictions.or(Restrictions.and(Restrictions.eq("charge.id", field.getString("value")),Restrictions.eq("virtualOrg.id", "ae26cdfd-ac69-4de4-a44e-8a2b4cee6282")),
								Restrictions.and(Restrictions.eq("pCharge.id", field.getString("value")),Restrictions.eq("virtualOrg.id", "74526f1e-b232-4c9c-8b61-a95172496cf4")),
								Restrictions.and(Restrictions.eq("sCharge.id", field.getString("value")),Restrictions.eq("virtualOrg.id", "710a9408-ac98-456a-b731-332dd91f69a9")),
								Restrictions.and(Restrictions.eq("creator.id", field.getString("value")), Restrictions.eq("isYear", 1)),
								Restrictions.and(Restrictions.eq("charge2.id", field.getString("value")), Restrictions.isNotNull("segment.id"))));
					} else if (field.getString("fieldName").equalsIgnoreCase("planName")) {
						// 计划名
						criteria.add(Restrictions.like("planName", field.getString("value"), MatchMode.ANYWHERE));
						count.add(Restrictions.like("planName", field.getString("value"), MatchMode.ANYWHERE));
					} else if (field.getString("fieldName").equalsIgnoreCase("planYear")) {
						// 计划年份
						criteria.add(Restrictions.eq("planYear", field.getString("value")));
						count.add(Restrictions.eq("planYear", field.getString("value")));
					} else if (field.getString("fieldName").equalsIgnoreCase("virtualOrg_")) {
						// 项目组
						criteria.add(Restrictions.eq("v.id", field.getString("value")));
						count.add(Restrictions.eq("v.id", field.getString("value")));
					}else if (field.getString("fieldName").equalsIgnoreCase("project_")) {
						criteria.add(Restrictions.eq("p.id", field.getString("value")));
						count.add(Restrictions.eq("p.id", field.getString("value")));
					}
				}
			} else {
				criteria.add(Restrictions.isNull("site.id"));
				criteria.add(Restrictions.isNull("segment.id"));
				count.add(Restrictions.isNull("site.id"));
				count.add(Restrictions.isNull("segment.id"));
			}

//			criteria.add(Restrictions.or(Restrictions.eq("", ""), Restrictions.eq("", "")));

			// 设置排序信息
			List<OrderByDto> orders = new ArrayList<>();
			if (!StringUtils.isEmpty(sort)) {
				String[] orderArray = StringUtils.commaDelimitedListToStringArray(order);
				String[] sortArray = StringUtils.commaDelimitedListToStringArray(sort);
				for (int i = 0; i < orderArray.length; i++) {

					// 将bean的Text后缀的名字改为对应的entity的名字，即去掉Text后缀，规则见[t_data_fields].field_name_view
					String beanSort = sortArray[i];
					if (beanSort.endsWith("Text")) {
						beanSort = beanSort.substring(0, beanSort.length() - 4);
					}
					orders.add(new OrderByDto(beanSort, orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
				}
			}

			if (orders != null) {
				for (OrderByDto o : orders) {
					if (o.isDesc()) {
						criteria = criteria.addOrder(Order.desc(o.getColumnName()));
					} else {
						criteria = criteria.addOrder(Order.asc(o.getColumnName()));
					}
				}
			}

			criteria.setFirstResult(currentPage);
			criteria.setMaxResults(rows);

			// 获取分页后的数据
			List<Plan> list = criteria.list();
			List<ViewBean> resultList = new ArrayList<>();
			PlanBeanConverter converter = new PlanBeanConverter();
			for (Plan entity : list) {
				resultList.add(converter.fromEntity(entity, this.messageSource, locale));
			}

			count.setProjection(Projections.rowCount());
			long total = Long.valueOf(count.uniqueResult().toString());

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用

		} catch (Exception e) {
			e.printStackTrace();
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plan> findList(String projectId, String virtualOrgId, String nowDate) {
		
//		String startDate = nowDate + "-01-01 00:00:00";
//		String endDate = nowDate + "-12-31 23:59:59";
		
		try {
			
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date startDate = simpleDateFormat.parse(nowDate + "-01-01 00:00:00");
//			Date endDate = simpleDateFormat.parse(nowDate + "-12-31 23:59:59");
			
			Criteria criteria = session().createCriteria(this.type)
					.createAlias("project", "p", JoinType.LEFT_OUTER_JOIN)
					.createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
					.createAlias("site", "si", JoinType.LEFT_OUTER_JOIN);
			
			criteria.add(Restrictions.eq("p.id", projectId));
//			criteria.add(Restrictions.between("actualEndDate", startDate, endDate));
			if (virtualOrgId != null && !"".equals(virtualOrgId)) {
				criteria.add(Restrictions.eq("v.id", virtualOrgId));
			}
			
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ArrayList<Plan>();
		}
		
//		String sql = "";
//		
//		if (virtualOrgId != null && !"".equals(virtualOrgId)) {
//			sql = " select p.* from `t_plan` p "
//					+ " left outer join `t_virtual_org` o on o.id=p.virtual_org_id "
//					+ " where o.is_echarts_show<>0 and o.project_id=:projectId "
//					+ " and p.virtual_org_id=:virtualOrgId "
//					+ " and p.actual_end_date>=:startDate and p.actual_end_date<=:endDate ";
//		} else {
//			sql = " select p.* from `t_plan` p "
//					+ " where p.project_id=:projectId "
//					+ " and p.actual_end_date>=:startDate and p.actual_end_date<=:endDate ";
//		}
//		
//		String selectSql = String.format(sql);
//
//        SQLQuery createQuery = (SQLQuery) session().createSQLQuery(selectSql).addEntity(Plan.class)
//        		.setString("projectId", projectId)
//        		.setString("startDate", startDate)
//        		.setString("endDate", endDate);
//        
//        if (virtualOrgId != null && !"".contentEquals(virtualOrgId)) {
//        	createQuery.setString("virtualOrgId", virtualOrgId);
//        }
//        return createQuery.list();
	}
}
