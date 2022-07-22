package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKMemberGuildSettingsModel {
    @SerializedName("guild_id")
    public String GuildId;
    @SerializedName("display_name")
    public String DisplayName;
    @SerializedName("avatar_url")
    public String AvatarUrl;
}
