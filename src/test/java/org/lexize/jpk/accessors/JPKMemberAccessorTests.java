package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.models.JPKMemberGuildSettingsModel;
import org.lexize.jpk.models.JPKMemberModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JPKMemberAccessorTests {
    private JPKMembersAccessor accessor;
    public JPKMemberAccessorTests(JPK jpk) {
        accessor = jpk.getMembersAccessor();
    }

    private void Log(String str) {
        System.out.println(str);
    }

    public void Tests() throws Exception {
        JPKMembersAccessor accessor = this.accessor;
        Log("Trying to add member to group");
        accessor.AddMemberToGroups("qcxdv", "iybwq");
        Log("Successfully");
        Log("Trying to overwrite member group");
        accessor.OverwriteMemberGroups("qcxdv", "iybwq");
        Log("Successfully");
        Log("Trying to remove member from group");
        accessor.RemoveMemberFromGroups("qcxdv", "iybwq");
        Log("Successfully");
        Log("Trying to get system members");
        JPKMemberModel[] systemMembers = accessor.GetSystemMembers();
        List<String> names = new LinkedList<>();
        for (JPKMemberModel model:
             systemMembers) {
            names.add(model.Name);
        }
        Log("Successfully. Current members names: %s".formatted(String.join(", ", names)));
        Log("Creating test member");
        JPKMemberModel testMember = new JPKMemberModel();
        testMember.Name = "test";
        testMember.DisplayName = "Test member";
        testMember.Pronouns = "It/It's";
        var proxyTag = new JPKMemberModel.ProxyTagModel();
        proxyTag.Prefix = "te/";
        testMember.ProxyTags = new JPKMemberModel.ProxyTagModel[] {proxyTag};
        testMember = accessor.CreateMember(testMember);
        Log("Successfully");
        Log("Trying to get created member from PK");
        testMember = accessor.GetMember(testMember.Id);
        Log("Successfully");
        Log("Waiting 4 seconds to avoid rate limit");
        Thread.sleep(4000);
        testMember.DisplayName = "Name changed cuz of member update";
        Log("Trying to update retrieved member");
        testMember = accessor.UpdateMember(testMember.Id, testMember);
        Log("Successfully");
        Log("Trying to get guild settings of already existing member");
        JPKMemberGuildSettingsModel settingsModel = accessor.GetMemberGuildSettings("hzzvl", "1002883501953126450");
        Log("Successfully");
        Log("Trying to update guild settings of already existing member");
        settingsModel.DisplayName = "Cocojambo";
        settingsModel = accessor.UpdateMemberGuildSettings("hzzvl", "1002883501953126450", settingsModel);
        Log("Successfully");
        Log("Deleting member created from code");
        if (accessor.DeleteMember(testMember.Id)) Log("Successfully");
        Log("All tests are successful.");
    }
}
