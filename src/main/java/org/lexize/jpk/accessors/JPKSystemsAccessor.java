package org.lexize.jpk.accessors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lexize.jpk.JPK;
import org.lexize.jpk.exceptions.JPKAbstractException;
import org.lexize.jpk.exceptions.JPKSystemNotFoundException;
import org.lexize.jpk.models.JPKErrorModel;
import org.lexize.jpk.models.JPKSystemGuildSettingsModel;
import org.lexize.jpk.models.JPKSystemModel;
import org.lexize.jpk.models.JPKSystemSettingsModel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class JPKSystemsAccessor {
    private JPK Parent;
    private HttpClient Client;
    private Gson Json;
    public JPKSystemsAccessor(JPK parent) {
        Parent = parent;
        Client = Parent.getClient();
        Json = Parent.getJson();
    }

    /**
     * Accessor for GET /systems/{systemRef}
     * @param systemReference ID of system
     * @return model retrieved from PK API
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemModel GetSystem(String systemReference) throws Exception {
        //Creating base request
        HttpRequest.Builder request = HttpRequest
                .newBuilder()
                .GET()
                .header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/systems/%s".formatted(systemReference);
        request.uri(URI.create(path));

        //Sending request and getting response
        var response = Client.send(request.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        //Checking, is error occurred
        if (statusCode < 400) {
            //If no, just returning system model
            String modelData = response.body();
            return Json.fromJson(modelData, JPKSystemModel.class);
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
     * Accessor for GET /systems/@me.
     * Retrieves current system for this auth token
     * @return model retrieved from PK API
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemModel GetSystem() throws Exception {
        return GetSystem("@me");
    }

    /**
     * Accessor for PATCH /systems/{systemRef}
     * @param systemReference ID of system
     * @param model Model object, that contains new data
     * @return edited model
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemModel UpdateSystem(String systemReference, JPKSystemModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = "https://api.pluralkit.me/v2/systems/%s".formatted(systemReference);

        //Building request
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(modelData, StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        requestBuilder.header("Content-Type", "application/json");
        var response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        //Throwing exception if code is more or equals 400
        if (statusCode < 400) {
            String newModelData = response.body();
            return Json.fromJson(newModelData, JPKSystemModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Accessor for PATCH /systems/@me.
     * Updates current system for this auth token
     * @param model Model with new data
     * @return edited model
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemModel UpdateSystem(JPKSystemModel model) throws Exception {
        return UpdateSystem("@me", model);
    }

    /**
     * Accessor for GET /systems/{systemRef}/settings
     * @param systemReference ID of system
     * @return model retrieved from PK API
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemSettingsModel GetSystemSettings(String systemReference) throws Exception {
        //Creating base request
        HttpRequest.Builder request = HttpRequest
                .newBuilder()
                .GET()
                .header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/systems/%s/settings".formatted(systemReference);
        request.uri(URI.create(path));

        //Sending request and getting response
        var response = Client.send(request.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        //Checking, is error occurred
        if (statusCode < 400) {
            //If no, just returning system settings model
            String modelData = response.body();
            return Json.fromJson(modelData, JPKSystemSettingsModel.class);
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
     * Accessor for GET /systems/@me/settings
     * @return model
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemSettingsModel GetSystemSettings() throws Exception {
        return GetSystemSettings("@me");
    }

    /**
     * Accessor for PATCH /systems/{systemRef}/settings.
     * @param model Model with new data
     * @return edited model
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemSettingsModel UpdateSystemSettings(String systemReference, JPKSystemSettingsModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = "https://api.pluralkit.me/v2/systems/%s".formatted(systemReference);

        //Building request
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(modelData, StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        requestBuilder.header("Content-Type", "application/json");
        var response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        //Throwing exception if code is more or equals 400
        if (statusCode < 400) {
            String newModelData = response.body();
            return Json.fromJson(newModelData, JPKSystemSettingsModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }
    /**
     * Accessor for PATCH /systems/@me/settings.
     * Updates settings of current system
     * @param model Model with new data
     * @return edited model
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     */
    public JPKSystemSettingsModel UpdateSystemSettings(JPKSystemSettingsModel model) throws Exception {
        return UpdateSystemSettings("@me", model);
    }

    /**
     * Retrieves system guild settings
     * @param systemReference ID of system
     * @param guildId ID of guild
     * @return Model with settings data
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     * @throws org.lexize.jpk.exceptions.JPKSystemGuildSettingsNotFound
     */
    public JPKSystemGuildSettingsModel GetSystemGuildSettings(String systemReference, String guildId) throws Exception {
        //Creating base request
        HttpRequest.Builder request = HttpRequest
                .newBuilder()
                .GET()
                .header("Authorization", Parent.AuthorizationToken);
        String path = "https://api.pluralkit.me/v2/systems/%s/guilds/%s".formatted(systemReference, guildId);
        request.uri(URI.create(path));

        //Sending request and getting response
        var response = Client.send(request.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        //Checking, is error occurred
        if (statusCode < 400) {
            //If no, just returning system guild settings model
            String modelData = response.body();
            return Json.fromJson(modelData, JPKSystemGuildSettingsModel.class);
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
     * Retrieves current system guild settings
     * @param guildId ID of guild
     * @return Model with settings data
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     * @throws org.lexize.jpk.exceptions.JPKSystemGuildSettingsNotFound
     */
    public JPKSystemGuildSettingsModel GetSystemGuildSettings(String guildId) throws Exception {
        return GetSystemGuildSettings("@me", guildId);
    }

    /**
     * Updates system guild settings
     * @param systemReference ID of system
     * @param guildId ID of Guild
     * @param model Model with new data
     * @return Model with edited data
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     * @throws org.lexize.jpk.exceptions.JPKSystemGuildSettingsNotFound
     */
    public JPKSystemGuildSettingsModel UpdateSystemGuildSettings(String systemReference, String guildId, JPKSystemGuildSettingsModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = "https://api.pluralkit.me/v2/systems/%s/guilds/%s".formatted(systemReference, guildId);

        //Building request
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(path));
        requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(modelData, StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", Parent.AuthorizationToken);
        requestBuilder.header("Content-Type", "application/json");
        var response = Client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();

        //Throwing exception if code is more or equals 400
        if (statusCode < 400) {
            String newModelData = response.body();
            return Json.fromJson(newModelData, JPKSystemGuildSettingsModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    /**
     * Updates current system guild settings
     * @param guildId ID of Guild
     * @param model Model with new data
     * @return Model with edited data
     * @throws org.lexize.jpk.exceptions.JPKSystemNotFoundException
     * @throws org.lexize.jpk.exceptions.JPKSystemGuildSettingsNotFound
     */
    public JPKSystemGuildSettingsModel UpdateSystemGuildSettings(String guildId, JPKSystemGuildSettingsModel model) throws Exception {
        return UpdateSystemGuildSettings("@me", guildId, model);
    }
}
