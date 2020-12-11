package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.Review;
import project.edge.domain.entity.ReviewExpertComment;
import project.edge.domain.view.ReviewExpertCommentBean;

/**
 * @author angel_000
 *         转换评审专家意见表对应的view和entity的转换器。
 */
public class ReviewExpertCommentBeanConverter
        implements ViewConverter<ReviewExpertComment, ReviewExpertCommentBean> {

    @Override
    public ReviewExpertCommentBean fromEntity(ReviewExpertComment entity,
            MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ReviewExpertCommentBean bean = new ReviewExpertCommentBean();

        Review review = entity.getReview();
        Expert expert = entity.getExpert();

        if (review != null) {
            bean.setReview_(review.getId());
            bean.setReviewText(review.getReviewedUnit());
        }
        if (expert != null) {
            bean.setExpert_(expert.getId());
            bean.setExpertText(expert.getExpertName());
        }

        bean.setCommentContent(entity.getCommentContent());

        return bean;
    }

    @Override
    public ReviewExpertComment toEntity(ReviewExpertCommentBean bean, ReviewExpertComment oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ReviewExpertComment entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ReviewExpertComment();


            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

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

        entity.setCommentContent(bean.getCommentContent());

        return entity;
    }

}
