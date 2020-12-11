package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;


public class EmployeeInfoBean implements ViewBean {

    private String id;
    private String department;
    private String name;
    private String station;
    private Date joinedDate;
    private Integer age;
    private String status;
    private String major;
    private String rank;
    private Boolean isDivisionDirector;
    private String reason4Leaving;
    private String remark;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getDepartment() {
        return this.department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getStation() {
        return this.station;
    }


    public void setStation(String station) {
        this.station = station;
    }


    public Date getJoinedDate() {
        return this.joinedDate;
    }


    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }


    public Integer getAge() {
        return this.age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    public String getStatus() {
        return this.status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Boolean getIsDivisionDirector() {
        return this.isDivisionDirector;
    }


    public void setIsDivisionDirector(Boolean isDivisionDirector) {
        this.isDivisionDirector = isDivisionDirector;
    }


    public String getReason4Leaving() {
        return this.reason4Leaving;
    }


    public void setReason4Leaving(String reason4Leaving) {
        this.reason4Leaving = reason4Leaving;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

}
