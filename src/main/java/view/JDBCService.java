package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.concurrent.Task;
import model.JDBCConfig;

public class JDBCService extends Task {

  private static final JDBCConfig jdbcConfig = new JDBCConfig();
  private List<String[]> lines;
  private String tableName;
  private Connection conn = null;
  private boolean tableToRewrite;
  private String setTypeData;

  public void setJDBCConfig(String driver, String url, String userName, String password) {
    jdbcConfig.setUserName(userName);
    jdbcConfig.setPassword(password);
    jdbcConfig.setDriver(driver);
    jdbcConfig.setUrl(url);
  }

  public void setLines(List<String[]> lines) {
    this.lines = lines;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public JDBCConfig getJdbcConfig() {
    return jdbcConfig;
  }

  private void queryRunner(String sql) {
    Statement stmt;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void dropTable() {
    startConnection();
    queryRunner("DROP TABLE " + tableName);
    closeConnection();
  }

  private void createTable() {
    startConnection();
    String[] nameColumns = lines.get(0);
    StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");
    for (int i = 0; i < nameColumns.length; i++) {
      sql.append(i == nameColumns.length - 1 ?
          nameColumns[i] + " " + setTypeData + ");" :
          nameColumns[i] + " " + setTypeData + ", ");
    }
    queryRunner(sql.toString());
    closeConnection();
  }

  private Object save() {
    startConnection();
    lines.remove(0);
    double size = lines.size();
    double counter = 1;
    for (String[] line : lines) {
      StringBuilder sql = new StringBuilder("INSERT INTO ");
      sql.append(tableName)
         .append(" VALUES (");
      for (int i = 0; i < line.length; i++) {
        sql.append(i == line.length - 1 ?
            "'" + line[i] + "'" + ");" :
            "'" + line[i] + "'" + ", ");
      }
      this.updateProgress(counter, size);
      counter++;
      queryRunner(sql.toString());
    }
    closeConnection();
    return "OK";
  }

  private String convertNameDBTODriver(String name) {
    if (name.equals("PostgreSQL")) {
      setTypeData = "text";
      return "org.postgresql.Driver";
    } else {
      setTypeData = "VARCHAR(255)";
      return "com.mysql.jdbc.Driver";
    }
  }

  private void startConnection() {
    try {
      Class.forName(convertNameDBTODriver(jdbcConfig.getDriver()));
      conn = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUserName(),
          jdbcConfig.getPassword());
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void closeConnection() {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setTableToRewrite(boolean tableToRewrite) {
    this.tableToRewrite = tableToRewrite;
  }

  public void createNewTable() {
    createTable();
  }

  public void insertData() {
    if (tableToRewrite) {
      dropTable();
      createTable();
    }
  }

  public boolean tryToConnect(JDBCConfig jdbcConfig) {
    if (jdbcConfig.getDriver() == null && jdbcConfig.getPassword() == null &&
        jdbcConfig.getUrl() == null && jdbcConfig.getUserName() == null) {
      return false;
    } else {
      return testConnection();
    }
  }

  private boolean testConnection() {
    try {
      Class.forName(convertNameDBTODriver(jdbcConfig.getDriver()));
    } catch (ClassNotFoundException e) {
      //e.printStackTrace();
    }
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(
          jdbcConfig.getUrl(), jdbcConfig.getUserName(),
          jdbcConfig.getPassword());
    } catch (SQLException e) {
      //e.printStackTrace();
    }
    return connection != null;
  }

  public boolean checkIfExistTable() {
    startConnection();
    Statement stmt;
    try {
      stmt = conn.createStatement();
      stmt.executeQuery("SELECT count(*) FROM " + tableName);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  protected Object call() {
    return save();
  }
}
