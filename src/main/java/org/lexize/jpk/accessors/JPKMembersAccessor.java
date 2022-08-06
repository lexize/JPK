package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.docs.annotations.JDocsInclude;
import org.lexize.jpk.exceptions.JPKException;
import org.lexize.jpk.models.JPKGroupModel;
import org.lexize.jpk.models.JPKMemberGuildSettingsModel;
import org.lexize.jpk.models.JPKMemberModel;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@JDocsInclude
public class JPKMembersAccessor extends JPKAbstractAccessor {
    // AccessURL + "

    /**
     * Default constructor of accessor
     *
     * @param parent
     */
    public JPKMembersAccessor(JPK parent) {
        super(parent);
    }

    /**
     * Retrieves system members list
     * @param systemReference ID of system
     * @return List of JPKMemberModel
     * @throws JPKException
     */
    public JPKMemberModel[] GetSystemMembers(String systemReference) throws Exception {
        String path = AccessURL + "systems/%s/members".formatted(systemReference);

        //Sending request and getting response
        var response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKMemberModel[].class);
    }

    /**
     * Retrieves current system members list
     * @return List of JPKMemberModel
     * @throws JPKException
     */
    public JPKMemberModel[] GetSystemMembers() throws Exception {
        return GetSystemMembers("@me");
    }

    /**
     * Retrieves member by specified ID
     * @param memberReference Member ID
     * @return Member Model
     * @throws Exception
     */
    public JPKMemberModel GetMember(String memberReference) throws Exception {
        String path = AccessURL + "members/%s".formatted(memberReference);

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKMemberModel.class);
    }

    /**
     * Creates new member
     * @param model Member data
     * @return New member
     * @throws Exception
     */
    public JPKMemberModel CreateMember(JPKMemberModel model) throws Exception {
        String modelData_ = Json.toJson(model);
        String path = AccessURL + "members/";
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData_, Client);

        return ConvertOrThrowError(response, JPKMemberModel.class);
    }

    /**
     * Updates member object
     * @param memberReference ID of member
     * @param model New member data
     * @return Updated member data
     * @throws Exception
     */
    public JPKMemberModel UpdateMember(String memberReference, JPKMemberModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = AccessURL + "members/%s".formatted(memberReference);

        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKMemberModel.class);
    }

    /**
     * Removes member
     * @param memberReference
     * @return True, if success
     * @throws Exception
     */
    public boolean DeleteMember(String memberReference) throws Exception {
        String path = AccessURL + "members/%s".formatted(memberReference);
        HttpResponse<String> response = JPK.Utils.DELETE(path, Parent.AuthorizationToken, Client);
        return IsCode(response, 204);
    }

    /**
     * Retrieves array with groups of member with specified ID
     * @param memberReference Member ID
     * @return Array of Group Models
     */
    public JPKGroupModel[] GetMemberGroups(String memberReference) throws Exception {
        //Creating base request
        HttpRequest.Builder requestBuilder = HttpRequest
                .newBuilder()
                .GET()
                .header("Authorization", Parent.AuthorizationToken);
        String path = AccessURL + "members/%s/groups".formatted(memberReference);
        requestBuilder.uri(URI.create(path));

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKGroupModel[].class);
    }

    /**
     * Adding member to specified groups
     * @param memberReference Member ID
     * @param groups List of group references
     * @return True, if success
     * @throws Exception
     */
    public boolean AddMemberToGroups(String memberReference, String... groups) throws Exception {
        String path = AccessURL + "members/%s/groups/add".formatted(memberReference);
        String modelData = Json.toJson(groups);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);

        return IsCode(response, 204);
    }

    /**
     * Adding member to specified groups
     * @param memberReference Member ID
     * @param groups List of groups
     * @return True, if success
     * @throws Exception
     */
    public boolean AddMemberToGroups(String memberReference, JPKGroupModel... groups) throws Exception {
        String[] groupIds = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupIds[i] = groups[i].Id;
        }
        return AddMemberToGroups(memberReference, groupIds);
    }

    /**
     * Removing member from specified groups
     * @param memberReference Member ID
     * @param groups List of group IDs
     * @return True, if success
     * @throws Exception
     */
    public boolean RemoveMemberFromGroups(String memberReference, String... groups) throws Exception {
        String path = AccessURL + "members/%s/groups/remove".formatted(memberReference);
        String modelData = Json.toJson(groups);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);

        return IsCode(response, 204);
    }

    /**
     * Removing member from specified groups
     * @param memberReference Member ID
     * @param groups List of groups
     * @return True, if success
     * @throws Exception
     */
    public boolean RemoveMemberFromGroups(String memberReference, JPKGroupModel... groups) throws Exception {
        String[] groupIds = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupIds[i] = groups[i].Id;
        }
        return RemoveMemberFromGroups(memberReference, groupIds);
    }

    /**
     * Overwriting member groups
     * @param memberReference MemberID
     * @param groups List of group IDs
     * @return True, if successful
     * @throws Exception
     */
    public boolean OverwriteMemberGroups(String memberReference, String... groups) throws Exception {
        String path = AccessURL + "members/%s/groups/overwrite".formatted(memberReference);
        String modelData = Json.toJson(groups);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);
        return IsCode(response, 204);
    }

    /**
     * Overwriting member groups
     * @param memberReference MemberID
     * @param groups List of groups
     * @return True, if successful
     * @throws Exception
     */
    public boolean OverwriteMemberGroups(String memberReference, JPKGroupModel... groups) throws Exception {
        String[] groupIds = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupIds[i] = groups[i].Id;
        }
        return OverwriteMemberGroups(memberReference, groupIds);
    }

    /**
     * Retrieves member settings in specified guild
     * @param memberReference Member ID
     * @param guildReference Guild ID
     * @return Member Settings
     * @throws Exception
     */
    public JPKMemberGuildSettingsModel GetMemberGuildSettings(String memberReference, String guildReference) throws Exception {
        String path = AccessURL + "members/%s/guilds/%s".formatted(memberReference, guildReference);
        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKMemberGuildSettingsModel.class);
    }

    /**
     * Updates member settings in specified guild
     * @param memberReference Member ID
     * @param guildReference Guild ID
     * @param model New model data
     * @return Updated model data
     * @throws Exception
     */
    public JPKMemberGuildSettingsModel UpdateMemberGuildSettings(String memberReference, String guildReference, JPKMemberGuildSettingsModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = AccessURL + "members/%s/guilds/%s".formatted(memberReference, guildReference);
        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKMemberGuildSettingsModel.class);
    }

}
