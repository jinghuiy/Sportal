package com.parse.starter;


import android.os.Parcel;
import android.os.Parcelable;

public class GPAItem implements Parcelable {
    private String mName;
    private double mMarks;
    private double mTotal;
    private double mWeightage;

    public GPAItem(String mName, double mMarks, double mTotal, double mWeightage) {
        this.mName = mName;
        this.mMarks = mMarks;
        this.mTotal = mTotal;
        this.mWeightage = mWeightage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmMarks() {
        return mMarks;
    }

    public void setmMarks(double mMarks) {
        this.mMarks = mMarks;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    public double getmWeightage() {
        return mWeightage;
    }

    public void setmWeightage(double mWeightage) {
        this.mWeightage = mWeightage;
    }

    protected GPAItem(Parcel in) {
        mName = in.readString();
        mMarks = in.readDouble();
        mTotal = in.readDouble();
        mWeightage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeDouble(mMarks);
        dest.writeDouble(mTotal);
        dest.writeDouble(mWeightage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GPAItem> CREATOR = new Parcelable.Creator<GPAItem>() {
        @Override
        public GPAItem createFromParcel(Parcel in) {
            return new GPAItem(in);
        }

        @Override
        public GPAItem[] newArray(int size) {
            return new GPAItem[size];
        }
    };
}