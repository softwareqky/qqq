package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存部门信息的表现层DTO。
 */
public class DeptBean implements ViewBean {

    private String id;

    private String org_;
    private String orgText;
    private String deptName;
    private String shortName;
    private String deptCode;
    private String pid;
    private int level;
    private int deptOrder;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrg_() {
        return this.org_;
    }


    public void setOrg_(String org_) {
        this.org_ = org_;
    }


    public String getOrgText() {
        return this.orgText;
    }


    public void setOrgText(String orgText) {
        this.orgText = orgText;
    }


    public String getDeptName() {
        return this.deptName;
    }


    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public String getShortName() {
        return this.shortName;
    }


    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getDeptCode() {
        return this.deptCode;
    }

    
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getPid() {
        return this.pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }


    public int getLevel() {
        return this.level;
    }


    public void setLevel(int level) {
        this.level = level;
    }


    public int getDeptOrder() {
        return this.deptOrder;
    }


    public void setDeptOrder(int deptOrder) {
        this.deptOrder = deptOrder;
    }



}
