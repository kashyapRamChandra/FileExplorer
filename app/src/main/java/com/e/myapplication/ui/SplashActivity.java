package com.e.myapplication.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.e.myapplication.R;
import com.e.myapplication.databinding.ActivitySplashBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFlags();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        updateUI();

    }

    private void updateUI() {
        String text = "<font color=#FF9933>Made </font> <font color=#FFFFFF> in </font> <font color=#138808>India</font>";
        mBinding.madeInIndiaTextview.setText(Html.fromHtml(text));

    }

    protected void setFlags() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onStart() {
        super.onStart();
        {
            startTimer();
        }
    }


    private void startTimer() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkAndRequestPermissions();
                } else
                    onPermissionGranted();

            }
        }.start();

    }


    private void launchScreens() {
        Intent intent;
        intent = MainActivity.getIntentWithNewTask(this);
        startActivitiesAndFinish(intent);


    }

    private void startActivitiesAndFinish(Intent intent) {

        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }


    private void checkAndRequestPermissions() {
        List<String> listOfPermissionsNeedToAsk = new ArrayList<>();
        //prepare list of permissions not granted.
        for (String perm : permissionsRequired) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                //Add these permissions.
                listOfPermissionsNeedToAsk.add(perm);
            }
        }

        if (listOfPermissionsNeedToAsk.size() > 0)
            ActivityCompat.requestPermissions(this, listOfPermissionsNeedToAsk.toArray(new String[listOfPermissionsNeedToAsk.size()]), PERMISSION_CALLBACK_CONSTANT);
        else
            onPermissionGranted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            // Gather permission grant results
            for (int i = 0; i < grantResults.length; i++) {
                // Add only permissions which are denied
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            // Check if all permissions are granted
            if (deniedCount == 0) {
                // Proceed ahead with the app
                onPermissionGranted();
            }
            // Atleast one or all permissions are denied
            else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    // permission is denied (this is the first time, when "never ask again" is not checked)
                    // so ask again explaining the usage of permission
                    // shouldShowRequestPermissionRationale will return true
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        // Show dialog of explanation
                        showDialog("", "This app needs Location and Storage permissions to work wihout any issues and problems.",
                                "Yes, Grant permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkAndRequestPermissions();
                                    }
                                },
                                "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }, false);
                    }
                    //permission is denied (and never ask again is  checked)
                    //shouldShowRequestPermissionRationale will return false
                    else {
                        // Ask user to go to settings and manually allow permissions
                        showDialog("", "You have denied some permissions to the app. Please allow all permissions at [Setting] > [Permissions] screen",
                                "Go to Settings",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        // Go to app settings
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }, false);
                        break;
                    }
                }
            }
        }
    }

    public AlertDialog showDialog(String title, String msg, String positiveLabel,
                                  DialogInterface.OnClickListener positiveOnClick,
                                  String negativeLabel, DialogInterface.OnClickListener negativeOnClick,
                                  boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_PERMISSION_SETTING) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                //Got Permission
//                onPermissionGranted();
//            }
//        }
//    }


    private void onPermissionGranted() {
        launchScreens();
    }


}
