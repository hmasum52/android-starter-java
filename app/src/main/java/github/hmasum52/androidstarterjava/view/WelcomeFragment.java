package github.hmasum52.androidstarterjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import github.hmasum52.androidstarterjava.R;
import github.hmasum52.androidstarterjava.databinding.FragmentWelcomeBinding;


public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.nextBtn.setOnClickListener(v -> {
            String name = binding.nameEdt.getText().toString();
            if(name.isEmpty()){
                binding.nameEdt.setError("Please enter your name");
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("name", name);

            NavHostFragment.findNavController(this).navigate(R.id.action_welcomeFragment_to_homeFragment, bundle);
        });
    }
}