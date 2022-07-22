package org.lexize.jpk.models;

import com.google.gson.annotations.SerializedName;

public class JPKMessageModel {
    @SerializedName("timestamp")
    public String Timestamp;
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
