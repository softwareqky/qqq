package project.edge.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import garage.origin.domain.entity.AbstractPage;

@Entity
@Table(name = "t_page")
public class Page extends AbstractPage implements java.io.Serializable {

    private static final long serialVersionUID = 1384433632606398708L;

    private String dataType;
    // private int location;
    private short isWeb;
    private short isApp;
    private short isHasFlowable;
    private String flowableEntity;

    private Set<Role> roles = new HashSet<Role>(0);

    @Column(name = "data_type", nullable = true, length = 30)
    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    // @Column(name = "location", nullable = false)
    // public int getLocation() {
    // return this.location;
    // }
    //
    // public void setLocation(int location) {
    // this.location = location;
    // }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_page",
            joinColumns = {@JoinColumn(name = "page_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", nullable = false, updatable = false)})
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "is_web", nullable = false)
    public short getIsWeb() {
        return this.isWeb;
    }


    public void setIsWeb(short isWeb) {
        this.isWeb = isWeb;
    }

    @Column(name = "is_app", nullable = false)
    public short getIsApp() {
        return this.isApp;
    }


    public void setIsApp(short isApp) {
        this.isApp = isApp;
    }

    @Column(name = "is_has_flowable", nullable = false)
    public short getIsHasFlowable() {
        return this.isHasFlowable;
    }

    public void setIsHasFlowable(short isHasFlowable) {
        this.isHasFlowable = isHasFlowable;
    }

    @Column(name = "flowable_entity", nullable = true, length = 100)
    public String getFlowableEntity() {
        return this.flowableEntity;
    }

    public void setFlowableEntity(String flowableEntity) {
        this.flowableEntity = flowableEntity;
    }
}
