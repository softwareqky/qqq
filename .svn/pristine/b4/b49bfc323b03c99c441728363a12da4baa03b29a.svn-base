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
import project.edge.domain.entity.OutbiddingInfo;
import project.edge.domain.entity.Person;
import project.edge.domain.view.OutbiddingInfoBean;


/**
 * @author angel_000
 *
 */
public class OutbiddingInfoBeanConverter
        implements ViewConverter<OutbiddingInfo, OutbiddingInfoBean> {

    @Override
    public OutbiddingInfoBean fromEntity(OutbiddingInfo entity, MessageSource messageSource,
            Locale locale) {

        OutbiddingInfoBean bean = new OutbiddingInfoBean();
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

        if (entity.getDropDate() != null) {
            bean.setDropDate(DateUtils.date2String(entity.getDropDate(), Constants.DATE_FORMAT));
        }

        bean.setBidSecurity(entity.getBidSecurity());

        if (entity.getIsReturn() != null) {
            bean.setIsReturn(entity.getIsReturn());
            bean.setIsReturnText(entity.getIsReturn() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        Person informingPeople = entity.getInformingPeople();
        if (informingPeople != null) {
            bean.setInformingPeople_(informingPeople.getId());
            bean.setInformingPeopleText(informingPeople.getPersonName());
        }

        bean.setCompetitor(entity.getCompetitor());
        bean.setScoreRanking(entity.getScoreRanking());
        bean.setCauseAnalysis(entity.getCauseAnalysis());


        return bean;
    }

    @Override
    public OutbiddingInfo toEntity(OutbiddingInfoBean bean, OutbiddingInfo oldEntity,
            AbstractSessionUserBean user, Date now) {

        OutbiddingInfo entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new OutbiddingInfo();

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

        if (!StringUtils.isEmpty(bean.getDropDate())) {
            try {
                entity.setDropDate(
                        DateUtils.string2Date(bean.getDropDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setBidSecurity(bean.getBidSecurity());
        entity.setIsReturn(bean.getIsReturn());

        if (!StringUtils.isEmpty(bean.getInformingPeople_())) {
            Person informingPeople = new Person();
            informingPeople.setId(bean.getInformingPeople_());
            entity.setInformingPeople(informingPeople);
        }

        entity.setCompetitor(bean.getCompetitor());
        entity.setScoreRanking(bean.getScoreRanking());
        entity.setCauseAnalysis(bean.getCauseAnalysis());

        return entity;
    }

}
