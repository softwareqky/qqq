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
import project.edge.domain.entity.AcceptanceInfo;
import project.edge.domain.entity.Person;
import project.edge.domain.view.AcceptanceInfoBean;


/**
 * @author angel_000
 *
 */
public class AcceptanceInfoBeanConverter
        implements ViewConverter<AcceptanceInfo, AcceptanceInfoBean> {

    @Override
    public AcceptanceInfoBean fromEntity(AcceptanceInfo entity, MessageSource messageSource,
            Locale locale) {

        AcceptanceInfoBean bean = new AcceptanceInfoBean();
        bean.setId(entity.getId());

        Person successfulBidder = entity.getSuccessfulBidder();
        if (successfulBidder != null) {
            bean.setSuccessfulBidder_(successfulBidder.getId());
            bean.setSuccessfulBidderText(successfulBidder.getPersonName());
        }

        if (entity.getApplicantDate() != null) {
            bean.setApplicantDate(
                    DateUtils.date2String(entity.getApplicantDate(), Constants.DATE_FORMAT));
        }

        if (entity.getWinningDate() != null) {
            bean.setWinningDate(
                    DateUtils.date2String(entity.getWinningDate(), Constants.DATE_FORMAT));
        }

        bean.setWinningAmount(entity.getWinningAmount());
        bean.setBidSecurity(entity.getBidSecurity());

        if (entity.getIsReturn() != null) {
            bean.setIsReturn(entity.getIsReturn());
            bean.setIsReturnText(entity.getIsReturn() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setImplementationDepartment(entity.getImplementationDepartment());

        Person projectLeader = entity.getProjectLeader();
        if (projectLeader != null) {
            bean.setProjectLeader_(projectLeader.getId());
            bean.setProjectLeaderText(projectLeader.getPersonName());
        }

        if (entity.getIsSigned() != null) {
            bean.setIsSigned(entity.getIsSigned());
            bean.setIsSignedText(entity.getIsSigned() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setSigningBody(entity.getSigningBody());

        if (entity.getEstimatedSigningTime() != null) {
            bean.setEstimatedSigningTime(
                    DateUtils.date2String(entity.getEstimatedSigningTime(), Constants.DATE_FORMAT));
        }

        if (entity.getIsBidWinningNotice() != null) {
            bean.setIsBidWinningNotice(entity.getIsBidWinningNotice());
            bean.setIsBidWinningNoticeText(entity.getIsBidWinningNotice() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        Person informingPeople = entity.getInformingPeople();
        if (informingPeople != null) {
            bean.setInformingPeople_(informingPeople.getId());
            bean.setInformingPeopleText(informingPeople.getPersonName());
        }

        bean.setContractingMode(entity.getContractingMode());
        bean.setContractScope(entity.getContractScope());
        bean.setCauseAnalysis(entity.getCauseAnalysis());


        return bean;
    }

    @Override
    public AcceptanceInfo toEntity(AcceptanceInfoBean bean, AcceptanceInfo oldEntity,
            AbstractSessionUserBean user, Date now) {

        AcceptanceInfo entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new AcceptanceInfo();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getSuccessfulBidder_())) {
            Person successfulBidder = new Person();
            successfulBidder.setId(bean.getSuccessfulBidder_());
            entity.setSuccessfulBidder(successfulBidder);
        }

        if (!StringUtils.isEmpty(bean.getApplicantDate())) {
            try {
                entity.setApplicantDate(
                        DateUtils.string2Date(bean.getApplicantDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getWinningDate())) {
            try {
                entity.setWinningDate(
                        DateUtils.string2Date(bean.getWinningDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setWinningAmount(bean.getWinningAmount());
        entity.setBidSecurity(bean.getBidSecurity());
        entity.setIsReturn(bean.getIsReturn());
        entity.setImplementationDepartment(bean.getImplementationDepartment());

        if (!StringUtils.isEmpty(bean.getProjectLeader_())) {
            Person projectLeader = new Person();
            projectLeader.setId(bean.getProjectLeader_());
            entity.setProjectLeader(projectLeader);
        }

        entity.setIsSigned(bean.getIsSigned());
        entity.setSigningBody(bean.getSigningBody());

        if (!StringUtils.isEmpty(bean.getEstimatedSigningTime())) {
            try {
                entity.setEstimatedSigningTime(DateUtils.string2Date(bean.getEstimatedSigningTime(),
                        Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setIsBidWinningNotice(bean.getIsBidWinningNotice());

        if (!StringUtils.isEmpty(bean.getInformingPeople_())) {
            Person informingPeople = new Person();
            informingPeople.setId(bean.getInformingPeople_());
            entity.setInformingPeople(informingPeople);
        }

        entity.setContractingMode(bean.getContractingMode());
        entity.setContractScope(bean.getContractScope());
        entity.setCauseAnalysis(bean.getCauseAnalysis());

        return entity;
    }

}
