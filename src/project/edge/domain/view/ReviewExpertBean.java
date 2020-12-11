package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

public class ReviewExpertBean implements ViewBean {

	private String id;

    private String review_;
    private String reviewText;
    private String reviewContent;
    private String expert_;
    private String expertText;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }


    public String getReview_() {
        return this.review_;
    }


    public void setReview_(String review_) {
        this.review_ = review_;
    }
    
    
    public String getReviewContent() {
        return this.reviewContent;
    }


    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }


    public String getReviewText() {
        return this.reviewText;
    }


    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }


    public String getExpert_() {
        return this.expert_;
    }


    public void setExpert_(String expert_) {
        this.expert_ = expert_;
    }


    public String getExpertText() {
        return this.expertText;
    }


    public void setExpertText(String expertText) {
        this.expertText = expertText;
    }


    public void setId(String id) {
        this.id = id;
    }

}
