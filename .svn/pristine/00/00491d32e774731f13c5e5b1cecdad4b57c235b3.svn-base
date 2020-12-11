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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.IncomeContract;
import project.edge.domain.entity.Person;
import project.edge.domain.view.IncomeContractBean;


/**
 * @author angel_000
 *
 */
public class IncomeContractBeanConverter
        implements ViewConverter<IncomeContract, IncomeContractBean> {

    @Override
    public IncomeContractBean fromEntity(IncomeContract entity, MessageSource messageSource,
            Locale locale) {

        IncomeContractBean bean = new IncomeContractBean();
        bean.setId(entity.getId());

        bean.setContractName(entity.getContractName());
        bean.setSerialNumber(entity.getSerialNumber());
        bean.setContractYear(entity.getContractYear());
        bean.setContractCount(entity.getContractCount());
        bean.setContractAmount(entity.getContractAmount());

        if (entity.getIsTemporaryPricing() != null) {
            bean.setIsTemporaryPricing(entity.getIsTemporaryPricing());
            bean.setIsTemporaryPricingText(entity.getIsTemporaryPricing() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setAmountExcludingTax(entity.getAmountExcludingTax());
        bean.setContractKind(entity.getContractKind());
        bean.setContractAttribute(entity.getContractAttribute());

        if (entity.getContractTime() != null) {
            bean.setContractTime(
                    DateUtils.date2String(entity.getContractTime(), Constants.DATE_FORMAT));
        }

        bean.setOntractAddress(entity.getOntractAddress());

        if (entity.getStartTime() != null) {
            bean.setStartTime(DateUtils.date2String(entity.getStartTime(), Constants.DATE_FORMAT));
        }

        if (entity.getEndTime() != null) {
            bean.setEndTime(DateUtils.date2String(entity.getEndTime(), Constants.DATE_FORMAT));
        }

        Person signingPeople = entity.getSigningPeople();
        if (signingPeople != null) {
            bean.setSigningPeople_(signingPeople.getId());
            bean.setSigningPeopleText(signingPeople.getPersonName());
        }

        bean.setSigningStatus(entity.getSigningStatus());
        bean.setContractConstructionScale(entity.getContractConstructionScale());
        bean.setSignedContractsNumber(entity.getSignedContractsNumber());
        bean.setQuantities(entity.getQuantities());
        bean.setPartyA(entity.getPartyA());
        bean.setPartyB(entity.getPartyB());
        bean.setPartyAAgent(entity.getPartyAAgent());
        bean.setPartyBAgent(entity.getPartyBAgent());
        bean.setPartyAContactInfo(entity.getPartyAContactInfo());
        bean.setPartyBContactInfo(entity.getPartyBContactInfo());
        bean.setPartyC(entity.getPartyC());
        bean.setBriefIntroduction(entity.getBriefIntroduction());
        bean.setAmountCollected(entity.getAmountCollected());

        if (entity.getReminderDate() != null) {
            bean.setReminderDate(
                    DateUtils.date2String(entity.getReminderDate(), Constants.DATE_FORMAT));
        }

        bean.setReminderPeople(entity.getReminderPeople());
        bean.setGeneralContractor(entity.getGeneralContractor());
        bean.setChapterType(entity.getChapterType());
        bean.setConstructionUnitProjectNo(entity.getConstructionUnitProjectNo());
        bean.setConstructionUnitContractNo(entity.getConstructionUnitProjectNo());
        bean.setInsurance(entity.getInsurance());

        if (entity.getIsFiled() != null) {
            bean.setIsFiled(entity.getIsFiled());
            bean.setIsFiledText(entity.getIsFiled() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        if (entity.getPlannedOperationTime() != null) {
            bean.setPlannedOperationTime(
                    DateUtils.date2String(entity.getPlannedOperationTime(), Constants.DATE_FORMAT));
        }

        if (entity.getReturnDate() != null) {
            bean.setReturnDate(
                    DateUtils.date2String(entity.getReturnDate(), Constants.DATE_FORMAT));
        }

        bean.setContractReturnInfo(entity.getContractReturnInfo());
        bean.setCountersignatureReturn(entity.getCountersignatureReturn());
        bean.setMainProvisions(entity.getMainProvisions());
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
    public IncomeContract toEntity(IncomeContractBean bean, IncomeContract oldEntity,
            AbstractSessionUserBean user, Date now) {

        IncomeContract entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new IncomeContract();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setContractName(bean.getContractName());
        entity.setSerialNumber(bean.getSerialNumber());
        entity.setContractYear(bean.getContractYear());
        entity.setContractCount(bean.getContractCount());
        entity.setContractAmount(bean.getContractAmount());
        entity.setIsTemporaryPricing(bean.getIsTemporaryPricing());
        entity.setAmountExcludingTax(bean.getAmountExcludingTax());
        entity.setContractKind(bean.getContractKind());
        entity.setContractAttribute(bean.getContractAttribute());

        if (!StringUtils.isEmpty(bean.getContractTime())) {
            try {
                entity.setContractTime(
                        DateUtils.string2Date(bean.getContractTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setOntractAddress(bean.getOntractAddress());

        if (!StringUtils.isEmpty(bean.getStartTime())) {
            try {
                entity.setStartTime(
                        DateUtils.string2Date(bean.getStartTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getEndTime())) {
            try {
                entity.setEndTime(DateUtils.string2Date(bean.getEndTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getSigningPeople_())) {
            Person signingPeople = new Person();
            signingPeople.setId(bean.getSigningPeople_());
            entity.setSigningPeople(signingPeople);
        }

        entity.setSigningStatus(bean.getSigningStatus());
        entity.setContractConstructionScale(bean.getContractConstructionScale());
        entity.setSignedContractsNumber(bean.getSignedContractsNumber());
        entity.setQuantities(bean.getQuantities());
        entity.setPartyA(bean.getPartyA());
        entity.setPartyB(bean.getPartyB());
        entity.setPartyAAgent(bean.getPartyAAgent());
        entity.setPartyBAgent(bean.getPartyBAgent());
        entity.setPartyAContactInfo(bean.getPartyAContactInfo());
        entity.setPartyBContactInfo(bean.getPartyBContactInfo());
        entity.setPartyC(bean.getPartyC());
        entity.setBriefIntroduction(bean.getBriefIntroduction());
        entity.setAmountCollected(bean.getAmountCollected());

        if (!StringUtils.isEmpty(bean.getReminderDate())) {
            try {
                entity.setReminderDate(
                        DateUtils.string2Date(bean.getReminderDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setReminderPeople(bean.getReminderPeople());
        entity.setGeneralContractor(bean.getGeneralContractor());
        entity.setChapterType(bean.getChapterType());
        entity.setConstructionUnitProjectNo(bean.getConstructionUnitProjectNo());
        entity.setConstructionUnitContractNo(bean.getConstructionUnitContractNo());
        entity.setInsurance(bean.getInsurance());
        entity.setIsFiled(bean.getIsFiled());

        if (!StringUtils.isEmpty(bean.getPlannedOperationTime())) {
            try {
                entity.setPlannedOperationTime(DateUtils.string2Date(bean.getPlannedOperationTime(),
                        Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getReturnDate())) {
            try {
                entity.setReturnDate(
                        DateUtils.string2Date(bean.getReturnDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setContractReturnInfo(bean.getContractReturnInfo());
        entity.setCountersignatureReturn(bean.getCountersignatureReturn());
        entity.setMainProvisions(bean.getMainProvisions());
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
