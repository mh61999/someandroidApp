package il.ac.tcb.st.finalassignment.ui.Register;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import il.ac.tcb.st.finalassignment.Assists.User;
import il.ac.tcb.st.finalassignment.R;
import il.ac.tcb.st.finalassignment.databinding.FragmentLoginBinding;
import il.ac.tcb.st.finalassignment.databinding.FragmentRegisterBinding;
import il.ac.tcb.st.finalassignment.ui.Login.LoginFragment;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.RegBtn.setOnClickListener(register());
        binding.CReBtn.setOnClickListener(view1 -> NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.loginFragment));
    }

    View.OnClickListener register(){
        return view -> {
            if(binding.FNameIn.getText().toString().matches("")||binding.LNameIn.getText().toString().matches("")||binding.EmailIn.getText().toString().matches("")||binding.PWordIn.getText().toString().matches("")||binding.RePWordIn.getText().toString().matches("")){
                Toast.makeText(getContext(),"all inputs need to be filled",Toast.LENGTH_SHORT).show();
            }
            else{
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.EmailIn.getText().toString()).matches()){
                    if(binding.PWordIn.getText().toString().matches(binding.RePWordIn.getText().toString())){
                        registerRequest(binding.EmailIn.getText().toString(),binding.PWordIn.getText().toString());
                    }
                    else Toast.makeText(getContext(),"password doesn't match",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(),"Email Input Is Wrong",Toast.LENGTH_SHORT).show();
            }
        };
    }
    public void registerRequest(String email,String pass){
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(binding.FNameIn.getText().toString(),binding.LNameIn.getText().toString(),binding.EmailIn.getText().toString());
                            Log.d("TAG", "onComplete: "+ task.getResult().getUser().getUid());
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(task.getResult().getUser().getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.dashFragment);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}