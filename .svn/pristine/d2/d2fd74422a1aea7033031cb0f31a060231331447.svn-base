/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.City;
import project.edge.domain.entity.Province;
import project.edge.domain.view.CityBean;


/**
 * @author angel_000
 *         转换城市信息对应的view和entity的转换器。
 */
public class CityBeanConverter implements ViewConverter<City, CityBean> {

    @Override
    public CityBean fromEntity(City entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        CityBean bean = new CityBean();
        bean.setId(entity.getId());

        Province province = entity.getProvince();
        if (province != null) {
            bean.setProvince_(province.getId());
            bean.setProvinceText(province.getProvinceName());
        }

        bean.setCityCode(entity.getCityCode());
        bean.setCityName(entity.getCityName());

        return bean;
    }

    @Override
    public City toEntity(CityBean bean, City oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        City entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new City();

        } else { // 表示修改

        }

        bean.setId(entity.getId()); // ID必须赋值

        if (!StringUtils.isEmpty(bean.getProvince_())) {
            Province province = new Province();
            province.setId(bean.getProvince_());
            entity.setProvince(province);
        }

        entity.setCityCode(bean.getCityCode());
        entity.setCityName(bean.getCityName());

        return entity;
    }

}
