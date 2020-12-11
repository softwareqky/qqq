package project.edge.service.contract;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.ContractCategoryDao;
import project.edge.domain.entity.ContractCategory;

/**
 * [t_contract_account]对应的 Service。
 */
@Service
public class ContractCategoryServiceImpl extends GenericServiceImpl<ContractCategory, String>
        implements ContractCategoryService {

    @Resource
    private ContractCategoryDao contractCategoryDao;

    @Override
    public Dao<ContractCategory, String> getDefaultDao() {
        return this.contractCategoryDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
