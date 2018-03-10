package ezy.demo.update;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import ezy.boost.update.ICheckAgent;
import ezy.boost.update.IUpdateAgent;
import ezy.boost.update.IUpdateChecker;
import ezy.boost.update.IUpdatePrompter;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;
import ezy.boost.update.UpdateUtil;

/**
 * 作者：NeilLee on 2017/12/15 18:54.
 * 邮箱：cnneillee@163.com
 */

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {
    String mCheckUrl = "http://client.waimai.baidu.com/message/updatetag";

    String mUpdateUrl = "http://dldir1.qq.com/dmpt/apkSet/qqcomic_android_dm2102.apk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initViews();
    }

    private void initViews() {
        Button btnNormal = (Button) findViewById(R.id.btn_normal);
        Button btnForce = (Button) findViewById(R.id.btn_force);
        Button btnSilent = (Button) findViewById(R.id.btn_silent);
        Button btnClearCache = (Button) findViewById(R.id.btn_clear_cache);
        btnNormal.setOnClickListener(this);
        btnForce.setOnClickListener(this);
        btnSilent.setOnClickListener(this);
        btnClearCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                normalUpdate();
                break;
            case R.id.btn_force:
                forceUpdate();
                break;
            case R.id.btn_silent:
                silentUpdate();
                break;
            case R.id.btn_clear_cache:
                clearCache();
                break;
        }
    }

    private void clearCache() {
        UpdateUtil.clean(this);
        Toast.makeText(this, "cleared", Toast.LENGTH_LONG).show();
    }

    private void silentUpdate() {
        UpdateManager.create(this)
                .setChecker(new IUpdateChecker() {
                    @Override
                    public void check(String url, ICheckAgent agent) {
                        try {
                            Thread.sleep(500);
                            UpdateInfo info = UpdateInfo.parse("{\"hasUpdate\":true,\"isSilent\":true,\"selfHandleDownloadPrompt\":false,\"versionName\":\"1.0\",\"versionCode\":1,\"url\":\"http://download.nangua.com/xxx.apk\",\"md5\":\"fdawfdasfefdafda\",\"title\":\"APP新版上线啦!\",\"msg\":\"本次更新了如下内容\",\"isForce\":true,\"isMD5Ignorable\":true}");
                            info.isSilent = true;
                            info.isMD5Ignorable = true;
                            info.url = mUpdateUrl;
                            info.updateContent = "本次更新了如下内容：\\n1.静默更新APK有更新确认弹窗；\\n2.下载时，不提示进度，后台静默下载\\n3.下载完成后直接弹出系统安装";
                            agent.setInfo(info);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setPrompter(new IUpdatePrompter() {
                    @Override
                    public void prompt(IUpdateAgent agent) {
                        UpdateDialog dialog = new UpdateDialog(DemoActivity.this);
                        dialog.setAgent(agent);
                        dialog.show();
                    }
                })
                .check();
    }

    private void forceUpdate() {
        UpdateManager.create(this)
                .setChecker(new IUpdateChecker() {
                    @Override
                    public void check(String url, ICheckAgent agent) {
                        try {
                            Thread.sleep(500);
                            UpdateInfo info = UpdateInfo.parse("{\"hasUpdate\":true,\"isSilent\":false,\"selfHandleDownloadPrompt\":false,\"versionName\":\"1.0\",\"versionCode\":1,\"url\":\"http://download.nangua.com/xxx.apk\",\"md5\":\"fdawfdasfefdafda\",\"title\":\"APP新版上线啦!\",\"msg\":\"本次更新了如下内容\",\"isForce\":true,\"isMD5Ignorable\":true}");
                            info.isForce = true;
                            info.isMD5Ignorable = true;
                            info.url = mUpdateUrl;
                            info.updateContent = "本次更新了如下内容：\n1.强制更新APK有更新确认弹窗；\n2.下载时，弹窗提示下载进度\n3.下载完成后关闭APP，直接弹出系统安装";
                            agent.setInfo(info);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setPrompter(new IUpdatePrompter() {
                    @Override
                    public void prompt(IUpdateAgent agent) {
                        UpdateDialog dialog = new UpdateDialog(DemoActivity.this);
                        dialog.setAgent(agent);
                        dialog.show();
                    }
                })
                .check();
    }

    private void normalUpdate() {
        UpdateManager.create(this)
                .setChecker(new IUpdateChecker() {
                    @Override
                    public void check(String url, ICheckAgent agent) {
                        try {
                            Thread.sleep(500);
                            UpdateInfo info = UpdateInfo.parse("{\"hasUpdate\":true,\"isSilent\":false,\"selfHandleDownloadPrompt\":false,\"versionName\":\"1.0\",\"versionCode\":1,\"url\":\"http://download.nangua.com/xxx.apk\",\"md5\":\"fdawfdasfefdafda\",\"title\":\"APP新版上线啦!\",\"msg\":\"本次更新了如下内容\",\"isForce\":true,\"isMD5Ignorable\":true}");
                            info.isForce = false;
                            info.isSilent = false;
                            info.isMD5Ignorable = true;
                            info.url = mUpdateUrl;
                            info.updateContent = "本次更新了如下内容：\n1.正常更新APK有更新确认弹窗；\n2.下载时，通知栏提示下载进度\n3.下载完成后不关闭APP，弹出系统安装";
                            agent.setInfo(info);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setPrompter(new IUpdatePrompter() {
                    @Override
                    public void prompt(IUpdateAgent agent) {
                        UpdateDialog dialog = new UpdateDialog(DemoActivity.this);
                        dialog.setAgent(agent);
                        dialog.show();
                    }
                })
                .check();
    }
}
