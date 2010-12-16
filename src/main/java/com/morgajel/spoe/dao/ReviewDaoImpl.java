package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Review;
import com.morgajel.spoe.model.Snippet;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Implementation of the reviewDao interface, used to save and load reviews from the datasource.
 */
@Repository("reviewDao")
public class ReviewDaoImpl implements ReviewDao {

    @Autowired
    private SessionFactory sessionFactory;
    private static final transient Logger LOGGER = Logger.getLogger(ReviewDaoImpl.class);

    /**
     * Sets the Session Factory used to get the currentSession.
     * @param pSessionFactory sessionFactory to be used
     */
    public void setSessionFactory(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;
    }
    /**
     * Get the Session Factory used to get the currentSession.
     * @return SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
    /**
     * Saves a given review to the datasource.
     * @param review review to save
     */
    @Override
    public void saveReview(Review review) {
        sessionFactory.getCurrentSession().saveOrUpdate(review);
        LOGGER.info("saved review" + review);
    }
    @Override
    public List<Review> listReviews() {
        return (List<Review>) sessionFactory.getCurrentSession().createCriteria(Review.class).list();
    }

    @Override
    public Review loadById(Long id) {
        List<Review> sniplist = sessionFactory.getCurrentSession().getNamedQuery("findReviewById").setLong("review_id", id).list();
        if (sniplist.size() > 0) {
            LOGGER.info("Loaded review " + sniplist.get(0));
            return (Review) sniplist.get(0);
        } else {
            return null;
        }
    }
    @Override
    public List<Review> loadByAuthor(Account account) {
        return (List<Review>) sessionFactory.getCurrentSession().getNamedQuery("findReviewByAuthor").setLong("account_id", account.getAccountId()).list();
    }

    @Override
    public List<Review> loadRecentlyModifiedPublished(int count) {
        return (List<Review>) sessionFactory.getCurrentSession().getNamedQuery("findReviewRecentlyModifiedPublished").setMaxResults(5).list();
    }
    /** {@inheritDoc}  */
    @Override
    public List<Review> loadBySnippet(Snippet snippet) {
        return (List<Review>) sessionFactory.getCurrentSession().getNamedQuery("findReviewBySnippet").list();
    }

}
