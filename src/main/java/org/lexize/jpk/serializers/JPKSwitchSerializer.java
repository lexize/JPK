package org.lexize.jpk.serializers;

import com.google.gson.*;
import org.lexize.jpk.models.JPKMemberModel;
import org.lexize.jpk.models.JPKSwitchModel;

import java.lang.reflect.Type;

public class JPKSwitchSerializer implements JsonSerializer<JPKSwitchModel> {
    @Override
    public JsonElement serialize(JPKSwitchModel jpkSwitchModel, Type type, JsonSerializationContext jsonSerializationContext) {
        //Creating json object
        JsonObject obj = new JsonObject();
        //Adding default switch fields
        obj.addProperty("id", jpkSwitchModel.Id);
        obj.addProperty("timestamp", jpkSwitchModel.Timestamp);

        //Preparing JsonObject for members field
        JsonArray membersArray = new JsonArray();

        //Checking, is switch model contains members objects or ids list
        if (jpkSwitchModel.UsingIDs) {
            //If switch model uses ids

            for (String id:
                    jpkSwitchModel.MemberIDs) {
                membersArray.add(id);
            }
        }
        else {
            //If switch model uses member models

            for (JPKMemberModel model:
                 jpkSwitchModel.MemberModels) {
                membersArray.add(jsonSerializationContext.serialize(model));
            }
        }
        obj.add("members", membersArray);
        return obj;
    }
}
