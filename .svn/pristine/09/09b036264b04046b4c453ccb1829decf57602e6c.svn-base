/**
 * 
 */
package project.edge.service.system;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.DataFieldsDao;
import project.edge.domain.entity.DataFields;


/**
 * @author angel_000
 *         [t_data_fields]对应的 Service。
 */
@Service
public class DataFieldsServiceImpl extends GenericServiceImpl<DataFields, String>
        implements DataFieldsService {

    @Resource
    private DataFieldsDao dataFieldsDao;

    @Override
    public Dao<DataFields, String> getDefaultDao() {
        return this.dataFieldsDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("fieldOrder");
    }

}
