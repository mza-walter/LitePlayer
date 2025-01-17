import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class MessageBox {

  public static void show(String message, String title) {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle(title);
    stage.setMinWidth(250);

    Label lbl = new Label();
    lbl.setText(message);

    Button btn_Ok = new Button();
    btn_Ok.setText("Ok");
    btn_Ok.setOnAction(e -> stage.close());
    
    VBox pane = new VBox(20);
    pane.getChildren().addAll(lbl, btn_Ok);
    pane.setPadding(new Insets(10));
    pane.setAlignment(Pos.CENTER);

    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.showAndWait();

  }

}