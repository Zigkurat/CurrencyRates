package com.sfl.sflassignment.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RateData implements Parcelable {
    private float buy;
    private float sell;

    public RateData() {}

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public float getSell() {
        return sell;
    }

    public void setSell(float sell) {
        this.sell = sell;
    }

    protected RateData(Parcel in) {
        buy = in.readFloat();
        sell = in.readFloat();
    }

    public static final Creator<RateData> CREATOR = new Creator<RateData>() {
        @Override
        public RateData createFromParcel(Parcel in) {
            return new RateData(in);
        }

        @Override
        public RateData[] newArray(int size) {
            return new RateData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(buy);
        dest.writeFloat(sell);
    }
}
