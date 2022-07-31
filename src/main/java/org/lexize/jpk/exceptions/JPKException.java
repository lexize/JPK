package org.lexize.jpk.exceptions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lexize.jpk.enums.JPKErrorEnum;
import org.lexize.jpk.models.JPKErrorModel;

import java.util.HashMap;

public class JPKException extends Exception{
    public final JPKErrorEnum ErrorType;
    public final int ErrorCode;
    public final int HttpCode;
    public final String Message;
    public final HashMap<String, JPKErrorModel> Errors;
    public final int RetryAfter;

    public JPKException(JPKErrorEnum errorType, int errorCode, int httpCode, String message, HashMap<String, JPKErrorModel> errors, int retryAfter) {
        ErrorType = errorType;
        ErrorCode = errorCode;
        HttpCode = httpCode;
        Message = message;
        Errors = errors;
        RetryAfter = retryAfter;
    }

    @Override
    public String getMessage() {
        return "(%s)[%s]{%s} %s".formatted(ErrorCode, HttpCode, ErrorType, Message);
    }

    private static Gson Json;

    public static void SetJsonInstance(Gson json) {
        Json = json;
    }

    public static JPKException ExceptionFromJsonObject(JsonObject jsonObject, int httpCode) {
        var codeElem = jsonObject.get("code");
        boolean isPKError = codeElem != null;
        HashMap<String, JPKErrorModel> Errors = null;
        int HttpCode = httpCode;
        int ErrorCode = isPKError ? jsonObject.get("code").getAsInt() : 0;
        int RetryAfter = 0;
        JPKErrorEnum ErrorType = JPKErrorEnum.ErrorTypeFromCodes(ErrorCode, HttpCode);
        String Message = isPKError ? jsonObject.get("message").getAsString() : null;
        if (jsonObject.has("retry_after")) RetryAfter = jsonObject.get("retry_after").getAsInt();
        if (jsonObject.has("errors")) {
            Errors = new HashMap<>();
            for (var keypair:
                    jsonObject.get("errors").getAsJsonObject().entrySet()) {
                Errors.put(keypair.getKey(), Json.fromJson(keypair.getValue(), JPKErrorModel.class));
            }
        }
        JPKException exception = new JPKException(ErrorType, ErrorCode, HttpCode, Message, Errors, RetryAfter);
        return exception;
    }
}
