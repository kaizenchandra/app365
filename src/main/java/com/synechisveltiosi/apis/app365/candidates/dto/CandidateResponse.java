package com.synechisveltiosi.apis.app365.candidates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty(value = "fullName", access = JsonProperty.Access.READ_ONLY)
    private String fullName;

    @JsonProperty("profilePicture")
    private String profilePicture;

    @JsonProperty("party")
    private PoliticalParty party;

    @JsonProperty("summary")
    private CandidateSummaryResponse summary;

    @JsonProperty("bio")
    private CandidateBiographyResponse bio;

    @JsonProperty("meta")
    private DefaultMetaResponse meta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CandidateResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CandidateResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CandidateResponse withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        fullName = StringUtils.join(new String[]{firstName, lastName}, " ");
        return fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public CandidateResponse withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public PoliticalParty getParty() {
        return party;
    }

    public void setParty(PoliticalParty party) {
        this.party = party;
    }

    public CandidateResponse withParty(PoliticalParty party) {
        this.party = party;
        return this;
    }

    public CandidateSummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(CandidateSummaryResponse summary) {
        this.summary = summary;
    }

    public CandidateResponse withSummary(CandidateSummaryResponse summary) {
        this.summary = summary;
        return this;
    }

    public CandidateBiographyResponse getBio() {
        return bio;
    }

    public void setBio(CandidateBiographyResponse bio) {
        this.bio = bio;
    }

    public CandidateResponse withBio(CandidateBiographyResponse bio) {
        this.bio = bio;
        return this;
    }

    public DefaultMetaResponse getMeta() {
        return meta;
    }

    public void setMeta(DefaultMetaResponse meta) {
        this.meta = meta;
    }

    public CandidateResponse withMeta(DefaultMetaResponse meta) {
        this.meta = meta;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("fullName", fullName)
                .append("profilePicture", profilePicture)
                .append("party", party)
                .append("summary", summary)
                .append("bio", bio)
                .append("meta", meta)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(summary)
                .append(firstName)
                .append(lastName)
                .append(profilePicture)
                .append(fullName)
                .append(bio)
                .append(id)
                .append(party)
                .append(meta)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CandidateResponse)) {
            return false;
        }

        CandidateResponse rhs = ((CandidateResponse) other);
        return new EqualsBuilder()
                .append(summary, rhs.summary)
                .append(firstName, rhs.firstName)
                .append(lastName, rhs.lastName)
                .append(profilePicture, rhs.profilePicture)
                .append(fullName, rhs.fullName)
                .append(bio, rhs.bio)
                .append(id, rhs.id)
                .append(party, rhs.party)
                .append(meta, rhs.meta)
                .isEquals();
    }
}
