package utils;


import com.alibaba.fastjson.JSON;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

/**
 * FileUtil class.
 */
public class FileUtil {
  /**
   * Save objects to file.
   */
  public static void saveObjectsToFile(List<?> objects, String fileName) {
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
      String json = JSON.toJSONString(objects);
      writer.write(json);
      System.out.println("Data saved to file: " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Load objects from file.
   */
  public static <T> List<T> loadObjectsFromFile(String fileName, Type type) {
    List<T> loadedData = null;
    try (Reader reader = new InputStreamReader(new FileInputStream(fileName), "UTF-8")) {
      StringBuilder jsonString = new StringBuilder();
      int charRead;
      while ((charRead = reader.read()) != -1) {
        jsonString.append((char) charRead);
      }
      loadedData = JSON.parseObject(jsonString.toString(), type);
      System.out.println("Data loaded from file: " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return loadedData;
  }

}
