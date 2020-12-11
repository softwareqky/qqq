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
@Table(name = "t_defined_segment_icon")
public class DefinedSegmentIcon implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -532817322984219825L;
    private String id = UUID.randomUUID().toString();
    private Short isMap;
    private String segmentType;
    private String width;
    private String color;
    private String lineStyle;
    private String modifier;
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

    @Column(name = "segment_type", nullable = true, length = 60)
    public String getSegmentType() {
        return this.segmentType;
    }


    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    @Column(name = "width", nullable = true, length = 50)
    public String getWidth() {
        return this.width;
    }


    public void setWidth(String width) {
        this.width = width;
    }

    @Column(name = "color", nullable = true, length = 50)
    public String getColor() {
        return this.color;
    }


    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "line_style", nullable = true, length = 50)
    public String getLineStyle() {
        return this.lineStyle;
    }


    public void setLineStyle(String lineStyle) {
        this.lineStyle = lineStyle;
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
