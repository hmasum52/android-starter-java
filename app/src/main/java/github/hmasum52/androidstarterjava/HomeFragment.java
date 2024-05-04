package github.hmasum52.androidstarterjava;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import github.hmasum52.androidstarterjava.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    String name;

    FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            Log.d(TAG, "onCreate-> username: "+name);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String msg= "Hello "+name;
        binding.greetingEdt.setText(msg);

        List<String> tabNameList = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            String tabName = "Tab-"+i;
            tabNameList.add(tabName);
            fragments.add(new TabFragment(tabName));
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(this, fragments);
        binding.pager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> tab.setText(tabNameList.get(position))).attach();


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Snackbar.make(binding.getRoot(), tab.getText(), Snackbar.LENGTH_SHORT).setDuration(800).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
