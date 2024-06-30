package il.ac.tcb.st.finalassignment.ui.favorites;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import il.ac.tcb.st.finalassignment.Assists.GL_RecyclerViewAdpater;
import il.ac.tcb.st.finalassignment.Assists.Game;
import il.ac.tcb.st.finalassignment.Assists.Games;
import il.ac.tcb.st.finalassignment.Controller.GameService;
import il.ac.tcb.st.finalassignment.Controller.Request;
import il.ac.tcb.st.finalassignment.MainActivity;
import il.ac.tcb.st.finalassignment.R;
import il.ac.tcb.st.finalassignment.databinding.FragmentDashBinding;
import il.ac.tcb.st.finalassignment.databinding.FragmentFavoritesBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoritesFragment extends Fragment {
    private final String TAG = "gksg";
    private Games array;
    private FavoritesViewModel mViewModel;
    private FragmentFavoritesBinding binding;
    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        array = new Games(new ArrayList<Game>());
        loadFavs();
    }
    private void loadRecycler(){
        RecyclerView recyclerView = binding.favrecycler;
        GL_RecyclerViewAdpater adpater = new GL_RecyclerViewAdpater(getContext(),array.getArray());
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void loadFavs(){
        Retrofit retrofit = Request.getGames();
        GameService service = retrofit.create(GameService.class);
        for(long id : MainActivity.faveIds){
            Call<Game> callAsynce = service.getGameByid(id);
            callAsynce.enqueue(new Callback<Game>() {
                @Override
                public void onResponse(@NonNull Call<Game> call, @NonNull Response<Game> response) {
                    Game tmp = new Game(response.body());
                    tmp.setFromFav(true);
                    array.addtoArray(tmp);
                    if(MainActivity.faveIds.size()==array.getArray().size()){
                        loadRecycler();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Game> call, @NonNull Throwable throwable) {
                    Log.d(TAG, "onResponse: fail " + throwable.getMessage());
                }
            });
        }
    }
}