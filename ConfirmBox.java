import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.geometry.*;

@SuppressWarnings("unused")
public class ConfirmBox {
  static Stage stage;
  static boolean btn_flag;

  public static boolean show(String message, String title, String text_yes,
    String text_no) {
      btn_flag = false;

      stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(title);
      stage.setMinWidth(250);

      Label lbl = new Label();
      lbl.setText(message);

      Button btnYes = new Button();
      btnYes.setText(text_yes);
      btnYes.setOnAction(e -> btnYesAction());

      Button btnNo = new Button();
      btnNo.setText(text_no);
      btnNo.setOnAction(e -> btnNoAction());

      HBox paneBtn = new HBox(20);
      paneBtn.setPadding(new Insets(10));

      paneBtn.getChildren().addAll(btnYes, btnNo);

      VBox pane = new VBox(20);
      pane.getChildren().addAll(lbl, paneBtn);
      
      pane.setAlignment(Pos.CENTER);

      Scene scene = new Scene(pane);
      stage.setScene(scene);
      stage.showAndWait();

      return btn_flag;
  }

  public static void btnYesAction() {
    stage.close();
    btn_flag = !btn_flag;
  }

  public static void btnNoAction() {
    stage.close();
    btn_flag = !btn_flag;
  }

}