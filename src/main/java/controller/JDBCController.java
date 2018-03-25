package controller;

import java.util.List;
import model.JDBCConfig;
import view.JDBCService;

public class JDBCController {

  public static final JDBCService jdbcService = new JDBCService();

  public void setTableToRewrite(boolean what) {
    jdbcService.setTableToRewrite(what);
  }

  public void setJDBCConfig(JDBCConfig jdbcConfig) {
    jdbcService.setJDBCConfig(jdbcConfig
        .getDriver(), jdbcConfig.getUrl(), jdbcConfig.getUserName(), jdbcConfig.getPassword());
  }

  public void setLines(List<String[]> lines) {
    jdbcService.setLines(lines);
  }

  public void setTableName(String tableName) {
    jdbcService.setTableName(tableName);
  }

  public JDBCConfig getJdbcConfig() {
    return jdbcService.getJdbcConfig();
  }

  public boolean tryToConnect(JDBCConfig jdbcConfig) {
    return jdbcService.tryToConnect(jdbcConfig);
  }

  public void insertData() {
    jdbcService.insertData();
  }

  public boolean checkIfExistTable() {
    return jdbcService.checkIfExistTable();
  }

  public void createNewTable() {
    jdbcService.createNewTable();
  }
}
