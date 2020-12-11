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
import project.edge.domain.entity.BiddingBasicInfo;
import project.edge.domain.entity.Person;
import project.edge.domain.view.BiddingBasicInfoBean;


/**
 * @author angel_000
 *
 */
public class BiddingBasicInfoBeanConverter
        implements ViewConverter<BiddingBasicInfo, BiddingBasicInfoBean> {

    @Override
    public BiddingBasicInfoBean fromEntity(BiddingBasicInfo entity, MessageSource messageSource,
            Locale locale) {

        BiddingBasicInfoBean bean = new BiddingBasicInfoBean();
        bean.setId(entity.getId());

        Person applicant = entity.getApplicant();
        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
        }

        if (entity.getApplicantDate() != null) {
            bean.setApplicantDate(
                    DateUtils.date2String(entity.getApplicantDate(), Constants.DATE_FORMAT));
        }

        Person biddingLeader = entity.getBiddingLeader();
        if (biddingLeader != null) {
            bean.setBiddingLeader_(biddingLeader.getId());
            bean.setBiddingLeaderText(biddingLeader.getPersonName());
        }

        bean.setBiddingDocumentType(entity.getBiddingDocumentType());
        bean.setTenderDocumentType(entity.getTenderDocumentType());

        bean.setIsAutoBidding(entity.getIsAutoBidding());
        bean.setIsAutoBiddingText(entity.getIsAutoBidding() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setBiddingSupport(entity.getBiddingSupport());

        Person projectLeader = entity.getProjectLeader();
        if (projectLeader != null) {
            bean.setProjectLeader_(projectLeader.getId());
            bean.setProjectLeaderText(projectLeader.getPersonName());
        }

        bean.setCompetitor(entity.getCompetitor());

        if (entity.getOpeningDate() != null) {
            bean.setOpeningDate(
                    DateUtils.date2String(entity.getOpeningDate(), Constants.DATE_FORMAT));
        }

        bean.setPriceCeiling(entity.getPriceCeiling());
        bean.setPriceUnit(entity.getPriceUnit());
        bean.setQuotedPrice(entity.getQuotedPrice());
        bean.setBidEvaluationMethod(entity.getBidEvaluationMethod());
        bean.setReferencePrice(entity.getReferencePrice());

        if (entity.getBidQaDate() != null) {
            bean.setBidQaDate(DateUtils.date2String(entity.getBidQaDate(), Constants.DATE_FORMAT));
        }

        bean.setBidSecurity(entity.getBidSecurity());
        bean.setPaymentMethod(entity.getPaymentMethod());

        if (entity.getSiteInvestigationDate() != null) {
            bean.setSiteInvestigationDate(DateUtils.date2String(entity.getSiteInvestigationDate(),
                    Constants.DATE_FORMAT));
        }


        Person reader = entity.getReader();
        if (reader != null) {
            bean.setReader_(reader.getId());
            bean.setReaderText(reader.getPersonName());
        }

        bean.setAdvanceAmount(entity.getAdvanceAmount());
        bean.setAdvancePeriod(entity.getAdvancePeriod());
        bean.setProfitMargin(entity.getProfitMargin());

        Person bidApplicant = entity.getBidApplicant();
        if (bidApplicant != null) {
            bean.setBidApplicant_(bidApplicant.getId());
            bean.setBidApplicantText(bidApplicant.getPersonName());
        }

        bean.setBidResult(entity.getBidResult());
        bean.setBidResultText(entity.getBidResult() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        bean.setWinningAmount(entity.getWinningAmount());
        bean.setSiteFeeContent(entity.getSiteFeeContent());
        bean.setTenderDocumentContent(entity.getTenderDocumentContent());
        bean.setBiddingStrategy(entity.getBiddingStrategy());


        return bean;
    }

    @Override
    public BiddingBasicInfo toEntity(BiddingBasicInfoBean bean, BiddingBasicInfo oldEntity,
            AbstractSessionUserBean user, Date now) {

        BiddingBasicInfo entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new BiddingBasicInfo();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
        }

        if (!StringUtils.isEmpty(bean.getApplicantDate())) {
            try {
                entity.setApplicantDate(
                        DateUtils.string2Date(bean.getApplicantDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getBiddingLeader_())) {
            Person biddingLeader = new Person();
            biddingLeader.setId(bean.getBiddingLeader_());
            entity.setBiddingLeader(biddingLeader);
        }

        entity.setBiddingDocumentType(bean.getBiddingDocumentType());
        entity.setTenderDocumentType(bean.getTenderDocumentType());
        entity.setIsAutoBidding(bean.getIsAutoBidding());
        entity.setBiddingSupport(bean.getBiddingSupport());

        if (!StringUtils.isEmpty(bean.getProjectLeader_())) {
            Person projectLeader = new Person();
            projectLeader.setId(bean.getProjectLeader_());
            entity.setProjectLeader(projectLeader);
        }

        entity.setCompetitor(bean.getCompetitor());

        if (!StringUtils.isEmpty(bean.getOpeningDate())) {
            try {
                entity.setOpeningDate(
                        DateUtils.string2Date(bean.getOpeningDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setPriceCeiling(bean.getPriceCeiling());
        entity.setPriceUnit(bean.getPriceUnit());
        entity.setQuotedPrice(bean.getQuotedPrice());
        entity.setBidEvaluationMethod(bean.getBidEvaluationMethod());
        entity.setReferencePrice(bean.getReferencePrice());

        if (!StringUtils.isEmpty(bean.getBidQaDate())) {
            try {
                entity.setBidQaDate(
                        DateUtils.string2Date(bean.getBidQaDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setBidSecurity(bean.getBidSecurity());
        entity.setPaymentMethod(bean.getPaymentMethod());

        if (!StringUtils.isEmpty(bean.getSiteInvestigationDate())) {
            try {
                entity.setSiteInvestigationDate(DateUtils
                        .string2Date(bean.getSiteInvestigationDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getReader_())) {
            Person reader = new Person();
            reader.setId(bean.getReader_());
            entity.setReader(reader);
        }

        entity.setAdvanceAmount(bean.getAdvanceAmount());
        entity.setAdvancePeriod(bean.getAdvancePeriod());
        entity.setProfitMargin(bean.getProfitMargin());

        if (!StringUtils.isEmpty(bean.getBidApplicant_())) {
            Person bidApplicant = new Person();
            bidApplicant.setId(bean.getBidApplicant_());
            entity.setBidApplicant(bidApplicant);
        }

        entity.setBidResult(bean.getBidResult());
        entity.setWinningAmount(bean.getWinningAmount());
        entity.setSiteFeeContent(bean.getSiteFeeContent());
        entity.setTenderDocumentContent(bean.getTenderDocumentContent());
        entity.setBiddingStrategy(bean.getBiddingStrategy());

        return entity;
    }

}
