package github.hmasum52.androidstarterjava.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import github.hmasum52.androidstarterjava.databinding.FragmentMapBinding;
import github.hmasum52.androidstarterjava.util.GPS;

@AndroidEntryPoint
public class MapFragment extends Fragment {
    @Inject
    GPS gps;

    FragmentMapBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gps.requestDeviceLocation(latLng -> {
            String msg = "Your current location: " + latLng.latitude + ", " + latLng.longitude;
            binding.tv.setText(msg);
        });
    }

}
