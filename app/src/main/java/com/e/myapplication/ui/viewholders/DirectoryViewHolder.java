package com.e.myapplication.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.models.FileModel;

public class DirectoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView mNameTextView;
    private final TextView mDirDetailsTextView;

    public DirectoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameTextView = itemView.findViewById(R.id.dir_name_text_view);
        mDirDetailsTextView = itemView.findViewById(R.id.dir_details_text_view);

    }

    public void onBind(FileModel fileModel) {
        if (fileModel == null)
            return;
        mNameTextView.setText(fileModel.getFile().getName());
        long count =0;
        if (fileModel.getFile().listFiles() != null) {
            count=fileModel.getFile().listFiles().length;
        }

        mDirDetailsTextView.setText("Files count:" +count);

    }
}
