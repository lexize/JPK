package org.lexize.jpk.accessors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.lexize.jpk.JPK;
import org.lexize.jpk.exceptions.JPKAbstractException;
import org.lexize.jpk.exceptions.JPKSystemNotFoundException;
import org.lexize.jpk.models.JPKGroupModel;
import org.lexize.jpk.models.JPKMemberGuildSettingsModel;
import org.lexize.jpk.models.JPKMemberModel;
import org.lexize.jpk.models.JPKSystemModel;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JPKMembersAccessor extends JPKAbstractAccessor {


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
     * @throws JPKSystemNotFoundException
     */
    public JPKMemberModel[] GetSystemMembers(String systemReference) throws Exception {
        String path = "https://api.pluralkit.me/v2/systems/%s".formatted(systemReference);

        //Sending request and getting response
        var response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        int statusCode = response.statusCode();

        //Checking, is error occurred
        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKMemberModel[].class);
        }
        else {
            //If yes, just put json object of error in function and magic happens
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Retrieves current system members list
     * @return List of JPKMemberModel
     * @throws JPKSystemNotFoundException
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
        String path = "https://api.pluralkit.me/v2/members/%s".formatted(memberReference);

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKMemberModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Creates new member
     * @param model Member data
     * @return New member
     * @throws Exception
     */
    public JPKMemberModel CreateMember(JPKMemberModel model) throws Exception {
        String modelData_ = Json.toJson(model);
        String path = "https://api.pluralkit.me/v2/members/";
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData_, Client);

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            //If no, just returning system model
            String modelData = response.body();
            return Json.fromJson(modelData, JPKMemberModel.class);
        }
        else {
            //If yes, just put json object of error in function and magic happens
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
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

        String path = "https://api.pluralkit.me/v2/members/%s".formatted(memberReference);

        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        int statusCode = response.statusCode();

        //Throwing exception if code is more or equals 400
        if (statusCode < 400) {
            String newModelData = response.body();
            return Json.fromJson(newModelData, JPKMemberModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Removes member
     * @param memberReference
     * @return True, if success
     * @throws Exception
     */
    public boolean DeleteMember(String memberReference) throws Exception {
        String path = "https://api.pluralkit.me/v2/members/%s".formatted(memberReference);
        HttpResponse<String> response = JPK.Utils.DELETE(path, Parent.AuthorizationToken, Client);
        int statusCode = response.statusCode();
        if (statusCode >= 400) {
            //If yes, just put json object of error in function and magic happens
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
        return statusCode == 204;
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
        String path = "https://api.pluralkit.me/v2/members/%s/groups".formatted(memberReference);
        requestBuilder.uri(URI.create(path));

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKGroupModel[].class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Adding member to specified groups
     * @param memberReference Member ID
     * @param groups List of group references
     * @return True, if success
     * @throws Exception
     */
    public boolean AddMemberToGroups(String memberReference, String... groups) throws Exception {
        String path = "https://api.pluralkit.me/v2/members/%s/groups/add".formatted(memberReference);
        String modelData = Json.toJson(groups);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);

        int statusCode = response.statusCode();
        if (statusCode >= 400) {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
        return statusCode == 204;
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
        String path = "https://api.pluralkit.me/v2/members/%s/groups/remove".formatted(memberReference);
        String modelData = Json.toJson(groups);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);

        int statusCode = response.statusCode();
        if (statusCode >= 400) {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
        return statusCode == 204;
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
        String path = "https://api.pluralkit.me/v2/members/%s/groups/overwrite".formatted(memberReference);
        String modelData = Json.toJson(groups);
        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);
        int statusCode = response.statusCode();
        if (statusCode >= 400) {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
        return statusCode == 204;
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
        String path = "https://api.pluralkit.me/v2/members/%s/guilds/%s".formatted(memberReference, guildReference);
        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        int statusCode = response.statusCode();

        //Throwing exception if code is more or equals 400
        if (statusCode < 400) {
            String newModelData = response.body();
            return Json.fromJson(newModelData, JPKMemberGuildSettingsModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
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

        String path = "https://api.pluralkit.me/v2/members/%s/guilds/%s".formatted(memberReference, guildReference);
        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        int statusCode = response.statusCode();

        //Throwing exception if code is more or equals 400
        if (statusCode < 400) {
            String newModelData = response.body();
            return Json.fromJson(newModelData, JPKMemberGuildSettingsModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

}
