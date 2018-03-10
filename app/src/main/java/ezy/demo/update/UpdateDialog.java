package ezy.demo.update;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import ezy.boost.update.IUpdateAgent;
import ezy.boost.update.UpdateInfo;

/**
 * 作者：NeilLee on 2017/12/15 19:31.
 * 邮箱：cnneillee@163.com
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private TextView tvContent, tvNegative, tvPositive;
    private UpdateInfo mInfo;
    private IUpdateAgent agent;

    public UpdateDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_update);
        initViews();
    }

    private void initViews() {
        tvContent = (TextView) findViewById(R.id.content);
        tvNegative = (TextView) findViewById(R.id.negative);
        tvPositive = (TextView) findViewById(R.id.positive);
        tvNegative.setOnClickListener(this);
        tvPositive.setOnClickListener(this);
    }

    private void setContent(String content) {
        tvContent.setText(content);
    }

    public void setAgent(IUpdateAgent agent) {
        this.agent = agent;
        setInfo(agent.getInfo());
    }

    private void setInfo(UpdateInfo info) {
        mInfo = info;
        setContent(mInfo.updateContent);
        if (mInfo.isForce) {
            tvNegative.setText("退出应用");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.negative:
                if (mInfo.isForce) {
                    System.exit(0);
                } else {
                    agent.ignore();
                    this.dismiss();
                }
                break;
            case R.id.positive:
                agent.update();
                break;
        }
    }
}
