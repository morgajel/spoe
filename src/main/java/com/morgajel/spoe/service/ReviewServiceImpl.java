package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Review;
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.dao.ReviewDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default Review Service Implementation used for managing ReviewDao.
 */
@Service("reviewService")
@Transactional(propagation = Propagation.SUPPORTS)
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;
    private static final transient Logger LOGGER = Logger.getLogger(ReviewServiceImpl.class);

    public void setReviewDao(ReviewDao pReviewDao) {
        this.reviewDao = pReviewDao;
    }

    /**
     * Set reviewDao for accessing the datasource.
     * @param review the reviewDao you wish to set
     */
    @Override
    public void saveReview(Review review) {
        reviewDao.saveReview(review);
    }

    @Override
    public List<Review> listReviews() {
        return reviewDao.listReviews();
    }

    @Override
    public Review loadById(Long id) {
        LOGGER.info("Loading review " + id);
        Review review = reviewDao.loadById(id);
        return review;
    }

    @Override
    public List<Review> loadByAuthor(Account account) {
        LOGGER.info("Loading reviews owned by  " + account.getUsername());
        List<Review> review = reviewDao.loadByAuthor(account);
        return review;
    }

    @Override
    public List<Review> loadBySnippet(Snippet snippet) {
        LOGGER.info("Loading published reviews for " + snippet.getTitle());
        List<Review> review = reviewDao.loadBySnippet(snippet);
        return review;
    }
    @Override
    public List<Review> loadRecentlyModifiedPublished(int count) {
        LOGGER.info("Loading last " + count + " recently modified published reviews.");
        List<Review> review = reviewDao.loadRecentlyModifiedPublished(count);
        return review;
    }

}
