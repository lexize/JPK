package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKSystemModel {
    @SerializedName("id")
    public String Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("description")
    public String Description;
    @SerializedName("tag")
    public String Tag;
    @SerializedName("pronouns")
    public String Pronouns;
    @SerializedName("avatar_url")
    public String AvatarUrl;
    @SerializedName("banner")
    public String Banner;
    @SerializedName("color")
    public String Color;
    @SerializedName("created")
    public String CreatedAt;
    @SerializedName("privacy")
    public PrivacyModel Privacy;

    public static class PrivacyModel {
        @SerializedName("description_privacy")
        public String DescriptionPrivacy;
        @SerializedName("pronoun_privacy")
        public String PronounPrivacy;
        @SerializedName("member_list_privacy")
        public String MemberListPrivacy;
        @SerializedName("group_list_privacy")
        public String GroupListPrivacy;
        @SerializedName("front_privacy")
        public String FrontPrivacy;
        @SerializedName("front_history_privacy")
        public String FrontHistoryPrivacy;
    }
}
