package com.yuang.library.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: DownloadUtil
 * 类描述: DownloadManager 下载
 * 创建人: Yuang QQ:274122635
 * 创建时间: 2018/10/11 下午12:04
 */
public class DownloadUtil {
    private static DownloadUtil mDownloadUtil;
    private static HashMap<String, Long> downloadMap = new HashMap<String, Long>();
    private BootReceiver mReceiver;
    private List<Integer> enqueueIdList = new ArrayList();
    private DownloadManager mDownloadManager;
    private Activity context;

    public interface DownloadCallback {
        void onDownloadStart();

        void onDownloadComplete(File file);

        void onDownloadFail(String error);

        void onDownloadProgress(int progress);
    }

    public static DownloadUtil getInstance(Activity context) {
        if (mDownloadUtil == null) {
            mDownloadUtil = new DownloadUtil(context);
        }
        return mDownloadUtil;
    }

    public DownloadUtil(Activity context) {
        this.context = context;
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        try {
            mReceiver = new BootReceiver();
            context.getApplicationContext().registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception e) {
            Logg.d("DownloadManager Constructor:" + e.toString());
        }
    }

    /**
     * DownloadManager下载成功失败接收
     */
    public class BootReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context mContext, Intent intent) {
            long downloadCompletedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            // 检查是否是自己的下载队列 id, 有可能是其他应用的
            for (int i = 0; i < enqueueIdList.size(); i++) {
                int id = enqueueIdList.get(i);
                if (id != downloadCompletedId)
                    continue;
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(Long.parseLong(String.valueOf(id)));
                Iterator<Map.Entry<String, Long>> entries = downloadMap.entrySet().iterator();
                String key = null;
                while (entries.hasNext()) {
                    Map.Entry<String, Long> entry = entries.next();
                    if (entry.getValue() == Long.parseLong(String.valueOf(id)))
                        key = entry.getKey();
                }
                Cursor c = mDownloadManager.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        if (!TextUtils.isEmpty(key))
                            downloadMap.remove(key);
                        String oldPath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
//                        String newPath = oldPath.replace(".apk", "__s.apk");
//                        boolean isEx = false;
//                        try {
//                            File old = new File(oldPath);
//                            File nnew = new File(newPath);
//                            old.renameTo(nnew);
//                        } catch (Exception e) {
//                            isEx = true;
//                        }
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                            }
                        });
                        complete(Uri.parse("file://" + oldPath));
                    } else if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {
                        if (!TextUtils.isEmpty(key))
                            downloadMap.remove(key);
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    if (!TextUtils.isEmpty(key))
                        downloadMap.remove(key);
                }
                break;
            }
        }
    }


    /**
     * @param url                下载地址
     * @param title              DownloadManager通知的title 可为空
     * @param finish             开始下载后，是否关闭当前Activity
     * @param useDownloadManager 使用DownloadManager下载
     */
    public void download(String url, String title, final int id, final boolean finish, boolean useDownloadManager) {
        if (useDownloadManager)
            downloadManager(url, id, finish, title);
        else
            downloadCore(url, new DownloadCallback() {
                @Override
                public void onDownloadStart() {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
                            if (finish)
                                context.finish();
                        }
                    });
                }

                @Override
                public void onDownloadComplete(File file) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                    complete(Uri.fromFile(file));
                }

                @Override
                public void onDownloadFail(String error) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onDownloadProgress(int progress) {
//                        Log.d(Constants.LOG_TAG, "下载进度" + progress);
                }
            });
    }

    /**
     * 使用DownloadManager下载
     */
    private void downloadManager(String url, int id, final boolean finish, String title) {
        if (downloadMap.containsKey(url)){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "正在下载，请耐心等候...", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        String fileName = getFileName(url);
        try {
            File nnew = new File(Environment.getExternalStorageDirectory(), "adDownloads/" + fileName);
            if (nnew.exists()) {
                Logg.i("install " + nnew.getAbsolutePath());
                complete(Uri.fromFile(nnew));
                return;
            }
        } catch (Exception e) {
        }
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
//        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);//默认所有网络下载
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setDestinationInExternalPublicDir("adDownloads", fileName);
        req.setTitle(TextUtils.isEmpty(title) ? fileName : title);
        req.setDescription("下载完后请点击打开");
        req.setMimeType("application/vnd.android.package-archive");
        long enqueueId = mDownloadManager.enqueue(req);
        downloadMap.put(url, enqueueId);
        //pair:[id_adtype,callback]

        enqueueIdList.add(Integer.parseInt(String.valueOf(enqueueId)));

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
                if (finish)
                    context.finish();
            }
        });
    }

    /**
     * fileName 可为空
     */
    private void downloadCore(final String url, final DownloadCallback callback) {
        new Thread() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                callback.onDownloadStart();

                for (int tryies = 0; tryies < 4; tryies++) {
                    Pair<Boolean, File> pair = url2file(url, callback);
                    if (pair.first)
                        return;
                    final File f = pair.second;
                    FileLock fileL = null;
                    RandomAccessFile raf = null;
                    try {
                        File dfile = new File(f.getAbsoluteFile() + "___lock");
                        long offset = 0;
                        if (dfile.exists()) {
                            offset = dfile.length();
                        }

                        raf = new RandomAccessFile(dfile, "rw");
                        FileChannel fileC = raf.getChannel();
                        fileL = fileC.tryLock();

                        boolean fulldownload = false;

                        URL u = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(10000);
                        conn.setReadTimeout(60000);
                        conn.setDoInput(true);
                        conn.setDoOutput(false);
                        // conn.setRequestProperty("User-Agent", "AssistantDownloader");

                        if (offset > 0) {
                            conn.setRequestProperty("RANGE",
                                    String.format("bytes=%d-", offset));
                        }

                        int progress = 0;

                        String error = null;
                        conn.connect();
                        int code = conn.getResponseCode();
                        if (code == 200 || code == 206) {
                            int length = conn.getContentLength();
                            InputStream is = conn.getInputStream();
                            raf.seek(offset);

                            progress = (int) (100 * offset / (length + offset));
                            callback.onDownloadProgress(progress);

                            int len = 0;
                            try {
                                byte[] buf = new byte[2048];
                                int i = 0;
                                do {
                                    i = is.read(buf);
                                    if (i > 0) {
                                        raf.write(buf, 0, i);
                                        len += i;
                                        int np = (int) (100 * (offset + len) / (length + offset));
                                        if (np > progress) {
                                            progress = np;
                                            callback.onDownloadProgress(progress);
                                        }
                                    } else if (i < 0) {
                                        break;
                                    }
                                } while (true);
                            } catch (Exception e) {
                                error = e.getMessage();
                            }

                            raf.close();
                            is.close();

                            if (length == len) {
                                fulldownload = true;
                            } else {
                                if (error == null) {
                                    error = String.format("%d:%d", length, len);
                                }
                            }

                        } else if (code == 416) {
                            fulldownload = true;
                        } else {
                            error = "status code=" + String.valueOf(code);
                        }

                        if (fulldownload) {
                            /**
                             * 记录下载完成
                             */
                            dfile.renameTo(f);
                            callback.onDownloadComplete(f);
                            return;
                        } else {
                            if (tryies == 3) {
                                if (error == null) {
                                    error = "unknown broken";
                                }
                                callback.onDownloadFail(error);
                                break;
                            }
                        }
                    } catch (OverlappingFileLockException e2) {

                    } catch (Exception e) {
                        callback.onDownloadFail(e.getMessage());
                        break;
                    } finally {
                        if (raf != null) {
                            try {
                                raf.close();
                            } catch (IOException e) {
                                // e.printStackTrace();
                            }

                            raf = null;
                        }
                        if (fileL != null) {
                            try {
                                fileL.release();
                            } catch (IOException e) {
                                // e.printStackTrace();
                            }

                            try {
                                fileL.channel().close();
                            } catch (IOException e) {
                                // e.printStackTrace();
                            }

                            fileL = null;
                        }

                    }
                }
            }
        }.start();

    }

    private void complete(Uri uri) {
        promptInstall(uri);
    }

    private Pair<Boolean, File> url2file(String url, DownloadCallback callback) {

        File mDownloadDir = new File(Environment.getExternalStorageDirectory(), "adDownloads");
        String filename = getFileName(url);
        if (!mDownloadDir.exists()) {
            mDownloadDir.mkdir();
        }
        File apkfile = new File(mDownloadDir, filename);
        if (apkfile.exists()) {
            callback.onDownloadComplete(apkfile);
        }
        Pair<Boolean, File> pair = new Pair<Boolean, File>(apkfile.exists(), apkfile);
        return pair;
    }

    private String getFileName(String url) {
        String filename = EncodeProvider.encodeHex(DigestProvider.digestText("MD5", url), false) + ".apk";
        return filename;
    }

    private void promptInstall(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
