package com.sfl.sflassignment.ui.data;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class BranchData implements Parcelable {
    private String title;
    private String address;
    private Location location;
    private String phoneNumber;
    private Map<String, String> workHours;

    public BranchData() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, String> getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Map<String, String> workHours) {
        this.workHours = workHours;
    }

    //region parcelable
    protected BranchData(Parcel in) {
        title = in.readString();
        address = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        phoneNumber = in.readString();
        workHours = in.readHashMap(getClass().getClassLoader());
    }

    public static final Creator<BranchData> CREATOR = new Creator<BranchData>() {
        @Override
        public BranchData createFromParcel(Parcel in) {
            return new BranchData(in);
        }

        @Override
        public BranchData[] newArray(int size) {
            return new BranchData[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(address);
        dest.writeParcelable(location, flags);
        dest.writeString(phoneNumber);
        dest.writeMap(workHours);
    }
    //endregion
}
