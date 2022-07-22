package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;
import org.lexize.jpk.enums.JPKAutoproxyModeEnum;

public class JPKAutoproxySettingsModel {
    @SerializedName("autoproxy_mode")
    public JPKAutoproxyModeEnum AutoproxyMode;
    @SerializedName("autoproxy_member")
    public String AutoproxyMember;
    @SerializedName("last_latch_timestamp")
    public String LastLatchTimestamp;
}
