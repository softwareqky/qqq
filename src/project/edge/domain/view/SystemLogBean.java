/**
 * 
 */
package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;
import project.edge.domain.entity.Person;


/**
 * @author angel_000
 *         保存人员信息的表现层DTO。
 */
public class SystemLogBean implements ViewBean {

    private String id;

    private String loginName;
    private String userIp;
    private String personName;
    private String action;
    private String description;
    private String cDatetime;
    private String remark;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getAction() {
        return this.action;
    }

    public void setKind(String action) {
        this.action = action;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getcDatetime() {
        return cDatetime;
    }

    public void setcDatetime(String cDatetime) {
        this.cDatetime = cDatetime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
