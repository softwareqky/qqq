package project.edge.service.contract;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.contract.PaymentContractAttachmentDao;
import project.edge.domain.entity.PaymentContractAttachment;

public class PaymentContractAttachmentServiceImpl extends GenericServiceImpl<PaymentContractAttachment, String>
		implements PaymentContractAttachmentService {
	
	@Resource
    private PaymentContractAttachmentDao paymentContractAttachmentDao;
	
	@Override
	public Dao<PaymentContractAttachment, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return paymentContractAttachmentDao;
	}

}
