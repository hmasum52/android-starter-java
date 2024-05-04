package github.hmasum52.androidstarterjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import github.hmasum52.androidstarterjava.databinding.FragmentTabBinding;

public class TabFragment extends Fragment {
    private final String tabName;
    FragmentTabBinding binding;

    public TabFragment(){ tabName="Default";}

    public TabFragment(String tabName){
        this.tabName = tabName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.textView.setText(tabName);
    }
}
