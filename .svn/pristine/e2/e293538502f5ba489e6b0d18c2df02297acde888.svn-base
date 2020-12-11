/**
 * 
 */
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


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_city")
public class City implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9212793517109325554L;
    
    private String id = UUID.randomUUID().toString();
    
    
    private Province province;
    private String cityCode;
    private String cityName;
    
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
    @JoinColumn(name = "province_id", nullable = false)
    public Province getProvince() {
        return this.province;
    }

    
    public void setProvince(Province province) {
        this.province = province;
    }

    @Column(name = "city_code", nullable = false, length = 50)
    public String getCityCode() {
        return this.cityCode;
    }
    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    @Column(name = "city_name", nullable = false, length = 50)
    public String getCityName() {
        return this.cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
}
