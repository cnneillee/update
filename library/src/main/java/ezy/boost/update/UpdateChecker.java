/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ezy.boost.update;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements IUpdateChecker {

    final byte[] mPostData;

    private String mInfo;
    private UpdateError mError;

    public UpdateChecker() {
        mPostData = null;
    }

    public UpdateChecker(byte[] data) {
        mPostData = data;
    }

    @Override
    public void check(final String url, final ICheckAgent agent) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                doCheck(url);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (mError != null) {
                    agent.setError(mError);
                }else{
                    agent.setInfo(mInfo);
                }
            }
        }.execute();
    }

    private void doCheck(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Accept", "application/json");

            if (mPostData == null) {
                connection.setRequestMethod("GET");
                connection.connect();
            } else {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", Integer.toString(mPostData.length));
                connection.getOutputStream().write(mPostData);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                mInfo = UpdateUtil.readString(connection.getInputStream());
            } else {
                mError = new UpdateError(UpdateError.CHECK_HTTP_STATUS, "" + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            mError = new UpdateError(UpdateError.CHECK_NETWORK_IO);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}