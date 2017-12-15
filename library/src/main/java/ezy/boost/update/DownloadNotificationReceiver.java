package ezy.boost.update;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

/**
 * 作者：NeilLee on 2017/12/15 13:51.
 * 邮箱：cnneillee@163.com
 */

public class DownloadNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String DOWNLOADED_APK_MD5 = "downloaded_apk_md5";

    public static final String ACTION_CLICKED = "notification_clicked";
    public static final String ACTION_CANCELED = "notification_cancelled";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra(NOTIFICATION_ID, -1);
        String apkMD5 = intent.getStringExtra(DOWNLOADED_APK_MD5);

        if (type != -1 || TextUtils.isEmpty(apkMD5)) {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancel(type);
            }
        }

        if (ACTION_CLICKED.equals(action)) {
            // 处理点击事件
            File apkFile = new File(context.getExternalCacheDir(), apkMD5 + ".apk");
            if (apkFile.exists()) {
                UpdateUtil.install(context, apkFile, true);
            } else {
                Toast.makeText(context, R.string.toast_error_apk_file_not_exit, Toast.LENGTH_SHORT).show();
            }
        }

        if (ACTION_CANCELED.equals(action)) {
            // 处理滑动清除和点击删除事件
            Toast.makeText(context, R.string.toast_remove_notification_after_downloaded, Toast.LENGTH_SHORT).show();
        }
    }
}