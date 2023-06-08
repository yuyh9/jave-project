package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFileHandler {

  public List<String> readDataFromFile(String filename) {
    List<String> data = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("file/" + filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        data.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }


  public void writeDataToFile(String filename, List<String> data) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("file/" + filename))) {
      for (String line : data) {
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
