package il.ac.tcb.st.finalassignment.Controller;

import java.util.List;

import il.ac.tcb.st.finalassignment.Assists.Game;
import il.ac.tcb.st.finalassignment.Assists.Games;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameService {
    @GET("games")
    public Call<List<Game>> getGames(
    );

    @GET("filter")
    public Call<List<Game>> getGamesFilter(@Query("tag") String data);

    @GET("game")
    public Call<Game> getGameByid(@Query("id") Long data);

}
