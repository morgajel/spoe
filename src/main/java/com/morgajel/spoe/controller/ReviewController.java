package com.morgajel.spoe.controller;


import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Review;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.ReviewService;
import com.morgajel.spoe.web.EditReviewForm;

/**
 * Controls all review interactions, etc.
 */
@Controller
@RequestMapping(value = "/review")
public class ReviewController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ReviewService reviewService;

    private MailSender mailSender;

    private static final transient Logger LOGGER = Logger.getLogger(ReviewController.class);

    public void setMailSender(MailSender pMailSender) {
        //NOTE this may not be used anytime soon.
        this.mailSender = pMailSender;
    }

    public MailSender getMailSender() {
        //TODO is this needed?
        return this.mailSender;
    }

    /**
     * Saves the results of the incoming review form.
     * @param editReviewForm the form you are submitting with review data.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    //TODO figure out why I can't mark editReviewForm @Valid
    public ModelAndView saveReview(EditReviewForm editReviewForm) {
        ModelAndView mav = new ModelAndView();
        try {
            //FIXME refactor this whole try block
            LOGGER.debug("trying to save " + editReviewForm);
            Account account = getContextAccount();
            if (editReviewForm.getReviewId() == null) {
                LOGGER.info("no reviewId found, saving as a new review " + editReviewForm);
                Review review = new Review();
                review.configure(editReviewForm);
                reviewService.saveReview(review);
                editReviewForm.importReview(review);
                // FIXME how do I get reviewID in there??
                LOGGER.info("saved new review " + review);
                mav.setViewName("review/editReview");
                mav.addObject("message", "Review saved.");
            } else {
                LOGGER.info("reviewId found, verifying it's a real review " + editReviewForm);
                Review review = reviewService.loadById(editReviewForm.getReviewId());
                if (review != null) {
                    if (review.getAuthor().getUsername().equals(account.getUsername())) {
                        LOGGER.info("look, '" + review.getReviewId() + "' is a real review and owned by " + account.getUsername());
                        review.configure(editReviewForm);
                        reviewService.saveReview(review);
                        LOGGER.info("saved existing review " + review.getReviewId());

                        editReviewForm.importReview(review);
                        LOGGER.info("refreshing editReviewForm with " + editReviewForm);
                        mav.setViewName("review/editReview");
                        mav.addObject("message", "Review saved.");
                    } else {
                        mav.setViewName("review/reviewFailure");
                        mav.addObject("message", "I'm sorry, you're not the author of this review.");
                    }
                } else {
                    mav.setViewName("review/reviewFailure");
                    mav.addObject("message", "I'm sorry, that review wasn't found.");
                }
            }

        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("It couldn't save " + editReviewForm);
            LOGGER.error("damnit, something failed.", ex);
            mav.setViewName("review/reviewFailure");
            mav.addObject("message", "something failed.");
        }
        mav.addObject("editReviewForm", editReviewForm);
        return mav;
    }
    /**
     * Displays the form for editing a given review.
     * @param reviewId The ID of the review you wish to edit.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/edit/{reviewId}", method = RequestMethod.GET)
    public ModelAndView editReview(@PathVariable Long reviewId) {
        LOGGER.debug("trying to edit " + reviewId);
        ModelAndView mav = new ModelAndView();
        try {
            Review review = reviewService.loadById(reviewId);
            Account account = getContextAccount();
            EditReviewForm editReviewForm = new EditReviewForm();
            LOGGER.info(review);
            if (review != null) {
                LOGGER.info("Review found");
                if (account != null && review.getAuthor().getUsername().equals(account.getUsername())) {
                    LOGGER.info("Username matches " + review.getAuthor().getUsername());
                    mav.setViewName("review/editReview");
                    editReviewForm.importReview(review);
                    mav.addObject("editReviewForm", editReviewForm);
                } else {
                    LOGGER.info(SecurityContextHolder.getContext().getAuthentication().getName() + " isn't the author " + review.getAuthor().getUsername());
                    String message = "I'm sorry, Only the author can edit a review.";
                    mav.setViewName("review/viewReview");
                    mav.addObject("review", review);
                    mav.addObject("message", message);
                }
            } else {
                LOGGER.info("review doesn't exist");
                String message = "I'm sorry, " + reviewId + " was not found.";
                mav.setViewName("review/reviewFailure");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("Something failed while trying to display " + reviewId + ".", ex);
            mav.addObject("message", "Something failed while trying to display " + reviewId + ".");
            mav.setViewName("review/reviewFailure");
        }
        return mav;
    }

    /**
     * Displays the basic info for a given Review.
     * @param reviewId The review you wish to display.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/id/{reviewId}", method = RequestMethod.GET)
    public ModelAndView displayReview(@PathVariable Long reviewId) {
        LOGGER.debug("trying to display " + reviewId);
        ModelAndView mav = new ModelAndView();
        try {
            Account account = getContextAccount();
            Review review = reviewService.loadById(reviewId);
            LOGGER.info(review);
            if (review != null) {
                 if (account != null && review.getAuthor().getUsername().equals(account.getUsername())) {
                     // user logged in and owns it.
                     mav.addObject("editlink", "<div style='float:right;'><a href='/review/edit/" + review.getReviewId() + "'>[edit]</a></div>");
                     mav.addObject("review", review);
                     mav.setViewName("review/viewReview");
                 } else if (review.getPublished()) {
                     // user not logged in or doesn't own it
                     mav.addObject("review", review);
                     mav.setViewName("review/viewReview");
                 } else {
                     //  note is not published
                     String message = "I'm sorry, " + reviewId + " is not available.";
                     LOGGER.error(message);
                     mav.setViewName("review/reviewFailure");
                     mav.addObject("message", message);
                }
            } else {
                String message = "I'm sorry, " + reviewId + " was not found.";
                LOGGER.error(message);
                mav.setViewName("review/reviewFailure");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("Something failed while trying to display " + reviewId + ".", ex);
            mav.addObject("message", "Something failed while trying to display " + reviewId + ".");
            mav.setViewName("review/reviewFailure");
        }
        return mav;
    }

    /**
     * This will display the user's reviews.
     * @return ModelAndView mav
     */
    @RequestMapping("/my")
    public ModelAndView showMyReviews() {
        LOGGER.info("showing user reviews");
        ModelAndView mav = new ModelAndView();
        try {
            Account account = getContextAccount();
            if (account  != null) {
                mav.addObject("account", account);
                mav.setViewName("review/myReviews");
            } else {
                String message = "Odd, I couldn't find your account.";
                LOGGER.error(message);
                mav.addObject("message", message);
                mav.setViewName("review/reviewFailure");
            }
        } catch (Exception ex) {
            String message = "Something failed while trying to display user reviews.";
            LOGGER.error(message, ex);
            mav.addObject("message", message);
            mav.setViewName("review/reviewFailure");
        }
        return mav;
    }

    /**
     * This will display the review creation form.
     * @return ModelAndView mav
     */
    @RequestMapping("/create")
    public ModelAndView createReviewForm() {
        LOGGER.info("showing the createReviewForm");
        ModelAndView  mav = new ModelAndView();
        mav.setViewName("review/editReview");
        mav.addObject("editReviewForm", new EditReviewForm());
        return mav;
    }

    /**
     * This is the default view for review, a catch-all for most any one-offs.
     * Will show the generic review page.
     * @return ModelAndView mav
     */
    @RequestMapping("/")
    public ModelAndView defaultView() {
        LOGGER.info("showing the default view");
        ModelAndView  mav = new ModelAndView();
        mav.setViewName("review/view");
        mav.addObject("message", "show the default view for reviews");

        return mav;
    }

    public void setAccountService(AccountService pAccountService) {
        this.accountService = pAccountService;
    }

    public AccountService getAccountService() {
        return this.accountService;
    }

    public void setReviewService(ReviewService pReviewService) {
        this.reviewService = pReviewService;
    }

    public ReviewService getReviewService() {
        return this.reviewService;
    }

    /**
     * Returns the account for the current context.
     * @return Account
     */
    public Account getContextAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.loadByUsername(username);
    }

}
