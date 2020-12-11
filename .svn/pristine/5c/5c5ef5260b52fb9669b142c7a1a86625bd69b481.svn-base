/**
 * 
 */
package project.edge.domain.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractStatement;
import project.edge.domain.entity.Person;
import project.edge.domain.view.PaymentContractStatementBean;


/**
 * @author angel_000
 *
 */
public class PaymentContractStatementBeanConverter
        implements ViewConverter<PaymentContractStatement, PaymentContractStatementBean> {

    @Override
    public PaymentContractStatementBean fromEntity(PaymentContractStatement entity,
            MessageSource messageSource, Locale locale) {

        PaymentContractStatementBean bean = new PaymentContractStatementBean();
        bean.setId(entity.getId());

        if (entity.getPaymentContract() != null) {
            bean.setPaymentContract_(entity.getPaymentContract().getId());
            bean.setPaymentContractText(entity.getPaymentContract().getContractName());
        }
        bean.setPaymentCondition(entity.getPaymentCondition());
        bean.setContractNo(entity.getContractNo());
        bean.setContractName(entity.getContractName());
        if (entity.getContractKind() != null) {
            bean.setContractKind_(entity.getContractKind().getId());
            bean.setContractKindText(entity.getContractKind().getCategoryName());
        }
        bean.setPaymentCondition(entity.getPaymentCondition());
        bean.setFinalContractAmount(entity.getFinalContractAmount());
        bean.setCurrency(entity.getCurrency());
        bean.setAccumulatedCollectionAmount(entity.getAccumulatedCollectionAmount());
        bean.setPartyA(entity.getPartyA());
        bean.setPartyB(entity.getPartyB());

        if (entity.getSigningTime() != null) {
            bean.setSigningTime(
                    DateUtils.date2String(entity.getSigningTime(), Constants.DATE_FORMAT));
        }

        bean.setSigningAddress(entity.getSigningAddress());
        bean.setAccumulatedSettlementAmount(entity.getSettlementAmount());
        bean.setCumulativeSettlementProportion(entity.getCumulativeSettlementProportion());
        bean.setSettlementNumber(entity.getSettlementNumber());
        bean.setSettlementAmount(entity.getSettlementAmount());
        bean.setSettlementType(entity.getSettlementType());
        bean.setDifference(entity.getDifference());
        bean.setSettlementObject(entity.getSettlementObject());

        if (entity.getIsIncludePendingData() != null) {
            bean.setIsIncludePendingData(entity.getIsIncludePendingData());
            bean.setIsIncludePendingDataText(entity.getIsIncludePendingData() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        Person settlementPerson = entity.getSettlementPerson();
        if (settlementPerson != null) {
            bean.setSettlementPerson_(settlementPerson.getId());
            bean.setSettlementPersonText(settlementPerson.getPersonName());
        }

        bean.setPaymentMethod(entity.getPaymentMethod());

        if (entity.getSettlementDate() != null) {
            bean.setSettlementDate(
                    DateUtils.date2String(entity.getSettlementDate(), Constants.DATE_FORMAT));
        }

        bean.setSettlementRatio(entity.getSettlementRatio());
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
    public PaymentContractStatement toEntity(PaymentContractStatementBean bean,
            PaymentContractStatement oldEntity, AbstractSessionUserBean user, Date now) {

        PaymentContractStatement entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PaymentContractStatement();

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
        entity.setPaymentCondition(bean.getPaymentCondition());
        entity.setContractNo(bean.getContractNo());
        entity.setContractName(bean.getContractName());
        if (!StringUtils.isEmpty(bean.getContractKind_())) {
        	ContractCategory contractCategory = new ContractCategory();
        	contractCategory.setId(bean.getContractKind_());
        	entity.setContractKind(contractCategory);
        }
        entity.setFinalContractAmount(bean.getFinalContractAmount());
        entity.setCurrency(bean.getCurrency());
        entity.setAccumulatedCollectionAmount(bean.getAccumulatedCollectionAmount());
        entity.setPartyA(bean.getPartyA());
        entity.setPartyB(bean.getPartyB());

        if (!StringUtils.isEmpty(bean.getSigningTime())) {
            try {
                entity.setSigningTime(
                        DateUtils.string2Date(bean.getSigningTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setSigningAddress(bean.getSigningAddress());
        entity.setAccumulatedSettlementAmount(bean.getAccumulatedSettlementAmount());
        entity.setCumulativeSettlementProportion(bean.getCumulativeSettlementProportion());
        entity.setSettlementNumber(bean.getSettlementNumber());
        entity.setSettlementAmount(bean.getSettlementAmount());
        entity.setSettlementType(bean.getSettlementType());
        entity.setDifference(bean.getDifference());
        entity.setSettlementObject(bean.getSettlementObject());
        entity.setIsIncludePendingData(bean.getIsIncludePendingData());

        if (!StringUtils.isEmpty(bean.getSettlementPerson_())) {
            Person settlementPerson = new Person();
            settlementPerson.setId(bean.getSettlementPerson_());
            entity.setSettlementPerson(settlementPerson);
        }

        entity.setPaymentMethod(bean.getPaymentMethod());

        if (!StringUtils.isEmpty(bean.getSettlementDate())) {
            try {
                entity.setSettlementDate(
                        DateUtils.string2Date(bean.getSettlementDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setSettlementRatio(bean.getSettlementRatio());
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
