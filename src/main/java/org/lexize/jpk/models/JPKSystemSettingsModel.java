package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKSystemSettingsModel {
    @SerializedName("timezone")
    public String Timezone;
    @SerializedName("pings_enabled")
    public boolean PingsEnabled;
    @SerializedName("latch_timeout")
    public Integer LatchTimeout;
    @SerializedName("member_default_private")
    public boolean MemberDefaultPrivate;
    @SerializedName("group_default_private")
    public boolean GroupDefaultPrivate;
    @SerializedName("show_private_info")
    public boolean ShowPrivateInfo;
    @SerializedName("member_limit")
    public int MemberLimit;
    @SerializedName("group_limit")
    public int GroupLimit;
}
