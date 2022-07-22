package org.lexize.jpk.accessors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lexize.jpk.JPK;
import org.lexize.jpk.exceptions.JPKAbstractException;
import org.lexize.jpk.exceptions.JPKSystemNotFoundException;
import org.lexize.jpk.models.JPKErrorModel;
import org.lexize.jpk.models.JPKSystemModel;

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

        if (statusCode < 400) {
            String modelData = response.body();
            return Json.fromJson(modelData, JPKSystemModel.class);
        }
        else {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

}
