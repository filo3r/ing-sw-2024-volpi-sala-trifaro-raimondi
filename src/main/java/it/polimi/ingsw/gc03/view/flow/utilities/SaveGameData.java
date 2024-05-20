package it.polimi.ingsw.gc03.view.flow.utilities;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc03.model.GameImmutable;


public class SaveGameData {
    private final String path;
    /**
     * Init class
     */
    public SaveGameData() {
        path = System.getProperty("user.home") + "/AppData/Roaming/.Codex";
    }

    public void saveGameData(String nickname, int gameId){
        ArrayList<Integer> gameData = loadGameData(nickname);
        gameData.add(gameId);
        Gson gson = new Gson();
        try(FileWriter writer = new FileWriter(path + "/" + nickname + ".json")) {
            gson.toJson(gameData,writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId(String nickname){
        ArrayList<Integer> gameData = loadGameData(nickname);
        return gameData.getLast();
    }
    public ArrayList<Integer> loadGameData(String nickname)  {
        Gson gson = new Gson();
        try(FileReader reader = new FileReader(path + "/" + nickname + ".json")){
            Type type = new TypeToken <ArrayList<Integer>>() {}.getType();
            return gson.fromJson(reader,type);
        }
        catch (IOException e) {
        return new ArrayList<Integer>();
        }
    }
}
