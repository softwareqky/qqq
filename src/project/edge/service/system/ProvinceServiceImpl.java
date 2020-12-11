/**
 * 
 */
package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.ProvinceDao;
import project.edge.domain.entity.Province;


/**
 * @author angel_000
 *         [t_province]对应的 Service。
 */
@Service
public class ProvinceServiceImpl extends GenericServiceImpl<Province, String>
        implements ProvinceService {

    @Resource
    private ProvinceDao provinceDao;

    @Override
    public Dao<Province, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.provinceDao;
    }
    
    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("provinceCode", false);
    }
}
