/**
 * 
 */
package project.edge.service.org;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.org.DeptDao;
import project.edge.domain.entity.Dept;


/**
 * @author angel_000
 *         [t_dept]对应的 Service。
 */
@Service
public class DeptServiceImpl extends GenericServiceImpl<Dept, String> implements DeptService {

    @Resource
    private DeptDao deptDao;

    @Override
    public Dao<Dept, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.deptDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("deptName", false);
    }
}
