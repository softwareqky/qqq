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
import project.edge.domain.converter.PlanProgressBeanConverter;
import project.edge.domain.entity.PlanProgress;

/**
 * @author angel_000 [t_plan_progress]对应的DAO。
 */
@Repository
public class PlanProgressDaoImpl extends HibernateDaoImpl<PlanProgress, String> implements PlanProgressDao {

	@Resource
	protected MessageSource messageSource;
	
	@Override
	protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

		criteria.createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site", "ps", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.province", "psp", JoinType.LEFT_OUTER_JOIN)
				.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
				.createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.creator", "creator", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
				.createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN);

		criteria.createAlias("planProgressAttachments", "planProgressAttachments", JoinType.LEFT_OUTER_JOIN)
				.createAlias("planProgressAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many
		return super.prepareConditions(criteria, filter);
	}

	/**
	 * 获取单条实体。
	 * 
	 * @param id 主键
	 * @return 实体对象，如果没有找到实体，则返回null
	 */
	@Override
	public PlanProgress find(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}

		// 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
		PlanProgress planProgress = (PlanProgress) session().createCriteria(this.type)
				.createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site", "ps", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.province", "psp", JoinType.LEFT_OUTER_JOIN)
				.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
				.createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
				.createAlias("planProgressAttachments", "planProgressAttachments", JoinType.LEFT_OUTER_JOIN)
				.createAlias("planProgressAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
				.createAlias("plan.creator", "creator", JoinType.LEFT_OUTER_JOIN)
				// .addOrder(Order.asc("planProgressTasks.id"))
				.add(Restrictions.eq("id", id)).uniqueResult();
		return planProgress;
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
					.createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site", "ps", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.province", "psp", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.segment", "se", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.creator", "creator", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
					.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
					.createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
					.createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN);
			Criteria count = session().createCriteria(this.type)
					.createAlias("plan", "p", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site", "ps", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.province", "psp", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.segment", "se", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.personInCharge", "charge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.programmablePersonInCharge", "pCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.site.sdnPersonInCharge", "sCharge", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.segment.personInCharge", "charge2", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.creator", "creator", JoinType.LEFT_OUTER_JOIN)
					.createAlias("plan.virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
					.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
					.createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
					.createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN);

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
							criteria.add(Restrictions.isNull("ps.id"));
							criteria.add(Restrictions.isNull("se.id"));
							count.add(Restrictions.isNull("ps.id"));
							count.add(Restrictions.isNull("se.id"));
							break;
						case "1":
							// 站点计划
							criteria.add(Restrictions.like("ps.id", "-", MatchMode.ANYWHERE));
							count.add(Restrictions.like("ps.id", "-", MatchMode.ANYWHERE));
							break;
						case "2":
							// 中继段计划
							criteria.add(Restrictions.like("se.id", "-", MatchMode.ANYWHERE));
							count.add(Restrictions.like("se.id", "-", MatchMode.ANYWHERE));
							break;
						default:
							break;
						}
					} else if (field.getString("fieldName").equalsIgnoreCase("personInCharge_")) {
						// 负责人，站点负责人或者中继段负责人，或者两者都没有的情况下的创建人
						criteria.add(Restrictions.or(Restrictions.and(Restrictions.eq("charge.id", field.getString("value")),Restrictions.eq("v.id", "ae26cdfd-ac69-4de4-a44e-8a2b4cee6282")),
								Restrictions.and(Restrictions.eq("pCharge.id", field.getString("value")),Restrictions.eq("v.id", "74526f1e-b232-4c9c-8b61-a95172496cf4")),
								Restrictions.and(Restrictions.eq("sCharge.id", field.getString("value")),Restrictions.eq("v.id", "710a9408-ac98-456a-b731-332dd91f69a9")),
								Restrictions.and(Restrictions.eq("creator.id", field.getString("value")), Restrictions.eq("p.isYear", 1))));
						count.add(Restrictions.or(Restrictions.and(Restrictions.eq("charge.id", field.getString("value")),Restrictions.eq("v.id", "ae26cdfd-ac69-4de4-a44e-8a2b4cee6282")),
								Restrictions.and(Restrictions.eq("pCharge.id", field.getString("value")),Restrictions.eq("v.id", "74526f1e-b232-4c9c-8b61-a95172496cf4")),
								Restrictions.and(Restrictions.eq("sCharge.id", field.getString("value")),Restrictions.eq("v.id", "710a9408-ac98-456a-b731-332dd91f69a9")),
								Restrictions.and(Restrictions.eq("creator.id", field.getString("value")), Restrictions.eq("p.isYear", 1))));
					} else if (field.getString("fieldName").equalsIgnoreCase("plan_")) {
						// 计划名
						criteria.add(Restrictions.like("p.planName", field.getString("value"), MatchMode.ANYWHERE));
						count.add(Restrictions.like("p.planName", field.getString("value"), MatchMode.ANYWHERE));
					} else if (field.getString("fieldName").equalsIgnoreCase("planYear")) {
						// 计划年份
						criteria.add(Restrictions.eq("p.planYear", field.getString("value")));
						count.add(Restrictions.eq("p.planYear", field.getString("value")));
					} else if (field.getString("fieldName").equalsIgnoreCase("reportType")) {
						// 简报类型
						criteria.add(Restrictions.eq("reportType", Integer.parseInt(field.getString("value"))));
						count.add(Restrictions.eq("reportType", Integer.parseInt(field.getString("value"))));
					} else if (field.getString("fieldName").equalsIgnoreCase("project_")) {
						criteria.add(Restrictions.eq("project.id", field.getString("value")));
						count.add(Restrictions.eq("project.id", field.getString("value")));
					}
				}
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
			List<PlanProgress> list = criteria.list();
			List<ViewBean> resultList = new ArrayList<>();
			PlanProgressBeanConverter converter = new PlanProgressBeanConverter();
			for (PlanProgress entity : list) {
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
}
