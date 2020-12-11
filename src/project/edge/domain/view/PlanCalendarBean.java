package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存计划日历信息的表现层DTO。
 */
public class PlanCalendarBean implements ViewBean {

    private String id;
    private String calendarName;
    private String weekdays;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }


    public String getCalendarName() {
        return this.calendarName;
    }


    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }


    public String getWeekdays() {
        return this.weekdays;
    }


    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }


    public void setId(String id) {
        this.id = id;
    }

}
