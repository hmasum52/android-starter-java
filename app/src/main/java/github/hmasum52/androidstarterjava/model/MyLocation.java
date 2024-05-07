package github.hmasum52.androidstarterjava.model;

import androidx.annotation.NonNull;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Parcel // for passing this object through intent
public class MyLocation {
    String addressLine;
    String locality;
    String subAdminArea;
    String adminArea;
    String countryName;
    String countryCode;
    double latitude;
    double longitude;

    public String getFullAddress(){
        String fullAddress = "";
        if(addressLine!=null)
            fullAddress += addressLine;
        if(locality!=null)
            fullAddress += ", " + locality;
        if(subAdminArea!=null)
            fullAddress += ", " + subAdminArea;
        if(adminArea!=null)
            fullAddress += ", " + adminArea;
        if(countryName!=null)
            fullAddress += ", " + countryName;
        return fullAddress;
    }

    @NonNull
    @Override
    public String toString() {
        return getFullAddress();
    }
}
