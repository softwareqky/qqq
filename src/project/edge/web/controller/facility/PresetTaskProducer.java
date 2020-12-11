package project.edge.web.controller.facility;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PresetTask;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PresetTaskService;

@Component
public class PresetTaskProducer {

    // 未来网络试验设施国家重大科技基础设施
    private static final String PROJECT_NUM = "FNII20200103001";

    @Resource
    private ProjectService projectService;

    @Resource
    private PresetTaskService presetTaskService;
    
    @Resource
    private VirtualOrgService virtualOrgService;

    @Resource
    private PlanService planService;
    
//    @Resource
//    private PersonService personService;

    public void generatePlanTask(Object entity, String planName, CommonFilter filter,
            Date cDatetime, String creator, int type) {

        Project project = null;

        CommonFilter projectFilter = new CommonFilter();
        projectFilter.addExact("projectNum", PROJECT_NUM);

        List<Project> projects = this.projectService.list(projectFilter, null);

        if (!projects.isEmpty()) {
            project = projects.get(0);
        }

        if (project == null) {
            return;
        }

        Date now = cDatetime;
        Date tDate = null;

        Plan plan = new Plan();

        if (entity instanceof Site) {

            Site site = ((Site) entity);

            if (type == 2) {
            	if (site.getEdgeNodeType() != null && site.getEdgeNodeType().getId().equals("EDGE_NODE_TYPE_1")) {
            		tDate = site.getProgrammableEdgeNodeInitMaterialArchieveDate();
            	} else {
            		tDate = site.getProgrammableInitMaterialArchieveDate();
            	}
            } else if (type == 3) {
            	if (site.getEdgeNodeType() != null && site.getEdgeNodeType().getId().equals("EDGE_NODE_TYPE_2")) {
            		tDate = site.getSdnEdgeNodeInitMaterialArchieveDate();
            	} else {
            		tDate = site.getSdnInitMaterialArchieveDate();
            	}
            }
            
//            if (site.getEdgeNodeType() != null) {
//            	if (site.getEdgeNodeType().getId().equals("EDGE_NODE_TYPE_1")) {
//            		tDate = site.getProgrammableEdgeNodeInitMaterialArchieveDate();
//            	} else if (site.getEdgeNodeType().getId().equals("EDGE_NODE_TYPE_2")) {
//            		tDate = site.getSdnEdgeNodeInitMaterialArchieveDate();
//            	}
//            }
            
            if (tDate == null) {
            	tDate = site.getInitMaterialArchieveDate();
            }
            
            plan.setSite(site);
            plan.setPlanVersion("00.00.01");
            plan.setPersonInCharge(site.getPersonInCharge());
            String tmp = DateUtils.date2String(site.getInitMaterialArchieveDate(), Constants.DATE_FORMAT);
            if(tmp != null) {
                plan.setPlanYear(tmp.split("-")[0]);
            }
            
            CommonFilter orgFilter = new CommonFilter();
            orgFilter.addExact("project.id", project.getId());
            
            if(type == 1) {                
                orgFilter.addAnywhere("virtualOrgName", "基础网络组");                
            }else if(type == 2){
                orgFilter.addAnywhere("virtualOrgName", "可编程网络组");
            }else if(type == 3) {
                orgFilter.addAnywhere("virtualOrgName", "SDN网络组");
            }
            
            if ("1".equals(site.getBasicNetworkTransmitStationType())) {
            	
            }
            
            List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
            
            VirtualOrg v = null;
            
            if(!virtualOrgs.isEmpty()) {
                v = virtualOrgs.get(0);
            }
            
            if(v != null) {
                plan.setVirtualOrg(v);
            }
            
        } else if (entity instanceof Segment) {

            Segment segment = ((Segment) entity);

            tDate = segment.getInitMaterialArchieveDate();
            plan.setSegment(segment);
            plan.setPlanVersion("00.00.01");
            plan.setPersonInCharge(segment.getPersonInCharge());
            String tmp = DateUtils.date2String(segment.getInitMaterialArchieveDate(), Constants.DATE_FORMAT);
            if(tmp != null) {
                plan.setPlanYear(tmp.split("-")[0]);
            }

            CommonFilter orgFilter = new CommonFilter();
            orgFilter.addExact("project.id", project.getId());
            
//            if(planName.indexOf("基础网络") >= 0) {                
                orgFilter.addAnywhere("virtualOrgName", "基础网络组");
//            }
            
            List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
            
            VirtualOrg v = null;
            
            if(!virtualOrgs.isEmpty()) {
                v = virtualOrgs.get(0);
            }
            
            if(v != null) {
                plan.setVirtualOrg(v);
            }
            
        } else if (entity instanceof Link) {
            tDate = now;
        }

        List<OrderByDto> orders = new ArrayList<>();
        orders.add(new OrderByDto("taskNum", false));

        List<PresetTask> presetTasks = this.presetTaskService.list(filter, orders);

        if (presetTasks.isEmpty()) {
            return;
        }

        // plan.setSite(site);
        plan.setProject(project);
        plan.setIsDeleted(OnOffEnum.OFF.value());
        plan.setPlanName(planName);
        
        plan.setIsYear(0);

        
//        Person p = this.personService.find(creator);
//        if(p != null) {
//            plan.setCreator(p);
//        }
        
        if (!StringUtils.isEmpty(creator)) {
            Person person = new Person();
            person.setId(creator);
            plan.setCreator(person);
        }
        
        plan.setcDatetime(now);
        plan.setmDatetime(now);

        List<PlanTask> planTasks = new ArrayList<>();

        for (PresetTask preTask : presetTasks) {

            if (StringUtils.isEmpty(preTask.getTaskName())
                    || StringUtils.isEmpty(preTask.getStartDateDayOffset())
                    || StringUtils.isEmpty(preTask.getDurationDays())) {
                continue;
            }

            PlanTask planTask = new PlanTask();
            planTask.setPlan(plan);
            planTask.setIsDeleted(OnOffEnum.OFF.value());
            planTask.setCreator(creator);
            planTask.setcDatetime(now);

            planTask.setmDatetime(now);

            planTask.setTaskNum(preTask.getTaskNum());
            planTask.setTaskName(preTask.getTaskName());
            planTask.setPid("0");
            planTask.setTaskLayer(1);
            planTask.setWbs(String.valueOf(preTask.getTaskNum()));
            planTask.setTaskType(preTask.getTaskType());
            planTask.setDurationDays(preTask.getDurationDays());
            planTask.setIsSiteTask(OnOffEnum.ON.value());

            Calendar initCalendar = Calendar.getInstance();
            initCalendar.setTime(tDate);
            initCalendar.add(Calendar.DATE, preTask.getStartDateDayOffset());
            planTask.setPlanStartDate(initCalendar.getTime());
            initCalendar.add(Calendar.DATE, preTask.getDurationDays());
            planTask.setPlanEndDate(initCalendar.getTime());
            
            if(preTask.getConstructionStatus() != null) {
                planTask.setConstructionStatus(preTask.getConstructionStatus()); 
            }


            planTasks.add(planTask);
        }

        if (!planTasks.isEmpty()) {
            this.planService.createPresetPlan(plan, planTasks);
        }
    }

}
