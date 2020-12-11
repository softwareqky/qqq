/**
 * 
 */
package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.CityDao;
import project.edge.domain.entity.City;


/**
 * @author angel_000
 *        [t_city]对应的 Service。
 */
@Service
public class CityServiceImpl extends GenericServiceImpl<City, String> implements CityService {

    @Resource
    private CityDao cityDao;
    
    @Override
    public Dao<City, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.cityDao;
    }
    
    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cityCode", false);
    }

}
