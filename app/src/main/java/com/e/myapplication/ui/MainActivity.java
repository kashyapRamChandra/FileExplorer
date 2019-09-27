package com.e.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Environment;

import com.e.myapplication.BundleConstant;
import com.e.myapplication.R;
import com.e.myapplication.models.FileModel;
import com.e.myapplication.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileModel fileModel = new FileModel(Environment.getExternalStorageDirectory());

        replaceContentFragment(getExplorerFragment(fileModel));
    }

    public Fragment getExplorerFragment(FileModel fileModel) {
        ExplorerFragment fragment = new ExplorerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleConstant.FILE_OBJECT, fileModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void replaceContentFragment(Fragment fragment) {
        ActivityUtils.replaceContentFragment(getSupportFragmentManager(),
                R.id.activity_main_frame_layout_container,
                fragment,
                true);
    }
}
