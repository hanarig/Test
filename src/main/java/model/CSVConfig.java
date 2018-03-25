package model;

public class CSVConfig {

  private String path;
  private String lineSeparator;
  private String tableName;

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getLineSeparator() {
    return lineSeparator;
  }

  public void setLineSeparator(String lineSeparator) {
    this.lineSeparator = lineSeparator;
  }
}
