/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentInvoice;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.view.PaymentInvoiceBean;


/**
 * @author angel_000
 *
 */
public class PaymentInvoiceBeanConverter
        implements ViewConverter<PaymentInvoice, PaymentInvoiceBean> {

    @Override
    public PaymentInvoiceBean fromEntity(PaymentInvoice entity, MessageSource messageSource,
            Locale locale) {

        PaymentInvoiceBean bean = new PaymentInvoiceBean();
        bean.setId(entity.getId());
        
        if (entity.getPaymentContract() != null) {
            bean.setPaymentContract_(entity.getPaymentContract().getId());
            bean.setPaymentContractText(entity.getPaymentContract().getContractName());
            bean.setContractName(entity.getPaymentContract().getContractName());
            bean.setContractNo(entity.getPaymentContract().getSerialNumber());
        }
//        bean.setContractNo(entity.getContractNo());
//        bean.setContractName(entity.getContractName());
        bean.setFinalContractAmount(entity.getFinalContractAmount());

        if (entity.getSigningTime() != null) {
            bean.setSigningTime(
                    DateUtils.date2String(entity.getSigningTime(), Constants.DATE_FORMAT));
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setCumulativePaymentAmount(entity.getCumulativePaymentAmount());
        bean.setCumulativeInvoiceReceived(entity.getCumulativeInvoiceReceived());
        bean.setAccumulatedOutstandingAmount(entity.getAccumulatedOutstandingAmount());
        bean.setPaymentUnit(entity.getPaymentUnit());
        bean.setCollectionUnit(entity.getCollectionUnit());

        Person entryPerson = entity.getEntryPerson();
        if (entryPerson != null) {
            bean.setEntryPerson_(entryPerson.getId());
            bean.setEntryPersonText(entryPerson.getPersonName());
        }

        if (entity.getEntryTime() != null) {
            bean.setEntryTime(DateUtils.date2String(entity.getEntryTime(), Constants.DATE_FORMAT));
        }

        bean.setStorageNumber(entity.getStorageNumber());
        bean.setInvoiceAmount(entity.getInvoiceAmount());
        bean.setInvoiceNo(entity.getInvoiceNo());
        bean.setInvoiceCode(entity.getInvoiceCode());
        bean.setInvoiceType(entity.getInvoiceType());
        bean.setSocialCreditCode(entity.getSocialCreditCode());
        bean.setTaxpayerIdentificationNumber(entity.getTaxpayerIdentificationNumber());
        bean.setDepositBank(entity.getDepositBank());
        bean.setBankAccount(entity.getBankAccount());
        bean.setAddress(entity.getAddress());
        bean.setPhone(entity.getPhone());
        bean.setTransactor(entity.getTransactor());

        if (entity.getInvoiceDate() != null) {
            bean.setInvoiceDate(
                    DateUtils.date2String(entity.getInvoiceDate(), Constants.DATE_FORMAT));
        }

        bean.setInvoiceContent(entity.getInvoiceContent());

        if (entity.getArrivalDate() != null) {
            bean.setArrivalDate(
                    DateUtils.date2String(entity.getArrivalDate(), Constants.DATE_FORMAT));
        }

        bean.setRemark(entity.getRemark());
        bean.setDeductibleInputTax(entity.getDeductibleInputTax());
        bean.setInputTaxActuallyDeducted(entity.getInputTaxActuallyDeducted());
        bean.setAmountExcludingTax(entity.getAmountExcludingTax());

        return bean;
    }

    @Override
    public PaymentInvoice toEntity(PaymentInvoiceBean bean, PaymentInvoice oldEntity,
            AbstractSessionUserBean user, Date now) {

        PaymentInvoice entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PaymentInvoice();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPaymentContract_())) {
            PaymentContract paymentContract = new PaymentContract();
            paymentContract.setId(bean.getPaymentContract_());
            entity.setPaymentContract(paymentContract);
        }
        entity.setContractNo(bean.getContractNo());
        entity.setContractName(bean.getContractName());
        entity.setFinalContractAmount(bean.getFinalContractAmount());

        if (!StringUtils.isEmpty(bean.getSigningTime())) {
            try {
                entity.setSigningTime(
                        DateUtils.string2Date(bean.getSigningTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        entity.setCumulativePaymentAmount(bean.getCumulativePaymentAmount());
        entity.setCumulativeInvoiceReceived(bean.getCumulativeInvoiceReceived());
        entity.setAccumulatedOutstandingAmount(bean.getAccumulatedOutstandingAmount());
        entity.setPaymentUnit(bean.getPaymentUnit());
        entity.setCollectionUnit(bean.getCollectionUnit());

        if (!StringUtils.isEmpty(bean.getEntryPerson_())) {
            Person entryPerson = new Person();
            entryPerson.setId(bean.getEntryPerson_());
            entity.setEntryPerson(entryPerson);
        }

        if (!StringUtils.isEmpty(bean.getEntryTime())) {
            try {
                entity.setEntryTime(
                        DateUtils.string2Date(bean.getEntryTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setStorageNumber(bean.getStorageNumber());
        entity.setInvoiceAmount(bean.getInvoiceAmount());
        entity.setInvoiceNo(bean.getInvoiceNo());
        entity.setInvoiceCode(bean.getInvoiceCode());
        entity.setInvoiceType(bean.getInvoiceType());
        entity.setSocialCreditCode(bean.getSocialCreditCode());
        entity.setTaxpayerIdentificationNumber(bean.getTaxpayerIdentificationNumber());
        entity.setDepositBank(bean.getDepositBank());
        entity.setBankAccount(bean.getBankAccount());
        entity.setAddress(bean.getAddress());
        entity.setPhone(bean.getPhone());
        entity.setTransactor(bean.getTransactor());

        if (!StringUtils.isEmpty(bean.getInvoiceDate())) {
            try {
                entity.setInvoiceDate(
                        DateUtils.string2Date(bean.getInvoiceDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setInvoiceContent(bean.getInvoiceContent());

        if (!StringUtils.isEmpty(bean.getArrivalDate())) {
            try {
                entity.setArrivalDate(
                        DateUtils.string2Date(bean.getArrivalDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setRemark(bean.getRemark());
        entity.setDeductibleInputTax(bean.getDeductibleInputTax());
        entity.setInputTaxActuallyDeducted(bean.getInputTaxActuallyDeducted());
        entity.setAmountExcludingTax(bean.getAmountExcludingTax());

        return entity;
    }

}
