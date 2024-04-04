package it.polimi.ingsw.gc03.model.card.card.objective;

import com.google.gson.*;
import java.lang.reflect.Type;


/**
 * This class is used to provide Gson with a custom TypeAdapter to handle the deserialization of classes that implement
 * the CalculateScoreStrategy interface.
 */
public class CalculateScoreStrategyAdapter implements JsonSerializer<CalculateScoreStrategy>, JsonDeserializer<CalculateScoreStrategy> {
    @Override
    public JsonElement serialize(CalculateScoreStrategy src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("className", src.getClass().getName());
        return jsonObject;
    }

    @Override
    public CalculateScoreStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get("className").getAsString();

        // Deserialization of strategies
        switch (className) {
            case "SecondaryDiagonalStrategy":
                return new SecondaryDiagonalStrategy();
            case "MainDiagonalStrategy":
                return new MainDiagonalStrategy();
            case "PileTopLeftStrategy":
                return new PileTopLeftStrategy();
            case "PileTopRightStrategy":
                return new PileTopRightStrategy();
            case "PileBottomLeftStrategy":
                return new PileBottomLeftStrategy();
            case "PileBottomRightStrategy":
                return new PileBottomRightStrategy();
            case "RequirementStrategy":
                return new RequirementStrategy();
            default:
                throw new JsonParseException("Unknown strategy class: " + className);
        }
    }


}