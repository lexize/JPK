package org.lexize.jpk.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.lexize.jpk.enums.JPKAutoproxyModeEnum;

import java.lang.reflect.Type;

public class JPKAutoproxyModeSerializer implements JsonSerializer<JPKAutoproxyModeEnum> {
    @Override
    public JsonElement serialize(JPKAutoproxyModeEnum jpkAutoproxyModeEnum, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(jpkAutoproxyModeEnum.toString());
    }
}
