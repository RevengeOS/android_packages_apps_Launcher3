package com.revengeos.launcher;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager.Authenticators;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.android.launcher3.R;
import com.android.launcher3.Utilities;

import com.revengeos.launcher.OverlayCallbackImpl;

public class LauncherUtils {

    static final String KEY_SHOW_SEARCHBAR = "pref_show_searchbar";

    public static boolean hasPackageInstalled(Context context, String pkgName) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(pkgName, 0);
            return ai.enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean showQSB(Context context) {
        SharedPreferences prefs = Utilities.getPrefs(context.getApplicationContext());
        if (!hasPackageInstalled(context, OverlayCallbackImpl.SEARCH_PACKAGE)) {
            return false;
        }
        return prefs.getBoolean(KEY_SHOW_SEARCHBAR, true);
    }

}
