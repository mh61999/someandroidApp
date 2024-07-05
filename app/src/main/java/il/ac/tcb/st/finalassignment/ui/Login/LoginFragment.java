package il.ac.tcb.st.finalassignment.ui.Login;

import static androidx.navigation.Navigation.findNavController;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

import il.ac.tcb.st.finalassignment.MainActivity;
import il.ac.tcb.st.finalassignment.R;
import il.ac.tcb.st.finalassignment.databinding.ActivityMainBinding;
import il.ac.tcb.st.finalassignment.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private final String TAG ="loginFrag";
    private Context context;
    private FirebaseAuth firebaseAuth;
    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.LoginBtn.setOnClickListener(Login());
        binding.RegPageBtn.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registerFragment);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    View.OnClickListener Login(){
        return view -> {
            Log.d(TAG, "Login: "+binding.emailIN.getText().toString());
            firebaseAuth.signInWithEmailAndPassword( binding.emailIN.getText().toString(),binding.passIN.getText().toString())
                    .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: Success");
                                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.dashFragment);
                            }
                            else {
                                Log.w(TAG, "onComplete: failure", task.getException());
                            }
                        }
                    });

        };
    }
}