import controller.CSVController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TableExistsInformation {

  @FXML
  private ChoiceBox chooseWhatToDo;

  @FXML
  private void okExtraInf() {
      InitProgram.jdbsController.setLines(CSVController.getLines());
      InitProgram.jdbsController.setTableToRewrite(chooseWhatToDo.getValue()
                                                                 .equals(
                                                                     "Удалить существующие данные"));
      InitProgram.jdbsController.insertData();
      Stage stage = InitProgram.stageExtraInf;
      stage.close();
      InitProgram.progress.getWindow();
  }

  @FXML
  private void cancelExtraInf() {

  }

  public void getWindow() {
    try {
      InitProgram.initWindowTableExistInf();
      InitProgram.stageExtraInf.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    chooseWhatToDo.setValue("Удалить существующие данные");
  }
}
