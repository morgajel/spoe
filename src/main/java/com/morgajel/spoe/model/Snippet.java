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
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Named Queries for retrieving Snippets.
 **/
@NamedQueries({
    /**
     * Returns a snippet matching a given title.
     */
    @NamedQuery(
        name = "findSnippetByTitle",
        query = "from Snippet snip where snip.title = :title"
    ),
    /**
     * Returns a snippet matching a given id.
     */
    @NamedQuery(
        name = "findSnippetById",
        query = "from Snippet snip where snip.snippetId = :snippet_id"
    ),
    /**
     * Returns snippets matching a given account_id.
     */
    @NamedQuery(
        name = "findSnippetByAuthor",
        query = "from Snippet snip where snip.accountId = :account_id"
    )
})

/**
 * Snippet manager displays, edits and performs all other snippet-related tasks.
 **/
@Entity
@Table(name = "snippet")
public class Snippet implements Serializable {

    
    
    @ManyToOne
    @JoinColumn (name="account_id", updatable = false, insertable = false)
    private Account author;
    /**
     * Returns the account associated as the author of the snippet.
     * @return Account
     **/
    public Account getAuthor() {
        return this.author;
    }
    /**
     * Set an Account as the author of a Snippet.
     * @param pAuthor accounts to associate with the snippet
     **/
    public void setAuthor(Account pAuthor) {
        this.author = pAuthor;
    }

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "snippet_id")
    private Long snippetId;
    @NotNull
    @Column(name = "account_id")
    private Long accountId;
    @NotNull
    @Column(name = "title")
    private String title;
    @DateTimeFormat
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
    @DateTimeFormat
    @Column(name = "creation_date")
    private Date creationDate;
    @NotNull
    @Column(name = "content")
    private String content;


    private static final long serialVersionUID = -5461020313014420728L;

    private static transient Logger logger = Logger.getLogger(Snippet.class);

    
    /**
     * Constructor for snippet.
     **/
    public Snippet() {
        this.setLastModifiedDate(new Date());
        this.setCreationDate(new Date());
        this.content = "";

    }
    
    /**
     * Primary constructor for snippet.
     * @param pAuthor Account that created the snippet.
     * @param pTitle Title of the snippet.
     **/
    public Snippet(Account pAuthor, String pTitle) {
        this.setLastModifiedDate(new Date());
        this.setCreationDate(new Date());
        this.content = "";
        this.author = pAuthor;
        this.title = pTitle;
    }
    /**
     * Returns the snippetId of the Snippet instance.
     * @return Long
     */
    public Long getSnippetId() {
        return snippetId;
    }
    /**
     * Returns the accountId of the Snippet instance.
     * @return Long
     */
    public Long getAccountId() {
        return this.accountId;
    }

    /**
     * Sets the snippetId of the Snippet instance.
     * @param pSnippetId sets the snippetId
     **/
    public void setSnippetId(Long pSnippetId) {
        this.snippetId = pSnippetId;
    }
    /**
     * Sets the accountId of the Snippet instance.
     * @param pAccountId sets the snippetId
     **/
    public  void setAccountId( Long pAccountId) {
        this.accountId=pAccountId;
    }

    
    /**
     * Gets this.creationDate, the date when the snippet was created.
     * @return Date creationDate
     **/
    public Date getCreationDate() {
        return (Date) creationDate.clone();
    }



    /**
     * Sets this.creationDate, which should only be used once.
     * @param cDate date of creation of Snippet
     **/
    public void setCreationDate(Date cDate) {
        //TODO need to enforce this as only happening once, perhaps final?
        this.creationDate = (Date) cDate.clone();
    }
    /**
     * Gets this.lastModifiedDate, which tells when the snippet was last modified.
     * @return Date lastModifiedDate
     **/
    public Date getLastModifiedDate() {
        return (Date) lastModifiedDate.clone();
    }
    /**
     * Sets this.lastModifiedDate, which tells when the snippet was last modified.
     * @param pLastModifiedDate date that code was last modified.
     **/
    public void setLastModifiedDate(Date pLastModifiedDate) {
        //TODO change this to timestamp in mysql and it will auto update
        //FIXME should be lastModifiedDate
        this.lastModifiedDate = (Date) pLastModifiedDate.clone();
    }

    /**
     * Returns the content of the Snippet instance.
     * @return String
     **/
    public String getContent() {
        return this.content;
    }
    /**
     * Sets the content of the Snippet instance.
     * @param pContent Set the content of the snippet
     **/
    public void setContent(String pContent) {
        this.content = pContent;
    }

    /**
     * Returns the title of the Snippet instance.
     * @return String
     **/
    public String getTitle() {
        return this.title;
    }
    /**
     * Sets the title of the Snippet instance.
     * @param pTitle Set the title of the snippet
     **/
    public void setTitle(String pTitle) {
        this.title = pTitle;
    }
    /**
     * Overrides the toString with pertinent information.
     * @return String
     **/
    @Override
    public String toString() {
        //TODO may want to include userlist here.
        logger.debug("printing toString");
        return "Snippet "
                + "[ snippetId=" + snippetId
                + ", title=" + title
                + ", author=" + author.getUsername()
                +  "]";
    }
}
