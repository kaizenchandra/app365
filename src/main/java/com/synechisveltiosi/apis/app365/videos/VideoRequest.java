package com.synechisveltiosi.apis.app365.videos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.videos.entity.Video;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoRequest {

    @NotBlank(message = "Video title is required")
    @Size(min = 6, max = 100, message = "Video title should be 6 to 100 characters. ")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "Video content is required")
    @JsonProperty("content")
    private String content;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("url")
    private String url;

    public Video mapToVideo() {

        Video video = new Video();

        video.setTitle(getTitle());
        video.setContent(getContent());
        video.setThumbnail(getThumbnail());
        video.setUrl(getUrl());

        return video;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
