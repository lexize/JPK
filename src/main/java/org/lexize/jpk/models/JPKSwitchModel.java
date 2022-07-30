package org.lexize.jpk.models;

import java.time.Instant;

public class JPKSwitchModel {
    public String Id;
    public Instant Timestamp;
    public String[] MemberIDs;
    public JPKMemberModel[] MemberModels;
    public boolean UsingIDs;
}
