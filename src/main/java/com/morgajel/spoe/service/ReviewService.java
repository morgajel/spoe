package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Review;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;

public interface ReviewService {
    /**
     * Save a provided review to the datasource.
     * @param review review to save
     */
    void saveReview(Review review);

    /**
     * List all reviews in the datasource.
     * @return List<Review>
     */
    List<Review> listReviews();

    /**
     * Return an review with a given ID, or else null.
     * @param id review title to load
     * @return Review
     */
    Review loadById(Long id);

    /**
     * List all reviews in the datasource owned by a given account.
     * @param account account to look for
     * @return List<Review>
     */
    List<Review> loadByAuthor(Account account);

    /**
     * List all reviews in the datasource owned by a given snippet.
     * @param snippet snippet to look for
     * @return List<Review>
     */
    List<Review> loadBySnippet(Snippet snippet);

    /**
     * List x published reviews that were recently modified.
     * @param count number of reviews to return
     * @return List<Review>
     */
    List<Review> loadRecentlyModifiedPublished(int count);
}
