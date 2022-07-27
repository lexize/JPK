package org.lexize.jpk.accessors;

import com.google.gson.Gson;
import org.lexize.jpk.JPK;

import java.net.http.HttpClient;

public abstract class JPKAbstractAccessor {
    protected JPK Parent;
    protected HttpClient Client;
    protected Gson Json;

    /**
     * Default constructor of accessor
     * @param parent
     */
    public JPKAbstractAccessor(JPK parent) {
        Parent = parent;
        Client = Parent.getClient();
        Json = Parent.getJson();
    }
}
