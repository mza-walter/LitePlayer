import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ImportMusicDialog extends Application{
  public static void main(String[] args) {
    launch(args);
  }

  TextField TxtLocation;

  @Override public void start(Stage primaryStage) {
    Label LblLocation = new Label();
    LblLocation.setText("Music Folder:");
    LblLocation.setMinWidth(100);
    LblLocation.setAlignment(Pos.TOP_LEFT);

    TxtLocation = new TextField();
    TxtLocation.setMinWidth(250);
   // TxtLocation.setMinHeight(200);
    TxtLocation.setMaxWidth(350);
    TxtLocation.setMaxHeight(200);
    TxtLocation.setPromptText("Enter location of music directory");

    Button BtnImport = new Button();
    BtnImport.setText("Import");
    BtnImport.setMinWidth(75);
    BtnImport.setOnAction(e -> BtnImportAction());

    HBox paneLocation = new HBox(20, LblLocation, TxtLocation);
    paneLocation.setPadding(new Insets(10));

    Label LblMessage_1 = new Label();
    LblMessage_1.setText("e.g /home/user/Music");
  

    HBox paneMessage = new HBox(20, LblMessage_1);
    paneMessage.setPadding(new Insets(10));

  
    HBox paneBtn = new HBox(20, BtnImport);
    paneBtn.setPadding(new Insets(10));
    paneBtn.setAlignment(Pos.BOTTOM_RIGHT);
    
    VBox pane = new VBox(10, paneLocation, paneMessage, paneBtn);

    Scene scene = new Scene(pane);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Import Music");
    primaryStage.show();

  }

  public void BtnImportAction() {
    String errMessage = "";

    if (TxtLocation.getText().length() ==0 ) {
      errMessage += "\nPlease enter valid music directory.";
    }

    if ( errMessage.length() ==0 ) {
      String message = "Successfully imported 19 tracks.";
      MessageBox.show(message, "Complete");
    } else {
      MessageBox.show(errMessage, "Error");
    }

  }

}