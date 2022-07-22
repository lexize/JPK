package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKSystemGuildSettingsModel {
    @SerializedName("guild_id")
    public String GuildId;
    @SerializedName("proxying_enabled")
    public boolean ProxyingEnabled;
    @SerializedName("tag")
    public String Tag;
    @SerializedName("tag_enabled")
    public boolean TagEnabled;
}
