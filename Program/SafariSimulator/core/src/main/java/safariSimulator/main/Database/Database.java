package safariSimulator.main.Database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import safariSimulator.main.Models.Map;

public class Database {

    public static int saveGame(Map map) {
        Json json = new Json();
        FileHandle file;
        MapState mapState = new MapState(map);

        if (map.savingFileName != null) {
            file = Gdx.files.local(map.savingFileName);
        } else {
            if (!Gdx.files.local("save1.json").exists()) {
                map.savingFileName = "save1.json";
                file = Gdx.files.local("save1.json");
            } else if (!Gdx.files.local("save2.json").exists()) {
                map.savingFileName = "save2.json";
                file = Gdx.files.local("save2.json");
            } else if (!Gdx.files.local("save3.json").exists()) {
                map.savingFileName = "save3.json";
                file = Gdx.files.local("save3.json");
            } else {
                return 0;
            }
        }
        file.writeString(json.prettyPrint(mapState), false);
        System.out.println("Save file created at: " + file.file().getAbsolutePath());
        return 1;
    }

    public static Map loadMap(String fileName) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(fileName);

        if (file.exists()) {
            MapState mapState = json.fromJson(MapState.class, file.readString());
            return new Map(mapState);
        }
        return new Map();
    }

    public static boolean isExistsSave(String fileName) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(fileName);
        return file.exists();
    }
}
