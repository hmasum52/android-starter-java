package github.hmasum52.androidstarterjava.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import github.hmasum52.androidstarterjava.R;
import github.hmasum52.androidstarterjava.databinding.FragmentMapBinding;
import github.hmasum52.androidstarterjava.util.GPS;

@AndroidEntryPoint
public class MapFragment extends Fragment implements OnMapReadyCallback {
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
    public void onResume() {
        super.onResume();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment == null){
            Log.e("MapFragment", "onResume: mapFragment is null");
            return;
        }
        try {
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            Log.e("MapFragment", "onResume: " + e.getMessage());
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) throws SecurityException {
        binding.fabMyLocation.setOnClickListener(v -> {
            gps.requestDeviceLocation(latLng -> {
                googleMap.setMyLocationEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            });
        });

    }
}
