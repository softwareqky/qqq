package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angel_000
 *
 */
public class MapDefinedIconBean {

    private List<DefinedSiteIconBean> siteIconList = new ArrayList<>();
    private List<DefinedSegmentIconBean> segmentIconList = new ArrayList<>();

    public List<DefinedSiteIconBean> getSiteIconList() {
        return this.siteIconList;
    }

    public void setSiteIconList(List<DefinedSiteIconBean> siteIconList) {
        this.siteIconList = siteIconList;
    }

    public List<DefinedSegmentIconBean> getSegmentIconList() {
        return this.segmentIconList;
    }

    public void setSegmentIconList(List<DefinedSegmentIconBean> segmentIconList) {
        this.segmentIconList = segmentIconList;
    }


}
