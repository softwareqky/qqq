package project.edge.service.contract;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.IncomeContractStatementDao;
import project.edge.domain.entity.IncomeContractStatement;

/**
 * [t_income_contract_statement]对应的 Service。
 */
@Service
public class IncomeContractStatementServiceImpl
        extends GenericServiceImpl<IncomeContractStatement, String>
        implements IncomeContractStatementService {

    @Resource
    private IncomeContractStatementDao incomeContractStatementDao;

    @Override
    public Dao<IncomeContractStatement, String> getDefaultDao() {
        return this.incomeContractStatementDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
