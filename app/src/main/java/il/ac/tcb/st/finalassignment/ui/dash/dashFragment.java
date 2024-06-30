package il.ac.tcb.st.finalassignment.ui.dash;

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

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.ac.tcb.st.finalassignment.Assists.GL_RecyclerViewAdpater;
import il.ac.tcb.st.finalassignment.Assists.Game;
import il.ac.tcb.st.finalassignment.Assists.Games;
import il.ac.tcb.st.finalassignment.Controller.GameService;
import il.ac.tcb.st.finalassignment.Controller.Request;
import il.ac.tcb.st.finalassignment.R;
import il.ac.tcb.st.finalassignment.databinding.FragmentDashBinding;
import il.ac.tcb.st.finalassignment.databinding.FragmentProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class dashFragment extends Fragment {
    private FragmentDashBinding binding;
    private DashViewModel mViewModel;
    private Games array;
    private ArrayList<String> keyTags;
    private final String TAG = "dash";
    public static dashFragment newInstance() {
        return new dashFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashViewModel.class);

        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchOptCd.setVisibility(View.INVISIBLE);
        keyTags = new ArrayList<String>();
        loadRequest();
        loadClickables();

        Log.d(TAG, "loadClickables: helllloooooo");
        binding.searchBtn.setOnClickListener(view1 -> {
            binding.searchOptCd.setVisibility(View.VISIBLE);
        });
        binding.btnCancel.setOnClickListener(view1 -> {
            binding.searchOptCd.setVisibility(View.INVISIBLE);
        });
        binding.btnFilter.setOnClickListener(view1 -> {
            if(!keyTags.isEmpty()){
            binding.searchOptCd.setVisibility(View.INVISIBLE);
            String data = "";
            for (String s: keyTags) {
                data += s+".";
            }
            loadFilteredRequest(data);}
            else {
                loadRequest();
                binding.searchOptCd.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void loadClickables(){
        binding.CbAction.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("action");
            else
                keyTags.remove("action");
        });
        binding.CbFantasy.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("fantasy");
            else
                keyTags.remove("fantasy");
        });
        binding.CbFighting.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("fighting");
            else
                keyTags.remove("fighting");
        });
        binding.CbFirstperson.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("first-person");
            else
                keyTags.remove("first-person");
        });
        binding.CbHorror.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("horror");
            else
                keyTags.remove("horror");
        });
        binding.CbMmorpg.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("mmorpg");
            else
                keyTags.remove("mmorpg");
        });
        binding.CbShooter.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("shooter");
            else
                keyTags.remove("shooter");
        });
        binding.CbStrategy.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("strategy");
            else
                keyTags.remove("strategy");
        });
        binding.CbMoba.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("moba");
            else
                keyTags.remove("moba");
        });
        binding.CbRacing.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("racing");
            else
                keyTags.remove("racing");
        });
        binding.CbOpenworld.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("open-world");
            else
                keyTags.remove("open-world");
        });
        binding.Cbsurvival.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("survival");
            else
                keyTags.remove("survival");
        });
        binding.CbPvp.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("pvp");
            else
                keyTags.remove("pvp");
        });
        binding.CbSandbox.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("sandbox");
            else
                keyTags.remove("sandbox");
        });
        binding.CbThirdperson.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("third-Person");
            else
                keyTags.remove("third-Person");
        });
        binding.CbSocial.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("social");
            else
                keyTags.remove("social");
        });
        binding.CbSports.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked)
                keyTags.add("sports");
            else
                keyTags.remove("sports");
        });
    }

    private void loadRecycler(){
        RecyclerView recyclerView = binding.gameRecycler;
        GL_RecyclerViewAdpater adpater = new GL_RecyclerViewAdpater(getContext(),array.getArray());
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void loadRequest(){
        Retrofit retrofit = Request.getGames();
        GameService service = retrofit.create(GameService.class);
        Call<List<Game>> callAsynce = service.getGames();
        callAsynce.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
               array = new Games(response.body());
               loadRecycler();
            }
            @Override
            public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable throwable) {
                Log.d(TAG, "onResponse: fail " + throwable.getMessage());
            }
        });

    }

    private void loadFilteredRequest(String data){
        Retrofit retrofit = Request.getGames();
        GameService service = retrofit.create(GameService.class);
        Call<List<Game>> callAsynce = service.getGamesFilter(data);
        callAsynce.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
                array = new Games(response.body());
                loadRecycler();
            }
            @Override
            public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable throwable) {
                Log.d(TAG, "onResponse: fail " + throwable.getMessage());
            }
        });

    }

}