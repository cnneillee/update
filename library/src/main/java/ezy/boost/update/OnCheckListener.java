package ezy.boost.update;

/**
 * 作者：NeilLee on 2017/12/12 20:49.
 * 邮箱：cnneillee@163.com
 */

public interface OnCheckListener {
    void onSuccess(String source);

    void onError(UpdateError error);

    void onFinish(UpdateInfo info);
}
