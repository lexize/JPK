package org.lexize.jpk.exceptions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.lexize.jpk.models.JPKErrorModel;

import java.util.HashMap;

public class JPKAbstractException extends Exception{
    public int Code;
    public int HttpCode;
    public String Message;
    public HashMap<String, JPKErrorModel> Errors;
    public int RetryAfter;

    @Override
    public String getMessage() {
        return this.Message;
    }

    private static Gson Json;

    public static void SetJsonInstance(Gson json) {
        Json = json;
    }

    public static JPKAbstractException ExceptionFromJsonObject(JsonObject jsonObject, int httpCode) {
        JPKAbstractException exception;
        switch (jsonObject.get("code").getAsInt()) {
            case (20001) -> {
                exception = new JPKSystemNotFoundException();
            }
            default -> {
                exception = new JPKAbstractException();
            }
        }
        exception.HttpCode = httpCode;
        exception.Code = jsonObject.get("code").getAsInt();
        exception.Message = jsonObject.get("message").getAsString();
        if (jsonObject.has("retry_after")) exception.RetryAfter = jsonObject.get("retry_after").getAsInt();
        if (jsonObject.has("errors")) {
            exception.Errors = new HashMap<>();
            for (var keypair:
                    jsonObject.get("errors").getAsJsonObject().entrySet()) {
                exception.Errors.put(keypair.getKey(), Json.fromJson(keypair.getValue(), JPKErrorModel.class));
            }
        }
        return exception;
    }
}
