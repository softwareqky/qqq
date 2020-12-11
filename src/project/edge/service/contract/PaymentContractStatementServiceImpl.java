package project.edge.service.contract;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.PaymentContractDao;
import project.edge.dao.contract.PaymentContractStatementDao;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractStatement;
import project.edge.domain.entity.Project;

/**
 * [t_payment_contract_statement]对应的 Service。
 */
@Service
public class PaymentContractStatementServiceImpl
        extends GenericServiceImpl<PaymentContractStatement, String>
        implements PaymentContractStatementService {

    @Resource
    private PaymentContractStatementDao paymentContractStatementDao;
    
    @Resource
    private PaymentContractDao paymentContractDao;

    @Override
    public Dao<PaymentContractStatement, String> getDefaultDao() {
        return this.paymentContractStatementDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
    
    @Override
    @Transactional
    public void create(PaymentContractStatement entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 自动设置对应合同信息
        if (entity.getPaymentContract() != null) {
            PaymentContract paymentContract = paymentContractDao.find(entity.getPaymentContract().getId());
            entity.setContractNo(paymentContract.getSerialNumber());
            entity.setContractName(paymentContract.getContractName());
            entity.setContractKind(paymentContract.getContractKind());
        }
        
        this.paymentContractStatementDao.create(entity);
    }
    
    @Override
    @Transactional
    public void update(PaymentContractStatement entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 自动设置对应合同信息
        if (entity.getPaymentContract() != null) {
            PaymentContract paymentContract = paymentContractDao.find(entity.getPaymentContract().getId());
            entity.setContractNo(paymentContract.getSerialNumber());
            entity.setContractName(paymentContract.getContractName());
            entity.setContractKind(paymentContract.getContractKind());
        }
        
    	this.paymentContractStatementDao.update(entity);
    }
}
