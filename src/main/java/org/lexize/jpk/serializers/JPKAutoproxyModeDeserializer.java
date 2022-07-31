package org.lexize.jpk.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.lexize.jpk.enums.JPKAutoproxyModeEnum;

import java.lang.reflect.Type;

public class JPKAutoproxyModeDeserializer implements JsonDeserializer<JPKAutoproxyModeEnum> {
    @Override
    public JPKAutoproxyModeEnum deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String m = jsonElement.getAsString();
        for (JPKAutoproxyModeEnum mode:
             JPKAutoproxyModeEnum.values()) {
            if (m.equals(mode.toString())) return mode;
        }

        return null;
    }
}
