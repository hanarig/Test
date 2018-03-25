package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import model.CSVConfig;

public class CSVService extends Task<List<String[]>> {

  private static final CSVConfig csvConfig = new CSVConfig();

  public static List<String[]> getLines() {
    return lines;
  }

  private static List<String[]> lines = new ArrayList<>();

  public static void setLines(List<String[]> lines) {
    CSVService.lines = lines;
  }

  public static long numberStrError;

  public void setCSVConfig(String path, String lineSeparator, String tableName) {
    csvConfig.setPath(path);
    csvConfig.setLineSeparator(lineSeparator);
    csvConfig.setTableName(tableName);
  }

  public void setCSVConfig(String path, String lineSeparator) {
    csvConfig.setPath(path);
    csvConfig.setLineSeparator(lineSeparator);
  }

  private List<String[]> read() {
    numberStrError = 0;
    List<String[]> result = new ArrayList<>();
    lines.clear();
    int length = 0;
    int countWords = 8;
    int lastIndex = 1;
    double size = 0;
    try {
      size = Files.lines(Paths.get(csvConfig.getPath()))
                  .count();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvConfig.getPath()))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] dataSet = line.replace("\"", "")
                               .split(csvConfig.getLineSeparator());
        if (length == 0) {
          countWords = line.length() - line.replace(csvConfig.getLineSeparator(), "")
                                           .length();
          length = dataSet.length;
        }
        if (dataSet.length != length) {
          if (line.charAt(line.length() - 1) == csvConfig.getLineSeparator()
                                                         .charAt(0)) {
            dataSet = (line + " ").split(csvConfig.getLineSeparator());
          } else if (line.charAt(0) == csvConfig.getLineSeparator()
                                                .charAt(0)) {
            dataSet = (" " + line).split(csvConfig.getLineSeparator());
          } else {
            numberStrError = lastIndex + 1;
            break;
          }
        }
        if (countWords == 0) {
          numberStrError = -1;
          break;
        }
        result.add(dataSet);
        this.updateProgress(lastIndex, size);
        lastIndex++;
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return result;
  }

  @Override
  public List<String[]> call() {
    return read();
  }
}
