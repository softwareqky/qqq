/**
 * 
 */
package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.DataConfigDao;
import project.edge.domain.entity.DataConfig;


/**
 * @author angel_000
 *         [t_data_config]对应的 Service。
 */
@Service
public class DataConfigServiceImpl extends GenericServiceImpl<DataConfig, String>
        implements DataConfigService {
    
    @Resource
    private DataConfigDao dataConfigDao;

    @Override
    public Dao<DataConfig, String> getDefaultDao() {
        return this.dataConfigDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("dataType");
    }

}
