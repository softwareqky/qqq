/**
 * 
 */
package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *         保存人员信息的表现层DTO。
 */
public class UserBean implements ViewBean {

    private String id;

    private String role_;
    private String roleText;
    private String person_;
    private String personText;
    private String loginName;
    private short isSystem;
    private String isSystemText;
    private short isSuper;
    private String isSuperText;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_() {
        return this.role_;
    }

    public void setRole_(String role_) {
        this.role_ = role_;
    }

    public String getRoleText() {
        return this.roleText;
    }

    public void setRoleText(String roleText) {
        this.roleText = roleText;
    }

    public String getPerson_() {
        return this.person_;
    }

    public void setPerson_(String person_) {
        this.person_ = person_;
    }

    public String getPersonText() {
        return this.personText;
    }

    public void setPersonText(String personText) {
        this.personText = personText;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public short getIsSystem() {
        return this.isSystem;
    }

    public void setIsSystem(short isSystem) {
        this.isSystem = isSystem;
    }

    public String getIsSystemText() {
        return this.isSystemText;
    }

    public void setIsSystemText(String isSystemText) {
        this.isSystemText = isSystemText;
    }

    public short getIsSuper() {
        return this.isSuper;
    }

    public void setIsSuper(short isSuper) {
        this.isSuper = isSuper;
    }

    public String getIsSuperText() {
        return this.isSuperText;
    }

    public void setIsSuperText(String isSuperText) {
        this.isSuperText = isSuperText;
    }

}
