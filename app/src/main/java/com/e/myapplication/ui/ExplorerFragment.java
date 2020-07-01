package com.e.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.domain.constants.BundleConstant;
import com.e.myapplication.R;
import com.e.myapplication.ui.adapter.ExplorerAdapter;
import com.e.myapplication.domain.models.FileModel;

import java.io.File;
import java.util.ArrayList;

public class ExplorerFragment extends Fragment implements OnItemClickListener {

    private FileModel mCurrentFileModel = null;
    private ExplorerAdapter mExplorerAdapter = null;
    private ArrayList<FileModel> fileModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explorer, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundleArguments();
        if (mCurrentFileModel != null) {
            RecyclerView mRecyclerview = view.findViewById(R.id.recyclerView);
            mExplorerAdapter = new ExplorerAdapter(this);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerview.setAdapter(mExplorerAdapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCurrentFileModel != null) {
            //Data fetching need to run on background thread, hope contains lot of files.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    retrieveFileSystem();
                }
            }, 500);
        }

    }

    private void retrieveFileSystem() {
        //This is background thread.
        //to prevent duplicate.
        fileModels.clear();
        if (mCurrentFileModel != null && mCurrentFileModel.getFile().isDirectory()) {
            for (File child : mCurrentFileModel.getFile().listFiles()) {
                fileModels.add(new FileModel(child));
            }
        }
        mExplorerAdapter.setNewList(fileModels);
    }

    private void getBundleArguments() {
        if (getArguments() != null && getArguments().containsKey(BundleConstant.FILE_OBJECT)) {
            mCurrentFileModel = getArguments().getParcelable(BundleConstant.FILE_OBJECT);
        }

    }

    @Override
    public void onItemClick(FileModel fileModel) {
        if (fileModel != null && fileModel.getFile().isDirectory()) {
            if (getActivity() instanceof MainActivity) {
                Fragment fragment=((MainActivity) getActivity()).getExplorerFragment(fileModel);
                ((MainActivity) getActivity()).replaceContentFragment(fragment,true);
            }
        }
    }
}
