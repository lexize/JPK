package org.lexize.jpk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lexize.jpk.accessors.JPKMembersAccessor;
import org.lexize.jpk.accessors.JPKSystemsAccessor;
import org.lexize.jpk.exceptions.JPKException;
import org.lexize.jpk.models.JPKSwitchModel;
import org.lexize.jpk.serializers.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class JPK {

    public String AuthorizationToken;
    private HttpClient Client;
    private Gson Json;

    private String _accessUrl;

    public String getAccessUrl() {
        return _accessUrl;
    }

    private JPKSystemsAccessor SystemsAccessor;
    private JPKMembersAccessor MembersAccessor;

    /**
     * Retrieves HTTP client, used by JPK
     * @return HTTP client
     */
    public HttpClient getClient() {
        return Client;
    }

    /**
     * Retrieves JSON serializer for that JPK instance
     * @return JSON serializer
     */
    public Gson getJson() {
        return Json;
    }

    /**
     * Retrieves accessor for systems models
     * @return Systems Accessor
     */
    public JPKSystemsAccessor getSystemsAccessor() {
        return SystemsAccessor;
    }

    /**
     * Retrieves accessor for members models
     * @return Members Accessor
     */
    public JPKMembersAccessor getMembersAccessor() {
        return MembersAccessor;
    }

    /**
     * Constructor of JPK
     * @param token
     */
    public JPK(String token) {
        this(token, "https://api.pluralkit.me/v2/");
    }

    public JPK(String token, String accessUrl) {
        AuthorizationToken = token;
        Client = HttpClient.newBuilder().build();
        Json = new GsonBuilder()
                .registerTypeAdapter(JPKSwitchModel.class, new JPKSwitchSerializer())
                .registerTypeAdapter(JPKSwitchModel.class, new JPKSwitchDeserializer()).create();
        JPKException.SetJsonInstance(Json);
        _accessUrl = accessUrl;
        SystemsAccessor = new JPKSystemsAccessor(this);
        MembersAccessor = new JPKMembersAccessor(this);
    }

    public static class Utils {
        public static HttpResponse<String> GET(String path, String token, HttpClient client) throws IOException, InterruptedException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(path));
            builder.header("Authorization", token);
            builder.GET();
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        }
        public static HttpResponse<String> POST(String path, String token, String data, HttpClient client) throws IOException, InterruptedException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(path));
            builder.header("Authorization", token);
            builder.header("Content-Type", "application/json");
            builder.POST(HttpRequest.BodyPublishers.ofString(data));
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        }
        public static HttpResponse<String> PATCH(String path, String token, String data, HttpClient client) throws IOException, InterruptedException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(path));
            builder.header("Authorization", token);
            builder.header("Content-Type", "application/json");
            builder.method("PATCH", HttpRequest.BodyPublishers.ofString(data));
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        }
        public static HttpResponse<String> DELETE(String path, String token, HttpClient client) throws IOException, InterruptedException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(path));
            builder.header("Authorization", token);
            builder.DELETE();
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        }

    }
}
