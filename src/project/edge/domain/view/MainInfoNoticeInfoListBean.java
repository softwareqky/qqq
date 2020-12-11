package project.edge.domain.view;

/**
 * @author angel_000
 *
 */
public class MainInfoNoticeInfoListBean {

    private String id;
    private String noticeTitle;
    private String originator;
    private String time;
    private String detail;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getOriginator() {
        return this.originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
