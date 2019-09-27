package com.e.myapplication.utils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void replaceContentFragment(@NonNull FragmentManager fragmentManager,
                                              int frameLayouId,
                                              @NonNull Fragment fragment,
                                              boolean addToBackStack) {


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameLayouId, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();

    }
}
