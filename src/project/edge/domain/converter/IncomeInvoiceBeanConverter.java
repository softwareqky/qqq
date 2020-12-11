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
import project.edge.domain.entity.IncomeContract;
import project.edge.domain.entity.IncomeInvoice;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.view.IncomeInvoiceBean;


/**
 * @author angel_000
 *
 */
public class IncomeInvoiceBeanConverter implements ViewConverter<IncomeInvoice, IncomeInvoiceBean> {

    @Override
    public IncomeInvoiceBean fromEntity(IncomeInvoice entity, MessageSource messageSource,
            Locale locale) {

        IncomeInvoiceBean bean = new IncomeInvoiceBean();
        bean.setId(entity.getId());

        bean.setContractNo(entity.getContractNo());
        bean.setContractName(entity.getContractName());
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

        if (entity.getIncomeContract() != null) {
            bean.setIncomeContract_(entity.getIncomeContract().getId());
            bean.setIncomeContractText(entity.getIncomeContract().getContractName());
        }
        bean.setAccumulatedCollectionAmount(entity.getAccumulatedCollectionAmount());
        bean.setCumulativeInvoiced(entity.getCumulativeInvoiced());
        bean.setAccumulatedInvoiceNotIssued(entity.getAccumulatedInvoiceNotIssued());
        bean.setInvoiceApplication(entity.getInvoiceApplication());
        bean.setCurrentInvoiceAmount(entity.getCurrentInvoiceAmount());
        bean.setTaxRate(entity.getTaxRate());
        bean.setInvoiceNumber(entity.getInvoiceNumber());
        bean.setInvoiceType(entity.getInvoiceType());
        bean.setCollectionUnit(entity.getCollectionUnit());
        bean.setPaymentUnit(entity.getPaymentUnit());
        bean.setTaxpayerIdentificationNumber(entity.getTaxpayerIdentificationNumber());
        bean.setInvoiceCode(entity.getInvoiceCode());
        bean.setTransactor(entity.getTransactor());

        if (entity.getInvoiceDate() != null) {
            bean.setInvoiceDate(
                    DateUtils.date2String(entity.getInvoiceDate(), Constants.DATE_FORMAT));
        }

        bean.setPreviousInputDeductedTax(entity.getPreviousInputDeductedTax());
        bean.setUnifiedSocialCreditCode(entity.getUnifiedSocialCreditCode());
        bean.setAddress(entity.getAddress());
        bean.setTelephone(entity.getTelephone());
        bean.setVatPaidInAdvanceInOtherPlaces(entity.getVatPaidInAdvanceInOtherPlaces());
        bean.setDeposit(entity.getDeposit());
        bean.setVoucherNumber(entity.getVoucherNumber());
        bean.setInvoiceContents(entity.getInvoiceContents());
        bean.setAdditionalTaxPaidInAdvance(entity.getAdditionalTaxPaidInAdvance());
        bean.setSuspendOtherBusiness(entity.getSuspendOtherBusiness());
        bean.setDeductManagementFee(entity.getDeductManagementFee());
        bean.setInputDeductedTax(entity.getInputDeductedTax());
        bean.setCurrentRetainedInputTax(entity.getCurrentRetainedInputTax());
        bean.setFinalRetainedInputTaxBalance(entity.getFinalRetainedInputTaxBalance());
        bean.setPrepaidIndividualIncomeTax(entity.getPrepaidIndividualIncomeTax());
        bean.setOtherTaxesPaidInAdvance(entity.getOtherTaxesPaidInAdvance());
        bean.setEnterpriseIncomeTaxPrepaid(entity.getEnterpriseIncomeTaxPrepaid());
        bean.setOpeningBank(entity.getOpeningBank());
        bean.setAccountNumber(entity.getAccountNumber());
        bean.setRemark(entity.getRemark());

        Person entryPerson = entity.getEntryPerson();
        if (entryPerson != null) {
            bean.setEntryPerson_(entryPerson.getId());
            bean.setEntryPersonText(entryPerson.getPersonName());
        }

        if (entity.getEntryTime() != null) {
            bean.setEntryTime(DateUtils.date2String(entity.getEntryTime(), Constants.DATE_FORMAT));
        }

        return bean;
    }

    @Override
    public IncomeInvoice toEntity(IncomeInvoiceBean bean, IncomeInvoice oldEntity,
            AbstractSessionUserBean user, Date now) {

        IncomeInvoice entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new IncomeInvoice();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getIncomeContract_())) {
            IncomeContract incomeContract = new IncomeContract();
            incomeContract.setId(bean.getIncomeContract_());
            entity.setIncomeContract(incomeContract);
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

        entity.setAccumulatedCollectionAmount(bean.getAccumulatedCollectionAmount());
        entity.setCumulativeInvoiced(bean.getCumulativeInvoiced());
        entity.setAccumulatedInvoiceNotIssued(bean.getAccumulatedInvoiceNotIssued());
        entity.setInvoiceApplication(bean.getInvoiceApplication());
        entity.setCurrentInvoiceAmount(bean.getCurrentInvoiceAmount());
        entity.setTaxRate(bean.getTaxRate());
        entity.setInvoiceNumber(bean.getInvoiceNumber());
        entity.setInvoiceType(bean.getInvoiceType());
        entity.setCollectionUnit(bean.getCollectionUnit());
        entity.setPaymentUnit(bean.getPaymentUnit());
        entity.setTaxpayerIdentificationNumber(bean.getTaxpayerIdentificationNumber());
        entity.setInvoiceCode(bean.getInvoiceCode());
        entity.setTransactor(bean.getTransactor());

        if (!StringUtils.isEmpty(bean.getInvoiceDate())) {
            try {
                entity.setInvoiceDate(
                        DateUtils.string2Date(bean.getInvoiceDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setPreviousInputDeductedTax(bean.getPreviousInputDeductedTax());
        entity.setUnifiedSocialCreditCode(bean.getUnifiedSocialCreditCode());
        entity.setAddress(bean.getAddress());
        entity.setTelephone(bean.getTelephone());
        entity.setVatPaidInAdvanceInOtherPlaces(bean.getVatPaidInAdvanceInOtherPlaces());
        entity.setDeposit(bean.getDeposit());
        entity.setVoucherNumber(bean.getVoucherNumber());
        entity.setInvoiceContents(bean.getInvoiceContents());
        entity.setAdditionalTaxPaidInAdvance(bean.getAdditionalTaxPaidInAdvance());
        entity.setSuspendOtherBusiness(bean.getSuspendOtherBusiness());
        entity.setDeductManagementFee(bean.getDeductManagementFee());
        entity.setInputDeductedTax(bean.getInputDeductedTax());
        entity.setCurrentRetainedInputTax(bean.getCurrentRetainedInputTax());
        entity.setFinalRetainedInputTaxBalance(bean.getFinalRetainedInputTaxBalance());
        entity.setPrepaidIndividualIncomeTax(bean.getPrepaidIndividualIncomeTax());
        entity.setOtherTaxesPaidInAdvance(bean.getOtherTaxesPaidInAdvance());
        entity.setEnterpriseIncomeTaxPrepaid(bean.getEnterpriseIncomeTaxPrepaid());
        entity.setOpeningBank(bean.getOpeningBank());
        entity.setAccountNumber(bean.getAccountNumber());
        entity.setRemark(bean.getRemark());

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

        return entity;
    }

}
