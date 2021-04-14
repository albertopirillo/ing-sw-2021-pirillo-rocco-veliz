package it.polimi.ingsw.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.DevLeaderCard;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResLeaderCard;

import java.lang.reflect.Type;

public class LeaderCardJsonDeserializer implements JsonDeserializer<LeaderCard> {
        /*@Override
        public final JsonElement serialize(final T object, final Type interfaceType, final JsonSerializationContext context)
        {
            final JsonObject member = new JsonObject();

            member.addProperty("type", object.getClass().getName());

            member.add("data", context.serialize(object));

            return member;
        }*/
        @Override
        public LeaderCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String type = json.getAsJsonObject().get("type").getAsString();
            switch(type) {
                case "dev":
                    return context.deserialize(json, DevLeaderCard.class);
                case "res":
                    return context.deserialize(json, ResLeaderCard.class);
                default:
                    throw new IllegalArgumentException("Nessun tipo di carta leader");
            }
        }
}