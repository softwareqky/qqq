package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_contract_category")
public class ContractCategory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1319827475534774000L;

    private String id = UUID.randomUUID().toString();

    private String categoryName;
    private String categoryCode;
    private String pid;
    private short isLeaf;
    private Integer dataSource;
    private Integer fieldOrder;
    private String remark;
    private String creator;
    private Date cDatetime;
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

    @Column(name = "category_name", nullable = false, length = 100)
    public String getCategoryName() {
        return this.categoryName;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "category_code", nullable = false, length = 36)
    public String getCategoryCode() {
        return this.categoryCode;
    }


    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Column(name = "pid", nullable = false, length = 36)
    public String getPid() {
        return this.pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "is_leaf", nullable = false)
    public short getIsLeaf() {
        return this.isLeaf;
    }


    public void setIsLeaf(short isLeaf) {
        this.isLeaf = isLeaf;
    }


    @Column(name = "data_source", nullable = false)
    public Integer getDataSource() {
        return this.dataSource;
    }


    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }


    @Column(name = "field_order", nullable = false)
    public int getFieldOrder() {
        return this.fieldOrder;
    }


    public void setFieldOrder(int fieldOrder) {
        this.fieldOrder = fieldOrder;
    }


    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }


    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
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
