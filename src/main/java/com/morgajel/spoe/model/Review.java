package com.morgajel.spoe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.springframework.format.annotation.DateTimeFormat;

import com.morgajel.spoe.web.EditReviewForm;
import com.morgajel.spoe.web.EditSnippetForm;

/**
 * @author jmorgan
 *
 */

/**
 * Named Queries for retrieving Reviews.
 **/
@NamedQueries({
    /**
     * Returns a Review matching a given Reviewer.
     */
    @NamedQuery(
        name = "findReviewByAuthor",
        query = "from Review rev where rev.accountId = :account_id"
    ),
    /**
     * Returns a review matching a given id.
     */
    @NamedQuery(
        name = "findReviewById",
        query = "from Review rev where rev.reviewId = :review_id"
    ),
    /**
     * Returns review matching a given snippet_id.
     */
    @NamedQuery(
        name = "findReviewBySnippet",
        query = "from Review rev where rev.snippetId = :snippet_id"
    ),
    @NamedQuery(
            name = "findReviewRecentlyModifiedPublished",
            query = "from Review rev where published=true order by last_modified_date"
        )
})

/**
 * Review displays, edits and performs all other review-related tasks.
 **/
@Entity
@Indexed
@Table(name = "review",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "review_id") }
    )
public class Review implements Serializable {

    @ManyToOne
    @JoinColumn (name = "account_id", updatable = false, insertable = false)
    @IndexedEmbedded
    private Account author;
    /**
     * Returns the account associated as the author of the review.
     * @return Account
     **/
    public Account getAuthor() {
        return this.author;
    }
    /**
     * Set an Account as the author of a Review.
     * @param pAuthor accounts to associate with the Review
     **/
    public void setAuthor(Account pAuthor) {
        this.author = pAuthor;
    }

    @ManyToOne
    @JoinColumn (name = "snippet_id", updatable = false, insertable = false)
    @IndexedEmbedded
    private Snippet snippet;
    /**
     * Returns the snippet associated as the snippet of the review.
     * @return Snippet
     **/
    public Snippet getSnippet() {
        return this.snippet;
    }
    /**
     * Set a Snippet as the snippet of a Review.
     * @param pSnippet snippet to associate with the Review
     **/
    public void setSnippet(Snippet pSnippet) {
        this.snippet = pSnippet;
    }

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id", unique = true, nullable = false)
    @DocumentId
    private Long reviewId;
    @NotNull
    @Column(name = "account_id")
    private Long accountId;
    @NotNull
    @Column(name = "snippet_id")
    private Long snippetId;
    @DateTimeFormat
    @Column(name = "last_modified_date")
    @Field(index = Index.UN_TOKENIZED, store = Store.NO)
    @DateBridge(resolution = Resolution.MINUTE)
    private Date lastModifiedDate;
    @DateTimeFormat
    @Column(name = "creation_date")
    @Field(index = Index.UN_TOKENIZED, store = Store.NO)
    @DateBridge(resolution = Resolution.MINUTE)
    private Date creationDate;
    @NotNull
    @Column(name = "content")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String content;

    @Column(name = "published")
    private boolean published;

    private static final long serialVersionUID = -8772679180443991371L;

    private static final transient Logger LOGGER = Logger.getLogger(Review.class);

    /**
     * Constructor for Review.
     **/
    public Review() {
        lastModifiedDate = new Date();
        creationDate = new Date();
        this.content = "";
    }
    /**
     * A constructor for Review.
     * @param pAuthor Account that created the review.
     * @param pSnippet Snippet that created the review.
     **/
    public Review(Account pAuthor, Snippet pSnippet) {
        lastModifiedDate = new Date();
        creationDate = new Date();
        this.author = pAuthor;
        this.snippet = pSnippet;
        this.accountId = pAuthor.getAccountId();
        this.snippetId = pSnippet.getSnippetId();
    }
    /**
     * Configure base info from a given editReviewForm.
     * @param editReviewForm for to use
     */
    public void configure(EditReviewForm editReviewForm) {
        this.content = editReviewForm.getContent();
        this.published = editReviewForm.getPublished();
    }

    public Long getReviewId() {
        return reviewId;
    }
    public void setReviewId(Long pReviewId) {
        this.reviewId = pReviewId;
    }
    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long pAccountId) {
        this.accountId = pAccountId;
    }
    public Long getSnippetId() {
        return snippetId;
    }
    public void setSnippetId(Long pSnippetId) {
        this.snippetId = pSnippetId;
    }
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    public void setLastModifiedDate(Date pLastModifiedDate) {
        this.lastModifiedDate = pLastModifiedDate;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date pCreationDate) {
        this.creationDate = pCreationDate;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String pContent) {
        this.content = pContent;
    }
    public boolean getPublished() {
        return published;
    }
    public void setPublished(boolean pPublished) {
        this.published = pPublished;
    }
    @Override
    public String toString() {
        return "Review ["
                + "reviewId=" + reviewId
                + ", accountId=" + accountId
                + ", snippetId=" + snippetId
                + ", lastModifiedDate=" + lastModifiedDate
                + ", creationDate=" + creationDate
                + ", content=" + content
                + ", published=" + published
                + "]";
    }

}
