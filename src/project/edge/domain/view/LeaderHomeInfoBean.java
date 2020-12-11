package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import project.edge.domain.entity.SegmentSystemName;

/**
 * @author angel_000
 *
 */
public class LeaderHomeInfoBean {

    private List<SegmentSystemName> segSystemName = new ArrayList<>();
    private List<LeaderHomePieChartListBean> pieChart = new ArrayList<>();


    public List<SegmentSystemName> getSegSystemName() {
        return this.segSystemName;
    }


    public void setSegSystemName(List<SegmentSystemName> segSystemName) {
        this.segSystemName = segSystemName;
    }

    public List<LeaderHomePieChartListBean> getPieChart() {
        return this.pieChart;
    }

    public void setPieChart(List<LeaderHomePieChartListBean> pieChart) {
        this.pieChart = pieChart;
    }

}
