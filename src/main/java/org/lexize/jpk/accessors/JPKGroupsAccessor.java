package org.lexize.jpk.accessors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.lexize.jpk.JPK;
import org.lexize.jpk.exceptions.JPKAbstractException;
import org.lexize.jpk.models.JPKGroupModel;
import org.lexize.jpk.models.JPKMemberModel;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JPKGroupsAccessor {
    private JPK Parent;
    private HttpClient Client;
    private Gson Json;

    /**
     * Default constructor of accessor
     * @param parent
     */
    public JPKGroupsAccessor(JPK parent) {
        Parent = parent;
        Client = Parent.getClient();
        Json = Parent.getJson();
    }

    /**
     * Retrieves groups of system
     * @param systemReference System ID
     * @param with_members Retrieve members in group
     * @return Array of group models
     * @throws Exception
     */
    public JPKGroupModel[] GetSystemGroups(String systemReference, boolean with_members) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/systems/%s/groups?with_members=%s".formatted(systemReference, with_members);
        requestBuilder.uri(URI.create(path));
        requestBuilder.GET();

        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

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
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/groups";
        requestBuilder.uri(URI.create(path));
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(groupData));

        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKGroupModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Retrieves group by id
     * @param groupReference Group ID
     * @return Group model
     * @throws Exception
     */
    public JPKGroupModel GetGroup(String groupReference) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/groups/%s".formatted(groupReference);
        requestBuilder.uri(URI.create(path));
        requestBuilder.GET();

        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKGroupModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
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
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/groups/%s".formatted(groupReference);
        requestBuilder.uri(URI.create(path));
        requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(groupData));

        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKGroupModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Deletes group with specified group
     * @param groupReference Group ID
     * @return True, if successful
     */
    public boolean DeleteGroup(String groupReference) throws Exception {
        String path = "https://api.pluralkit.me/v2/groups/%s".formatted(groupReference);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.DELETE();
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
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
     * Retrieves array with members of group
     * @param groupReference Group ID
     * @return Array of members
     * @throws Exception
     */
    public JPKMemberModel[] GetGroupMembers(String groupReference) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/groups/%s/members".formatted(groupReference);
        requestBuilder.uri(URI.create(path));
        requestBuilder.GET();

        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKMemberModel[].class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Add members to specified group
     * @param groupReference Group ID
     * @param memberIDs ID's of members
     * @return True, if successful
     * @throws Exception
     */
    public boolean AddMembersToGroup(String groupReference, String... memberIDs) throws Exception {
        String path = "https://api.pluralkit.me/v2/groups/%s/members/add".formatted(groupReference);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(Json.toJson(memberIDs), StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
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
        String path = "https://api.pluralkit.me/v2/groups/%s/members/remove".formatted(groupReference);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(Json.toJson(memberIDs), StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
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
        String path = "https://api.pluralkit.me/v2/groups/%s/members/overwrite".formatted(groupReference);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(Json.toJson(memberIDs), StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        HttpResponse<String> response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
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
