package project.edge.service.contract;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.IncomeContractDao;
import project.edge.domain.entity.IncomeContract;

/**
 * [t_income_contract]对应的 Service。
 */
@Service
public class IncomeContractServiceImpl extends GenericServiceImpl<IncomeContract, String>
        implements IncomeContractService {

    @Resource
    private IncomeContractDao incomeContractDao;

    @Override
    public Dao<IncomeContract, String> getDefaultDao() {
        return this.incomeContractDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
