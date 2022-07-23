package org.lexize.jpk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lexize.jpk.accessors.JPKSystemsAccessor;
import org.lexize.jpk.exceptions.JPKAbstractException;
import org.lexize.jpk.models.JPKSwitchModel;
import org.lexize.jpk.serializers.*;

import java.net.http.HttpClient;

public class JPK {

    public String AuthorizationToken;
    private HttpClient Client;
    private Gson Json;
    private JPKSystemsAccessor SystemsAccessor;

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
     * Constructor of JPK
     * @param token
     */
    public JPK(String token) {
        AuthorizationToken = token;
        Client = HttpClient.newBuilder().build();
        Json = new GsonBuilder()
                .registerTypeAdapter(JPKSwitchModel.class, new JPKSwitchSerializer())
                .registerTypeAdapter(JPKSwitchModel.class, new JPKSwitchDeserializer()).create();
        JPKAbstractException.SetJsonInstance(Json);
        SystemsAccessor = new JPKSystemsAccessor(this);
    }
}
