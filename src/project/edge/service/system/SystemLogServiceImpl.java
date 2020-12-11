package project.edge.service.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.SystemLogDao;
import project.edge.domain.entity.SystemLog;


/**
 * 用户对应的Service。
 * 
 */
@Service
public class SystemLogServiceImpl extends GenericServiceImpl<SystemLog, String> implements SystemLogService {

    @Resource
    private SystemLogDao systemLogDao;

    @Override
    public Dao<SystemLog, String> getDefaultDao() {
        return this.systemLogDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", true);
    }

}
