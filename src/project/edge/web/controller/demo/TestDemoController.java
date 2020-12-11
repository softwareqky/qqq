package project.edge.web.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Demo画面。
 * 
 */
@Controller
@RequestMapping("/demo")
public class TestDemoController {

    @RequestMapping("/tab-content")
    public String demoTabContent() {
        return "demo/tabContent";
    }


    @RequestMapping("/schedule")
    public String demoSchedule() {
        return "schedule/plantask";
    }

    // @RequestMapping("/demo/bidding-management")
    // public String demoBiddingManagement(@ModelAttribute(Constants.SESSION_USER) SessionUserBean
    // userBean,
    // Model model, HttpServletResponse response) {
    //
    // SimpleCache tmpCache = SimpleCache.instance();
    // List<ProjectInfoBean> projectInfoBeanList = new ArrayList<>();
    //
    // for(int i = 0;i< 10;i++) {
    // ProjectInfoBean p = new ProjectInfoBean();
    // p.setId("projectinfo" + i);
    // p.setProjectState("投标");
    // p.setNewState("中标");
    // p.setProjectManager("陌生人");
    // p.setProjectNo("T20130516002" + i);
    // p.setProjectName("武汉光谷展厅项目"+ i);
    // p.setConstructingUnits("江苏南通农村商业银行股份有限公司");
    // p.setConstructionCost("12,000.00");
    // p.setAdvanceCycle("60日历天");
    // p.setAdvanceFundsAmount("0.000");
    // p.setProfitMargin("10%");
    // p.setEngineeringCycle("150日历天");
    // p.setProcessState("已审核");
    // p.setApplicationDate("2019-09-30");
    //
    // projectInfoBeanList.add(p);
    // }
    //
    // tmpCache.put("projectInfoBeanList", projectInfoBeanList);
    //
    // model.addAttribute("projectInfo",tmpCache.get("projectInfoBeanList"));
    //
    // return "demo/biddingManagement";
    // }

    // @RequestMapping("/demo/project-state-change")
    // public String demoProjectStateChange() {
    // return "demo/projectStateChange";
    // }

    // @RequestMapping("/demo/monthly-expenditure-plan-report")
    // public String demoMonthlyExpenditurePlanReport() {
    // return "demo/monthlyExpenditurePlanReport";
    // }

    // @RequestMapping("/demo/cost-recorder")
    // public String demoCostRecorder() {
    // return "demo/costRecorder";
    // }

    // @RequestMapping("/demo/bidding-management")
    // public String demoBiddingManagement(@ModelAttribute(Constants.SESSION_USER) SessionUserBean
    // userBean,
    // Model model, HttpServletResponse response) {
    //
    // SimpleCache tmpCache = SimpleCache.instance();
    // List<ProjectInfoBean> projectInfoBeanList = new ArrayList<>();
    //
    // for(int i = 0;i< 10;i++) {
    // ProjectInfoBean p = new ProjectInfoBean();
    // p.setId("projectinfo" + i);
    // p.setProjectState("投标");
    // p.setNewState("中标");
    // p.setProjectManager("陌生人");
    // p.setProjectNo("T20130516002" + i);
    // p.setProjectName("武汉光谷展厅项目"+ i);
    // p.setConstructingUnits("江苏南通农村商业银行股份有限公司");
    // p.setConstructionCost("12,000.00");
    // p.setAdvanceCycle("60日历天");
    // p.setAdvanceFundsAmount("0.000");
    // p.setProfitMargin("10%");
    // p.setEngineeringCycle("150日历天");
    // p.setProcessState("已审核");
    // p.setApplicationDate("2019-09-30");
    //
    // projectInfoBeanList.add(p);
    // }
    //
    // tmpCache.put("projectInfoBeanList", projectInfoBeanList);
    //
    // model.addAttribute("projectInfo",tmpCache.get("projectInfoBeanList"));
    //
    // return "demo/biddingManagement";
    // }

    // @RequestMapping("/demo/project-state-change")
    // public String demoProjectStateChange() {
    // return "demo/projectStateChange";
    // }

    // @RequestMapping("/demo/monthly-expenditure-plan-report")
    // public String demoMonthlyExpenditurePlanReport() {
    // return "demo/monthlyExpenditurePlanReport";
    // }

    // @RequestMapping("/demo/cost-recorder")
    // public String demoCostRecorder() {
    // return "demo/costRecorder";
    // }

    @RequestMapping("/test")
    public String demoTest() {
        return "demo/test";
    }
    
    @RequestMapping("/map")
    public String demoMap() {
        return "demo/map";
    }

}
