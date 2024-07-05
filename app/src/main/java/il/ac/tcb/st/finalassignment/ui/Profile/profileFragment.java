package il.ac.tcb.st.finalassignment.ui.Profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import il.ac.tcb.st.finalassignment.Assists.User;
import il.ac.tcb.st.finalassignment.R;
import il.ac.tcb.st.finalassignment.databinding.FragmentLoginBinding;
import il.ac.tcb.st.finalassignment.databinding.FragmentProfileBinding;
import il.ac.tcb.st.finalassignment.ui.Login.LoginFragment;

public class profileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private final String TAG ="loginFrag";
    private ProfileViewModel mViewModel;
    private FirebaseAuth firebaseAuth;
    public static profileFragment newInstance() {
        return new profileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(firebaseAuth.getUid()!=null){
            load();
        }
        binding.logoutBtn.setOnClickListener(Logout());
    }
    View.OnClickListener Logout(){
        return view -> {
            firebaseAuth.signOut();
            NavHostFragment.findNavController(profileFragment.this).navigate(R.id.loginFragment);
        };
    }
    private void load(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(firebaseAuth.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "user: " + document.getData());
                    User u = new User(document.getString("firstName"),document.getString("lastName"),document.getString("email"));
                    binding.emailLabel.setText(u.getFirstName()+ " "+ u.getLastName());
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }
}