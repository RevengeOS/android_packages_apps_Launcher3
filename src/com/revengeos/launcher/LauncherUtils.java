package com.revengeos.launcher;

import static com.android.launcher3.util.Executors.MODEL_EXECUTOR;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager.Authenticators;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.android.launcher3.LauncherModel;
import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.android.launcher3.util.LooperExecutor;

import com.revengeos.launcher.OverlayCallbackImpl;

public class LauncherUtils {

    static final String KEY_SHOW_SEARCHBAR = "pref_show_searchbar";

    private static final long WAIT_BEFORE_RESTART = 250;

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

    public static void restart(final Context context) {
        ProgressDialog.show(context, null, context.getString(R.string.state_loading), true, false);
        MODEL_EXECUTOR.execute(() -> {
            try {
                Thread.sleep(WAIT_BEFORE_RESTART);
            } catch (Exception ignored) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        });
    }

}
