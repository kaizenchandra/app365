package com.synechisveltiosi.apis.app365.actions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "action_types")
public class ActionType implements Serializable {

    private static final long serialVersionUID = 0L;

    public static final String ID_CARD = "ID_CARD";
    public static final String ADDRESS = "ADDRESS";
    public static final String PHONE = "PHONE";
    public static final String MEMBER = "MEMBER";
    public static final String LEVEL = "LEVEL";
    public static final String POINT = "POINT";
    public static final String DONATION = "DONATION";
    public static final String VOLUNTEER_SCHEDULE = "VOLUNTEER_SCHEDULE";
    public static final String SHARE = "SHARE";
    public static final String LIKE = "LIKE";
    public static final String COMMENT = "COMMENT";
    public static final String CANDIDATE_SHARE = "CANDIDATE_SHARE";
    public static final String CANDIDATE_LIKE = "CANDIDATE_LIKE";
    public static final String CANDIDATE_COMMENT = "CANDIDATE_COMMENT";
    public static final String CANDIDATE_POST_COMMENT = "CANDIDATE_POST_COMMENT";
    public static final String EVENT_JOIN = "EVENT_JOIN";
    public static final String EVENT_SHARE = "EVENT_SHARE";
    public static final String EVENT_LIKE = "EVENT_LIKE";
    public static final String EVENT_COMMENT = "EVENT_COMMENT";
    public static final String NEWS_SHARE = "NEWS_SHARE";
    public static final String NEWS_LIKE = "NEWS_LIKE";
    public static final String NEWS_COMMENT = "NEWS_COMMENT";
    public static final String VIDEO_SHARE = "VIDEO_SHARE";
    public static final String VIDEO_LIKE = "VIDEO_LIKE";
    public static final String VIDEO_COMMENT = "VIDEO_COMMENT";

    public static final List<String> ALL = Arrays.asList(ID_CARD, ADDRESS, PHONE, MEMBER, LEVEL, POINT, DONATION,
            VOLUNTEER_SCHEDULE, SHARE, LIKE, COMMENT, CANDIDATE_SHARE, CANDIDATE_LIKE, CANDIDATE_COMMENT,
            CANDIDATE_POST_COMMENT, EVENT_JOIN, EVENT_SHARE, EVENT_LIKE, EVENT_COMMENT, NEWS_SHARE, NEWS_LIKE,
            NEWS_COMMENT, VIDEO_SHARE, VIDEO_LIKE, VIDEO_COMMENT);

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public ActionType() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static boolean isValid(String name) {
        return ActionType.ALL.contains(name);
    }

    public static void assertTypeName(String name) throws IllegalArgumentException {
        if (!ActionType.isValid(name)) throw new IllegalArgumentException("Invalid action type name");
    }
}
