package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.Review;
import project.edge.domain.entity.ReviewExpert;
import project.edge.domain.view.ReviewExpertBean;

public class ReviewExpertBeanConverter implements ViewConverter<ReviewExpert, ReviewExpertBean> {
	
	@Override
    public ReviewExpertBean fromEntity(ReviewExpert entity,
            MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ReviewExpertBean bean = new ReviewExpertBean();

        bean.setId(entity.getId());
        
        Review review = entity.getReview();
        Expert expert = entity.getExpert();

        if (review != null) {
            bean.setReview_(review.getId());
            bean.setReviewText(review.getReviewedUnit());
            bean.setReviewContent(review.getReviewContent());
        }
        if (expert != null) {
            bean.setExpert_(expert.getId());
            bean.setExpertText(expert.getExpertName());
        }

        return bean;
    }

    @Override
    public ReviewExpert toEntity(ReviewExpertBean bean, ReviewExpert oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ReviewExpert entity = oldEntity;

        bean.setId(entity.getId()); // ID必须赋值

        if (!StringUtils.isEmpty(bean.getReview_())) {
            Review review = new Review();
            review.setId(bean.getReview_());
            entity.setReview(review);
        }

        if (!StringUtils.isEmpty(bean.getExpert_())) {
            Expert expert = new Expert();
            expert.setId(bean.getExpert_());
            entity.setExpert(expert);
        }

        return entity;
    }
}
