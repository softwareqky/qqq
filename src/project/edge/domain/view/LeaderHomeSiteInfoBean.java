package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angel_000
 *
 */
public class LeaderHomeSiteInfoBean {

    private List<MapSiteListBean> mapSiteList = new ArrayList<>();
    private List<MapSegmentListBean> mapSegmentList = new ArrayList<>();

    public List<MapSiteListBean> getMapSiteList() {
        return this.mapSiteList;
    }

    public void setMapSiteList(List<MapSiteListBean> mapSiteList) {
        this.mapSiteList = mapSiteList;
    }

    public List<MapSegmentListBean> getMapSegmentList() {
        return this.mapSegmentList;
    }

    public void setMapSegmentList(List<MapSegmentListBean> mapSegmentList) {
        this.mapSegmentList = mapSegmentList;
    }

}
