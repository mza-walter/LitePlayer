public class MusicTrack {

  private String title;
  private int year;
  private double duration;
  private String path;

  public MusicTrack() {
    this.title = "";
    this.year  = 1900;
    this.duration = 0;
    this.path = "";
  }

  public MusicTrack(String title, int year, double duration, String path) {
    this.title = title;
    this.year  = year;
    this.duration = duration;
    this.path = path;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public double getDuration() {
    return this.duration;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

}