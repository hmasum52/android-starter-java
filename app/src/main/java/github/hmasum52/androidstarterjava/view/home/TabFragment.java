package github.hmasum52.androidstarterjava.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import github.hmasum52.androidstarterjava.api.FakeStoreApi;
import github.hmasum52.androidstarterjava.databinding.FragmentTabBinding;

@AndroidEntryPoint
public class TabFragment extends Fragment {
    private String tabName;
    FragmentTabBinding binding;

    @Inject
    FakeStoreApi api;

    public TabFragment(){ tabName="Default";}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString("tabName");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        api.getProducts(tabName).observe(getViewLifecycleOwner(), products -> {
            // check if the products are not null
            StringBuilder productsTitle = new StringBuilder();
            if(products == null) {
                String msg = "No products found";
                binding.textView.setText(msg);
                return;
            }
            // loop through the products and log the title
            products.forEach(product -> {
                productsTitle.append(product.getId()).append("-")
                        .append(product.getTitle()).append("\n\n");
            });
            binding.textView.setText(productsTitle.toString());

        });
    }
}
