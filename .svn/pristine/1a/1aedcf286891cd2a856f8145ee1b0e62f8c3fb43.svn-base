package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_defined_site_icon")
public class DefinedSiteIcon implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6397346743240035968L;
    private String id = UUID.randomUUID().toString();

    private Short isMap;
    private String siteType;
    private String iconName;
    private String x;
    private String y;
    private String modifier;
    private Short size;       // 大图标：1，小图标：0
    private Date mDatetime;


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


    @Column(name = "is_map", nullable = true)
    public Short getIsMap() {
        return this.isMap;
    }


    public void setIsMap(Short isMap) {
        this.isMap = isMap;
    }

    @Column(name = "site_type", nullable = true, length = 60)
    public String getSiteType() {
        return this.siteType;
    }


    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    @Column(name = "icon_name", nullable = true, length = 50)
    public String getIconName() {
        return this.iconName;
    }


    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @Column(name = "x", nullable = true, length = 50)
    public String getX() {
        return this.x;
    }


    public void setX(String x) {
        this.x = x;
    }

    @Column(name = "y", nullable = true, length = 50)
    public String getY() {
        return this.y;
    }


    public void setY(String y) {
        this.y = y;
    }
    
    
    @Column(name = "size", nullable = true)
    public Short getSize() {
		return size;
	}

	public void setSize(Short size) {
		this.size = size;
	}

	@Column(name = "modifier", nullable = true, length = 50)
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

}
