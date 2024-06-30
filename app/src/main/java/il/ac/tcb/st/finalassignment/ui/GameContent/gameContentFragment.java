package il.ac.tcb.st.finalassignment.ui.GameContent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import il.ac.tcb.st.finalassignment.Assists.User;
import il.ac.tcb.st.finalassignment.MainActivity;
import il.ac.tcb.st.finalassignment.R;
import il.ac.tcb.st.finalassignment.databinding.FragmentDashBinding;
import il.ac.tcb.st.finalassignment.databinding.FragmentGameContentBinding;

public class gameContentFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private final String TAG = "GAMEcontTENT";
    private GameContentViewModel mViewModel;
    private FragmentGameContentBinding binding;
    public static gameContentFragment newInstance() {
        return new gameContentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGameContentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GameContentViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backBtn.setOnClickListener(view1 -> {
            if(getArguments().getBoolean("isFromFav")){
                Navigation.findNavController(view).navigate(R.id.favoritesFragment);
            }
            else
                Navigation.findNavController(view).navigate(R.id.dashFragment);
        });
        firebaseAuth = FirebaseAuth.getInstance();
        Glide.with(binding.thumbNail.getContext()).load(getArguments().getString("thumbnail")).into(binding.thumbNail);
        binding.titleLabel.setText(getArguments().getString("title"));
        binding.discLabel.setText(getArguments().getString("disc"));
        binding.devLabel.setText(getArguments().getString("developer"));
        binding.platLabel.setText(getArguments().getString("platform"));
        if(isFav(getArguments().getInt("id")))
            binding.heartCheckbox.setChecked(true);
        binding.heartCheckbox.setOnCheckedChangeListener((v,isChecked)->{
            if(isChecked){
                MainActivity.faveIds.add((long)getArguments().getInt("id"));
                addtoFav(getArguments().getInt("id"));
            }
            else {
                MainActivity.faveIds.remove((long)getArguments().getInt("id"));
                removeFromFav(getArguments().getInt("id"));
            }
        });
    }

    private boolean isFav(int id){
     return MainActivity.faveIds.contains((long)id);
    }

    private void addtoFav(int id ){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newSwitch = new HashMap<>();
        newSwitch.put("gameId", id);
        db.collection("users")
                .document(firebaseAuth.getUid())
                .collection("games")
                .add(newSwitch)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " +
                        documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
    private void removeFromFav(int id ){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ColRef = db.collection("users").document(firebaseAuth.getUid()).collection("games");
        Query query = ColRef.whereEqualTo("gameId",id);
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Iterate through the documents
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        db.collection("users").document(firebaseAuth.getUid()).collection("games").document(document.getId())
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                })
                                .addOnFailureListener(e -> {
                                    Log.w(TAG, "Error deleting document", e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error getting documents.", e);
                });
    }
}