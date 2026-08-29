package com.synechisveltiosi.apis.app365.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.events.entity.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRequest {

    @NotBlank(message = "Event title is required")
    @Size(min = 6, max = 100, message = "Event title should be 6 to 100 characters. ")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "Event content is required")
    @JsonProperty("content")
    private String content;

    @JsonProperty("coverPicture")
    private String coverPicture;

    @JsonProperty("flagged")
    private boolean flagged;

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("endDate")
    private Date endDate;

    public Event mapToEvent() {

        Event event = new Event();

        event.setTitle(getTitle());
        event.setContent(getContent());
        event.setCoverPicture(String.format("https://via.placeholder.com/500x500?text=%s", this.getTitle()));
        event.setFlagged(isFlagged());
        event.setStartDate(getStartDate());
        event.setEndDate(getEndDate());

        return event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
