package org.lexize.jpk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lexize.jpk.accessors.*;
import org.lexize.jpk.docs.JDocs;
import org.lexize.jpk.docs.annotations.JDocsDescription;
import org.lexize.jpk.docs.annotations.JDocsDescriptions;
import org.lexize.jpk.docs.annotations.JDocsInclude;
import org.lexize.jpk.exceptions.JPKException;
import org.lexize.jpk.models.JPKSwitchModel;
import org.lexize.jpk.serializers.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@JDocsDescription("Test description")
@JDocsInclude(true)
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
    private JPKGroupsAccessor GroupsAccessor;
    private JPKSwitchesAccessor SwitchesAccessor;
    private JPKMiscAccessor MiscAccessor;

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
     * Retrieves accessor for groups models
     * @return Groups Accessor
     */
    public JPKGroupsAccessor getGroupsAccessor() {
        return GroupsAccessor;
    }

    /**
     * Retrieves accessor for switches models
     * @return Switches Accessor
     */
    public JPKSwitchesAccessor getSwitchesAccessor() {
        return SwitchesAccessor;
    }

    /**
     * Retrieves accessor for misc
     * @return Misc Accessor
     */
    @JDocsDescription("Test description")
    public JPKMiscAccessor getMiscAccessor() {
        return MiscAccessor;
    }

    public <T,K,V> void TestMethod(HashMap<String,V> listOfT) {

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
                .registerTypeAdapter(JPKSwitchModel.class, new JPKSwitchDeserializer())
                .registerTypeAdapter(Instant.class, new JPKTimeStampSerializer())
                .registerTypeAdapter(Instant.class, new JPKTimeStampDeserializer())
                .serializeNulls()
                .create();
        JPKException.SetJsonInstance(Json);
        _accessUrl = accessUrl;
        SystemsAccessor = new JPKSystemsAccessor(this);
        MembersAccessor = new JPKMembersAccessor(this);
        GroupsAccessor = new JPKGroupsAccessor(this);
        SwitchesAccessor = new JPKSwitchesAccessor(this);
        MiscAccessor = new JPKMiscAccessor(this);
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

    public static void main(String[] args) {
        switch (args[0]) {
            case ("--gen-docs") -> {
                Gson json = new GsonBuilder().setPrettyPrinting().create();
                JDocs docs = JDocs.GenerateTreeFromRoots(JPK.class);
                System.out.println(json.toJson(docs));
            }
        }
    }
}
