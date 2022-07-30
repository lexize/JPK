package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class JPKMessageModel {
    @SerializedName("timestamp")
    public Instant Timestamp;
    @SerializedName("id")
    public String Id;
    @SerializedName("original")
    public String Original;
    @SerializedName("sender")
    public String Sender;
    @SerializedName("guild")
    public String Guild;
    @SerializedName("system")
    public JPKSystemModel System;
    @SerializedName("member")
    public JPKMemberModel Member;
}
