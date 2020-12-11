package project.edge.service.contract;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.IncomeInvoiceDao;
import project.edge.domain.entity.IncomeInvoice;

/**
 * [t_income_invoice]对应的 Service。
 */
@Service
public class IncomeInvoiceServiceImpl extends GenericServiceImpl<IncomeInvoice, String>
        implements IncomeInvoiceService {

    @Resource
    private IncomeInvoiceDao incomeInvoiceDao;

    @Override
    public Dao<IncomeInvoice, String> getDefaultDao() {
        return this.incomeInvoiceDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
