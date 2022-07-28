package org.lexize.jpk.accessors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lexize.jpk.JPK;
import org.lexize.jpk.exceptions.JPKAbstractException;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

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

    public <T> T ConvertOrThrowError(HttpResponse<String> response, Class<T> type) throws JPKAbstractException {
        int statusCode = response.statusCode();

        //Checking, is error occurred
        if (statusCode < 400) {
            //If no, just returning system model
            String modelData = response.body();
            return Json.fromJson(modelData, type);
        }
        else {
            //If yes, just put json object of error in function and magic happens
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
    }

    public boolean IsCode(HttpResponse<String> response, int code) throws JPKAbstractException {
        int statusCode = response.statusCode();
        if (statusCode >= 400) {
            String errorData = response.body();
            JsonObject jsonObject = Json.fromJson(errorData, JsonObject.class);
            JPKAbstractException exception = JPKAbstractException.ExceptionFromJsonObject(jsonObject, statusCode);
            throw exception;
        }
        return statusCode == code;
    }
}
