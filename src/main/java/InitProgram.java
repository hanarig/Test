
import controller.CSVController;
import controller.JDBCController;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InitProgram {

  public static final Stage stageExtraInf = new Stage();

  public static final Stage stageInsertProgress = new Stage();

  public static final Stage stageDBConfig = new Stage();

  public static final JDBCController jdbsController = new JDBCController();

  public static final Stage primaryStage = new Stage();

  public static final DBConfig dbConfig = new DBConfig();

  public static final MainWindow mainWindow = new MainWindow();

  public static final TableExistsInformation tableExist = new TableExistsInformation();

  public static final CSVController csvController = new CSVController();

  public static final InsertProgress progress = new InsertProgress();

  public static void initWindowTableExistInf() throws IOException {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("table_exists_information.fxml"));
    AnchorPane rootLayout = loader.load();
    Scene dbConfig = new Scene(rootLayout);
    // Отображаем сцену, содержащую корневой макет.
    stageExtraInf.setTitle("Изменение данных в таблице БД");
    stageExtraInf.setScene(dbConfig);
  }

  public static void initWindowInsertProgress() throws IOException {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("insert_progress.fxml"));
    AnchorPane rootLayout = loader.load();
    Scene dbConfig = new Scene(rootLayout);
    // Отображаем сцену, содержащую корневой макет.
    stageInsertProgress.setTitle("Процесс импорта данных в БД");
    stageInsertProgress.setScene(dbConfig);
  }

  public static void initWindowDBConfig() throws IOException {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("db_config.fxml"));
    AnchorPane rootLayout = loader.load();
    Scene dbConfig = new Scene(rootLayout);
    // Отображаем сцену, содержащую корневой макет.
    InitProgram.stageDBConfig.setTitle("Настройка соединения с БД");
    InitProgram.stageDBConfig.setScene(dbConfig);
  }

  static {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("main_window.fxml"));
    AnchorPane rootLayout = null;
    try {
      rootLayout = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Отображаем сцену, содержащую корневой макет.
    Scene mainWindow = new Scene(Objects.requireNonNull(rootLayout));
    primaryStage.setTitle("Импорт CSV в БД");
    primaryStage.setScene(mainWindow);
    /////////////////////////
    stageExtraInf.initModality(Modality.APPLICATION_MODAL);
    //////////////////////////
    stageDBConfig.initModality(Modality.APPLICATION_MODAL);
    //////////////////////////
    stageInsertProgress.initModality(Modality.APPLICATION_MODAL);
  }
}
