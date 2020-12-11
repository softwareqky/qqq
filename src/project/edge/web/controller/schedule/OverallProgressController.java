/**
 * 
 */
package project.edge.web.controller.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.PlanBeanConverter;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.PlanBean;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SiteService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanCalendarService;
import project.edge.service.schedule.PlanService;
import project.edge.web.controller.common.TreeGridController;
import project.edge.web.controller.general.DataPermissionService;

/**
 * @author angel_000
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/overall-progress")
public class OverallProgressController extends TreeGridController<Plan, PlanBean> {

	private static final Logger logger = LoggerFactory.getLogger(OverallProgressController.class);

	private static final String PID = "P5003";

	@Resource
	private PlanService planService;

	@Resource
	private ProjectService projectService;

	@Resource
	private VirtualOrgService virtualOrgService;

	@Resource
	private PlanCalendarService planCalendarService;
	
	@Resource
	private DataPermissionService dataPermissionService;

	@Resource
	private SiteService siteService;

	@Resource
	private SegmentService segmentService;

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.PLAN_OVERALL_PROGRESS.value();
	}

	@Override
	protected Service<Plan, String> getDataService() {
		// TODO Auto-generated method stub
		return this.planService;
	}

	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return PID;
	}

	@Override
	protected ViewConverter<Plan, PlanBean> getViewConverter() {
		// TODO Auto-generated method stub
		return new PlanBeanConverter();
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = ControllerMapUtils.buildJsMap(idMap, urlMap, this.useFile(),
				this.getJsCallbackObjName());

		// 导入文件
		jsMap.put(ControllerJsMapKeys.OPEN_IMPORT,
				String.format("PlanTaskUtils.openImport('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 导出文件
		jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
				String.format("PlanTaskUtils.openExport('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		return jsMap;
	}

	/**
	 * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
	 * 
	 * @param locale
	 * @return key:[v_data_option].option_source，value:[v_data_option]
	 */
	@Override
	protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

		String[] planTypeArr = new String[] { "年度建设工作计划", "站点计划", "中继段计划" };
		List<ComboboxOptionBean> planTypeOptions = new ArrayList<>();
		for (int i = 0; i < planTypeArr.length; i++) {
			planTypeOptions.add(new ComboboxOptionBean(i + "", planTypeArr[i]));
		}
		optionMap.put("planTypeOptions", planTypeOptions);

		List<ComboboxOptionBean> projectGroupOptions2 = new ArrayList<>();
		CommonFilter filter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		filter.addExact("isEchartsShow", OnOffEnum.ON.value().intValue());
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(filter, null);
		if (virtualOrgList != null) {
			for (VirtualOrg v : virtualOrgList) {
				projectGroupOptions2.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
			}
		}
		optionMap.put("groupOptions2", projectGroupOptions2);

		String[] planYearArr = new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
				"2028", "2029", "2030" };

		List<ComboboxOptionBean> planYearOptions = new ArrayList<>();

		for (String s : planYearArr) {
			planYearOptions.add(new ComboboxOptionBean(s, s));
		}

		optionMap.put("planYearOptions", planYearOptions);

		CommonFilter pFilter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		List<Site> sites = this.siteService.list(pFilter, null);
		List<Segment> segments = this.segmentService.list(pFilter, null);
		pFilter.addNull("site.id");
		pFilter.addNull("segment.id");
		List<Plan> plans = this.planService.list(pFilter, null);
		List<ComboboxOptionBean> personOptions = new ArrayList<>();
		List<String> personIds = new ArrayList<>();
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).getPersonInCharge() != null
					&& !personIds.contains(sites.get(i).getPersonInCharge().getId())) {
				personIds.add(sites.get(i).getPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getPersonInCharge().getId(),
						sites.get(i).getPersonInCharge().getPersonName()));
			}
			if (sites.get(i).getProgrammablePersonInCharge() != null
					&& !personIds.contains(sites.get(i).getProgrammablePersonInCharge().getId())) {
				personIds.add(sites.get(i).getProgrammablePersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getProgrammablePersonInCharge().getId(),
						sites.get(i).getProgrammablePersonInCharge().getPersonName()));
			}
			if (sites.get(i).getSdnPersonInCharge() != null
					&& !personIds.contains(sites.get(i).getSdnPersonInCharge().getId())) {
				personIds.add(sites.get(i).getSdnPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getSdnPersonInCharge().getId(),
						sites.get(i).getSdnPersonInCharge().getPersonName()));
			}
		}
		for (int i = 0; i < segments.size(); i++) {
			if (segments.get(i).getPersonInCharge() != null
					&& !personIds.contains(segments.get(i).getPersonInCharge().getId())) {
				personIds.add(segments.get(i).getPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(segments.get(i).getPersonInCharge().getId(),
						segments.get(i).getPersonInCharge().getPersonName()));
			}
		}
		for (int i = 0; i < plans.size(); i++) {
			if (plans.get(i).getCreator() != null && !personIds.contains(plans.get(i).getCreator().getId())) {
				personIds.add(plans.get(i).getCreator().getId());
				personOptions.add(new ComboboxOptionBean(plans.get(i).getCreator().getId(),
						plans.get(i).getCreator().getPersonName()));
			}
		}
		optionMap.put("personOptions", personOptions);
		return optionMap;
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/schedule/overallProgress.jsp";
	}

	/**
	 * 画面Open的入口方法，用于生成JSP。
	 * 
	 * @param paramMap 画面请求中的任何参数，都会成为检索的字段
	 * @param model    model
	 * @param userBean session中的当前登录的用户信息
	 * @param locale   locale
	 * @return
	 */
	@RequestMapping("/main")
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		return super.main(paramMap, model, userBean, locale);
	}

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param page             页数
	 * @param rows             每页的记录数
	 * @param sort             排序的字段，CSV
	 * @param order            顺序，CSV
	 * @param locale
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		commonFilterJson = dataPermissionService.editFilterBySelfVirtualOrg(userBean, commonFilterJson);
		
		JsonResultBean jsonResult = planService.list(commonFilterJson, page, rows, sort, order, locale);

		return jsonResult;
	}

	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id     ID
	 * @param locale
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	@Override
	public JsonResultBean find(@RequestParam String id, Locale locale) {
		return super.find(id, locale);
	}

	/**
	 * 新建，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	@SysLogAnnotation(description = "总体进度", action = "新增")
	public JsonResultBean create(@ModelAttribute PlanBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.create(bean, null, userBean, locale);
	}

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "总体进度", action = "更新")
	public JsonResultBean update(@ModelAttribute PlanBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.update(bean, null, userBean, locale);
	}

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "总体进度", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}
}
