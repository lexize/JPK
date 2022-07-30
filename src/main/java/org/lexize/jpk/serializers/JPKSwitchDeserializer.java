package org.lexize.jpk.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.lexize.jpk.models.JPKMemberModel;
import org.lexize.jpk.models.JPKSwitchModel;

import java.lang.reflect.Type;
import java.time.Instant;

public class JPKSwitchDeserializer implements JsonDeserializer<JPKSwitchModel> {
    @Override
    public JPKSwitchModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        //Converting jsonElement to JsonObject
        JsonObject obj = jsonElement.getAsJsonObject();
        //Preparing SwitchModel
        JPKSwitchModel model = new JPKSwitchModel();


        //Setting fields that always has same type
        model.Id = obj.get("id").getAsString();
        model.Timestamp = Instant.parse(obj.get("timestamp").getAsString());

        //Preparing variable that determines, is switch model using list of ids, or member models
        boolean isUsingIDs = false;

        //JsonArray for members
        JsonArray members = obj.get("members").getAsJsonArray();

        //Arrays for members objects
        String[] memberIds = new String[members.size()];
        JPKMemberModel[] memberModels = new JPKMemberModel[members.size()];

        for (int i = 0; i < members.size(); i++) {
            JsonElement elem = members.get(i);
            if (i == 0 && elem.isJsonPrimitive()) isUsingIDs = true;

            if (isUsingIDs) memberIds[i] = elem.getAsString();
            else memberModels[i] = jsonDeserializationContext.deserialize(elem, JPKMemberModel.class);
        }

        model.UsingIDs = isUsingIDs;

        return model;
    }
}
