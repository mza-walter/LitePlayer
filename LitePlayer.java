import java.io.File;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.collections.*;
import javafx.event.EventType;
import javafx.geometry.*;

public class LitePlayer extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  Button btnPlay;
  Button btnStop;
  Button btnMute;
  Button btnNext;
  Button btnPrevious;

  boolean btnPlay_flag = false;
  boolean mute_flag = false;
  boolean stop_flag = false;
  
  private String PATH = "/home/mza_almighty/Music/Kurarama Inyasha [V73ZodWrELw].mp3";
 
  File f = new File(PATH);
  Media media = new Media(f.toURI().toString());
  MediaPlayer mplayer = new MediaPlayer(media);
  String prev_path = "";
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override public void start(Stage primaryStage) {
    // children
    /*** DEV PLACEHOLDERS
    Label lbl_1 = new Label();
    lbl_1.setText("MenuBar");
    Label lbl_2 = new Label();
    lbl_2.setText("Playlist");
    Label lbl_3 = new Label();
    lbl_3.setText("Controls");
    **/
    //child::menubar
    MenuBar mainMenuBar = new MenuBar();
    Menu mainMenu = new Menu("Menu");
    Menu aboutMenu = new Menu("About");
    MenuItem authorItem = new MenuItem("Authors");
    MenuItem importItem = new MenuItem("Import Music");
    MenuItem removeAllItem = new MenuItem("Remove all");
    
    importItem.setOnAction(e -> importItemAction());
    aboutMenu.setOnAction(e -> aboutMenuAction());

    removeAllItem.setOnAction(e -> removeAllItemAction());

    aboutMenu.getItems().addAll(authorItem);
    mainMenu.getItems().addAll(importItem, removeAllItem);
    mainMenuBar.getMenus().addAll(mainMenu, aboutMenu);

    //child::playlist view
    Label lblPlaylist = new Label("Available Music Tracks");
    lblPlaylist.setFont(new Font("Arial", 11));

    TableView<MusicTrack> table = new TableView<MusicTrack>();
    table.setOnMouseClicked( (MouseEvent e) -> {
      if( e.getButton().equals(MouseButton.PRIMARY)) {
        MusicTrack track = table.getSelectionModel().getSelectedItem();
        PATH = track.getPath();
        System.out.println("\n[selected>] "+PATH);
      }
    });
    table.setItems(loadData());

    //@SuppressWarnings({ "rawtypes", "unchecked" })
    TableColumn<MusicTrack, String> colTitle = new TableColumn("Title");
    colTitle.setMinWidth(300);
    colTitle.setCellValueFactory(
      new PropertyValueFactory<MusicTrack, String>("Title")
    );

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    TableColumn<MusicTrack, Integer> colYear = new TableColumn("Year");
    colYear.setMinWidth(100);
    colYear.setCellValueFactory(
      new PropertyValueFactory<MusicTrack, Integer>("Year")
    );

    //@SuppressWarnings({ "rawtypes", "unchecked" })
    TableColumn<MusicTrack, Double> colDuration = new TableColumn("Duration");
    colDuration.setMinWidth(100);
    colDuration.setCellValueFactory(
      new PropertyValueFactory<MusicTrack, Double>("Duration")
    );

    table.getColumns().addAll(colTitle, colYear, colDuration);

    // setup playlist pane
    VBox playlistPane = new VBox();
    playlistPane.setSpacing(10);
    playlistPane.setPadding(new Insets(10, 10, 10, 10));
    playlistPane.getChildren().addAll(lblPlaylist, table);


    //child::controls
    //Hpane: seek vol scales
    Slider seek = new Slider();
    seek.setOrientation(Orientation.HORIZONTAL);
    seek.setPrefWidth(350);
    seek.valueProperty().addListener(
      new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
          Duration total;
          total = mplayer.getTotalDuration();
          //Duration whatisthis = total.subtract(Duration.minutes(1));
          int percent = (int) seek.getValue();
          double current = ((percent) * total.toSeconds()/100);
          //lblPlaylist.setText("total=" + total.toSeconds() + " current="+current);
          mplayer.seek(Duration.seconds(current));
        }
        
      }
    );

    Slider vol = new Slider();
    vol.setOrientation(Orientation.HORIZONTAL);
    vol.setShowTickMarks(true);
    vol.setPrefWidth(200);
    vol.setValue(50);
    vol.valueProperty().addListener(
      new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
          double volumn = vol.getValue()/100;
          mplayer.setVolume(volumn);
        }
        
      }
    );

    //Hpane: scales container
    HBox ScalesPane = new HBox(20);
    ScalesPane.getChildren().addAll(seek, vol);
    ScalesPane.setPadding(new Insets(5));

    //Hpane: playback controls buttons
    btnPlay = new Button();
    btnPlay.setText(" Play/Pause");
    btnPlay.setOnAction(e -> btnPlayAction());

    btnStop = new Button();
    btnStop.setText("Stop");
    btnStop.setOnAction(e -> btnStopAction());

    btnMute = new Button();
    btnMute.setText("Mute");
    btnMute.setOnAction(e -> btnMuteAction());

    btnNext = new Button();
    btnNext.setText("Next");
    btnNext.setOnAction(e -> btnNextAction());

    btnPrevious = new Button();
    btnPrevious.setText("Prev");
    btnPrevious.setOnAction(e -> btnPreviousAction());

    //Hpane:: btns container
    HBox BtnPane = new HBox(20);
    BtnPane.getChildren().addAll(btnMute, btnPrevious, btnPlay, btnNext, btnStop);
    BtnPane.setPadding(new Insets(5));
    BtnPane.setAlignment(Pos.CENTER);

    VBox controlsPane = new VBox(20);
    controlsPane.getChildren().addAll(ScalesPane, BtnPane);
    controlsPane.setPadding(new Insets(10));
    controlsPane.setAlignment(Pos.CENTER);

    //parent::application view
    VBox pane = new VBox();
    pane.getChildren().addAll(mainMenuBar, playlistPane, controlsPane);
    pane.setAlignment(Pos.TOP_CENTER);
    
    Scene scene = new Scene(pane, 550, 450);

    primaryStage.setScene(scene);
    primaryStage.setTitle("LitePlayer");
    primaryStage.show();
  }
  
  public void removeAllItemAction() {
    ConfirmBox.show("Remove all music from the library?", "Confirm",
      "Yes", "Cancel");
  }

  public void importItemAction() {

  }

  public void aboutMenuAction() {
    MessageBox.show("mza.walter.dev@gmail.com, MIT License 2025."
      , "Authors");
  }

  public ObservableList<MusicTrack> loadData() {
    System.out.println("[+] LoadData()");
    ObservableList<MusicTrack> data = FXCollections.observableArrayList();
    ProcessFileData.createPlaylist();
    MusicTrack[] tracks = ProcessFileData.loadPlaylist();
    for(MusicTrack track: tracks) {
      data.add(track);
      System.out.println(track.getTitle());
    }

    return data;
  }

  private void btnPlayAction() {
    if (prev_path != PATH) {
      System.out.println("\n[Prev:] "+prev_path);
      System.out.println("[Curr:] "+PATH+"\n");

      mplayer.stop();
      mplayer.dispose();
      f = new File(PATH);
      media = new Media(f.toURI().toString());
      mplayer = new MediaPlayer(media);
      mplayer.play();
      prev_path = PATH;
      btnPlay_flag = false;
    } else {
      
      if (btnPlay_flag) {

        System.out.println("[" + btnPlay_flag + "] playing audio: " + PATH);
        mplayer.play();

      } else {
        System.out.println("[" + btnPlay_flag + "] playing audio: " + PATH);
        mplayer.pause();
        btnPlay_flag = true;

      }
    }
  }

  private void btnStopAction() {   
    if (!stop_flag) { // 
      System.out.println("[" + stop_flag + "] playing audio: " + PATH);
      mplayer.stop();
    }
  }

  private void btnMuteAction() {
    mute_flag = !mute_flag;
    if (mute_flag) {
      mplayer.setMute(mute_flag);
      System.out.println("[" + mute_flag + "] mute audio: " + PATH);
    }
    else {
      mplayer.setMute(mute_flag);
      System.out.println("[" + mute_flag + "] mute audio: " + PATH);
    }
  }

  private void btnNextAction() {
  
  }

  private void btnPreviousAction() {
  
  }
 

}

