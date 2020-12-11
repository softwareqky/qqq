package project.edge.web.controller.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.SiteTypeEnum;
import project.edge.domain.converter.LeaderHomePieChartListBeanConvert;
import project.edge.domain.converter.LeaderHomePlanStieListBeanConvert;
import project.edge.domain.converter.MapSegmentListBeanConverter;
import project.edge.domain.converter.MapSiteListBeanConverter;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.SegmentSystemName;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.LeaderHomeInfoBean;
import project.edge.domain.view.LeaderHomeLayerInfoBean;
import project.edge.domain.view.LeaderHomePieChartListBean;
import project.edge.domain.view.LeaderHomeSiteInfoBean;
import project.edge.domain.view.MapSegmentListBean;
import project.edge.domain.view.MapSiteListBean;
import project.edge.service.facility.LinkService;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SegmentSystemNameService;
import project.edge.service.facility.SiteService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.DataOptionService;

/**
 * 驾驶舱Home画面。
 * 
 */
/**
 * @author admin
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class LeaderHomeController {

    private static final Logger logger = LoggerFactory.getLogger(LeaderHomeController.class);

    private static final String HOME_VIEW_NAME = "main/leaderHomePage";

    // 未来网络试验设施国家重大科技基础设施
    private static final String PROJECT_NUM = "FNII20200103001";

    private static final int LIST_PAGE = 1; // 获取首页
    private static final int LIST_ROW = 5; // 首页模块数据条数

    private boolean isSegment = false;

    @Autowired
    protected ServletContext context;

    @Resource
    private MessageSource messageSource;

    @Resource
    private SegmentService segmentService;

    @Resource
    private PlanService planService;

    @Resource
    private PlanTaskService planTaskService;

    @Resource
    private SiteService siteService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private SegmentService segementService;
    
    @Resource
    private SegmentSystemNameService segmentSystemNameService;

    @Resource
    private LinkService linkService;

    @Resource
    private ProjectService projectService;

    @Resource
    private VirtualOrgService virtualOrgService;

    /**
     * 打开主画面。
     */
    @RequestMapping("/leader-home")
    public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Model model, HttpServletResponse response, Locale locale) {

        return HOME_VIEW_NAME;
    }
    
    @PostMapping("/segment-system-name")
    @ResponseBody
    public JsonResultBean createSegmentSystemNames(
    		@RequestBody List<SegmentSystemName> nameList, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale
    ) {
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	try {
    		
//    		List<SegmentSystemName> nameList = Arrays.asList(names);
    		
    		// 更新所有的中继段系统名
    		segmentSystemNameService.update(nameList);
    		
    		// 取得并返回
    		List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("name", false));
            List<SegmentSystemName> systemNameList = segmentSystemNameService.list(null, orders);
            for (SegmentSystemName systemName: systemNameList) {
            	CommonFilter filter = new CommonFilter();
            	filter.addExact("systemName.id", systemName.getId());
            	int count = (int) segmentService.count(filter);
            	systemName.setSegmentCount(count);
            }
            
    		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, systemNameList);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception segment-system-name", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
    	}
    	
    	return jsonResult;
    }


    /**
     * 
     * @param userBean
     * @param locale
     * @return 中继段系统名称、边缘节点完成数量、站点完成数量
     */
    @RequestMapping("/site-info")
    @ResponseBody
    public JsonResultBean getSiteInfo(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            LeaderHomeInfoBean homeInfoBean = new LeaderHomeInfoBean();

            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            pageCtrl.setCurrentPage(LIST_PAGE);
            pageCtrl.setPageSize(LIST_ROW);

            // 所属系统名称筛选
            CommonFilter f = null;
//            List<Segment> filtrateList = this.segementService.list(f, null);
//
//            if ((filtrateList != null) && (filtrateList.size() > 0)) {
//                List<String> sysList = new ArrayList<>();
//                for (Segment s : filtrateList) {
//                    if (s != null) {
//                        sysList.add(s.getSystemName());
//                    }
//                }
//                List<String> systemList = sysList.stream().distinct().collect(Collectors.toList());
//                homeInfoBean.setSegSystemName(systemList);
//            }
//            
            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("name", false));
            List<SegmentSystemName> systemNameList = segmentSystemNameService.list(null, orders);
            for (SegmentSystemName systemName: systemNameList) {
            	CommonFilter filter = new CommonFilter();
            	filter.addExact("systemName.id", systemName.getId());
            	int count = (int) segmentService.count(filter);
            	systemName.setSegmentCount(count);
            }
            
            homeInfoBean.setSegSystemName(systemNameList);
            

            List<Site> statisticsList = this.siteService.list(f, null);

            List<LeaderHomePieChartListBean> pieChartList =
                    new ArrayList<LeaderHomePieChartListBean>();

            int finishCount = 0, ongoingCount = 0, unfinishCount = 0;
            int edgeFinishCount = 0, edgeOngoingCount = 0, edgeUnfinishCount = 0;

            if ((statisticsList != null) && (statisticsList.size() > 0)) {
                LeaderHomePieChartListBeanConvert converter =
                        new LeaderHomePieChartListBeanConvert();
                for (Site site : statisticsList) {
                    if (site != null) {

                        if (site.getEdgeNodeType() != null) {

                            if (site.getEdgeNodeManualSiteStatus() != null) {
                                if (site.getEdgeNodeManualSiteStatus() == 0) {
                                    edgeUnfinishCount++;
                                } else if (site.getEdgeNodeManualSiteStatus() == 1) {
                                    edgeOngoingCount++;
                                } else if (site.getEdgeNodeManualSiteStatus() == 2) {
                                    edgeFinishCount++;
                                }
                            }
                        }
                        if (site.getEdgeNodeType() == null) {
                            if (site.getBasicNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getBasicNetworkManualSiteStatus() == 1) {
                                    ongoingCount++;
                                } else if (site.getBasicNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                }
                            } else if (site.getProgrammableNetworkManualSiteStatus() != null) {
                                if (site.getProgrammableNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getProgrammableNetworkManualSiteStatus() == 1) {
                                    ongoingCount++;
                                } else if (site.getProgrammableNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                }
                            } else if (site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getSdnNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getSdnNetworkManualSiteStatus() == 1) {
                                    ongoingCount++;
                                } else if (site.getSdnNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                }
                            } else if (site.getBasicNetworkManualSiteStatus() != null
                                    && site.getProgrammableNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 0
                                        && site.getProgrammableNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getBasicNetworkManualSiteStatus() == 2
                                        && site.getProgrammableNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                } else {
                                    ongoingCount++;
                                }
                            } else if (site.getBasicNetworkManualSiteStatus() != null
                                    && site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 0
                                        && site.getSdnNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getBasicNetworkManualSiteStatus() == 2
                                        && site.getSdnNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                } else {
                                    ongoingCount++;
                                }
                            } else if (site.getProgrammableNetworkManualSiteStatus() != null
                                    && site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getProgrammableNetworkManualSiteStatus() == 0
                                        && site.getSdnNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getProgrammableNetworkManualSiteStatus() == 2
                                        && site.getSdnNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                } else {
                                    ongoingCount++;
                                }
                            } else if (site.getBasicNetworkManualSiteStatus() != null
                                    && site.getProgrammableNetworkManualSiteStatus() != null
                                    && site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 0
                                        && site.getProgrammableNetworkManualSiteStatus() == 0
                                        && site.getSdnNetworkManualSiteStatus() == 0) {
                                    unfinishCount++;
                                } else if (site.getBasicNetworkManualSiteStatus() == 2
                                        && site.getProgrammableNetworkManualSiteStatus() == 2
                                        && site.getSdnNetworkManualSiteStatus() == 2) {
                                    finishCount++;
                                } else {
                                    ongoingCount++;
                                }
                            }
                        }

                    }
                }
                pieChartList.add(converter.fromEntity(finishCount, ongoingCount, unfinishCount,
                        edgeFinishCount, edgeOngoingCount, edgeUnfinishCount, messageSource,
                        locale));
                homeInfoBean.setPieChart(pieChartList);
                unfinishCount = 0;
                ongoingCount = 0;
                finishCount = 0;
                edgeFinishCount = 0;
                edgeOngoingCount = 0;
                edgeUnfinishCount = 0;

            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, homeInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception LeaderHome", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }
    
    /**
     * 取得设施进展图层的中继段相关信息
     * @param layer
     * @param locale
     * @return
     */
    private LeaderHomeLayerInfoBean getSegmentInfoForFacilityProgress(String layer, Locale locale) {
    	
    	LeaderHomeLayerInfoBean layerInfoBean = new LeaderHomeLayerInfoBean();
		LeaderHomePlanStieListBeanConvert converter = new LeaderHomePlanStieListBeanConvert();
		
		// 查询所有中继段建设计划
    	CommonFilter progSegFilter = new CommonFilter();
        progSegFilter.addAnywhere("planName", "中继段_");
        List<Plan> segmentPlanList = this.planService.list(progSegFilter, null);
        
        for (Plan plan: segmentPlanList) {
        	
        	Segment segment = plan.getSegment();
        	if (segment != null) {
        		
        		// 如果手动设置了进展状态
        		Integer status = segment.getManualSegmentStatus();
        		if (status == null) {
        			status = plan.getStatus();
        		}
        		layerInfoBean.getLayerSegmentList().add(converter.fromEntitySeg(layer, segment, status, messageSource, locale));
        	}
        }
        
        return layerInfoBean;
    }
    
    
    /**
     * 取得设施进展图层的站点相关信息
     * @param layer
     * @param siteType
     * @param locale
     * @param planList
     * @return
     */
    private LeaderHomeLayerInfoBean getLayerSiteInfoForFacilityProgress(String layer, String siteType, Locale locale, List<Plan> planList) {
    	
    	LeaderHomeLayerInfoBean layerInfoBean = new LeaderHomeLayerInfoBean();
		LeaderHomePlanStieListBeanConvert converter = new LeaderHomePlanStieListBeanConvert();
        
        int[] siteStatusCounts = {0, 0, 0};  // 未完成、进行中、已完成
        for (Plan plan: planList) {
        	
        	Site site = plan.getSite();
        	if (site != null) {
        		
        		// 如果是手动设置进展状态
        		Integer status = site.getBasicNetworkManualSiteStatus();
        		if (status == null) {
        			status = plan.getStatus();
    				siteStatusCounts[status]++;       // 更新对应状态的站点计数
        		}
        		layerInfoBean.getLayerSiteList().add(converter.fromEntityPro(siteType, status, site, messageSource, locale));
        	}
        }
        layerInfoBean.getStatusCountSiteList().add(converter.fromEntityCount(siteStatusCounts[2], siteStatusCounts[1], siteStatusCounts[0], messageSource, locale));
    	
    	return layerInfoBean;
    }
    
    
    /**
     * 取得设施进展图层的链路相关信息
     * @param layer
     * @param siteType
     * @param locale
     * @param planList
     * @return
     */
    private LeaderHomeLayerInfoBean getLayerLinkInfoForFacilityProgress(String layer, String siteType, Locale locale, List<Plan> planList) {
    	
    	LeaderHomeLayerInfoBean layerInfoBean = new LeaderHomeLayerInfoBean();
		LeaderHomePlanStieListBeanConvert converter = new LeaderHomePlanStieListBeanConvert();
		
		List<Link> linkList = this.linkService.list(null, null);
		
		for (Link link : linkList) {
            if (link.getEndA() != null && link.getEndB() != null) {
//                for (Plan plan : planList) {
//                    Site site = plan.getSite();
//                    if (site != null) {
//                        if (link.getEndA().getStationName().equals(site.getStationName())
//                         || link.getEndB().getStationName().equals(site.getStationName())) {
//                        	layerInfoBean.getLayerLinkList().add(converter.fromEntityLink(link, messageSource, locale));
//                            break;
//                        }
//                    }
//                }
            	
            	if ((siteType.equals("3") && link.getInvolvedProjectGroup() == 2) ||
            		(siteType.equals("2") && link.getInvolvedProjectGroup() == 1)) {
            		layerInfoBean.getLayerLinkList().add(converter.fromEntityLink(link, messageSource, locale));
            	}
            }
        }
		
		return layerInfoBean;
    }


    @RequestMapping("/site-layer-info")
    @ResponseBody
    public JsonResultBean getSiteLayerInfo(
            @RequestParam(required = true, defaultValue = "") String layer,
            @RequestParam(required = false, defaultValue = "") String siteType,
            @RequestParam(required = false, defaultValue = "") List<String> segSystemName,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

    	logger.info("态势图数据请求开始");
    	
        JsonResultBean jsonResult = new JsonResultBean();
        try {

            /*
             * layer = "0";
             * siteType = "3";
             */

            // 参数检查
            if (StringUtils.isEmpty(layer)) {
                jsonResult.setStatus(JsonStatus.ERROR);
                return jsonResult;
            }

            LeaderHomeLayerInfoBean layerInfoBean = new LeaderHomeLayerInfoBean();

            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            pageCtrl.setCurrentPage(LIST_PAGE);
            pageCtrl.setPageSize(LIST_ROW);

            if (layer.equals("0")) {// 目标图层 基础网络组返回站点和中继段 可编程和SDN返回站点和链路

                LeaderHomePlanStieListBeanConvert converter =
                        new LeaderHomePlanStieListBeanConvert();

                List<Segment> planSegmentList = this.segementService.list(null, null);
                List<Link> planLinkList = this.linkService.list(null, null);

                if (siteType.equals("1")) {
                    CommonFilter basicFilter =
                            new CommonFilter().addExact("basicNetworkTransmitStationType", "1");

                    List<Site> list = this.siteService.list(basicFilter, null);
                    List<Site> basicSiteList =
                            list.stream().distinct().collect(Collectors.toList());

                    if ((basicSiteList != null) && (basicSiteList.size() > 0)) {
                        for (Site site : basicSiteList) {
                            layerInfoBean.getLayerSiteList().add(converter.fromEntity(siteType,
                                    site, this.messageSource, locale));
                        }
                        if ((planSegmentList != null) && (planSegmentList.size() > 0)) {
                            for (Segment s : planSegmentList) {
                                layerInfoBean.getLayerSegmentList().add(converter
                                        .fromEntitySeg(layer, s, 0, this.messageSource, locale));

                            }
                        }


                    }

                } else if (siteType.equals("2")) {
                    CommonFilter programFilter = new CommonFilter()
                            .addExact("basicNetworkTransmitStationType", "1");
//                            .addExact("isProgrammableNetworkTransmitStationType", (short) 1);

                    List<Site> list = this.siteService.list(programFilter, null);
                    List<Site> programSiteList =
                            list.stream().distinct().collect(Collectors.toList());

                    if ((programSiteList != null) && (programSiteList.size() > 0)) {
                        for (Site site : programSiteList) {
                            layerInfoBean.getLayerSiteList().add(converter.fromEntity(siteType,
                                    site, this.messageSource, locale));
                        }
                        if ((planLinkList != null) && (planLinkList.size() > 0)) {
                            for (Link link : planLinkList) {
                                if (link.getEndA() != null && link.getEndB() != null) {
                                    for (Site site : programSiteList) {
                                        if (link.getEndA().getStationName()
                                                .equals(site.getStationName())
                                                || link.getEndB().getStationName()
                                                        .equals(site.getStationName())) {
                                            isSegment = true;
                                        }
                                    }
                                    if (isSegment) {
                                        layerInfoBean.getLayerLinkList().add(converter
                                                .fromEntityLink(link, this.messageSource, locale));
                                        isSegment = false;
                                    }
                                }
                            }
                        }
                    }

                } else if (siteType.equals("3")) {
                    CommonFilter sdnFilter =
                            new CommonFilter().addExact("basicNetworkTransmitStationType", "1");
//                                    .addExact("isSdnNetworkTransmitStationType", (short) 1);

                    List<Site> list = this.siteService.list(sdnFilter, null);
                    List<Site> sdnSiteList = list.stream().distinct().collect(Collectors.toList());

                    if ((sdnSiteList != null) && (sdnSiteList.size() > 0)) {
                        for (Site site : sdnSiteList) {
                            layerInfoBean.getLayerSiteList().add(converter.fromEntity(siteType,
                                    site, this.messageSource, locale));
                        }
                        if ((planLinkList != null) && (planLinkList.size() > 0)) {
                            for (Link link : planLinkList) {
                                if (link.getEndA() != null && link.getEndB() != null) {
                                    for (Site site : sdnSiteList) {
                                        if (link.getEndA().getStationName()
                                                .equals(site.getStationName())
                                                || link.getEndB().getStationName()
                                                        .equals(site.getStationName())) {
                                            isSegment = true;
                                        }

                                    }
                                    if (isSegment) {
                                        layerInfoBean.getLayerLinkList().add(converter
                                                .fromEntityLink(link, this.messageSource, locale));
                                        isSegment = false;
                                    }
                                }

                            }
                        }
                    }
                }

            } else if (layer.equals("1")) {// 实际图层
            	
                CommonFilter projectFilter = new CommonFilter();
                projectFilter.addExact("projectNum", PROJECT_NUM);
                List<Project> projects = this.projectService.list(projectFilter, null);
                Project project = null;
                if (!projects.isEmpty()) {
                    project = projects.get(0);
                }

                if (project != null) {
                	if (siteType.equals("1")) {
                    	
                    	CommonFilter orgFilter = new CommonFilter();
                        orgFilter.addExact("project.id", project.getId());
                        orgFilter.addAnywhere("virtualOrgName", "基础网络");
                        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
                        VirtualOrg v = null;
                        if (!virtualOrgs.isEmpty()) {
                        	
                        	// 查询所有站点建设计划
                        	v = virtualOrgs.get(0);
                            CommonFilter filter = new CommonFilter()
                                    .addExact("si.basicNetworkTransmitStationType", "1")
                                    .addExact("v.id", v.getId());
//                            filter.addAnywhere("planName", "基础网络传输组站点计划_");
                            List<Plan> planList = this.planService.list(filter, null);
                        	List<Plan> planList1 = new ArrayList<Plan>();
                        	Plan plan;
                        	for (int i=0;i<planList.size();i++) {
                        		plan = planList.get(i);
                        		if (plan.getVirtualOrg() != null && plan.getVirtualOrg().getId().equals(v.getId())) {
                        			planList1.add(plan);
                        		}
                        	}
                        	
                        	LeaderHomeLayerInfoBean layerSiteInfo = this.getLayerSiteInfoForFacilityProgress(layer, siteType, locale, planList1);
                        	layerInfoBean.setStatusCountSiteList(layerSiteInfo.getStatusCountSiteList());
                        	layerInfoBean.setLayerSiteList(layerSiteInfo.getLayerSiteList());
                        	
                        	LeaderHomeLayerInfoBean layerSegmentInfo = this.getSegmentInfoForFacilityProgress(layer, locale);
                        	layerInfoBean.setLayerSegmentList(layerSegmentInfo.getLayerSegmentList());
                        }
                	} 
                	else if (siteType.equals("2")) {
                        CommonFilter orgFilter = new CommonFilter();
                        orgFilter.addExact("project.id", project.getId());
                        orgFilter.addAnywhere("virtualOrgName", "可编程");
                        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
                        VirtualOrg v = null;
                        if (!virtualOrgs.isEmpty()) {
                        	
                        	// 查询所有站点建设计划
                            v = virtualOrgs.get(0);
                            CommonFilter filter = new CommonFilter()
                                    .addExact("si.basicNetworkTransmitStationType", "1")
                                    .addExact("v.id", v.getId());
                            filter.addAnywhere("planName", "核心节点");
                            List<Plan> planList = this.planService.list(filter, null);
                            List<Plan> planList1 = new ArrayList<Plan>();
                            Plan plan;
                        	for (int i=0;i<planList.size();i++) {
                        		plan = planList.get(i);
                        		if ("4".equals(plan.getSite().getBasicNetworkTransmitStationType())) {
                        			continue;
                        		}
                        		if (plan.getVirtualOrg() != null && plan.getVirtualOrg().getId().equals(v.getId())) {
                        			planList1.add(plan);
                        		}
                        	}
                            
                            LeaderHomeLayerInfoBean layerSiteInfo = this.getLayerSiteInfoForFacilityProgress(layer, siteType, locale, planList1);
                            layerInfoBean.setStatusCountSiteList(layerSiteInfo.getStatusCountSiteList());
                        	layerInfoBean.setLayerSiteList(layerSiteInfo.getLayerSiteList());
                        	
                        	LeaderHomeLayerInfoBean layerLinkInfo = this.getLayerLinkInfoForFacilityProgress(layer, siteType, locale, planList1);
                        	layerInfoBean.setLayerLinkList(layerLinkInfo.getLayerLinkList());
                        }
                	} 
                	else if (siteType.equals("3")) {
                    	
                        CommonFilter orgFilter = new CommonFilter();
                        orgFilter.addExact("project.id", project.getId());
                        orgFilter.addAnywhere("virtualOrgName", "SDN");
                        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
                        VirtualOrg v = null;
                        if (!virtualOrgs.isEmpty()) {
                        	
                        	// 查询所有站点建设计划
                            v = virtualOrgs.get(0);
                            CommonFilter sdnFilter = new CommonFilter()
                                    .addExact("si.basicNetworkTransmitStationType", "1")
                                    .addExact("v.id", v.getId());
                            sdnFilter.addAnywhere("planName", "核心节点");
                            List<Plan> planList = this.planService.list(sdnFilter, null);
                            List<Plan> planList1 = new ArrayList<Plan>();
                            Plan plan;
                        	for (int i=0;i<planList.size();i++) {
                        		plan = planList.get(i);
                        		if ("4".equals(plan.getSite().getBasicNetworkTransmitStationType())) {
                        			continue;
                        		}
                        		if (plan.getVirtualOrg() != null && plan.getVirtualOrg().getId().equals(v.getId())) {
                        			planList1.add(plan);
                        		}
                        	}
                            
                            LeaderHomeLayerInfoBean layerSiteInfo = this.getLayerSiteInfoForFacilityProgress(layer, siteType, locale, planList1);
                            layerInfoBean.setStatusCountSiteList(layerSiteInfo.getStatusCountSiteList());
                        	layerInfoBean.setLayerSiteList(layerSiteInfo.getLayerSiteList());
                        	
                        	LeaderHomeLayerInfoBean layerLinkInfo = this.getLayerLinkInfoForFacilityProgress(layer, siteType, locale, planList1);
                        	layerInfoBean.setLayerLinkList(layerLinkInfo.getLayerLinkList());

                        }
                    }
                }
            }

            if (!segSystemName.isEmpty() && segSystemName != null) {
            	
            	CommonFilter segFilter = new CommonFilter().addWithin("name", segSystemName);
            	List<SegmentSystemName> systemNameList = this.segmentSystemNameService.list(segFilter,  null);
            	
                CommonFilter filter = new CommonFilter().addWithin("systemName", systemNameList);
                List<Segment> segmentList = this.segementService.list(filter, null);
                LeaderHomePlanStieListBeanConvert converter =
                        new LeaderHomePlanStieListBeanConvert();

                if ((segmentList != null) && (segmentList.size() > 0)) {
                    for (Segment segment : segmentList) {
                        if (segment != null) {
                            layerInfoBean.getFiltrateSegmentList().add(converter.fromEntitySeg("", segment, null, this.messageSource, locale));
                        }
                    }
                }
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, layerInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (

        Exception e) {
            logger.error("Exception LeaderHome", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }
        
        logger.info("态势图数据请求结束");

        return jsonResult;

    }


//    @RequestMapping("/site-layer-info")
//    @ResponseBody
//    public JsonResultBean getSiteLayerInfo(
//            @RequestParam(required = true, defaultValue = "") String layer,
//            @RequestParam(required = false, defaultValue = "") String siteType,
//            @RequestParam(required = false, defaultValue = "") List<String> segSystemName,
//            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
//
//    	logger.info("态势图数据请求开始");
//    	
//        JsonResultBean jsonResult = new JsonResultBean();
//        try {
//
//            /*
//             * layer = "0";
//             * siteType = "3";
//             */
//
//            // 参数检查
//            if (StringUtils.isEmpty(layer)) {
//                jsonResult.setStatus(JsonStatus.ERROR);
//                return jsonResult;
//            }
//
//            LeaderHomeLayerInfoBean layerInfoBean = new LeaderHomeLayerInfoBean();
//
//            // 设置分页信息
//            PageCtrlDto pageCtrl = new PageCtrlDto();
//            pageCtrl.setCurrentPage(LIST_PAGE);
//            pageCtrl.setPageSize(LIST_ROW);
//
//            if (layer.equals("0")) {// 目标图层 基础网络组返回站点和中继段 可编程和SDN返回站点和链路
//
//                LeaderHomePlanStieListBeanConvert converter =
//                        new LeaderHomePlanStieListBeanConvert();
//
//                List<Segment> planSegmentList = this.segementService.list(null, null);
//                List<Link> planLinkList = this.linkService.list(null, null);
//
//                if (siteType.equals("1")) {
//                    CommonFilter basicFilter =
//                            new CommonFilter().addExact("basicNetworkTransmitStationType", "1");
//
//                    List<Site> list = this.siteService.list(basicFilter, null);
//                    List<Site> basicSiteList =
//                            list.stream().distinct().collect(Collectors.toList());
//
//                    if ((basicSiteList != null) && (basicSiteList.size() > 0)) {
//                        for (Site site : basicSiteList) {
//                            layerInfoBean.getLayerSiteList().add(converter.fromEntity(siteType,
//                                    site, this.messageSource, locale));
//                        }
//                        if ((planSegmentList != null) && (planSegmentList.size() > 0)) {
//                            for (Segment s : planSegmentList) {
//                                layerInfoBean.getLayerSegmentList().add(converter
//                                        .fromEntitySeg(layer, s, 0, this.messageSource, locale));
//
//                            }
//                        }
//
//
//                    }
//
//                } else if (siteType.equals("2")) {
//                    CommonFilter programFilter = new CommonFilter()
//                            .addExact("basicNetworkTransmitStationType", "1")
//                            .addExact("isProgrammableNetworkTransmitStationType", (short) 1);
//
//                    List<Site> list = this.siteService.list(programFilter, null);
//                    List<Site> programSiteList =
//                            list.stream().distinct().collect(Collectors.toList());
//
//                    if ((programSiteList != null) && (programSiteList.size() > 0)) {
//                        for (Site site : programSiteList) {
//                            layerInfoBean.getLayerSiteList().add(converter.fromEntity(siteType,
//                                    site, this.messageSource, locale));
//                        }
//                        if ((planLinkList != null) && (planLinkList.size() > 0)) {
//                            for (Link link : planLinkList) {
//                                if (link.getEndA() != null && link.getEndB() != null) {
//                                    for (Site site : programSiteList) {
//                                        if (link.getEndA().getStationName()
//                                                .equals(site.getStationName())
//                                                || link.getEndB().getStationName()
//                                                        .equals(site.getStationName())) {
//                                            isSegment = true;
//                                        }
//                                    }
//                                    if (isSegment) {
//                                        layerInfoBean.getLayerLinkList().add(converter
//                                                .fromEntityLink(link, this.messageSource, locale));
//                                        isSegment = false;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                } else if (siteType.equals("3")) {
//                    CommonFilter sdnFilter =
//                            new CommonFilter().addExact("basicNetworkTransmitStationType", "1")
//                                    .addExact("isSdnNetworkTransmitStationType", (short) 1);
//
//                    List<Site> list = this.siteService.list(sdnFilter, null);
//                    List<Site> sdnSiteList = list.stream().distinct().collect(Collectors.toList());
//
//                    if ((sdnSiteList != null) && (sdnSiteList.size() > 0)) {
//                        for (Site site : sdnSiteList) {
//                            layerInfoBean.getLayerSiteList().add(converter.fromEntity(siteType,
//                                    site, this.messageSource, locale));
//                        }
//                        if ((planLinkList != null) && (planLinkList.size() > 0)) {
//                            for (Link link : planLinkList) {
//                                if (link.getEndA() != null && link.getEndB() != null) {
//                                    for (Site site : sdnSiteList) {
//                                        if (link.getEndA().getStationName()
//                                                .equals(site.getStationName())
//                                                || link.getEndB().getStationName()
//                                                        .equals(site.getStationName())) {
//                                            isSegment = true;
//                                        }
//
//                                    }
//                                    if (isSegment) {
//                                        layerInfoBean.getLayerLinkList().add(converter
//                                                .fromEntityLink(link, this.messageSource, locale));
//                                        isSegment = false;
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                }
//
//            } else if (layer.equals("1")) {// 实际图层
//                int finishCount = 0, ongoingCount = 0, unfinishCount = 0;
//                LeaderHomePlanStieListBeanConvert converter =
//                        new LeaderHomePlanStieListBeanConvert();
//                
//                CommonFilter progSegFilter = new CommonFilter();
//                progSegFilter.addAnywhere("planName", "中继段_");
//
//
//                List<Plan> progSegmentList = this.planService.list(progSegFilter, null);
//                List<Link> progLinkList = this.linkService.list(null, null);
//
//                Project project = null;
//
//                CommonFilter projectFilter = new CommonFilter();
//                projectFilter.addExact("projectNum", PROJECT_NUM);
//
//                List<Project> projects = this.projectService.list(projectFilter, null);
//
//                if (!projects.isEmpty()) {
//                    project = projects.get(0);
//
//                }
//
//                if (siteType.equals("1")) {
//
//                    if (project != null) {
//                        CommonFilter orgFilter = new CommonFilter();
//                        orgFilter.addExact("project.id", project.getId());
//
//                        orgFilter.addAnywhere("virtualOrgName", "基础网络");
//
//                        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
//
//                        VirtualOrg v = null;
//
//                        if (!virtualOrgs.isEmpty()) {
//                            v = virtualOrgs.get(0);
//                            CommonFilter basicFilter = new CommonFilter()
//                                    .addExact("si.basicNetworkTransmitStationType", "1")
//                                    .addExact("v.id", v.getId());
//
//                            basicFilter.addAnywhere("planName", "基础网络传输组站点计划_");
//
//                            List<Plan> basicSiteList = this.planService.list(basicFilter, null);
//
//                            if ((basicSiteList != null) && (basicSiteList.size() > 0)) {
//                                for (Plan plan : basicSiteList) {
//
//                                    System.out.println("plan_name::::::" + plan.getPlanName());
//                                    Site site = plan.getSite();
//
//
//                                    if (site != null) {
//                                        System.out.println("site_name::::" + site.getStationName());
//                                        if (site.getBasicNetworkManualSiteStatus() != null) {
//                                            System.out.println("getBasicNetworkManualSiteStatus::::"
//                                                    + site.getBasicNetworkManualSiteStatus());
//                                            layerInfoBean.getLayerSiteList()
//                                                    .add(converter.fromEntityPro(siteType, null,
//                                                            site, this.messageSource, locale));
//                                            if (site.getBasicNetworkManualSiteStatus() == 0) {
//                                                unfinishCount++;
//                                            } else if (site
//                                                    .getBasicNetworkManualSiteStatus() == 1) {
//                                                ongoingCount++;
//                                            } else if (site
//                                                    .getBasicNetworkManualSiteStatus() == 2) {
//                                                finishCount++;
//                                            }
//                                        } else {
//                                            System.out.println("plan_id::::" + plan.getId());
//                                            CommonFilter basicStatusFilter = new CommonFilter()
//                                                    .addExact("p.id", plan.getId());
//
//                                            List<OrderByDto> orders = new ArrayList<>();
//                                            orders.add(new OrderByDto("taskNum", false));
//
//                                            List<PlanTask> statusList = this.planTaskService
//                                                    .list(basicStatusFilter, orders);
//
//                                            if ((statusList != null) && (statusList.size() > 0)) {
//                                                int constructionStatus = 0;
//                                                for (int i = 0; i < statusList.size(); i++) {
//                                                    PlanTask plTask = new PlanTask();
//
//                                                    plTask = statusList.get(i);
//
//                                                    System.out.println(
//                                                            "task_name::::" + plTask.getTaskName());
//                                                    if (plTask != null) {
//
//                                                        if (i == 0) {
//                                                            if (statusList.get(0)
//                                                                    .getProgress() == null
//                                                                    || statusList.get(0)
//                                                                            .getProgress() == 0.0) {
//
//                                                                System.out.println("i==0");
//
//                                                                constructionStatus = 0;
//                                                                break;
//                                                            }
//
//                                                        } else if (statusList.get(i)
//                                                                .getProgress() == null
//                                                                || statusList.get(i)
//                                                                        .getProgress() == 0) {
//
//                                                            System.out.println("i==1");
//                                                            if (statusList.get(i - 1)
//                                                                    .getConstructionStatus() != null) {
//                                                                constructionStatus = statusList
//                                                                        .get(i - 1)
//                                                                        .getConstructionStatus();
//                                                            } else {
//                                                                constructionStatus = 0;
//                                                            }
//                                                            break;
//
//                                                        } else if (statusList
//                                                                .get(statusList.size() - 1)
//                                                                .getProgress() != null
//                                                                && statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getProgress() != 0.0) {
//
//                                                            System.out.println("i==2");
//                                                            if (statusList
//                                                                    .get(statusList.size() - 1)
//                                                                    .getConstructionStatus() != null) {
//                                                                constructionStatus = statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getConstructionStatus();
//                                                            } else {
//                                                                constructionStatus = 0;
//                                                            }
//                                                            break;
//
//                                                        }
//                                                    }
//                                                }
//                                                layerInfoBean.getLayerSiteList()
//                                                        .add(converter.fromEntityPro(siteType,
//                                                                constructionStatus, site,
//                                                                this.messageSource, locale));
//                                                if (constructionStatus == 0) {
//                                                    unfinishCount++;
//                                                } else if (constructionStatus == 1) {
//                                                    ongoingCount++;
//                                                } else if (constructionStatus == 2) {
//                                                    finishCount++;
//                                                }
//                                            }
//
//                                        }
//                                    }
//
//                                }
//                                layerInfoBean.getStatusCountSiteList()
//                                        .add(converter.fromEntityCount(finishCount, ongoingCount,
//                                                unfinishCount, this.messageSource, locale));
//                                finishCount = 0;
//                                ongoingCount = 0;
//                                unfinishCount = 0;
//
//                                if ((progSegmentList != null) && (progSegmentList.size() > 0)) {
//                                    for (Plan segPlan : progSegmentList) {
//                                        Segment segment = segPlan.getSegment();
//                                        if (segment != null) {
//                                            if (segment.getManualSegmentStatus() != null) {
//                                                System.out.println("getManualSegmentStatus::::"
//                                                        + segment.getManualSegmentStatus());
//                                                layerInfoBean.getLayerSegmentList()
//                                                        .add(converter.fromEntitySeg(layer, segment,
//                                                                null, this.messageSource, locale));
//
//                                            } else {
//                                                System.out.println("plan_id::::" + segPlan.getId());
//                                                CommonFilter basicStatusFilter = new CommonFilter()
//                                                        .addExact("p.id", segPlan.getId());
//
//                                                List<OrderByDto> orders = new ArrayList<>();
//                                                orders.add(new OrderByDto("taskNum", false));
//
//                                                List<PlanTask> statusList = this.planTaskService
//                                                        .list(basicStatusFilter, orders);
//
//                                                if ((statusList != null)
//                                                        && (statusList.size() > 0)) {
//                                                    int manualSegmentStatus = 0;
//                                                    for (int i = 0; i < statusList.size(); i++) {
//                                                        PlanTask plTask = new PlanTask();
//
//                                                        plTask = statusList.get(i);
//
//                                                        System.out.println("task_name::::"
//                                                                + plTask.getTaskName());
//                                                        if (plTask != null) {
//
//                                                            if (i == 0) {
//                                                                if (statusList.get(0)
//                                                                        .getProgress() == null
//                                                                        || statusList.get(0)
//                                                                                .getProgress() == 0.0) {
//
//                                                                    System.out.println("i==0");
//
//                                                                    manualSegmentStatus = 0;
//                                                                    break;
//                                                                }
//
//                                                            } else if (statusList.get(i)
//                                                                    .getProgress() == null
//                                                                    || statusList.get(i)
//                                                                            .getProgress() == 0) {
//
//                                                                System.out.println("i==1");
//                                                                if (statusList.get(i - 1)
//                                                                        .getConstructionStatus() != null) {
//                                                                    manualSegmentStatus = statusList
//                                                                            .get(i - 1)
//                                                                            .getConstructionStatus();
//                                                                } else {
//                                                                    manualSegmentStatus = 0;
//                                                                }
//                                                                break;
//
//                                                            } else if (statusList
//                                                                    .get(statusList.size() - 1)
//                                                                    .getProgress() != null
//                                                                    && statusList
//                                                                            .get(statusList.size()
//                                                                                    - 1)
//                                                                            .getProgress() != 0.0) {
//
//                                                                System.out.println("i==2");
//                                                                if (statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getConstructionStatus() != null) {
//                                                                    manualSegmentStatus = statusList
//                                                                            .get(statusList.size()
//                                                                                    - 1)
//                                                                            .getConstructionStatus();
//                                                                } else {
//                                                                    manualSegmentStatus = 0;
//                                                                }
//                                                                break;
//
//                                                            }
//                                                        }
//                                                    }
//                                                    layerInfoBean.getLayerSegmentList()
//                                                            .add(converter.fromEntitySeg(layer,
//                                                                    segment, manualSegmentStatus,
//                                                                    this.messageSource, locale));
//
//                                                }
//                                            }
//                                        }
//
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                } else if (siteType.equals("2")) {
//                    if (project != null) {
//                        CommonFilter orgFilter = new CommonFilter();
//                        orgFilter.addExact("project.id", project.getId());
//
//                        orgFilter.addAnywhere("virtualOrgName", "可编程");
//                        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
//
//                        VirtualOrg v = null;
//
//                        if (!virtualOrgs.isEmpty()) {
//                            v = virtualOrgs.get(0);
//                            CommonFilter programFilter = new CommonFilter()
//                                    .addExact("si.basicNetworkTransmitStationType", "1")
//                                    .addExact("v.id", v.getId());
//                            programFilter.addAnywhere("planName", "可编程网络传输组站点计划_");
//
//                            List<Plan> programSiteList = this.planService.list(programFilter, null);
//
//                            if ((programSiteList != null) && (programSiteList.size() > 0)) {
//                                for (Plan plan : programSiteList) {
//                                    Site site = plan.getSite();
//                                    if (site != null) {
//
//                                        if (site.getProgrammableNetworkManualSiteStatus() != null) {
//                                            layerInfoBean.getLayerSiteList()
//                                                    .add(converter.fromEntityPro(siteType, null,
//                                                            site, this.messageSource, locale));
//                                            if (site.getProgrammableNetworkManualSiteStatus() == 0) {
//                                                unfinishCount++;
//                                            } else if (site
//                                                    .getProgrammableNetworkManualSiteStatus() == 1) {
//                                                ongoingCount++;
//                                            } else if (site
//                                                    .getProgrammableNetworkManualSiteStatus() == 2) {
//                                                finishCount++;
//                                            }
//                                        } else {
//                                            CommonFilter programStatusFilter = new CommonFilter()
//                                                    .addExact("p.id", plan.getId());
//
//                                            List<OrderByDto> orders = new ArrayList<>();
//                                            orders.add(new OrderByDto("taskNum", false));
//
//                                            List<PlanTask> statusList = this.planTaskService
//                                                    .list(programStatusFilter, orders);
//
//                                            if ((statusList != null) && (statusList.size() > 0)) {
//                                                int constructionStatus = 0;
//                                                for (int i = 0; i < statusList.size(); i++) {
//                                                    PlanTask plTask = new PlanTask();
//
//                                                    plTask = statusList.get(i);
//                                                    if (plTask != null) {
//
//                                                        if (i == 0) {
//                                                            if (statusList.get(0)
//                                                                    .getProgress() == null
//                                                                    || statusList.get(0)
//                                                                            .getProgress() == 0.0) {
//                                                                constructionStatus = 0;
//                                                                break;
//                                                            }
//
//                                                        } else if (statusList.get(i)
//                                                                .getProgress() == null
//                                                                || statusList.get(i)
//                                                                        .getProgress() == 0) {
//                                                            if (statusList.get(i - 1)
//                                                                    .getConstructionStatus() != null) {
//                                                                constructionStatus = statusList
//                                                                        .get(i - 1)
//                                                                        .getConstructionStatus();
//                                                            } else {
//                                                                constructionStatus = 0;
//                                                            }
//
//                                                            break;
//
//                                                        } else if (statusList
//                                                                .get(statusList.size() - 1)
//                                                                .getProgress() != null
//                                                                && statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getProgress() != 0.0) {
//                                                            if (statusList
//                                                                    .get(statusList.size() - 1)
//                                                                    .getConstructionStatus() != null) {
//                                                                constructionStatus = statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getConstructionStatus();
//                                                            } else {
//                                                                constructionStatus = 0;
//                                                            }
//
//                                                            break;
//
//                                                        }
//                                                    }
//                                                }
//
//                                                layerInfoBean.getLayerSiteList()
//                                                        .add(converter.fromEntityPro(siteType,
//                                                                constructionStatus, site,
//                                                                this.messageSource, locale));
//
//                                                if (constructionStatus == 0) {
//                                                    unfinishCount++;
//                                                } else if (constructionStatus == 1) {
//                                                    ongoingCount++;
//                                                } else if (constructionStatus == 2) {
//                                                    finishCount++;
//                                                }
//                                            }
//
//                                        }
//                                    }
//                                }
//                                layerInfoBean.getStatusCountSiteList()
//                                        .add(converter.fromEntityCount(finishCount, ongoingCount,
//                                                unfinishCount, this.messageSource, locale));
//                                finishCount = 0;
//                                ongoingCount = 0;
//                                unfinishCount = 0;
//                                if ((progLinkList != null) && (progLinkList.size() > 0)) {
//                                    for (Link link : progLinkList) {
//                                        if (link.getEndA() != null && link.getEndB() != null) {
//                                            for (Plan plan : programSiteList) {
//                                                Site site = plan.getSite();
//                                                if (site != null) {
//                                                    if (link.getEndA().getStationName()
//                                                            .equals(site.getStationName())
//                                                            || link.getEndB().getStationName()
//                                                                    .equals(site
//                                                                            .getStationName())) {
//                                                        isSegment = true;
//                                                    }
//                                                }
//                                            }
//                                            if (isSegment) {
//                                                layerInfoBean.getLayerLinkList()
//                                                        .add(converter.fromEntityLink(link,
//                                                                this.messageSource, locale));
//                                                isSegment = false;
//                                            }
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                } else if (siteType.equals("3")) {
//
//                    if (project != null) {
//                        CommonFilter orgFilter = new CommonFilter();
//                        orgFilter.addExact("project.id", project.getId());
//
//                        orgFilter.addAnywhere("virtualOrgName", "SDN");
//                        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
//
//                        VirtualOrg v = null;
//
//                        if (!virtualOrgs.isEmpty()) {
//                            v = virtualOrgs.get(0);
//                            CommonFilter sdnFilter = new CommonFilter()
//                                    .addExact("si.basicNetworkTransmitStationType", "1")
//                                    .addExact("v.id", v.getId());
//
//                            sdnFilter.addAnywhere("planName", "SDN网络传输组站点计划_");
//                            List<Plan> sdnSiteList = this.planService.list(sdnFilter, null);
//
//                            if ((sdnSiteList != null) && (sdnSiteList.size() > 0)) {
//                                for (Plan plan : sdnSiteList) {
//                                    Site site = plan.getSite();
//                                    if (site != null) {
//
//                                        if (site.getSdnNetworkManualSiteStatus() != null) {
//
//                                            layerInfoBean.getLayerSiteList()
//                                                    .add(converter.fromEntityPro(siteType, null,
//                                                            site, this.messageSource, locale));
//
//                                            if (site.getSdnNetworkManualSiteStatus() == 0) {
//                                                unfinishCount++;
//                                            } else if (site.getSdnNetworkManualSiteStatus() == 1) {
//                                                ongoingCount++;
//                                            } else if (site.getSdnNetworkManualSiteStatus() == 2) {
//                                                finishCount++;
//                                            }
//                                        } else {
//                                            CommonFilter sdnStatusFilter = new CommonFilter()
//                                                    .addExact("p.id", plan.getId());
//
//                                            List<OrderByDto> orders = new ArrayList<>();
//                                            orders.add(new OrderByDto("taskNum", false));
//
//                                            List<PlanTask> statusList = this.planTaskService
//                                                    .list(sdnStatusFilter, orders);
//
//                                            if ((statusList != null) && (statusList.size() > 0)) {
//                                                int constructionStatus = 0;
//                                                for (int i = 0; i < statusList.size(); i++) {
//                                                    PlanTask plTask = new PlanTask();
//
//                                                    plTask = statusList.get(i);
//                                                    if (plTask != null) {
//
//                                                        if (i == 0) {
//                                                            if (statusList.get(0)
//                                                                    .getProgress() == null
//                                                                    || statusList.get(0)
//                                                                            .getProgress() == 0.0) {
//                                                                constructionStatus = 0;
//                                                                break;
//                                                            }
//
//                                                        } else if (statusList.get(i)
//                                                                .getProgress() == null
//                                                                || statusList.get(i)
//                                                                        .getProgress() == 0) {
//                                                            if (statusList.get(i - 1)
//                                                                    .getConstructionStatus() != null) {
//                                                                constructionStatus = statusList
//                                                                        .get(i - 1)
//                                                                        .getConstructionStatus();
//                                                            } else {
//                                                                constructionStatus = 0;
//                                                            }
//                                                            break;
//
//                                                        } else if (statusList
//                                                                .get(statusList.size() - 1)
//                                                                .getProgress() != null
//                                                                && statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getProgress() != 0.0) {
//                                                            if (statusList
//                                                                    .get(statusList.size() - 1)
//                                                                    .getConstructionStatus() != null) {
//                                                                constructionStatus = statusList
//                                                                        .get(statusList.size() - 1)
//                                                                        .getConstructionStatus();
//                                                            } else {
//                                                                constructionStatus = 0;
//                                                            }
//                                                            break;
//
//                                                        }
//                                                    }
//                                                }
//                                                layerInfoBean.getLayerSiteList()
//                                                        .add(converter.fromEntityPro(siteType,
//                                                                constructionStatus, site,
//                                                                this.messageSource, locale));
//                                                if (constructionStatus == 0) {
//                                                    unfinishCount++;
//                                                } else if (constructionStatus == 1) {
//                                                    ongoingCount++;
//                                                } else if (constructionStatus == 2) {
//                                                    finishCount++;
//                                                }
//                                            }
//
//                                        }
//                                    }
//                                }
//                                layerInfoBean.getStatusCountSiteList()
//                                        .add(converter.fromEntityCount(finishCount, ongoingCount,
//                                                unfinishCount, this.messageSource, locale));
//                                finishCount = 0;
//                                ongoingCount = 0;
//                                unfinishCount = 0;
//                                
//                                if ((progLinkList != null) && (progLinkList.size() > 0)) {
//                                    for (Link link : progLinkList) {
//                                        if (link.getEndA() != null && link.getEndB() != null) {
//                                            for (Plan plan : sdnSiteList) {
//                                                Site site = plan.getSite();
//                                                if (site != null) {
//                                                    if (link.getEndA().getStationName()
//                                                            .equals(site.getStationName())
//                                                            || link.getEndB().getStationName()
//                                                                    .equals(site
//                                                                            .getStationName())) {
//                                                        isSegment = true;
//                                                    }
//                                                }
//
//                                            }
//                                            if (isSegment) {
//                                                layerInfoBean.getLayerLinkList()
//                                                        .add(converter.fromEntityLink(link,
//                                                                this.messageSource, locale));
//                                                isSegment = false;
//                                            }
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//
//            }
//
//            if (!segSystemName.isEmpty() && segSystemName != null) {
//                CommonFilter filter = new CommonFilter().addWithin("systemName", segSystemName);
//                List<Segment> segmentList = this.segementService.list(filter, null);
//                LeaderHomePlanStieListBeanConvert converter =
//                        new LeaderHomePlanStieListBeanConvert();
//
//                if ((segmentList != null) && (segmentList.size() > 0)) {
//                    for (Segment segment : segmentList) {
//                        if (segment != null) {
//                            layerInfoBean.getFiltrateSegmentList().add(converter.fromEntitySeg("",
//                                    segment, null, this.messageSource, locale));
//                        }
//                    }
//                }
//            }
//
//            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, layerInfoBean);
//
//            // 准备JSON结果
//            jsonResult.setStatus(JsonStatus.OK);
//
//        } catch (
//
//        Exception e) {
//            logger.error("Exception LeaderHome", e);
//            // 在JSON对象内设定服务器处理结果状态
//            jsonResult.setStatus(JsonStatus.ERROR);
//        }
//        
//        logger.info("态势图数据请求结束");
//
//        return jsonResult;
//
//    }

    @RequestMapping("/site-chart-info")
    @ResponseBody
    public JsonResultBean getSiteChartInfo(
            @RequestParam(required = true, defaultValue = "") String siteId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            LeaderHomeSiteInfoBean mapSiteInfoBean = new LeaderHomeSiteInfoBean();

            CommonFilter f = new CommonFilter();
            f.addExact("si.id", siteId);
            List<MapSiteListBean> mapSiteList = new ArrayList<MapSiteListBean>();
            List<Plan> plans = planService.list(f, null);
            if ((plans != null) && (plans.size() > 0)) {
                MapSiteListBeanConverter converter = new MapSiteListBeanConverter();

                for (Plan p : plans) {
                    mapSiteList.add(converter.fromEntity(p.getSite(), this.messageSource, locale));
                }

                mapSiteInfoBean.setMapSiteList(mapSiteList);
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mapSiteInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception getSiteSegmentProcess", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }

    @RequestMapping("/site-segment-plan")
    @ResponseBody
    public JsonResultBean getSiteSegmentPlan(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            LeaderHomeSiteInfoBean mapSiteInfoBean = new LeaderHomeSiteInfoBean();

            String userid = userBean.getSessionUserId();
            CommonFilter f = new CommonFilter();
            f.addExact("si.siteType", SiteTypeEnum.CORE_NODE.value());
            List<MapSiteListBean> mapSiteList = new ArrayList<MapSiteListBean>();
            List<Plan> plans = planService.list(f, null);
            if ((plans != null) && (plans.size() > 0)) {
                MapSiteListBeanConverter converter = new MapSiteListBeanConverter();

                for (Plan p : plans) {
                    mapSiteList.add(converter.fromEntity(p.getSite(), this.messageSource, locale));
                }

                mapSiteInfoBean.setMapSiteList(mapSiteList);
            }

            f = new CommonFilter().addExact("sA.siteType", SiteTypeEnum.CORE_NODE.value())
                    .addExact("sB.siteType", SiteTypeEnum.CORE_NODE.value());
            List<MapSegmentListBean> mapSegmentList = new ArrayList<MapSegmentListBean>();
            plans = planService.list(f, null);
            if ((plans != null) && (plans.size() > 0)) {
                MapSegmentListBeanConverter converter = new MapSegmentListBeanConverter();

                for (Plan p : plans) {
                    mapSegmentList
                            .add(converter.fromEntity(p.getSegment(), this.messageSource, locale));
                }

                mapSiteInfoBean.setMapSegmentList(mapSegmentList);
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mapSiteInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception getSiteSegmentPlan", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }

    @RequestMapping("/site-segment-process")
    @ResponseBody
    public JsonResultBean getSiteSegmentProcess(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            LeaderHomeSiteInfoBean mapSiteInfoBean = new LeaderHomeSiteInfoBean();

            String userid = userBean.getSessionUserId();
            CommonFilter f = new CommonFilter();
            f.addExact("si.siteType", SiteTypeEnum.CORE_NODE.value());
            List<MapSiteListBean> mapSiteList = new ArrayList<MapSiteListBean>();
            List<Plan> plans = planService.list(f, null);
            if ((plans != null) && (plans.size() > 0)) {
                MapSiteListBeanConverter converter = new MapSiteListBeanConverter();

                for (Plan p : plans) {
                    mapSiteList.add(converter.fromEntity(p.getSite(), this.messageSource, locale));
                }

                mapSiteInfoBean.setMapSiteList(mapSiteList);
            }

            f = new CommonFilter().addExact("sA.siteType", SiteTypeEnum.CORE_NODE.value())
                    .addExact("sB.siteType", SiteTypeEnum.CORE_NODE.value());
            List<MapSegmentListBean> mapSegmentList = new ArrayList<MapSegmentListBean>();
            plans = planService.list(f, null);
            if ((plans != null) && (plans.size() > 0)) {
                MapSegmentListBeanConverter converter = new MapSegmentListBeanConverter();

                for (Plan p : plans) {
                    mapSegmentList
                            .add(converter.fromEntity(p.getSegment(), this.messageSource, locale));
                }

                mapSiteInfoBean.setMapSegmentList(mapSegmentList);
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mapSiteInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception getSiteSegmentProcess", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }



}
