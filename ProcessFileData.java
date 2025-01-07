import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProcessFileData {
  private final static Path PATH = Path.of("/home/mza_almighty/Music/playlist.mza");
  /*public static void main(String[] args){
    System.out.println("[~] processing");
    /*createPlaylist();
    MusicTrack[] tracks = loadPlaylist();
    for(MusicTrack tck: tracks) {
      System.out.println(tck.getYear());
    }--SANITY-CHECK--
  }*/

  public static MusicTrack[] loadPlaylist() {
    Path path = PATH;
    int idx = 0;
    MusicTrack track = new MusicTrack();
    MusicTrack[] tracks = new MusicTrack[0];
    try {
      List<String> file_dat = Files.readAllLines(path);
      tracks = new MusicTrack[file_dat.size()];
      for (String line: file_dat) {
        //System.out.println(line);
        String regex = "[:]";
        String[] res_args = line.split(regex);
        String title = res_args[0];
        int year = Integer.parseInt(res_args[1]);
        double duration = Double.parseDouble(res_args[2]);
        String path_track = res_args[3];
        track = new MusicTrack(title, year, duration, path_track);
        tracks[idx] = track;
        //System.out.println(track.getPath());
        idx +=1;
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tracks;

  }

  public static void createPlaylist() {
    // creating playlist.mza and writing
    File dir = new File("/home/mza_almighty/Music");
    File[] dir_list = dir.listFiles();
    String title = "";
    String year = "1980";
    String duration = "2.0";
    String path = "/home/mza_almighty/Music/Chitubu.mp3";
    String tok = "";
    for( File file: dir_list) {
      title = file.getName();
      path = file.getPath();
      if (title.endsWith(".mp3") || title.endsWith(".wav")) {
        tok += title+":"+year+":"+duration+":"+path+"\n";
      }
    }
    Path ret_path = Path.of("/dev/null");
    try {
      ret_path = Files.writeString(PATH, tok);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("[+] write complete: "+ret_path);
  }

}