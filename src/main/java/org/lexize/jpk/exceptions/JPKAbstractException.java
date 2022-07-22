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
        JPKAbstractException exception = new JPKUnknownException();
        System.out.println(Json.toJson(jsonObject));
        var codeElem = jsonObject.get("code");
        boolean isPKError = codeElem != null;
        if (!isPKError) {
            // If non PK error
            switch (httpCode) {
                case (400) -> {
                    exception = new JPKBadRequestException();
                }
                case (401) -> {
                    exception = new JPKAuthException();
                }
                case (500) -> {
                    exception = new JPKInternalServerErrorException();
                }
            }
        }
        else {
            switch (codeElem.getAsInt()) {
                //If one of PK error

                //System not found
                case (20001) -> {
                    exception = new JPKSystemNotFoundException();
                }

                case (20009) -> {
                    exception = new JPKSystemGuildSettingsNotFound();
                }
            }
        }
        exception.HttpCode = httpCode;
        exception.Code = isPKError ? jsonObject.get("code").getAsInt() : 0;
        exception.Message = isPKError ? jsonObject.get("message").getAsString() : null;
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
