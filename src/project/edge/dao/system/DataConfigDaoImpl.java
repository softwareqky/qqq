/**
 * 
 */
package project.edge.dao.system;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.DataConfig;


/**
 * @author angel_000
 *         [t_data_config]对应的DAO。
 */
@Repository
public class DataConfigDaoImpl extends HibernateDaoImpl<DataConfig, String>
        implements DataConfigDao {

}
