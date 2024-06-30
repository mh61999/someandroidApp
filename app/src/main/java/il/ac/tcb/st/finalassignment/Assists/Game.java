package il.ac.tcb.st.finalassignment.Assists;

import android.graphics.Bitmap;

public class Game {
    int id;

    public boolean isFromFav() {
        return isFromFav;
    }

    public void setFromFav(boolean fromFav) {
        isFromFav = fromFav;
    }

    boolean isFromFav;

    String title;

    String thumbnail;

    String short_description;

    String game_url;

    String genre;

    String platform;

    String publisher;

    String developer;

    String release_date;

    String freetogame_profile_url;
    Bitmap imageBitmap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getGame_url() {
        return game_url;
    }

    public void setGame_url(String game_url) {
        this.game_url = game_url;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getFreetogame_profile_url() {
        return freetogame_profile_url;
    }

    public void setFreetogame_profile_url(String freetogame_profile_url) {
        this.freetogame_profile_url = freetogame_profile_url;
    }

    public Game(Game o){
        id=o.id;
        title = o.title;

        thumbnail = o.thumbnail;

        short_description = o.short_description;

        game_url =  o.game_url;

        genre = o.genre;

        platform = o.platform;

        publisher = o.publisher;

        developer = o.developer;

        release_date = o.release_date;

        freetogame_profile_url = o.freetogame_profile_url;
    }


}
