package github.hmasum52.androidstarterjava.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import github.hmasum52.androidstarterjava.model.MyLocation;
import lombok.Getter;

public class GPS {
    public static final String TAG = "[GPS] ";
    private final FragmentActivity activity;
    @Getter
    private LatLng deviceLatLng = null;
    private OnDeviceLocationFoundListener onDeviceLocationFoundListener;

    // see : https://developer.android.com/training/permissions/requesting
    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestPermissionLauncher;
    private final Geocoder geocoder;

    @Inject
    public GPS(FragmentActivity activity) {
        this.activity = activity;
        geocoder =  new Geocoder(activity, Locale.getDefault());
        requestPermissionLauncher = activity
                .registerForActivityResult(new ActivityResultContracts.RequestPermission()
                        , isGranted -> {
                            Log.d(TAG, "registerForActivityResult: isGranted: "+isGranted);
                            if (isGranted) {
                                Log.d(TAG, "DeviceLocationFinder: permission granted");
                                this.getDeviceLocation();
                            } else {
                                Log.d(TAG, "DeviceLocationFinder: permission not granted");// this.getLocationPermission();
                            }
                        });
        //this.getLocationPermission();
        Log.d(TAG, "DeviceLocationFinder: init done");
    }

    public interface OnDeviceLocationFoundListener {
        void onDeviceLocationFound(LatLng latLng);
    }

    public boolean isPermissionGranted() {
        return ContextCompat
                .checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isDeviceLocationFound() {
        return deviceLatLng != null;
    }

    public void requestDeviceLocation(OnDeviceLocationFoundListener onDeviceLocationFoundListener) {
        this.onDeviceLocationFoundListener = onDeviceLocationFoundListener;
        Log.d(TAG, "requestDeviceLocation: ");
        if(deviceLatLng!=null){
            onDeviceLocationFoundListener.onDeviceLocationFound(deviceLatLng);
            return;
        }
        Log.d(TAG, "requestDeviceLocation: device location is null");
        this.getDeviceLocation();
    }

    private void getDeviceLocation(){
        FusedLocationProviderClient mFusedLocationProviderClient
                = LocationServices.getFusedLocationProviderClient(activity);
        // Get the best and most recent location of the device, which may be null in rare
        // cases when a location is not available.
        try {
            if (isPermissionGranted()) {
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        if (location == null) {
                            Log.d(TAG, "onComplete:location is null");
                            return;
                        }
                        Log.d(TAG, "onComplete:location found");
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        deviceLatLng = currentLocation;
                        if (onDeviceLocationFoundListener != null)
                            onDeviceLocationFoundListener.onDeviceLocationFound(currentLocation);
                    } else {
                        Log.d(TAG, "getDeviceLocation onComplete: location not found");
                    }
                });
            } else {
                this.getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: "+e.getMessage());
        }
    }

    /**
     * get the user permission to access device location
     */
    public void getLocationPermission() {
        if (!isPermissionGranted())
            // get the location permission from user
            // this will prompt user a dialog to give the location permission
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public MyLocation getLocation(LatLng latLng) throws Exception{
        List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

        if(addressList == null || addressList.isEmpty()){
            throw new Exception("No address found for the given latlng");
        }

        Address address = addressList.get(0);
        StringBuilder addressText = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressText.append(address.getAddressLine(i)).append(", ");
        }
        return new MyLocation(
                addressText.toString(),
                address.getLocality(),
                address.getSubAdminArea(),
                address.getAdminArea(),
                address.getCountryName(),
                address.getCountryCode(),
                latLng.latitude,
                latLng.longitude
        );
    }
}


