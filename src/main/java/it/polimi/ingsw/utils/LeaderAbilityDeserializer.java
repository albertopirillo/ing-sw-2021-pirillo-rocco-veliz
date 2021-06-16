package it.polimi.ingsw.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.model.*;

import java.lang.reflect.Type;

public class LeaderAbilityDeserializer implements JsonDeserializer<LeaderAbility> {
        /*@Override
        public final JsonElement serialize(final T object, final Type interfaceType, final JsonSerializationContext context)
        {
            final JsonObject member = new JsonObject();

            member.addProperty("type", object.getClass().getName());

            member.add("data", context.serialize(object));

            return member;
        }*/
        @Override
        public LeaderAbility deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String type = json.getAsJsonObject().get("type").getAsString();
            return switch (type) {
                case "dis" -> context.deserialize(json, DiscountAbility.class);
                case "extra" -> context.deserialize(json, ExtraSlotAbility.class);
                case "prod" -> context.deserialize(json, ExtraProductionAbility.class);
                case "change" -> context.deserialize(json, ChangeWhiteMarblesAbility.class);
                default -> throw new IllegalArgumentException("Invalid LeaderAbility type");
            };
        }
    }
