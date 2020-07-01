package com.e.myapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.domain.models.FileModel;
import com.e.myapplication.ui.OnItemClickListener;
import com.e.myapplication.ui.viewholders.DirectoryViewHolder;
import com.e.myapplication.ui.viewholders.DummyViewHolder;
import com.e.myapplication.ui.viewholders.FileViewHolder;

import java.util.ArrayList;

public class ExplorerAdapter extends RecyclerView.Adapter {

    private final int FILE_TYPE_DIRECTORY = 0;
    private final int FILE_TYPE_FILE = 1;
    private final OnItemClickListener mOnItemClickListener;

    private ArrayList<FileModel> fileList = new ArrayList<>();

    public ExplorerAdapter(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == FILE_TYPE_DIRECTORY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_directory_view, parent, false);
            holder = new DirectoryViewHolder(itemView);
        } else if (viewType == FILE_TYPE_FILE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_view, parent, false);
            holder = new FileViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dummy_view, parent, false);
            holder = new DummyViewHolder(itemView);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DirectoryViewHolder) {
            ((DirectoryViewHolder) holder).onBind(fileList.get(position));
        } else if (holder instanceof FileViewHolder) {
            ((FileViewHolder) holder).onBind(fileList.get(position));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(fileList.get(position));
                }
            }
        });
    }

    public void setNewList(ArrayList<FileModel> fileList) {
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        return fileList.get(position).getFile().isDirectory() ? FILE_TYPE_DIRECTORY : FILE_TYPE_FILE;
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
