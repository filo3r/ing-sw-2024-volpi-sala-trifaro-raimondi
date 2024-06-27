package it.polimi.ingsw.gc03.model.card.cardObjective;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * This class is used to provide Gson with a custom TypeAdapter to handle the deserialization of classes that implement
 * the CalculateScoreStrategy interface.
 */
public class CalculateScoreStrategyAdapter implements JsonSerializer<CalculateScoreStrategy>, JsonDeserializer<CalculateScoreStrategy> {

    /**
     * Serializes a CalculateScoreStrategy object into its JSON representation.
     * @param src The source CalculateScoreStrategy object to serialize.
     * @param typeOfSrc The actual type of the source object.
     * @param context The context of the serialization process.
     * @return A JsonElement representing the serialized CalculateScoreStrategy object.
     */
    @Override
    public JsonElement serialize(CalculateScoreStrategy src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("className", src.getClass().getName());
        return jsonObject;
    }

    /**
     * Deserializes a JSON element into a CalculateScoreStrategy object.
     * Uses the strategy pattern to determine the specific subclass to instantiate based on the "className" property
     * in the JSON.
     * @param json The JSON element to deserialize.
     * @param typeOfT The type of the object to deserialize to.
     * @param context The context of the deserialization process.
     * @return The deserialized CalculateScoreStrategy object.
     * @throws JsonParseException If the JSON element cannot be parsed into a known CalculateScoreStrategy subclass.
     */
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