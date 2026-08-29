package com.synechisveltiosi.apis.app365.videos.helper;

import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import com.synechisveltiosi.apis.app365.users.dto.UserActionMetaResponse;
import com.synechisveltiosi.apis.app365.videos.dto.VideoResponse;
import com.synechisveltiosi.apis.app365.videos.entity.Video;
import com.synechisveltiosi.apis.app365.videos.mapper.VideoCommentMapper;
import com.synechisveltiosi.apis.app365.videos.mapper.VideoMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class VideoHelper {

    /**
     * Inject user meta data
     *
     * @param userId
     * @param videos
     * @return
     */
    public static List<VideoResponse> processVideoMetadata(Long userId, List<Video> videos) {
        return videos.stream()
                .map(video -> processVideoMetada(userId, video))
                .collect(Collectors.toList());
    }

    public static VideoResponse processVideoMetada(Long userId, Video video) {
        VideoResponse videoResponse = VideoMapper.INSTANCE.from(video);

        // Mark user likes
        markUserLikes(userId, video, videoResponse);

        // Mark user share
        markUserShare(userId, video, videoResponse);

        // Map de last comment
        mapLastComment(video, videoResponse);

        return videoResponse;
    }

    @SuppressWarnings("Duplicates")
    public static List<VideoResponse> nullifyListOnlyFields(List<VideoResponse> videoResponses) {
        return videoResponses.stream()
                .peek(video -> {
                    video.setSummary(null);
                    video.setCursor(null);
                    video.setMeta(null);
                })
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    private static void markUserShare(Long userId, Video video, VideoResponse videoResponse) {
        if (!video.getShares().isEmpty()) {
            // If the user is found that means he shared this video already, add the share flag
            video.getShares().stream()
                    .filter(videoShare -> Objects.equals(videoShare.getUserId().getId(), userId))
                    .forEach(videoShare -> {
                        if (videoResponse.getMeta() == null)
                            videoResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        videoResponse.getMeta().getUser().withShared(Boolean.TRUE);
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void markUserLikes(Long userId, Video video, VideoResponse videoResponse) {
        if (!video.getLikes().isEmpty()) {
            // If I liked this video already, add the like flag
            video.getLikes().stream()
                    .filter(videoLike -> Objects.equals(videoLike.getUserId().getId(), userId))
                    .forEach(videoLike -> {
                        if (videoResponse.getMeta() == null)
                            videoResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        videoResponse.getMeta().getUser().withLiked(videoLike.isLiked());
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void mapLastComment(Video video, VideoResponse videoResponse) {
        if (video.getLastComment() != null) {
            if (videoResponse.getMeta() == null)
                videoResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
            videoResponse.getMeta().setLastComment(VideoCommentMapper.INSTANCE.from(video.getLastComment()));
        }
    }
}