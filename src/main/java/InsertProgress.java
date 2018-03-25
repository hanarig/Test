import controller.JDBCController;
import java.io.IOException;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InsertProgress {

  @FXML
  private Button done;

  @FXML
  private ProgressBar progressBar = new ProgressBar();

  @FXML
  private Label supportText = new Label();

  @FXML
  private void progressDone() {
    Stage stage = (Stage) done.getScene()
                              .getWindow();
    stage.close();
    InitProgram.mainWindow.getWindow();
  }

  public void getWindow() {
    try {
      InitProgram.initWindowInsertProgress();
      InitProgram.stageInsertProgress.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initProgressBar() {
    done.setDisable(true);
    progressBar.progressProperty()
               .unbind();
    progressBar.progressProperty()
               .bind(JDBCController.jdbcService.progressProperty());
    supportText.textProperty()
               .unbind();
    supportText.textProperty()
               .bind(JDBCController.jdbcService.messageProperty());
    JDBCController.jdbcService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
        event -> {
          JDBCController.jdbcService.getValue();
          //supportText.setText("Конвертация прошла успешно.");
          done.setDisable(false);
          supportText.setTextFill(Color.GREEN);
        });
    Thread thread = new Thread(JDBCController.jdbcService);
    thread.setDaemon(true);
    thread.start();
  }

  @FXML
  public void initialize() {
    //supportText.setText("Идет процесс записи файла в базу данных. Пожалуйста, подождите.....");
    initProgressBar();
  }
}
