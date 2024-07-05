package il.ac.tcb.st.finalassignment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import il.ac.tcb.st.finalassignment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String TAG ="main";
    private Context context;
    private FirebaseAuth firebaseAuth;
    public static ArrayList<Long> faveIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewMain);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(onDestinationChangedListener);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        faveIds = new ArrayList<Long>();
        if(firebaseAuth.getUid()==null){
            Log.d(TAG, "onCreate: nah man is out");
            navController.navigate(R.id.loginFragment);
        }
        else {
            loadFavs();
        }
    }

    private void loadFavs(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ColRef = db.collection("users").document(firebaseAuth.getUid()).collection("games");
        ColRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Iterate through the documents
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                faveIds.add((Long)document.getData().get("gameId"));
                            }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error getting documents.", e);
                });
    }


    NavController.OnDestinationChangedListener onDestinationChangedListener =
            (navController, navDestination, bundle) -> {
                ActionBar actionBar = getSupportActionBar();
                if (navDestination.getId() == R.id.loginFragment) {
                    binding.bottomNavigationViewMain.setVisibility(View.GONE);
                    if (actionBar != null) { actionBar.hide(); }
                } else if (navDestination.getId() == R.id.gameContentFragment) {
                    binding.bottomNavigationViewMain.setVisibility(View.GONE);
                    if (actionBar != null) { actionBar.hide(); }
                } else if (navDestination.getId() == R.id.registerFragment) {
                    binding.bottomNavigationViewMain.setVisibility(View.GONE);
                    if (actionBar != null) { actionBar.hide(); }
                } else {
                    binding.bottomNavigationViewMain.setVisibility(View.VISIBLE);
                    if (actionBar != null) { actionBar.show(); }
                }
            };

    View.OnClickListener Login(){
        return view -> {
            firebaseAuth.signInWithEmailAndPassword("","")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: Success");
                            }
                            else {
                                Log.w(TAG, "onComplete: failure", task.getException());
                                Toast.makeText(MainActivity.this,"auth failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        };
    }

    View.OnClickListener Logout(){
        return view -> {
            firebaseAuth.signOut();
        };
    }

    View.OnClickListener dosomething(){
        return view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> newSwitch = new HashMap<>();
            newSwitch.put("vendor", "Cisco");
            newSwitch.put("model", "C9300X-48HX");
            newSwitch.put("category", "enterprise");
            newSwitch.put("portCategory", "UPOE+");
            newSwitch.put("pOEPowerWatts", 90);
            db.collection("switches")
                    .add(newSwitch)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " +
                            documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

        };
    }
    View.OnClickListener dostuff(){
        return view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("switches").document("uGf195FgiAW7LCdyYb4j");
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "user: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            });

        };
    }
}