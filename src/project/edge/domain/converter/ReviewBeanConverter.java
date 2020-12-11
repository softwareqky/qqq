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
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.Review;
import project.edge.domain.view.ReviewBean;

/**
 * @author angel_000
 *         转换评审表对应的view和entity的转换器。
 *
 */
public class ReviewBeanConverter implements ViewConverter<Review, ReviewBean> {

    @Override
    public ReviewBean fromEntity(Review entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ReviewBean bean = new ReviewBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();
        DataOption reviewType = entity.getReviewType();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
        if (reviewType != null) {
            bean.setReviewType_(reviewType.getId());
            bean.setReviewTypeText(reviewType.getOptionName());
        }

        bean.setReviewingUnit(entity.getReviewingUnit());
        bean.setReviewedUnit(entity.getReviewedUnit());
        bean.setReviewContent(entity.getReviewContent());
        bean.setReviewBasis(entity.getReviewBasis());
        
        ProjectPerson reviewer = entity.getReviewer();
        if (reviewer != null) {
            bean.setReviewer_(reviewer.getId());
            bean.setReviewerText(reviewer.getPerson().getPersonName());
        }

//        Set<Expert> experts = entity.getExperts();

        // if (experts != null && !experts.isEmpty()) {
        // for (Expert expert : experts) {
        // bean.getExpert_().add(expert.getId());
        // }
        // }

        bean.setIsImprove(entity.getIsImprove());
        if (entity.getIsImprove() != null) {
            bean.setIsImproveText(entity.getIsImprove() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        DataOption reviewResult = entity.getReviewResult();
        DataOption verifyResult = entity.getVerifyResult();

        if (reviewResult != null) {
            bean.setReviewResult_(reviewResult.getId());
            bean.setReviewResultText(reviewResult.getOptionName());
        }

        if (verifyResult != null) {
            bean.setVerifyResult_(verifyResult.getId());
            bean.setVerifyResultText(verifyResult.getOptionName());
        }

        if (entity.getReviewDate() != null) {
            bean.setReviewDate(
                    DateUtils.date2String(entity.getReviewDate(), Constants.DATE_FORMAT));
        }
        if (entity.getImproveReqDate() != null) {
            bean.setImproveReqDate(
                    DateUtils.date2String(entity.getImproveReqDate(), Constants.DATE_FORMAT));
        }
        if (entity.getImprovePlanDate() != null) {
            bean.setImprovePlanDate(
                    DateUtils.date2String(entity.getImprovePlanDate(), Constants.DATE_FORMAT));
        }
        if (entity.getImproveActualDate() != null) {
            bean.setImproveActualDate(
                    DateUtils.date2String(entity.getImproveActualDate(), Constants.DATE_FORMAT));
        }

        bean.setReviewResultContent(entity.getReviewResultContent());
        bean.setImproveReq(entity.getImproveReq());
        bean.setImprovePlan(entity.getImprovePlan());

        if (entity.getVerifyDate() != null) {
            bean.setVerifyDate(
                    DateUtils.date2String(entity.getVerifyDate(), Constants.DATE_FORMAT));
        }
        bean.setImproveResultVerify(entity.getImproveResultVerify());
        bean.setRemark(entity.getRemark());

        if (entity.getFlowStartDate() != null) {
            bean.setFlowStartDate(
                    DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
        }
        if (entity.getFlowEndDate() != null) {
            bean.setFlowEndDate(
                    DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
        }
        bean.setFlowStatus(entity.getFlowStatus());
        bean.setFlowStatusText(messageSource
                .getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));

        DataOption reviewStatus = entity.getReviewStatus();
        if (reviewStatus != null) {
            bean.setReviewStatus_(reviewStatus.getId());
            bean.setReviewStatusText(reviewStatus.getOptionName());
        }
        
        return bean;
    }

    @Override
    public Review toEntity(ReviewBean bean, Review oldEntity, AbstractSessionUserBean user,
            Date now) {
        // TODO Auto-generated method stub
        Review entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new Review();

            entity.setRemark("");
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        if (!StringUtils.isEmpty(bean.getReviewType_())) {
            DataOption reviewType = new DataOption();
            reviewType.setId(bean.getReviewType_());
            entity.setReviewType(reviewType);
        }

        entity.setReviewingUnit(bean.getReviewingUnit());
        entity.setReviewedUnit(bean.getReviewedUnit());
        entity.setReviewContent(bean.getReviewContent());
        entity.setReviewBasis(bean.getReviewBasis());
        
        if (!StringUtils.isEmpty(bean.getReviewer_())) {
        	ProjectPerson reviewer = new ProjectPerson();
        	Person person = new Person();
            person.setPersonName(bean.getReviewerText());
            reviewer.setId(bean.getReviewer_());
            reviewer.setPerson(person);
            entity.setReviewer(reviewer);
        }

        // entity.getExperts().clear();
        // for (String expertId : bean.getExpert_()) {
        // Expert expert = new Expert();
        // expert.setId(expertId);
        // entity.getExperts().add(expert);
        // }
        if (bean.getIsImprove() != null) {
            entity.setIsImprove(bean.getIsImprove());
        }

        if (!StringUtils.isEmpty(bean.getReviewResult_())) {
            DataOption reviewResult = new DataOption();
            reviewResult.setId(bean.getReviewResult_());
            entity.setReviewResult(reviewResult);
        }

        if (!StringUtils.isEmpty(bean.getVerifyResult_())) {
            DataOption verifyResult = new DataOption();
            verifyResult.setId(bean.getVerifyResult_());
            entity.setVerifyResult(verifyResult);
        }

        if (!StringUtils.isEmpty(bean.getReviewDate())) {
            try {
                entity.setReviewDate(
                        DateUtils.string2Date(bean.getReviewDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getImproveReqDate())) {
            try {
                entity.setImproveReqDate(
                        DateUtils.string2Date(bean.getImproveReqDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getImprovePlanDate())) {
            try {
                entity.setImprovePlanDate(
                        DateUtils.string2Date(bean.getImprovePlanDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getImproveActualDate())) {
            try {
                entity.setImproveActualDate(
                        DateUtils.string2Date(bean.getImproveActualDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setReviewResultContent(bean.getReviewResultContent());
        entity.setImproveReq(bean.getImproveReq());
        entity.setImprovePlan(bean.getImprovePlan());

        if (!StringUtils.isEmpty(bean.getVerifyDate())) {
            try {
                entity.setVerifyDate(
                        DateUtils.string2Date(bean.getVerifyDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        entity.setImproveResultVerify(bean.getImproveResultVerify());
        entity.setRemark(bean.getRemark());

        if (!StringUtils.isEmpty(bean.getFlowStartDate())) {
            try {
                entity.setFlowStartDate(
                        DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
            try {
                entity.setFlowEndDate(
                        DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        entity.setFlowStatus(bean.getFlowStatus());
        
        if (!StringUtils.isEmpty(bean.getReviewStatus_())) {
            DataOption reviewStatus = new DataOption();
            reviewStatus.setId(bean.getReviewStatus_());
            entity.setReviewStatus(reviewStatus);
        }

        return entity;
    }

}
