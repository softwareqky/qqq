/**
 * 
 */
package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.DataOptionDao;
import project.edge.domain.entity.DataOption;


/**
 * @author angel_000
 *         [t_data_option]对应的 Service。
 */
@Service
public class DataOptionServiceImpl extends GenericServiceImpl<DataOption, String>
        implements DataOptionService {

    @Resource
    private DataOptionDao dataOptionDao;

    @Override
    public Dao<DataOption, String> getDefaultDao() {
        return this.dataOptionDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("optionOrder");
    }

}
