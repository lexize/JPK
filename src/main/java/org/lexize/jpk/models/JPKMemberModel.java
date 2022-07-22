package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKMemberModel {
    @SerializedName("id")
    public String Id;
    @SerializedName("uuid")
    public String Uuid;
    @SerializedName("name")
    public String Name;
    @SerializedName("display_name")
    public String DisplayName;
    @SerializedName("color")
    public String Color;
    @SerializedName("birthday")
    public String Birthday;
    @SerializedName("pronouns")
    public String Pronouns;
    @SerializedName("avatar_url")
    public String AvatarUrl;
    @SerializedName("banner")
    public String Banner;
    @SerializedName("description")
    public String Description;
    @SerializedName("created")
    public String CreatedAt;
    @SerializedName("proxy_tags")
    public ProxyTagModel[] ProxyTags;
    @SerializedName("keep_proxy")
    public boolean KeepProxy;
    @SerializedName("privacy")
    public PrivacyModel Privacy;

    public static class PrivacyModel {
        @SerializedName("visibility")
        public String Visibility;
        @SerializedName("name_privacy")
        public String NamePrivacy;
        @SerializedName("description_privacy")
        public String DescriptionPrivacy;
        @SerializedName("birthday_privacy")
        public String BirthdayPrivacy;
        @SerializedName("pronoun_privacy")
        public String PronounPrivacy;
        @SerializedName("avatar_privacy")
        public String AvatarPrivacy;
        @SerializedName("metadata_privacy")
        public String MetadataPrivacy;
    }

    public static class ProxyTagModel {
        @SerializedName("prefix")
        public String Prefix;
        @SerializedName("suffix")
        public String Suffix;
    }
}
