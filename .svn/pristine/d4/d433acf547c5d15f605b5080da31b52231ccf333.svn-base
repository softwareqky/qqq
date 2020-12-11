package project.edge.service.contract;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.ContractAccountDao;
import project.edge.domain.entity.ContractAccount;

/**
 * [t_contract_account]对应的 Service。
 */
@Service
public class ContractAccountServiceImpl extends GenericServiceImpl<ContractAccount, String>
        implements ContractAccountService {

    @Resource
    private ContractAccountDao contractAccountDao;

    @Override
    public Dao<ContractAccount, String> getDefaultDao() {
        return this.contractAccountDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
