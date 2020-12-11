package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_data_config")
public class DataConfig implements java.io.Serializable {

    private static final long serialVersionUID = 838278421892592740L;

    private String id = UUID.randomUUID().toString();
    private String dataType;
    private short isOption;
    private short isCustomizable;
    private short isEnabled;
    private String modifier;
    private Date mDatetime;

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "data_type", nullable = false, length = 30)
    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "is_enabled", nullable = false)
    public short getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(short isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Column(name = "modifier", nullable = true,length = 50)
    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }

    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

    @Column(name = "is_option", nullable = false)
    public short getIsOption() {
        return this.isOption;
    }


    public void setIsOption(short isOption) {
        this.isOption = isOption;
    }


    @Column(name = "is_customizable", nullable = false)
    public short getIsCustomizable() {
        return this.isCustomizable;
    }


    public void setIsCustomizable(short isCustomizable) {
        this.isCustomizable = isCustomizable;
    }

}
