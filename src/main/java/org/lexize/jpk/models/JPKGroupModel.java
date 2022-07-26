package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKGroupModel {
    @SerializedName("id")
    public String Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("display_name")
    public String DisplayName;
    @SerializedName("description")
    public String Description;
    @SerializedName("icon")
    public String Icon;
    @SerializedName("banner")
    public String Banner;
    @SerializedName("color")
    public String Color;
    @SerializedName("members")
    public String[] MemberdUUIDs;
    @SerializedName("privacy")
    public PrivacyModel Privacy;

    public static class PrivacyModel {
        @SerializedName("visibility")
        public String Visibility;
        @SerializedName("name_privacy")
        public String NamePrivacy;
        @SerializedName("description_privacy")
        public String DescriptionPrivacy;
        @SerializedName("icon_privacy")
        public String IconPrivacy;
        @SerializedName("list_privacy")
        public String ListPrivacy;
        @SerializedName("metadata_privacy")
        public String MetadataPrivacy;
    }
}
