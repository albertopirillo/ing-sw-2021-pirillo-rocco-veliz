package it.polimi.ingsw.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.model.DevLeaderCard;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResLeaderCard;

import java.lang.reflect.Type;

/**
 * Deserializer that deserialize from JSON to LeaderCard Objects(abstract class)
 */
public class LeaderCardJsonDeserializer implements JsonDeserializer<LeaderCard> {

        @Override
        public LeaderCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String type = json.getAsJsonObject().get("type").getAsString();
            return switch (type) {
                case "dev" -> context.deserialize(json, DevLeaderCard.class);
                case "res" -> context.deserialize(json, ResLeaderCard.class);
                default -> throw new IllegalArgumentException("Nessun tipo di carta leader");
            };
        }
}
