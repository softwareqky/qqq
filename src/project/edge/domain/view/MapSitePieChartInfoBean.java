package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angel_000
 *
 */
public class MapSitePieChartInfoBean {

    private List<MapSitePieChartListBean> basicCoreNodeSiteList = new ArrayList<>();

    private List<MapSitePieChartListBean> basicHighCoreSiteList = new ArrayList<>();
    private List<MapSitePieChartListBean> basicHighRelaySiteList = new ArrayList<>();

    private List<MapSitePieChartListBean> programmableCoreSiteList = new ArrayList<>();
    private List<MapSitePieChartListBean> programmableEdgeSiteList = new ArrayList<>();

    private List<MapSitePieChartListBean> sdnCoreSiteList = new ArrayList<>();
    private List<MapSitePieChartListBean> sdnEdgeSiteList = new ArrayList<>();

    public List<MapSitePieChartListBean> getBasicCoreNodeSiteList() {
        return this.basicCoreNodeSiteList;
    }

    public void setBasicCoreNodeSiteList(List<MapSitePieChartListBean> basicCoreNodeSiteList) {
        this.basicCoreNodeSiteList = basicCoreNodeSiteList;
    }

    public List<MapSitePieChartListBean> getBasicHighCoreSiteList() {
        return this.basicHighCoreSiteList;
    }

    public void setBasicHighCoreSiteList(List<MapSitePieChartListBean> basicHighCoreSiteList) {
        this.basicHighCoreSiteList = basicHighCoreSiteList;
    }

    public List<MapSitePieChartListBean> getBasicHighRelaySiteList() {
        return this.basicHighRelaySiteList;
    }

    public void setBasicHighRelaySiteList(List<MapSitePieChartListBean> basicHighRelaySiteList) {
        this.basicHighRelaySiteList = basicHighRelaySiteList;
    }

    public List<MapSitePieChartListBean> getProgrammableCoreSiteList() {
        return this.programmableCoreSiteList;
    }

    public void setProgrammableCoreSiteList(
            List<MapSitePieChartListBean> programmableCoreSiteList) {
        this.programmableCoreSiteList = programmableCoreSiteList;
    }

    public List<MapSitePieChartListBean> getProgrammableEdgeSiteList() {
        return this.programmableEdgeSiteList;
    }

    public void setProgrammableEdgeSiteList(
            List<MapSitePieChartListBean> programmableEdgeSiteList) {
        this.programmableEdgeSiteList = programmableEdgeSiteList;
    }

    public List<MapSitePieChartListBean> getSdnCoreSiteList() {
        return this.sdnCoreSiteList;
    }

    public void setSdnCoreSiteList(List<MapSitePieChartListBean> sdnCoreSiteList) {
        this.sdnCoreSiteList = sdnCoreSiteList;
    }

    public List<MapSitePieChartListBean> getSdnEdgeSiteList() {
        return this.sdnEdgeSiteList;
    }

    public void setSdnEdgeSiteList(List<MapSitePieChartListBean> sdnEdgeSiteList) {
        this.sdnEdgeSiteList = sdnEdgeSiteList;
    }

}
