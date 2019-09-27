package com.e.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class FileModel implements Parcelable {
    private File mFile;

    public FileModel(File mFile) {
        this.mFile = mFile;
    }

    public File getFile() {
        return mFile;
    }


    protected FileModel(Parcel in) {
    }

    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        @Override
        public FileModel createFromParcel(Parcel in) {
            return new FileModel(in);
        }

        @Override
        public FileModel[] newArray(int size) {
            return new FileModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
