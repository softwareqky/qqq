package project.edge.domain.filter;

import garage.origin.domain.filter.CommonFilter;

/**
 * 画面的过滤器
 */
public class PageFilter extends CommonFilter {

    /** 权限ID */
    private String roleId;

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
