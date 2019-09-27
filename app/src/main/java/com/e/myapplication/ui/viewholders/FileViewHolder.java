package com.e.myapplication.ui.viewholders;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.models.FileModel;
import com.e.myapplication.utils.MemoryCalc;

public class FileViewHolder extends RecyclerView.ViewHolder {


    private final TextView mNameTextView;
    private final TextView mDirDetailsTextView;
    private final ImageView mFileImageView;

    public FileViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameTextView = itemView.findViewById(R.id.dir_name_text_view);
        mDirDetailsTextView = itemView.findViewById(R.id.dir_details_text_view);
        mFileImageView=itemView.findViewById(R.id.fileImageView);
    }

    public void onBind(FileModel fileModel) {
        if (fileModel == null)
            return;
        mNameTextView.setText(fileModel.getFile().getName());
        mDirDetailsTextView.setText("Size :" + MemoryCalc.getFileSize(fileModel.getFile().length()));
        if(fileModel.getFile().getName().endsWith(".png")  || fileModel.getFile().getName().endsWith(".jpg") ) {
            Uri uri = Uri.fromFile(fileModel.getFile());
            mFileImageView.setImageURI(uri);
        }
    }
}
