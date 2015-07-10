package com.parse.starter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Subject implements Parcelable {
    private String name;
    private double subjectGP;
    private ArrayList<GPAItem> mGPAItems;
    private double mPercentage;

    public Subject(String name, double subjectGP, ArrayList<GPAItem> mGPAItems, double mPercentage) {
        this.name = name;
        this.subjectGP = subjectGP;
        this.mGPAItems = mGPAItems;
        this.mPercentage = mPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSubjectGP() {
        return subjectGP;
    }

    public void setSubjectGP(double subjectGP) {
        this.subjectGP = subjectGP;
    }

    public ArrayList<GPAItem> getmGPAItems() {
        return mGPAItems;
    }

    public void setmGPAItems(ArrayList<GPAItem> mGPAItems) {
        this.mGPAItems = mGPAItems;
    }

    public double getmPercentage() {
        return mPercentage;
    }

    public void setmPercentage(double mPercentage) {
        this.mPercentage = mPercentage;
    }

    protected Subject(Parcel in) {
        name = in.readString();
        subjectGP = in.readDouble();
        if (in.readByte() == 0x01) {
            mGPAItems = new ArrayList<GPAItem>();
            in.readList(mGPAItems, GPAItem.class.getClassLoader());
        } else {
            mGPAItems = null;
        }
        mPercentage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(subjectGP);
        if (mGPAItems == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mGPAItems);
        }
        dest.writeDouble(mPercentage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}