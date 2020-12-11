package project.edge.service.contract;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.PaymentInvoiceDao;
import project.edge.domain.entity.PaymentInvoice;

/**
 * [t_payment_invoice]对应的 Service。
 */
@Service
public class PaymentInvoiceServiceImpl extends GenericServiceImpl<PaymentInvoice, String>
        implements PaymentInvoiceService {

    @Resource
    private PaymentInvoiceDao paymentInvoiceDao;

    @Override
    public Dao<PaymentInvoice, String> getDefaultDao() {
        return this.paymentInvoiceDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }

}
