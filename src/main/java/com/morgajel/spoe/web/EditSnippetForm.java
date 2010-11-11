package com.morgajel.spoe.web;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.morgajel.spoe.model.Snippet;
/**
 * EditSnippetForm is used to validate Snippet Form info, as minor as the validation may be.
 * @author jmorgan
 *
 */
public class EditSnippetForm {
    @NotEmpty
    @DecimalMin(value = "1")
    private Long snippetId;
    private String content;
    @NotEmpty
    private String title;

    public Long getSnippetId() {
        return snippetId;
    }
    public void setSnippetId(Long pSnippetId) {
        this.snippetId = pSnippetId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String pContent) {
        this.content = pContent;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String pTitle) {
        this.title = pTitle;
    }
    /**
     * Load a snippet's properties into the editSnippetForm.
     * @param pSnippet source snippet
     */
    public void loadSnippet(Snippet pSnippet) {
        this.title = pSnippet.getTitle();
        this.snippetId = pSnippet.getSnippetId();
        this.content = pSnippet.getContent();

    }

    @Override
    public String toString() {
        return "EditSnippetForm [snippetID=" + snippetId + ", content="
                + content + ", title=" + title + "]";
    }
}
