package it.polimi.ingsw.gc03.saveGameData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



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
