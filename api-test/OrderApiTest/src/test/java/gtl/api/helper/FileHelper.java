package gtl.api.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class FileHelper {
    public JsonObject loadJsonFile(String filePath) {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(filePath);
            File file = new File(resourceUrl.getFile());
            FileReader fileReader = new FileReader(file);
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            return jsonObject;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading file " + filePath + ": " + e.getMessage());
        }
    }
}
