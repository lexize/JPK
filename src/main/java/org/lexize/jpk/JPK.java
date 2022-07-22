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

    public HttpClient getClient() {
        return Client;
    }

    public Gson getJson() {
        return Json;
    }

    public JPKSystemsAccessor getSystemsAccessor() {
        return SystemsAccessor;
    }

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
