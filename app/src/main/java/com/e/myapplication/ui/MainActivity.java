package com.e.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.e.myapplication.domain.constants.BundleConstant;
import com.e.myapplication.R;
import com.e.myapplication.domain.models.FileModel;
import com.e.myapplication.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {
    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static Intent getIntentWithNewTask(Context context) {
        {
            Intent intent = getIntent(context);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            return intent;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileModel fileModel = new FileModel(Environment.getExternalStorageDirectory());

        replaceContentFragment(getExplorerFragment(fileModel), false);
    }

    public Fragment getExplorerFragment(FileModel fileModel) {
        ExplorerFragment fragment = new ExplorerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleConstant.FILE_OBJECT, fileModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void replaceContentFragment(Fragment fragment, boolean addToBackStack) {
        ActivityUtils.replaceContentFragment(getSupportFragmentManager(),
                R.id.activity_main_frame_layout_container,
                fragment,
                addToBackStack);
    }
}
