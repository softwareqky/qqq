package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存城市修改信息的表现层DTO。
 */
public class CityBean implements ViewBean {

    private String id;
    private String province_;
    private String provinceText;
    private String cityCode;
    private String cityName;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }


    public String getProvince_() {
        return this.province_;
    }


    public void setProvince_(String province_) {
        this.province_ = province_;
    }


    public String getProvinceText() {
        return this.provinceText;
    }


    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }


    public String getCityCode() {
        return this.cityCode;
    }


    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    public String getCityName() {
        return this.cityName;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public void setId(String id) {
        this.id = id;
    }

}
