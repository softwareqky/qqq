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
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.IncomeContract;
import project.edge.domain.entity.IncomeContractStatement;
import project.edge.domain.entity.Person;
import project.edge.domain.view.IncomeContractStatementBean;


/**
 * @author angel_000
 *
 */
public class IncomeContractStatementBeanConverter
        implements ViewConverter<IncomeContractStatement, IncomeContractStatementBean> {

    @Override
    public IncomeContractStatementBean fromEntity(IncomeContractStatement entity,
            MessageSource messageSource, Locale locale) {

        IncomeContractStatementBean bean = new IncomeContractStatementBean();
        bean.setId(entity.getId());

        if (entity.getIncomeContract() != null) {
            bean.setIncomeContract_(entity.getIncomeContract().getId());
            bean.setIncomeContractText(entity.getIncomeContract().getContractName());
        }
        bean.setContractNo(entity.getContractNo());
        bean.setContractName(entity.getContractName());
        bean.setContractAmount(entity.getContractAmount());

        if (entity.getSigningTime() != null) {
            bean.setSigningTime(
                    DateUtils.date2String(entity.getSigningTime(), Constants.DATE_FORMAT));
        }

        bean.setFinalAmount(entity.getFinalAmount());
        bean.setSettlementSum(entity.getSettlementSum());
        bean.setDifference(entity.getDifference());
        bean.setCumulativeSettlementProportion(entity.getCumulativeSettlementProportion());
        bean.setCurrency(entity.getCurrency());
        bean.setSettlementNumber(entity.getSettlementNumber());
        bean.setSettlementAmount(entity.getSettlementAmount());
        bean.setExchangeRate(entity.getExchangeRate());
        bean.setConvertedIntoRmb(entity.getConvertedIntoRmb());
        bean.setSettlementType(entity.getSettlementType());
        bean.setPaymentMethod(entity.getPaymentMethod());
        bean.setSettlementObject(entity.getSettlementObject());

        if (entity.getSettlementDate() != null) {
            bean.setSettlementDate(
                    DateUtils.date2String(entity.getSettlementDate(), Constants.DATE_FORMAT));
        }

        bean.setSettlementRatio(entity.getSettlementRatio());

        if (entity.getAgreedCollectionDate() != null) {
            bean.setAgreedCollectionDate(
                    DateUtils.date2String(entity.getAgreedCollectionDate(), Constants.DATE_FORMAT));
        }

        Person settlementPerson = entity.getSettlementPerson();
        if (settlementPerson != null) {
            bean.setSettlementPerson_(settlementPerson.getId());
            bean.setSettlementPersonText(settlementPerson.getPersonName());
        }

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
    public IncomeContractStatement toEntity(IncomeContractStatementBean bean,
            IncomeContractStatement oldEntity, AbstractSessionUserBean user, Date now) {

        IncomeContractStatement entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new IncomeContractStatement();

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
        entity.setContractAmount(bean.getContractAmount());

        if (!StringUtils.isEmpty(bean.getSigningTime())) {
            try {
                entity.setSigningTime(
                        DateUtils.string2Date(bean.getSigningTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setFinalAmount(bean.getFinalAmount());
        entity.setSettlementSum(bean.getSettlementSum());
        entity.setDifference(bean.getDifference());
        entity.setCumulativeSettlementProportion(bean.getCumulativeSettlementProportion());
        entity.setCurrency(bean.getCurrency());
        entity.setSettlementNumber(bean.getSettlementNumber());
        entity.setSettlementAmount(bean.getSettlementAmount());
        entity.setExchangeRate(bean.getExchangeRate());
        entity.setConvertedIntoRmb(bean.getConvertedIntoRmb());
        entity.setSettlementType(bean.getSettlementType());
        entity.setPaymentMethod(bean.getPaymentMethod());
        entity.setSettlementObject(bean.getSettlementObject());

        if (!StringUtils.isEmpty(bean.getSettlementDate())) {
            try {
                entity.setSettlementDate(
                        DateUtils.string2Date(bean.getSettlementDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setSettlementRatio(bean.getSettlementRatio());

        if (!StringUtils.isEmpty(bean.getAgreedCollectionDate())) {
            try {
                entity.setAgreedCollectionDate(DateUtils.string2Date(bean.getAgreedCollectionDate(),
                        Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getSettlementPerson_())) {
            Person settlementPerson = new Person();
            settlementPerson.setId(bean.getSettlementPerson_());
            entity.setSettlementPerson(settlementPerson);
        }

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
