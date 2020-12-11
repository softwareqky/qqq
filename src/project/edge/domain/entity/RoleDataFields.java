package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_role_data_fields")
public class RoleDataFields implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8719207511129175792L;

    private String id = UUID.randomUUID().toString();
    private Role role;
    private DataFields dataFields;

    private String captionLiteralName;
    private short isCommonVisible;
    private String defaultValue;

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    public Role getRole() {
        return this.role;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_fields_id", nullable = false)
    public DataFields getDataFields() {
        return this.dataFields;
    }


    public void setDataFields(DataFields dataFields) {
        this.dataFields = dataFields;
    }

    @Column(name = "caption_literal_name", nullable = true, length = 50)
    public String getCaptionLiteralName() {
        return this.captionLiteralName;
    }


    public void setCaptionLiteralName(String captionLiteralName) {
        this.captionLiteralName = captionLiteralName;
    }

    @Column(name = "is_common_visible", nullable = false)
    public short getIsCommonVisible() {
        return this.isCommonVisible;
    }


    public void setIsCommonVisible(short isCommonVisible) {
        this.isCommonVisible = isCommonVisible;
    }

    @Column(name = "default_value", nullable = true, length = 50)
    public String getDefaultValue() {
        return this.defaultValue;
    }


    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
