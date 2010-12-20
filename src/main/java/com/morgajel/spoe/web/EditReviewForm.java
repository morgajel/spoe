package com.morgajel.spoe.web;

import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.NotEmpty;
import com.morgajel.spoe.model.Review;
import com.morgajel.spoe.model.Snippet;
/**
 * EditReviewForm is used to validate Review Form info, as minor as the validation may be.
 * @author jmorgan
 *
 */
public class EditReviewForm {
    @NotEmpty
    private Long reviewId;
    private Long snippetId;
    private Snippet snippet;
    private String content;

    private boolean published;

    public Long getReviewId() {
        return reviewId;
    }
    public void setReviewId(Long pReviewId) {
        this.reviewId = pReviewId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String pContent) {
        this.content = pContent;
    }

    /**
     * @param pPublished the published to set
     */
    public void setPublished(boolean pPublished) {
        this.published = pPublished;
    }
    /**
     * @return the published
     */
    public boolean getPublished() {
        return published;
    }
    /**
     * import a review's properties into the editReviewForm.
     * @param pReview source review
     */
    public void importReview(Review pReview) {
        this.reviewId = pReview.getReviewId();
        this.content = pReview.getContent();
        this.published = pReview.getPublished();
    }
    /**
     * import a review's properties into the editReviewForm.
     * @param pReview source review
     */
    public void importSnippet(Snippet pSnippet) {
        this.snippetId = pSnippet.getSnippetId();
        this.snippet = pSnippet;
    }    

    @Override
    public String toString() {
        return "EditReviewForm [reviewID=" + reviewId + ", content="
                + content + "]";
    }
}
