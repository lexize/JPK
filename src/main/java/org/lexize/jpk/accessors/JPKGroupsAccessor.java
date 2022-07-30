package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.models.JPKGroupModel;
import org.lexize.jpk.models.JPKMemberModel;

import java.net.http.HttpResponse;

public class JPKGroupsAccessor extends JPKAbstractAccessor {

    /**
     * Default constructor of accessor
     *
     * @param parent
     */
    public JPKGroupsAccessor(JPK parent) {
        super(parent);
    }

    /**
     * Retrieves groups of system
     * @param systemReference System ID
     * @param with_members Retrieve members in group
     * @return Array of group models
     * @throws Exception
     */
    public JPKGroupModel[] GetSystemGroups(String systemReference, boolean with_members) throws Exception {
        String path = AccessURL + "systems/%s/groups?with_members=%s".formatted(systemReference, with_members);

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKGroupModel[].class);
    }

    /**
     * Retrieves groups of current system
     * @param with_members Retrieve members in group
     * @return Array of group models
     * @throws Exception
     */
    public JPKGroupModel[] GetSystemGroups(boolean with_members) throws Exception {
        return GetSystemGroups("@me", with_members);
    }

    /**
     * Creates new group
     * @param model Data of new group
     * @return Created group
     * @throws Exception
     */
    public JPKGroupModel CreateGroup(JPKGroupModel model) throws Exception {
        String groupData = Json.toJson(model);
        String path = AccessURL + "groups";

        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, groupData, Client);

        return ConvertOrThrowError(response, JPKGroupModel.class);
    }

    /**
     * Retrieves group by id
     * @param groupReference Group ID
     * @return Group model
     * @throws Exception
     */
    public JPKGroupModel GetGroup(String groupReference) throws Exception {
        String path = AccessURL + "groups/%s".formatted(groupReference);

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKGroupModel.class);
    }

    /**
     * Updates group with specified id
     * @param groupReference Group ID
     * @param model New group model
     * @return Updated model group
     * @throws Exception
     */
    public JPKGroupModel UpdateGroup(String groupReference, JPKGroupModel model) throws Exception {
        String groupData = Json.toJson(model);
        String path = AccessURL + "groups/%s".formatted(groupReference);

        HttpResponse<String> response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, groupData, Client);

        return ConvertOrThrowError(response, JPKGroupModel.class);
    }

    /**
     * Deletes group with specified group
     * @param groupReference Group ID
     * @return True, if successful
     */
    public boolean DeleteGroup(String groupReference) throws Exception {
        String path = AccessURL + "groups/%s".formatted(groupReference);
        HttpResponse<String> response = JPK.Utils.DELETE(path, Parent.AuthorizationToken, Client);

        return IsCode(response, 204);
    }

    /**
     * Retrieves array with members of group
     * @param groupReference Group ID
     * @return Array of members
     * @throws Exception
     */
    public JPKMemberModel[] GetGroupMembers(String groupReference) throws Exception {
        String path = AccessURL + "groups/%s/members".formatted(groupReference);
        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKMemberModel[].class);
    }

    /**
     * Add members to specified group
     * @param groupReference Group ID
     * @param memberIDs ID's of members
     * @return True, if successful
     * @throws Exception
     */
    public boolean AddMembersToGroup(String groupReference, String... memberIDs) throws Exception {
        String path = AccessURL + "groups/%s/members/add".formatted(groupReference);
        String modelData = Json.toJson(memberIDs);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);

        return IsCode(response, 204);
    }

    /**
     * Add members to specified group
     * @param groupReference Group ID
     * @param members Members
     * @return True, if successful
     * @throws Exception
     */
    public boolean AddMembersToGroup(String groupReference, JPKMemberModel... members) throws Exception {
        String[] ids = new String[members.length];
        for (int i = 0; i < members.length; i++) {
            ids[i] = members[i].Id;
        }
        return AddMembersToGroup(groupReference, ids);
    }

    /**
     * Removes members from specified group
     * @param groupReference Group ID
     * @param memberIDs ID's of members
     * @return True, if successful
     * @throws Exception
     */
    public boolean RemoveMembersFromGroup(String groupReference, String... memberIDs) throws Exception {
        String path = AccessURL + "groups/%s/members/remove".formatted(groupReference);
        String modelData = Json.toJson(memberIDs);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);

        return IsCode(response, 204);
    }

    /**
     * Removes members from specified group
     * @param groupReference Group ID
     * @param members Members
     * @return True, if successful
     * @throws Exception
     */
    public boolean RemoveMembersFromGroup(String groupReference, JPKMemberModel... members) throws Exception {
        String[] ids = new String[members.length];
        for (int i = 0; i < members.length; i++) {
            ids[i] = members[i].Id;
        }
        return RemoveMembersFromGroup(groupReference, ids);
    }

    /**
     * Overwrites members in specified group
     * @param groupReference Group ID
     * @param memberIDs Members
     * @return True, if successful
     * @throws Exception
     */
    public boolean OverwriteMembersInGroup(String groupReference, String... memberIDs) throws Exception {
        String path = AccessURL + "groups/%s/members/overwrite".formatted(groupReference);
        String modelData = Json.toJson(memberIDs);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);
        return IsCode(response, 204);
    }

    /**
     * Overwrites members in specified group
     * @param groupReference Group ID
     * @param members Members
     * @return True, if successful
     * @throws Exception
     */
    public boolean OverwriteMembersInGroup(String groupReference, JPKMemberModel... members) throws Exception {
        String[] ids = new String[members.length];
        for (int i = 0; i < members.length; i++) {
            ids[i] = members[i].Id;
        }
        return OverwriteMembersInGroup(groupReference, ids);
    }
}
