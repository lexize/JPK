package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;
import org.lexize.jpk.enums.JPKAutoproxyModeEnum;

import java.time.Instant;

public class JPKAutoproxySettingsModel {
    @SerializedName("autoproxy_mode")
    public String AutoproxyMode;
    @SerializedName("autoproxy_member")
    public String AutoproxyMember;
    @SerializedName("last_latch_timestamp")
    public Instant LastLatchTimestamp;
}
